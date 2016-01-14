package com.ffcs.grid;

import com.ffcs.activity.CaptureActivity;
import com.ffcs.activity.FirstActivity;
import com.ffcs.activity.GridLayoutActivity;
import com.ffcs.activity.Photograph_Sys;
import com.ffcs.activity.Photograph_SysActivity;
import com.ffcs.activity.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class DragGridView extends GridView {

	// ��������ĳ�Ա����

	private ImageView dragImageView;
    //�϶���ԭ����λ��
	private int dragSrcPosition;
    //�յ��Ϸ�λ��
	private int dragPosition;

	// x,y����ļ���

	private int dragPointX;

	private int dragPointY;
    
	//�ƶ�λ��
	private int dragOffsetX;

	private int dragOffsetY;

	private WindowManager windowManager;

	private WindowManager.LayoutParams windowParams;

	//����GestureDetector��
	private GestureDetector mGestureDetector;
	
	//�¼�����
	private MyGestureListener mGestureListener;

	public DragGridView(Context context, AttributeSet attrs) {

		super(context, attrs);
		mGestureListener = new MyGestureListener();
		mGestureDetector = new GestureDetector( context,mGestureListener);

	}
	
	// startDrag��stopDrag�������£�

	public void startDrag(Bitmap bm, int x, int y) {

		stopDrag();

		windowParams = new WindowManager.LayoutParams();

		windowParams.gravity = Gravity.TOP | Gravity.LEFT;

		windowParams.x = x - dragPointX + dragOffsetX;

		windowParams.y = y - dragPointY + dragOffsetY;

		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

		windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

		| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE

		| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON

		| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

		windowParams.format = PixelFormat.TRANSLUCENT;

		windowParams.windowAnimations = 0;

		ImageView imageView = new ImageView(getContext());

		imageView.setImageBitmap(bm);

		windowManager = (WindowManager) getContext().getSystemService("window");

		windowManager.addView(imageView, windowParams);

		dragImageView = imageView;

	}

	/**
	 * 2 * ֹͣ�϶���ȥ���϶����ͷ��
	 * 
	 * 3
	 */

	public void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);

			dragImageView = null;
		}
	}

//	 ��дonTouchEvent()����.��������GestureDetector�ļ����¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}


	// ����onDrag�������£�

	public void onDrag(int x, int y) {

		if (dragImageView != null) {

			windowParams.alpha = 0.8f;

			windowParams.x = x - dragPointX + dragOffsetX;

			windowParams.y = y - dragPointY + dragOffsetY;

			windowManager.updateViewLayout(dragImageView, windowParams);

		}
	}

	// ����Ӱ�����ݸ��¡�
	// ��onDrop������ʵ�֣�

	public void onDrop(int x, int y) {

		// Ϊ�˱��⻬�����ָ��ߵ�ʱ�򣬷���-1������

		int tempPosition = pointToPosition(x, y);
		if (tempPosition != INVALID_POSITION) {

			dragPosition = tempPosition;

		}

		// �����߽紦��

		if (y < getChildAt(0).getTop()) {
			// �����ϱ߽�

			dragPosition = 0;

		} else if (y > getChildAt(getChildCount() - 1).getBottom()
				|| (y > getChildAt(getChildCount() - 1).getTop() && x > getChildAt(
						getChildCount() - 1).getRight())) {

			// �����±߽�

			dragPosition = getAdapter().getCount() - 1;

		}

		// ���ݽ�������Ҫ�ǽ�����Item������

		if (dragPosition != dragSrcPosition && dragPosition > -1&& dragSrcPosition > -1
				&& dragPosition < getAdapter().getCount()) {

			GridAdapter adapter = (GridAdapter) getAdapter();
			adapter.exchange(dragPosition, dragSrcPosition);
			ViewGroup itemView1 = (ViewGroup) getChildAt(dragPosition
					- getFirstVisiblePosition());
			ViewGroup itemView2 = (ViewGroup) getChildAt(dragSrcPosition
					- getFirstVisiblePosition());
			itemView1.destroyDrawingCache();
			itemView2.destroyDrawingCache();

		}

	}
//�Զ���������¼�������
	class MyGestureListener extends SimpleOnGestureListener {
		
		//����
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
         //����
		@Override
		public void onLongPress(MotionEvent e) {
			
		}

		/**
		 * @param e1
		 *            The first down motion event that started the scrolling.
		 * @param e2
		 *            The move motion event that triggered the current onScroll.
		 * @param distanceX
		 *            The distance along the X axis(��) that has been scrolled
		 *            since the last call to onScroll. This is NOT the distance
		 *            between e1 and e2.
		 * @param distanceY
		 *            The distance along the Y axis that has been scrolled since
		 *            the last call to onScroll. This is NOT the distance
		 *            between e1 and e2. �����������϶�view�����������׵Ķ��������������δ���
		 *            ,���������ACTION_MOVE��������ʱ�ͻᴥ��
		 *            �ο�GestureDetector��onTouchEvent����Դ��
		 * */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			int x = (int) e1.getX();

			int y = (int) e1.getY();

			dragSrcPosition = dragPosition = pointToPosition(x, y);

			if (dragPosition == AdapterView.INVALID_POSITION) {

				return mGestureDetector.onTouchEvent(e1);

			}
			ViewGroup itemView = (ViewGroup) getChildAt(dragPosition
					- getFirstVisiblePosition());
			dragPointX = x - itemView.getLeft();

			dragPointY = y - itemView.getTop();

			dragOffsetX = (int) (e1.getRawX() - x);

			dragOffsetY = (int) (e1.getRawY() - y);

			// ÿ�ζ�����һ��cache����������һ��bitmap
			itemView.destroyDrawingCache();
			itemView.setDrawingCacheEnabled(true);

			Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache());
			startDrag(bm, x, y);
			int moveX = (int) e2.getX();

			int moveY = (int) e2.getY();
			onDrag(moveX, moveY);
			return false;
		}

		/**
		 * @param e1
		 *            ��1��ACTION_DOWN MotionEvent ����ֻ��һ��
		 * @param e2
		 *            ���һ��ACTION_MOVE MotionEvent
		 * @param velocityX
		 *            X���ϵ��ƶ��ٶȣ�����/��
		 * @param velocityY
		 *            Y���ϵ��ƶ��ٶȣ�����/�� �������������ACTION_UPʱ�Żᴥ��
		 *            �ο�GestureDetector��onTouchEvent����Դ��
		 * 
		 * */
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (dragImageView != null && dragPosition != INVALID_POSITION&& dragSrcPosition != INVALID_POSITION) {

				int upX = (int) e2.getX();

				int upY = (int) e2.getY();

				stopDrag();
				onDrop(upX, upY);

			}
			return false;
		}
    
		@Override
		public void onShowPress(MotionEvent e) {

		}
        //����
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
        //˫��
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return true;
		}
       
		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}

		/**
		 * ���������ͬ��onSingleTapUp��������GestureDetectorȷ���û��ڵ�һ�δ�����Ļ��û�н����ŵڶ��δ�����Ļ��
		 * Ҳ���ǲ��ǡ�˫������ʱ�򴥷�
		 * */
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			int x = (int) e.getX();

			int y = (int) e.getY();

			int position =pointToPosition(x, y);
			
			GridAdapter adapter = (GridAdapter) getAdapter();
			
			final View view=(View)getChildAt(position);
			
			final GridInfo item=(GridInfo)adapter.getItem(position);
			
            //������Item�ı�����ͼƬ����drawable
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.focused_application_background_static));
            
			//�����߳�˯��0.5��󽫱���������Ϊ�գ��Դﵽ��˸һ�µ�Ч��
			new Handler().postDelayed(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					view.setBackgroundDrawable(null);
				}
            	
            }, 500);
			
            //����ͼƬid��Ӧ��Ӧ��Ӧ�ù���
			new Handler().postDelayed(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					Context context = getContext();
					switch(item.getImage_id())
					{
					case 1:intent.setClass(context, CaptureActivity.class);context.startActivity(intent);break;
					case 2:intent.setClass(context, Photograph_Sys.class);context.startActivity(intent);break;
					default : break;
					}
				}
				
			},500);
		
			return false;
		}

	}
}
