package com.smarttoy.bluetoothspeaker.ui.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BSAddOnlineMusicActivity extends BSActionBarActivity {

	private int[] m_JayAlbumIds = new int[] { R.drawable.album_jay_1,
			R.drawable.album_jay_2, R.drawable.album_jay_3,
			R.drawable.album_jay_4, R.drawable.album_jay_5,
			R.drawable.album_jay_6, R.drawable.album_jay_7,
			R.drawable.album_jay_8, R.drawable.album_jay_9 };

	private int[] m_TaylorAlbumIds = new int[] { R.drawable.album_taylor_1,
			R.drawable.album_taylor_2, R.drawable.album_taylor_3,
			R.drawable.album_taylor_4, R.drawable.album_taylor_5,
			R.drawable.album_taylor_6, R.drawable.album_taylor_7,
			R.drawable.album_taylor_8, R.drawable.album_taylor_9 };

	private ListView m_listView;
	private OnlineMusicBaseAdapter m_adapter;
	private List<Map<String, Object>> m_listItems;
	private Map<String, Object> m_item;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_music_add_online);
		setActionBarCenterTitle(getResources().getString(R.string.bs_music_online));
		initListView();
	}

	private void initListView() {
		initData();
		m_listView = (ListView) findViewById(R.id.lv_music_online);
		m_adapter = new OnlineMusicBaseAdapter();
		m_listView.setAdapter(m_adapter);
	}

	private void initData() {
		m_listItems = new ArrayList<Map<String, Object>>();

		// Jay Chou
		m_item = new HashMap<String, Object>();
		m_item.put("title", "Jay Chou");
		m_item.put("album_pic", m_JayAlbumIds);
		m_listItems.add(m_item);

		// Taylor Swift
		m_item = new HashMap<String, Object>();
		m_item.put("title", "Taylor Swift");
		m_item.put("album_pic", m_TaylorAlbumIds);
		m_listItems.add(m_item);

		// Hardon
		m_item = new HashMap<String, Object>();
		m_item.put("title", "Hardon");
		m_item.put("album_pic", m_TaylorAlbumIds);
		m_listItems.add(m_item);

		// Kobe
		m_item = new HashMap<String, Object>();
		m_item.put("title", "Kobe");
		m_item.put("album_pic", m_TaylorAlbumIds);
		m_listItems.add(m_item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bs_menu_music_online, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
			
		case R.id.menu_complete:
			Toast.makeText(getBaseContext(), "ÃÌº”ÕÍ≥…", Toast.LENGTH_SHORT).show();
			finish();
			break;
		default:
			break;
		}
		return false;
	}

	private class OnlineMusicBaseAdapter extends BaseAdapter {

		private TextView mTitleTextView;
		private LinearLayout mLinearLayout;
		
		@Override
		public int getCount() {
			return m_listItems.size();
		}

		@Override
		public Object getItem(int position) {
			return m_listItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint({ "ViewHolder", "InflateParams", "NewApi" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = getLayoutInflater().inflate(R.layout.bs_music_online_item,
					null);
			mTitleTextView = (TextView) v.findViewById(R.id.tv_album_title);
			mTitleTextView.setText((String) m_listItems.get(position).get(
					"title"));

			mLinearLayout = (LinearLayout) v.findViewById(R.id.lyt_album);
			int len = mLinearLayout.getChildCount();
			for (int i = 0; i < len; i++) {
				final FrameLayout layout = (FrameLayout) mLinearLayout.getChildAt(i);
				layout.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						View view = layout.getChildAt(1);
						if(view.isShown()) {
							view.setVisibility(View.INVISIBLE);
						} else {
							view.setVisibility(View.VISIBLE);
						}
					}
				});
			}

			return v;
		}
	}

}
