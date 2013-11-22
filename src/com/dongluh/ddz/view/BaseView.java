package com.dongluh.ddz.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public abstract class BaseView extends SurfaceView implements Callback, Runnable {

	protected Paint paint; //默认的画笔
	public static final int TEXT_SIZE = 20;
	
	private SurfaceHolder holder; //Surface的大管家
	private Thread pThread; //绘图线程
	private boolean isRunning; //是否在绘图
	private static final int SPAN = 100;
	
	/**
	 * 屏幕尺寸
	 */
	public static int winWidth, winHeight;
	
	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);

		holder = getHolder();
		holder.addCallback(this);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG); //设置抗拒出
		paint.setTextSize(TEXT_SIZE);
		paint.setColor(Color.WHITE); //白色
		
		//获得屏幕尺寸
		winWidth = ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
		winHeight = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
	}

	@Override
	public void run() {
		while (isRunning) {
			Canvas canvas = null;
			try {
				// ����
				synchronized (holder) {
					canvas = holder.lockCanvas();
				}
				// ��ͼ�߼�
				render(canvas);
			} finally {
				// �������ص����̣߳���Ⱦ����Ļ��
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			
			try {
				Thread.sleep(SPAN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//开启绘图线程
		pThread = new Thread(this);
		pThread.start();
		isRunning = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//销毁绘图线程
		isRunning = false;
		if (pThread != null && pThread.isAlive()) {
			try {
				pThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void render(Canvas canvas);
}
