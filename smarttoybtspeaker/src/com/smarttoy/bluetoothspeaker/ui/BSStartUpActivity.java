package com.smarttoy.bluetoothspeaker.ui;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.util.STTimer;
import com.smarttoy.util.STTimer.OnTimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BSStartUpActivity extends Activity implements OnTimer {

	private static final long STARTUP_DELAY = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bs_activity_startup);

		STTimer timer = new STTimer(this);
		timer.start(STARTUP_DELAY);
	}

	@Override
	public void OnTrigger(Object arg) {
		Intent intent = new Intent(BSStartUpActivity.this,
				BSMainPanelActivity.class);
		startActivity(intent);
		finish();
	}
}
