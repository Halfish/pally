package com.smarttoy.bluetoothspeaker.ui;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.music.BSMusicPanelActivity;
import com.smarttoy.bluetoothspeaker.ui.radio.BSRadioPanelActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BSMainPanelActivity extends Activity {

	private View m_enterMusicView;
	private View m_enterRadioView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.bs_activity_panel_main);
		initButtons();
	}

	private void initButtons() {
		m_enterMusicView = (View) findViewById(R.id.view_event_music);
		m_enterRadioView = (View) findViewById(R.id.view_event_radio);

		m_enterMusicView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BSMainPanelActivity.this,
						BSMusicPanelActivity.class);
				startActivity(intent);
			}
		});

		m_enterRadioView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BSMainPanelActivity.this,
						BSRadioPanelActivity.class);
				startActivity(intent);
			}
		});
	}
}
