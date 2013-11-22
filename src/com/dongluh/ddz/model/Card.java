package com.dongluh.ddz.model;

import android.graphics.Bitmap;

/**
 * 牌类
 */
public class Card {
	private int color;

	private int value;

	private Bitmap bigImg;

	private Bitmap smallImg;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Bitmap getBigImg() {
		return bigImg;
	}

	public void setBigImg(Bitmap bigImg) {
		this.bigImg = bigImg;
	}

	public Bitmap getSmallImg() {
		return smallImg;
	}

	public void setSmallImg(Bitmap smallImg) {
		this.smallImg = smallImg;
	}
}
