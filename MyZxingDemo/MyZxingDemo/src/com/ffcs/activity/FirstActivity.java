package com.ffcs.activity;

import com.ffcs.activity.CaptureActivity;
import com.ffcs.activity.FirstActivity;
import com.ffcs.activity.R;
import com.ffcs.activity.FirstActivity.MyButtonListener;
import com.ffcs.grid.DragGridView;
import com.ffcs.mylocation.MyLocationManager;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

//������Ҫ������һ������GridViewActivity��Activity�����Զ���������ֻҪ��������Ϊ��һ������Ϳ�����
public class FirstActivity extends Activity {
	/** Called when the activity is first created. */
	// ����ť���������
	private Button btn_saomiao = null;
	private Button btn_exit = null;
	Context context = this;

	// ��д���൱�е�onCreate������Activity��һ������ʱ������������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ΪActivity���ò��ֹ����ļ�
		setContentView(R.layout.first);
		// �������д����Ǹ��ݿؼ���ID���õ��ؼ�����
		// TextView myTextView = (TextView) findViewById(R.id.myTextView);
		btn_saomiao = (Button) findViewById(R.id.btn_saomiao);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		// Ϊ��ť�������ü���������
		btn_saomiao.setOnClickListener(new MyButtonListener());
		btn_exit.setOnClickListener(new MyButtonListener());

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//������µ��Ƿ��ؼ�������û���ظ�
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			return false;
		}
		return false;
	}
	class MyButtonListener implements OnClickListener {
		// ���ɸ���Ķ��󣬲�����ע�ᵽ�ؼ��ϡ�����ÿؼ����û����£��ͻ�ִ��onClick����
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_saomiao) {
				// ����һ��Intent����
				Intent intent = new Intent();
				// ����Intent����Ҫ������Activity
				intent.setClass(FirstActivity.this, GridLayoutActivity.class);
				// ͨ��Intent������������һ��Activity
				FirstActivity.this.startActivity(intent);

				// ��ӽ����л�Ч����ע��ֻ��Android��2.0(SdkVersion�汾��Ϊ5)�Ժ�İ汾��֧��
//				int version = Integer.valueOf(android.os.Build.VERSION.SDK);
//				if (version >= 5) {
//					overridePendingTransition(R.anim.push_left_in,
//							R.anim.push_left_out); // ��Ϊ�Զ���Ķ���Ч������������Ϊϵͳ�Ķ���Ч��
//				}
			} else
				try {
					finish();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
}