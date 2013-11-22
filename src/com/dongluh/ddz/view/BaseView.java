package com.dongluh.ddz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public abstract class BaseView extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder holder; // 管理SufaceView
	private Thread pThread; // 绘图线程
	protected Paint paint; // 默认画笔
	private boolean isRunning; // 是否在绘图
	private static final int SPAN = 100;
	
	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);

		holder = getHolder();
		holder.addCallback(this);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);     // 设置抗锯齿
		paint.setTextSize(25);
		paint.setColor(Color.WHITE);
	}

	@Override
	public void run() {
		while (isRunning) {
			Canvas canvas = null;
			try {
				// 锁定画布
				synchronized (holder) {
					canvas = holder.lockCanvas();
				}
				// 画图逻辑
				render(canvas);
			} finally {
				// 解锁画布，回到主线程，渲染到屏幕上
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
		// 开启绘图线程
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
		// 销毁绘图线程
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
