package com.ffcs.listenerHelper;

import com.ffcs.activity.GridLayoutActivity;
import com.ffcs.activity.R;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;

public class MyOnGestureListener implements OnGestureListener {

    GridLayoutActivity first;

    private static final int SWIPE_MAX_OFF_PATH = 100;

    private static final int SWIPE_MIN_DISTANCE = 100;

    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    public MyOnGestureListener( GridLayoutActivity first) {
        this.first = first;
    }

    // �û��ᴥ����������1��MotionEvent ACTION_DOWN����
    public boolean onDown(MotionEvent e) {
        return true;
    }

    // �û����´������������ƶ����ɿ�,��1��MotionEvent ACTION_DOWN,
    // ���ACTION_MOVE, 1��ACTION_UP����
    // e1����1��ACTION_DOWN MotionEvent
    // e2�����һ��ACTION_MOVE MotionEvent
    // velocityX��X���ϵ��ƶ��ٶȣ�����/��
    // velocityY��Y���ϵ��ƶ��ٶȣ�����/��
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {
    	if(e1!=null&&e2!=null){
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            return false;

        if ((e1.getX() - e2.getX()) > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            first.container_gridview.setAnimation(AnimationUtils.loadAnimation(first,
                    R.anim.push_left_out1));
            first.container_2.setVisibility(View.VISIBLE);
            first.container_2.setAnimation(AnimationUtils.loadAnimation(first,
                    R.anim.push_right_in));
            first.container_gridview.setVisibility(View.GONE);

        } else if ((e2.getX() - e1.getX()) > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
        	first.container_gridview.setVisibility(View.VISIBLE);
        	first.container_gridview.setAnimation(AnimationUtils.loadAnimation(first,
                    R.anim.push_left_in1));
        	first.container_2.setAnimation(AnimationUtils.loadAnimation(first,
                    R.anim.push_right_out));
        	first.container_2.setVisibility(View.GONE);
        }
        }
        return true;
    	
    }

    // �û��������������ɶ��MotionEvent ACTION_DOWN����
    public void onLongPress(MotionEvent e) {
     
    }

    // �û����´����������϶�����1��MotionEvent ACTION_DOWN, ���ACTION_MOVE����
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        return true;
    }

    // �û��ᴥ����������δ�ɿ����϶�����һ��1��MotionEvent ACTION_DOWN����
    // ע���onDown()������ǿ������û���ɿ������϶���״̬
    public void onShowPress(MotionEvent e) {

    }

    // �û����ᴥ���������ɿ�����һ��1��MotionEvent ACTION_UP����
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

}

