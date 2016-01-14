package com.ffcs.activity;

import java.util.ArrayList;
import java.util.List;

import com.ffcs.activity.R;
import com.ffcs.grid.GridAdapter;
import com.ffcs.grid.GridInfo;
import com.ffcs.listenerHelper.MyOnGestureListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.Toast;

public class GridLayoutActivity extends Activity {
	private GridView gridview;
	
	private List<GridInfo> list;
	
	private GridAdapter adapter;

	public ViewGroup container_gridview;
	
	public ViewGroup container_2;
	
	private GestureDetector gestureDetector;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gridlayout);
		//���ﶨ�����������棬container_2��container_gridview
		container_2 = (ViewGroup) findViewById(R.id.container_2);
		
		container_gridview = (ViewGroup) findViewById(R.id.container_gridview);
		
		//�����Ҫ�Ǽ�����Ļ�Ļ����¼�����������������Ļ�л�
		MyOnGestureListener myOnGestureListener = new MyOnGestureListener(this);
		
		gestureDetector = new GestureDetector(this,myOnGestureListener);
		
		gridview = (GridView) findViewById(R.id.gridview);
		
		//��ʼ������
		list = new ArrayList<GridInfo>();
		list.add(new GridInfo("�γ�Ѳ��", 1));
		list.add(new GridInfo("�����ϴ�", 2));
		list.add(new GridInfo("��ͼ��λ", 3));
		list.add(new GridInfo("���߻Ự", 4));
		list.add(new GridInfo("����", 5));
		list.add(new GridInfo("�����ʼ�", 6));
		list.add(new GridInfo("����", 7));
		list.add(new GridInfo("����ʱ��", 8));
		list.add(new GridInfo("�����", 9));
		list.add(new GridInfo("�����", 10));
		list.add(new GridInfo("��ϵ��", 11));
		list.add(new GridInfo("����", 12));
		
		adapter = new GridAdapter(this);
		
		adapter.setList(list);
		
		gridview.setAdapter(adapter);
		

		//�����ֻ����̵ĵ���ȷ������
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				{
					
					GridInfo item = (GridInfo) adapter.getItem(position);
					Intent intent = new Intent();
					switch (item.getImage_id()) {
					case 1:
						intent.setClass(GridLayoutActivity.this,
								CaptureActivity.class);
						GridLayoutActivity.this.startActivity(intent);
						break;
					case 2:
						intent.setClass(GridLayoutActivity.this,
								Photograph_Sys.class);
						GridLayoutActivity.this.startActivity(intent);
						break;
					default:
						break;
					}

				}
			}

		});

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//������µ��Ƿ��ؼ�������û���ظ�
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			
			//���ý�����л�Ч��
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
			
			return false;
		}
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event))
			return true;
		else
			return false;
	}
}