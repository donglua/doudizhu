package com.dongluh.ddz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class GameView extends BaseView {

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void render(Canvas canvas) {
		canvas.drawText("donglua", 400, 300, paint);
	}
}
