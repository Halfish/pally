package com.smarttoy.bluetoothspeaker.ui;

import java.util.ArrayList;
import java.util.Map;

import android.util.Log;

public class BSConfigure {
	
	public static final String BLUETOOTH_SPEAKER_NAME = "BK8000L";
	
	private static ArrayList<BSAlbum> mData;
	private static ArrayList<Map<String, Object>> mAlbums;

	public static ArrayList<BSAlbum> getData() {
		return mData;
	}
	
	public static void setData(ArrayList<BSAlbum> data) {
		mData = data;
	}
	
	public static void setSimpleAlbum(ArrayList<Map<String, Object>> albums) {
		mAlbums = albums;
	}
	
	public static ArrayList<Map<String, Object>> getSimpleAlbum() {
		if(mAlbums != null) {
			Log.e("BSApplication get", mAlbums.toString());
		}
		return mAlbums;
	}
}
