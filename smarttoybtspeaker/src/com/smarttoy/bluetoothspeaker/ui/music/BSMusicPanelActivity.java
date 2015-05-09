package com.smarttoy.bluetoothspeaker.ui.music;

/*
 * @author: BruceZhang
 * @last edit: 2015-3-25
 * @description: you can play local music here
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;
import com.smarttoy.bluetoothspeaker.ui.BSConfigure;
import com.smarttoy.bluetoothspeaker.ui.BSPushActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BSMusicPanelActivity extends BSActionBarActivity {

	private SwipeListView m_listView;
	private SimpleMusicAdapter m_simpleAdapter;
	private List<Map<String, Object>> m_listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_panel_music);
		getApplicationContext();

		setActionBarCenterTitle(getResources().getString(R.string.bs_pally));

		initListView();
	}

	private void initListView() {
		m_listItems = new ArrayList<Map<String, Object>>();
		m_simpleAdapter = new SimpleMusicAdapter();

		m_listView = (SwipeListView) findViewById(R.id.lv_music);
		m_listView.setAdapter(m_simpleAdapter);
		m_listView.setOffsetLeft(getResources().getDisplayMetrics().widthPixels * 4 / 7);
		m_listView.setSwipeListViewListener(new DeleteBaseSwipeListViewListener());
	}
	
	private class DeleteBaseSwipeListViewListener extends BaseSwipeListViewListener {
		@Override
		public void onClickBackView(int position) {
			m_listItems.remove(position);
			m_simpleAdapter.notifyDataSetChanged();
			Log.e("Halfish", "delete clicked");
		}
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
			startPushActivity();
			break;

		case R.id.menu_add:
			break;

		case R.id.menu_add_local:
			Intent intent2 = new Intent(BSMusicPanelActivity.this,
					BSAddLocalMusicActivity.class);
			startActivityForResult(intent2, REQUEST_CODE);
			break;	

		default:
			break;
		}

		return false;
	}
	
	private void startPushActivity() {
		Intent intent = new Intent(BSMusicPanelActivity.this,
				BSPushActivity.class);
		
		String data = "1";
		for (int i = 0; i < m_listItems.size(); i++) {
			String seq = (String)m_listItems.get(i).get("sequence");
			if (!parseNumToAlphaBeta(seq).equals("0")) {
				data = data + parseNumToAlphaBeta(seq);
			}
		}
		intent.putExtra("data", data);
		Log.e("Halfish", data);
		startActivity(intent);
	}
	
	private String parseNumToAlphaBeta(String s) {
		char alphaBeta = 0;
		String ss[] = s.split("\\.");
		int num = Integer.parseInt(ss[0]);
		if (num >= 1 && num <= 26) {
			alphaBeta = (char) ('a' + num - 1);
		} else if(num >= 27 && num <= 52) {
			alphaBeta = (char) ('A' + num - 27);
		}
		
		return String.valueOf(alphaBeta);
	}

	public static final int REQUEST_CODE = 1000;
	public static final int RESULT_CODE = 1001;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
			m_listItems = BSConfigure.getSimpleAlbum();
			Log.e("Halfish", m_listItems.toString());
			m_simpleAdapter.notifyDataSetChanged();
		}
	}

	private class SimpleMusicAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return m_listItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.bs_music_panel_item, parent, false);
			}

			ImageView album = (ImageView) convertView
					.findViewById(R.id.img_album);
			TextView name = (TextView) convertView.findViewById(R.id.tv_name);

			album.setImageDrawable((Drawable) m_listItems.get(position).get(
					"album"));
			name.setText((String) m_listItems.get(position).get("name"));

			Button deleteButton = (Button)convertView.findViewById(R.id.btn_delete);
			deleteButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					m_listItems.remove(position);
					m_simpleAdapter.notifyDataSetChanged();
					m_listView.closeOpenedItems();
				}
			});
			
			return convertView;
		}

	}

}
