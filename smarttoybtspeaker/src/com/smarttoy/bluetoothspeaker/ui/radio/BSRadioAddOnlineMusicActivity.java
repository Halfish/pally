package com.smarttoy.bluetoothspeaker.ui.radio;

import java.util.ArrayList;
import java.util.List;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;
import com.smarttoy.bluetoothspeaker.ui.BSAlbum;
import com.smarttoy.bluetoothspeaker.ui.BSConfigure;

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

	private String[] m_channels = new String[] { "时下热门", "新闻",
			"音乐", "生活", "教育", "经济", "军事", "老年之声" };
	private static final int ALBUM_NUM_PER_CHANNEL = 4;
	
	private int m_radioCover[] = new int [] {
		R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, 
		R.drawable.r5, R.drawable.r6, R.drawable.r7, R.drawable.r8,
		R.drawable.r9, R.drawable.r10, R.drawable.r11, R.drawable.r11,
		R.drawable.r13, R.drawable.r14, R.drawable.r15, R.drawable.r16,
		R.drawable.r17, R.drawable.r18, R.drawable.r20, R.drawable.r19, 
		R.drawable.r21, R.drawable.r22, R.drawable.r23, R.drawable.r24,
		R.drawable.r25, R.drawable.r26, R.drawable.r26, R.drawable.r28,
		R.drawable.r29, R.drawable.r30, R.drawable.r31, R.drawable.r32,
	};
	
	private String m_radioAlbum[][] = new String [][] {
			{"坏蛋调频", "冬吴相对论", "chiliko聊日本", "电影不无聊"}, 
			{"新闻联播", "新闻空间（粤）", "央广新闻", "早安台湾（闽南语）"},
			{"MusicRadioTop排行", "MusicRadioTop排行（2）", "全球流行音乐榜", "全球流行音乐榜"},
			{"午夜剧场", "午夜听书", "学讲普通话", "养生大讲堂"},
			{"Learning English for China", "Slow French", "6 Minute English", "TED"},
			{"股市快评", "天天财经", "证券大本营", "天下公司"},
			{"国防军事干线", "国防时空", "国防时空（二）", "海峡军事漫谈"},
			{"健康之家", "评述听天下", "清晨有约", "夕阳红茶馆"}
	};
	
	private boolean m_albumSelected[];

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
		m_albumSelected = new boolean[50];

		
		int index = 0;
		
		for (int i = 0; i < m_channels.length; i++) {
			String s[] = m_radioAlbum[i];
			for (int j = 0; j < s.length; j++) {
				m_item = new BSAlbum(index, s[j], "", getResources()
						.getDrawable(m_radioCover[index]));
				m_listItems.add(m_item);
				m_albumSelected[i] = false;
				index ++;
			}
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
		for (int i = 0; i < 32; i++) {
			if (m_albumSelected[i]) {
				m_listSelected.add(m_listItems.get(i));
			}
		}

		Intent intent = getIntent();
		BSConfigure.setData(m_listSelected);
		intent.putExtra("data_selected", true);
		setResult(BSRadioPanelActivity.RESULT_CODE_ALBUM_DATA, intent);
	}

	private class OnlineMusicBaseAdapter extends BaseAdapter {

		private TextView mChannelName;
		private LinearLayout mLinearLayout;
		private LinearLayout mAlbumView;

		@Override
		public int getCount() {
			return m_channels.length;
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
