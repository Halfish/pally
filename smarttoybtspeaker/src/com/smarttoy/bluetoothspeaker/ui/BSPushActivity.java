package com.smarttoy.bluetoothspeaker.ui;

/*
 * @author: BruceZhang
 * @last edit: 2015-3-25
 * @description Push Music/Radio Data To Server
 */

import java.io.IOException;
import java.io.OutputStream;

import com.smarttoy.bluetooth.STBluetoothManager.OnEventCallback;
import com.smarttoy.bluetooth.STBluetoothManager;
import com.smarttoy.bluetooth.STSppClient;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.util.STTimer;
import com.smarttoy.util.STTimer.OnTimer;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class BSPushActivity extends BSActionBarActivity implements
		OnEventCallback, OnTimer {

	private STSppClient m_spp;
	private STTimer m_timer;
	private ProgressDialog m_progDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_music_push);
		setActionBarCenterTitle(getResources().getString(
				R.string.bs_music_local));

		// OPEN BLUETOOTH
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (!adapter.isEnabled()) {
			Toast.makeText(this, "正在打开蓝牙", Toast.LENGTH_LONG).show();
			adapter.enable();
		}
		
		m_spp = new STSppClient(this);
		m_spp.setEventCallBack(this);

		m_spp.startDeviceDiscovery();

		m_progDialog = new ProgressDialog(this);
		m_progDialog.setMessage("正在查找蓝牙音箱");
		m_progDialog.setCanceledOnTouchOutside(false);
		m_progDialog.show();

		m_timer = new STTimer(this);
		m_timer.start(15000);
	}

	@Override
	protected void onStart() {
		super.onStart();
		m_spp.registEventReceiver();
	}

	@Override
	protected void onStop() {
		super.onStop();
		m_spp.unregistEventReceiver();
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

	@Override
	public void onBluetoothEnable() {

	}

	@Override
	public void onBluetoothDisable() {

	}

	@Override
	public void onDeviceFound(BluetoothDevice device) {
		Log.v("BSPushActivity", "Find BluetoothDevice: " + device.getName());
		if (device.getName().equals(BSConfigure.BLUETOOTH_SPEAKER_NAME)) {
			m_spp.stopDeviceDiscovery();
			m_progDialog.setMessage("找到蓝牙音箱，正在配对");

			// bound
			if (device.getBondState() == BluetoothDevice.BOND_NONE) {
				m_spp.boundDevice(device);
			} else {
				// connect directly
				if (m_spp.connect(device)) {
					sendDataToServer();
				} else {
					Toast.makeText(this, "连接蓝牙出错！", Toast.LENGTH_LONG).show();
					Log.e("Halfish", "connect directly failed");
					m_progDialog.dismiss();
				}
			}

		}
	}

	private void sendDataToServer() {
		String data = getIntent().getExtras().getString("data");

		try {
			OutputStream output = m_spp.getOutputStream();
			output.write(data.getBytes());
			output.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Toast.makeText(this, "已经推送到蓝牙设备！", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDeviceBounded(BluetoothDevice device) {
		if(m_spp.isConnected()) {
			sendDataToServer();
		} else if (m_spp.connect(device)) {
			sendDataToServer();
		} else {
			Toast.makeText(this, "onDeviceBounded 连接蓝牙出错！", Toast.LENGTH_LONG)
					.show();
			m_progDialog.dismiss();
		}
	}

	@Override
	public void onDeviceUnbounded(BluetoothDevice device) {

	}

	@Override
	public void onError(int errorCode) {
		if (errorCode == STBluetoothManager.ERROR_BOUND_TIME_OUT
				|| errorCode == STBluetoothManager.ERROR_BOUND_ERROR) {
			Toast.makeText(this, "配对设置出错，请手动配对", Toast.LENGTH_SHORT).show();
		}

		Toast.makeText(this, "errorCode is " + errorCode, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void OnTrigger(Object arg) {
		if (m_spp.isDiscovering()) {
			m_spp.stopDeviceDiscovery();
		}
		m_progDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(! m_spp.isConnected()) {
			m_spp.disconnect();
		}
		m_progDialog.dismiss();
	}

}
