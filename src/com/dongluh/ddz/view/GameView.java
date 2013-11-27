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

	private Bitmap backCard; 	// 背景
	private Bitmap background;  // 牌的背面
	
	public static final int PLAYER_MARGIN = 10;    // 玩家和屏幕边框的距离
	
	private List<Player> players = new ArrayList<Player>();
	private List<Card> cards = new ArrayList<Card>();
	// private Rect bgRect;
	
	public static int cardWidth; 	// 一张牌的宽度
	public static int cardHeight; 	// 一张牌的宽度
	public static int pokerWidth; 	// 整副牌的宽度
	public static int pokerHeight; 	// 整副牌的宽度
	
	public static int miniCardWidth; 	// 一张小牌的宽度
	public static int miniCardHeight; 	// 一张小牌的宽度
	public static int miniPokerWidth; 	// 整副小牌的宽度
	public static int miniPokerHeight; 	// 整副小牌的宽度
	
	public static int cardMargin;    // 牌之间的间距
	
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
		// 初始化牌的尺寸
		cardWidth = winWidth / 13;
		cardHeight = winHeight / 6;
		pokerWidth = cardWidth * 13;
		pokerHeight = cardHeight * 5;

		// 初始化小牌尺寸
		miniCardHeight = cardHeight * 3 / 5;
		miniCardWidth = cardWidth * 3 / 5;
		miniPokerWidth = miniCardWidth * 13;
		miniPokerHeight = miniCardHeight * 5;
		
		cardMargin = cardWidth * 3 / 5;
		
		// 整副牌
		Bitmap poker = getBitmap(R.drawable.poker, pokerWidth, pokerHeight);
		// 整副小牌
		Bitmap pokerMini = getBitmap(R.drawable.poker_mini, miniPokerWidth,
				miniPokerHeight);
		// 卡片背面
		backCard = Bitmap.createBitmap(pokerMini, 2 * miniCardWidth,
				4 * miniCardHeight, miniCardWidth, miniCardHeight);
		
		// 把牌切割出来 
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 13; col++) {
				if (row == 4 && col > 1) {
					break;
				}
				
				Bitmap bigCard = Bitmap.createBitmap(poker, col * cardWidth,
						row * cardHeight, cardWidth, cardHeight);

				Bitmap miniCard = Bitmap.createBitmap(pokerMini, col
						* miniCardWidth, row * miniCardHeight, miniCardWidth,
						miniCardHeight);
				
				Card card = new Card();
				card.setBigImg(bigCard);
				card.setSmallImg(miniCard);
				card.setColor(row);
				card.setValue(col + 1);

				if (col < 2) { 
					card.setValue(card.getValue() + 13); // 14表示A，15表示2
					if (row == 4) { // 大小王
						card.setValue(col == 0 ? 17 : 16); // 16表示小王，17表示大王
					}
				}
				cards.add(card);
			}
		}
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
