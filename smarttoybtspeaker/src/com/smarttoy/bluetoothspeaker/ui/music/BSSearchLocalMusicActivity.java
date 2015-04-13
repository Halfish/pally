package com.smarttoy.bluetoothspeaker.ui.music;

/*
 * @author: Bruce
 * @description 搜寻本地音乐的等待页面
 */

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;
import com.smarttoy.util.STTimer;
import com.smarttoy.util.STTimer.OnTimer;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class BSSearchLocalMusicActivity extends BSActionBarActivity implements OnTimer {
	
	private static final long STARTUP_DELAY = 3000;
	private ImageView m_imageView;
	private AnimationDrawable m_anim;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_music_search_local);
		setActionBarCenterTitle(getResources().getString(R.string.bs_music_local));
		STTimer timer = new STTimer(this);
		timer.start(STARTUP_DELAY);
		
		m_imageView = (ImageView)findViewById(R.id.img_anim);
		m_anim = (AnimationDrawable) m_imageView.getBackground();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		m_anim.start();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		m_anim.stop();
	}

	@Override
	public void OnTrigger(Object arg) {
		Intent intent = new Intent(BSSearchLocalMusicActivity.this,
				BSAddLocalMusicActivity.class);
		startActivity(intent);
		finish();
	}
	
	
}
