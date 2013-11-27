package com.dongluh.ddz.model;

import static com.dongluh.ddz.view.BaseView.TEXT_SIZE;

import java.util.ArrayList;
import java.util.List;

import com.dongluh.ddz.view.BaseView;
import com.dongluh.ddz.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * 玩家类
 * 
 * @author donglu
 */
public class Player {

	private String name;

	private String score;

	private Bitmap head;

	private int x;

	private int y;

	private boolean isLandlord;

	private boolean isComputer;

	private Player next;

	/**
	 * 手里的牌
	 */
	private List<Card> handCards = new ArrayList<Card>();
	/**
	 * 即将打出去的牌
	 */
	private List<Card> willSendCards = new ArrayList<Card>();
	/**
	 * 打出去的牌
	 */
	private List<Card> sentCards = new ArrayList<Card>();

	public List<Card> getHandCards() {
		return handCards;
	}

	public void setHandCards(List<Card> handCards) {
		this.handCards = handCards;
	}

	public List<Card> getWillSendCards() {
		return willSendCards;
	}

	public void setWillSendCards(List<Card> willSendCards) {
		this.willSendCards = willSendCards;
	}

	public List<Card> getSentCards() {
		return sentCards;
	}

	public void setSentCards(List<Card> sentCards) {
		this.sentCards = sentCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Bitmap getHead() {
		return head;
	}

	public void setHead(Bitmap head) {
		this.head = head;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isLandlord() {
		return isLandlord;
	}

	public void setLandlord(boolean isLandlord) {
		this.isLandlord = isLandlord;
	}

	public boolean isComputer() {
		return isComputer;
	}

	public void setComputer(boolean isComputer) {
		this.isComputer = isComputer;
	}

	public Player getNext() {
		return next;
	}

	public void setNext(Player next) {
		this.next = next;
	}

	public void draw(Canvas canvas, Paint paint) {
		String scoreText = "积分（" + score + "）";
		int sorceX = x;
		// 画头像
		canvas.drawBitmap(head, x, y, paint);

		Rect tempRect = new Rect();
		paint.getTextBounds(name, 0, name.length(), tempRect);
		int nameWidth = tempRect.right; // name的宽度
		if (x + nameWidth > BaseView.winWidth) { // name超出屏幕
			x = BaseView.winWidth - nameWidth - GameView.PLAYER_MARGIN;
		}
		// 画名字
		canvas.drawText(name, x, y + head.getHeight() + TEXT_SIZE, paint);

		paint.getTextBounds(scoreText, 0, scoreText.length(), tempRect);
		int scoreWidth = tempRect.right; // score的宽度
		if (x + scoreWidth > BaseView.winWidth) { // score超出屏幕
			sorceX = BaseView.winWidth - scoreWidth - GameView.PLAYER_MARGIN;
		}
		// 画积分
		canvas.drawText(scoreText, sorceX,
				y + head.getHeight() + TEXT_SIZE * 2, paint);
		
		
		// 画扑克
		if(isComputer) { //如果是电脑
			
			/*********************画手牌**********************/
			// 卡片背面的Y
			int backCardY = y + head.getHeight() + TEXT_SIZE * 2 + GameView.PLAYER_MARGIN;
			// 画牌的背面
			canvas.drawBitmap(GameView.backCard, x, backCardY, paint);
			// 手牌数量的Y
			int sizeY = backCardY + GameView.miniCardHeight + GameView.TEXT_SIZE;
			//画卡片剩余数
			canvas.drawText(""+handCards.size(), x, sizeY, paint);
			
			
		 
		} else { //如果是玩家自己
			
			/*********************画手牌**********************/
			//计算手牌宽度
			int handCardsWidth  = (handCards.size()-1) * GameView.cardMargin + GameView.cardWidth;
			//计算手牌开始的X
			int handCardStartX = (GameView.winWidth - handCardsWidth) / 2;
			//开始画手牌
			for (int i = 0; i < handCards.size(); i++) {
				int cardX = handCardStartX + i * GameView.cardMargin; //卡片的x
				int cardY = GameView.winHeight - GameView.cardHeight - GameView.PLAYER_MARGIN; // 卡片的Y
				
				Card card = handCards.get(i);
				if(willSendCards.contains(card)) { //代表这张卡片被提起来了
					cardY -= GameView.PLAYER_MARGIN; //减小Y值
				}
				Bitmap bigIcon = handCards.get(i).getBigImg(); //卡片 
				canvas.drawBitmap(bigIcon, cardX, cardY, paint);
			}
		}
	}
}
