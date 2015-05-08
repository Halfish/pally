package com.smarttoy.bluetoothspeaker.ui;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.crash.STCrashHandler;

import android.app.Application;
import android.content.Context;

public class BSApplication extends Application {
	private static Context s_context = null;
	public static Context getContext() {
		return s_context;
	}
	
	@Override
	public void onCreate() {
		s_context = this.getBaseContext();
		CrashGuard crash = CrashGuard.getInstance();
		crash.init(s_context);
		
		super.onCreate();
	}

	// 处理崩溃
	static class CrashGuard extends STCrashHandler {
		
		private static CrashGuard s_instance;
		public static CrashGuard getInstance() {
			if (s_instance == null) {
				synchronized (CrashGuard.class) {
					if (s_instance == null) {
						s_instance = new CrashGuard();
					}
				}
			}
			return s_instance;
		}
		
		@Override
		protected String getAppName() {
			return getContext().getResources().getString(R.string.app_name);
		}
	}
}
