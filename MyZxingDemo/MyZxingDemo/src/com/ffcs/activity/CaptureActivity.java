package com.ffcs.activity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import com.ffcs.activity.R;
import com.ffcs.camera.CameraManager;
import com.ffcs.decoding.CaptureActivityHandler;
import com.ffcs.decoding.InactivityTimer;
import com.ffcs.mylocation.MyLocationManager;
import com.ffcs.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private TextView txtResult;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;

	/** Called when the activity is first created. onCreate()/onResume()��ɽ������*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��ʼ�� CameraManager
		CameraManager.init(getApplication());
		// �Զ����view��ʵ����ɨ��ʱ�����ˢ�£�����ʶ���һЩ���ݣ��綨λ�ĵ�ص���ʾ�ڽ����ϡ�
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//������µ��Ƿ��ؼ�������û���ظ�
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			//Activity�л�����
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}
	//�ж����activity����ʲô�����ģ�����������app������Ҳ�������û�ֱ��������
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);//�������شӵײ�Ӳ����ȡ�����ȡ����ͼ��
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;   //���뷽ʽ
		characterSet = null;    //���뷽ʽ
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	// ��ʼ������ͷ
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);//���ڽ���ɨ����봦��
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	// �����ά����
	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();//��ʱ�߳�
		viewfinderView.drawResultBitmap(barcode);//�����λͼ
		playBeepSoundAndVibrate();
/*		txtResult.setText(obj.getBarcodeFormat().toString() + ":"
				+ obj.getText());*/
		MyLocationManager lmg = new MyLocationManager(this);
		Date now = new Date();
		String format = new String("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String barcodeInfo = obj.getText() + "|" + sdf.format(now) + "|" + lmg.getLat() + "," + lmg.getLng();
		
		// ��ת��ʾ���
				/*Intent intent = new Intent();
				intent.setClass(CaptureActivity.this, ResultActivity.class);
				CaptureActivity.this.startActivity(intent);*/
		
		if(lmg.getLat()!= 0.0){
			sendInfo(barcodeInfo);//������Ϣ
//			lmg.setLat(0.0);
//			lmg.setLng(0.0);
		}else{
			Toast.makeText(this, "ǩ��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
		}		
		this.finish();
	}

	// ���Ͷ�ά���ж�ȡ����Ϣ
	private void sendInfo(String barcodeInfo) {
//		String urlPost = "http://xiagang.toxm.cn/cip/admin/public/access/cip_sectionInspect/sectionInspect_add.do"; // POST�����URL
		String urlPost = "http://1.1.1.1:8080/cip/admin/public/access/cip_sectionInspect/sectionInspect_add.do";
		HttpPost httpPostRequest = new HttpPost(urlPost); // ����HttpPost����
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>(); // ������Ų�����ArrayList
		String[] sourceStr = barcodeInfo.split("\\|"); //��ÿ�����ߴ����зֽ⣬�����ַ�������
		String[] paramsStr = { "name", "businessId", "signDate", "imgPath" };
//		if (sourceStr.length != paramsStr.length) {
//			Toast.makeText(this, "��ά����Ϣ��ʽ��ƥ�䣡", Toast.LENGTH_SHORT).show();
//			return;
//		}
		// ����post����(������ֵ)
		for (int i = 0; i < sourceStr.length; i++)
			httpParams.add(new BasicNameValuePair(paramsStr[i], sourceStr[i]));

		try {
			httpPostRequest.setEntity(new UrlEncodedFormEntity(httpParams,HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpPostRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// ȡ�÷��ص��ַ���
				Toast.makeText(this, "ǩ���ɹ���", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "ǩ��ʧ�ܣ������³��ԣ�", Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) { // ���񲢴�ӡ�쳣
			// EditText etPost = (EditText)findViewById(R.id.etPost);
			// //���EditText����
			// etPost.setText("���ӳ���:"+e.getMessage()); //ΪEditText���ó�����Ϣ
			Toast.makeText(this, "���ӳ���ǩ��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	// ��ʼ��������Ч
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	// �����������𶯷���
	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}