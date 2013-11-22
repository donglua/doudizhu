package com.dongluh.ddz.model;

import static com.dongluh.ddz.view.BaseView.TEXT_SIZE;

import com.dongluh.ddz.view.BaseView;
import com.dongluh.ddz.view.GameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
/**
 * 玩家类
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
		int nameWidth = tempRect.right;        // name的宽度
		if(x + nameWidth > BaseView.winWidth) {  // name超出屏幕
			x = BaseView.winWidth - nameWidth - GameView.PLAYER_MARGIN;
		}
		// 名字
		canvas.drawText(name, x, y + head.getHeight() + TEXT_SIZE, paint);		
		
		paint.getTextBounds(scoreText, 0, scoreText.length(), tempRect);
		int scoreWidth = tempRect.right;        // score的宽度
		if(x + scoreWidth > BaseView.winWidth) {  // score超出屏幕
			sorceX = BaseView.winWidth - scoreWidth - GameView.PLAYER_MARGIN;
		}
		// 积分
		canvas.drawText(scoreText, sorceX, y + head.getHeight() + TEXT_SIZE * 2, paint);
	}
}
