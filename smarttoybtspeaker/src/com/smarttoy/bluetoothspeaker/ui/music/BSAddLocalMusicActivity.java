package com.smarttoy.bluetoothspeaker.ui.music;

/*
 * @author: Bruce
 * @last edit: 2015-3-25
 * @description 添加本地音乐界面
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BSAddLocalMusicActivity extends BSActionBarActivity {

	private Context m_context;
	private LayoutInflater m_inflater;
	private ProgressBar m_progressBar;
	private ListView m_listView;
	private LocalMusicBaseAdapter m_adapter;
	private List<Map<String, Object>> m_listItems;
	private Map<String, Object> item;

	private boolean m_isPlayingMusic = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bs_activity_music_add_local);
		m_context = getBaseContext();
		setActionBarCenterTitle(getResources().getString(R.string.bs_music_local));
		
		initWidgets();
		initListView();

	}

	private void initWidgets() {
		m_inflater = getLayoutInflater();

		m_progressBar = (ProgressBar) findViewById(R.id.progBar_music_local);
		m_progressBar.setVisibility(View.INVISIBLE);
	}

	private void initListView() {
		m_listItems = new ArrayList<Map<String, Object>>();

		item = new HashMap<String, Object>();
		item.put("album", getResources().getDrawable(R.drawable.album_jay_6));
		item.put("name", "七里香");
		m_listItems.add(item);

		item = new HashMap<String, Object>();
		item.put("album", getResources().getDrawable(R.drawable.album_jay_7));
		item.put("name", "不能说的秘密");
		m_listItems.add(item);

		item = new HashMap<String, Object>();
		item.put("album", getResources().getDrawable(R.drawable.album_jay_8));
		item.put("name", "止战之殇");
		m_listItems.add(item);

		item = new HashMap<String, Object>();
		item.put("album", getResources().getDrawable(R.drawable.album_jay_9));
		item.put("name", "双节棍");
		m_listItems.add(item);

		m_adapter = new LocalMusicBaseAdapter();
		m_listView = (ListView) findViewById(R.id.lv_music_local);
		m_listView.setAdapter(m_adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bs_menu_music_local, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		case R.id.menu_search:
			m_progressBar.setVisibility(View.VISIBLE);
			break;

		case R.id.menu_complete:
			Intent intent = new Intent(BSAddLocalMusicActivity.this,
					BSSearchFinishLocalMusicActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}

		return false;
	}

	private static class ViewHolder {
		ImageView imageView;
		TextView textView;
		ImageButton imageButton;
		CheckBox checkBox;
	}

	private class LocalMusicBaseAdapter extends BaseAdapter {

		private ViewHolder mViewHolder;

		public LocalMusicBaseAdapter() {

		}

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
			return position;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View v = m_inflater.inflate(R.layout.bs_music_local_item, null);

			if (mViewHolder == null) {
				mViewHolder = new ViewHolder();
			}

			mViewHolder.imageView = (ImageView) v.findViewById(R.id.img_album);
			mViewHolder.textView = (TextView) v.findViewById(R.id.tv_name);
			mViewHolder.imageButton = (ImageButton) v
					.findViewById(R.id.btn_play_music);
			mViewHolder.checkBox = (CheckBox) v
					.findViewById(R.id.chBox_add_music);

			mViewHolder.imageView.setImageDrawable((Drawable) m_listItems.get(
					position).get("album"));
			mViewHolder.textView.setText((String) m_listItems.get(position)
					.get("name"));

			mViewHolder.imageButton
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (m_isPlayingMusic) {
								m_isPlayingMusic = false;
								((ImageButton) v)
										.setBackgroundResource(R.drawable.bs_music_pause);
							} else {
								m_isPlayingMusic = true;
								((ImageButton) v)
										.setBackgroundResource(R.drawable.bs_music_play);
							}

							Toast.makeText(m_context, "button: " + position,
									Toast.LENGTH_LONG).show();
						}
					});

			mViewHolder.checkBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Toast.makeText(
									m_context,
									"CheckBox: " + position + " checked? "
											+ isChecked, Toast.LENGTH_LONG)
									.show();
						}
					});

			return v;
		}
	}

}
