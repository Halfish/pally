package com.smarttoy.bluetoothspeaker.ui.music;

/*
 * @author: Bruce
 * @last edit: 2015-3-25
 * @description 音乐面板界面
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BSMusicPanelActivity extends BSActionBarActivity {

	private Context m_context;
	private ListView m_listView;
	private SimpleAdapter m_simpleAdapter;
	private List<Map<String, Object>> listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_panel_music);
		m_context = getApplicationContext();

		setActionBarCenterTitle(getResources().getString(R.string.bs_pally));
		
		initListView();
	}

	// TODO 数据来源待修改
	private List<Map<String, Object>> getData() {
		listItems = new ArrayList<Map<String, Object>>();

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("album", R.drawable.album_taylor_9);
		item.put("name", "Back To December");
		listItems.add(item);

		item = new HashMap<String, Object>();
		item.put("album", R.drawable.album_taylor_8);
		item.put("name", "Love Story");
		listItems.add(item);

		return listItems;
	}

	private void initListView() {
		m_simpleAdapter = new SimpleAdapter(m_context, getData(),
				R.layout.bs_music_panel_item, new String[] { "album", "name" },
				new int[] { R.id.img_album, R.id.tv_name });

		m_listView = (ListView) findViewById(R.id.lv_music);
		m_listView.setAdapter(m_simpleAdapter);
		m_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(m_context, "position: " + position,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bs_menu_music_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		case R.id.menu_push:
			Intent intent1 = new Intent(BSMusicPanelActivity.this,
					BSPushMusicActivity.class);
			startActivity(intent1);
			break;

		case R.id.menu_add:
			break;

		case R.id.menu_add_local:
			Intent intent2 = new Intent(BSMusicPanelActivity.this,
					BSSearchLocalMusicActivity.class);
			startActivity(intent2);
			break;

		case R.id.menu_add_online:
			Intent intent3 = new Intent(BSMusicPanelActivity.this,
					BSAddOnlineMusicActivity.class);
			startActivity(intent3);
			break;

		default:
			break;
		}

		return false;
	}

}
