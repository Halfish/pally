package com.smarttoy.bluetoothspeaker.ui;

import android.graphics.drawable.Drawable;

public class BSAlbum {

	public BSAlbum() {

	}

	public BSAlbum(int albumId, String title, String artist, Drawable cover) {
		mAlbumId = albumId;
		mTitle = title;
		mArtist = artist;
		mCover = cover;
	}

	private int mAlbumId;
	private String mTitle;
	private String mArtist;
	private Drawable mCover;

	@Override
	public int hashCode() {
		return Integer.valueOf(mAlbumId).hashCode();
	}

	@Override
	public boolean equals(Object o) {
		BSAlbum album = (BSAlbum) o;
		return (this.mTitle == album.getTitle());
	}

	public int getAlbumId() {
		return mAlbumId;
	}

	public void setAlbumId(int albumId) {
		this.mAlbumId = albumId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getArtist() {
		return mArtist;
	}

	public void setArtist(String artist) {
		this.mArtist = artist;
	}

	public Drawable getCover() {
		return mCover;
	}

	public void setCover(Drawable cover) {
		this.mCover = cover;
	}

}
