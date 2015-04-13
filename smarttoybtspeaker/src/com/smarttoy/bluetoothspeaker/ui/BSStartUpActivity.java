package com.smarttoy.bluetoothspeaker.ui;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.util.STTimer;
import com.smarttoy.util.STTimer.OnTimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BSStartUpActivity extends Activity implements OnTimer {

	private static final long STARTUP_DELAY = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 去掉信息栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
