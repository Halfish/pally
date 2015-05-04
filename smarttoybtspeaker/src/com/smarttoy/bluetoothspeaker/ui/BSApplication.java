package com.smarttoy.bluetoothspeaker.ui;

import java.util.ArrayList;

public class BSApplication {
	private static ArrayList<BSAlbum> mData;

	public static ArrayList<BSAlbum> getData() {
		return mData;
	}
	
	public static void setData(ArrayList<BSAlbum> data) {
		mData = data;
	}
}
