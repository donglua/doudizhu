package com.dongluh.ddz.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.dongluh.ddz.R;
import com.dongluh.ddz.model.Card;
import com.dongluh.ddz.model.Player;

public class GameView extends BaseView {

	public static final int PLAYER_MARGIN = 10;    // 玩家和屏幕边框的距离
	private Bitmap background;
	
	private List<Player> players = new ArrayList<Player>();
	private List<Card> cards = new ArrayList<Card>();
	// private Rect bgRect;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initRes((Activity) context);
	
		initPlayer();
		
		intiCards();
	}

	private void initRes(Activity activity) {
		background = getBitmap(R.drawable.bg, winWidth, winHeight);
		// bgRect = new Rect(0, 0, width, height);
	}

	private void initPlayer() {
		// 自己
		Player me = new Player();
		me.setComputer(false);
		me.setName("donglu");
		me.setLandlord(true);
		me.setScore("0");
		Bitmap myHead = BitmapFactory.decodeResource(getResources(), R.drawable.me);
		me.setHead(myHead);
		me.setX(PLAYER_MARGIN);
		me.setY(winHeight- myHead.getHeight() - TEXT_SIZE - 100);

		// 左边
		Player left = new Player();
		left.setComputer(true);
		left.setName("马化騰");
		left.setLandlord(false);
		left.setScore("0");
		Bitmap leftHead = BitmapFactory.decodeResource(getResources(), R.drawable.left);
		left.setHead(leftHead);
		left.setX(PLAYER_MARGIN);
		left.setY(PLAYER_MARGIN);
		
		// 右边
		Player right = new Player();
		right.setComputer(true);
		right.setName("乔布斯");
		right.setLandlord(false);
		right.setScore("543210");
		Bitmap rightHead = BitmapFactory.decodeResource(getResources(), R.drawable.right);
		right.setHead(rightHead);
		right.setX(winWidth - rightHead.getWidth() - PLAYER_MARGIN);
		right.setY(PLAYER_MARGIN);
		
		/**
		 * 设置下家
		 */
		me.setNext(right);
		right.setNext(left);
		left.setNext(me);
		
		players.add(me);
		players.add(left);
		players.add(right);
	}


	private void intiCards() {
		
	}


	
	@Override
	protected void render(Canvas canvas) {
		
		// draw background bitmap
		canvas.drawBitmap(background, 0, 0, paint);    // 性能更高
		// canvas.drawBitmap(background, null, bgRect, paint);
 
		synchronized (players) {
			for (Player p : players) {
				p.draw(canvas, paint);
			}
		}
	}

	public Bitmap getBitmap(int resId, int w, int h) {
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}
}
