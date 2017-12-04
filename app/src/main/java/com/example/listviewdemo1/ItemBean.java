package com.example.listviewdemo1;

public class ItemBean {
	private int mId;
	private int mRed;
	private int mGreen;
	private int mYellow;
	
	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getmRed() {
		return mRed;
	}

	public void setmRed(int mRed) {
		this.mRed = mRed;
	}

	public int getmGreen() {
		return mGreen;
	}

	public void setmGreen(int mGreen) {
		this.mGreen = mGreen;
	}

	public int getmYellow() {
		return mYellow;
	}

	public void setmYellow(int mYellow) {
		this.mYellow = mYellow;
	}

	public ItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemBean(int mId, int mRed, int mGreen, int mYellow) {
		super();
		this.mId = mId;
		this.mRed = mRed;
		this.mGreen = mGreen;
		this.mYellow = mYellow;
	}

}
