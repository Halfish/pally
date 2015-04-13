package com.smarttoy.bluetoothspeaker.ui.music;

/*
 * @author: Bruce
 * @last edit: 2015-3-25
 * @description “Ù¿÷Õ∆ÀÕΩÁ√Ê
 */


import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class BSPushMusicActivity extends BSActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_music_push);
		setActionBarCenterTitle(getResources().getString(R.string.bs_music_local));	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		
		return false;
	}
	
}
