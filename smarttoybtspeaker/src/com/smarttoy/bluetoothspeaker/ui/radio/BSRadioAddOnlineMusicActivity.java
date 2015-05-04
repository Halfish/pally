package com.smarttoy.bluetoothspeaker.ui.radio;

import java.util.ArrayList;
import java.util.List;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;
import com.smarttoy.bluetoothspeaker.ui.BSAlbum;
import com.smarttoy.bluetoothspeaker.ui.BSApplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BSRadioAddOnlineMusicActivity extends BSActionBarActivity {

	private int[] m_JayAlbumIds = new int[] { R.drawable.album_jay_1,
			R.drawable.album_jay_2, R.drawable.album_jay_3,
			R.drawable.album_jay_4, R.drawable.album_jay_5,
			R.drawable.album_jay_6, R.drawable.album_jay_7,
			R.drawable.album_jay_8, R.drawable.album_jay_9 };
	private String[] m_channels = new String[] { "Hip Hop", "Country Music",
			"Blues" };
	private boolean m_albumSelected[];

	private static final int ALBUM_NUM_PER_CHANNEL = 3;
	private static final int CHANNEL_NUM = 3;

	private ListView m_listView;
	private OnlineMusicBaseAdapter m_adapter;
	private List<BSAlbum> m_listItems;
	private ArrayList<BSAlbum> m_listSelected;
	private BSAlbum m_item;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_radio_add_online);
		getBaseContext();

		initData();
		setActionBarCenterTitle(getResources().getString(
				R.string.bs_hot_channels));
		initListView();
	}

	private void initListView() {
		m_listView = (ListView) findViewById(R.id.lv_radio_online);
		m_adapter = new OnlineMusicBaseAdapter();
		m_listView.setAdapter(m_adapter);
	}

	private void initData() {
		m_listItems = new ArrayList<BSAlbum>();
		m_listSelected = new ArrayList<BSAlbum>();
		m_albumSelected = new boolean[ALBUM_NUM_PER_CHANNEL * CHANNEL_NUM];

		for (int i = 0; i < ALBUM_NUM_PER_CHANNEL * CHANNEL_NUM; ++i) {
			m_item = new BSAlbum(i, "title" + i, "artist" + i, getResources()
					.getDrawable(m_JayAlbumIds[i]));
			m_listItems.add(m_item);
			m_albumSelected[i] = false;
		}
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
			setResultForRadioPanel();
			finish();
			break;

		default:
			break;
		}
		return false;
	}

	private void setResultForRadioPanel() {
		for (int i = 0; i < ALBUM_NUM_PER_CHANNEL * CHANNEL_NUM; i++) {
			if (m_albumSelected[i]) {
				m_listSelected.add(m_listItems.get(i));
			}
		}

		Intent intent = getIntent();
		BSApplication.setData(m_listSelected);
		intent.putExtra("data_selected", true);
		setResult(BSRadioPanelActivity.RESULT_CODE_ALBUM_DATA, intent);
	}

	private class OnlineMusicBaseAdapter extends BaseAdapter {

		private TextView mChannelName;
		private LinearLayout mLinearLayout;
		private LinearLayout mAlbumView;

		@Override
		public int getCount() {
			return CHANNEL_NUM;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = getLayoutInflater().inflate(R.layout.bs_radio_online_item,
					null, false);
			mChannelName = (TextView) v.findViewById(R.id.tv_channel_name);
			mChannelName.setText(m_channels[position]);

			// add every album to this LinearLayout
			mLinearLayout = (LinearLayout) v.findViewById(R.id.lyt_album);

			for (int i = 0; i < ALBUM_NUM_PER_CHANNEL; ++i) {

				mAlbumView = (LinearLayout) getLayoutInflater().inflate(
						R.layout.bs_radio_music_item, null, false);
				FrameLayout layout = (FrameLayout) mAlbumView.getChildAt(0);

				final BSAlbum album = m_listItems.get(position
						* ALBUM_NUM_PER_CHANNEL + i);
				final int index = position * ALBUM_NUM_PER_CHANNEL + i;

				((ImageView) layout.getChildAt(0)).setImageDrawable(album
						.getCover());
				((TextView) mAlbumView.findViewById(R.id.tv_radio_title))
						.setText(album.getTitle());
				((TextView) mAlbumView.findViewById(R.id.tv_radio_artist))
						.setText(album.getArtist());

				layout.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						View view = ((ViewGroup) v).getChildAt(1);
						if (view.isShown()) {
							view.setVisibility(View.INVISIBLE);
							m_albumSelected[index] = false;
						} else {
							view.setVisibility(View.VISIBLE);
							m_albumSelected[index] = true;
						}
					}
				});

				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(10, 10, 10, 10);

				// add this album to LinearLayout
				mLinearLayout.addView(mAlbumView, lp);
			}

			return v;
		}
	}

}
