package com.smarttoy.bluetoothspeaker.ui.radio;

/*
 * @author: BruceZhang
 * @description: radio panel;
 */

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;
import com.smarttoy.bluetoothspeaker.ui.BSAlbum;
import com.smarttoy.bluetoothspeaker.ui.BSApplication;

public class BSRadioPanelActivity extends BSActionBarActivity {

	private GridView m_gridView;
	private RadioBaseAdapter m_adapter;
	private List<BSAlbum> m_listItems;
	public static final int REQUEST_CODE_ALBUM_DATA = 1000;
	public static final int RESULT_CODE_ALBUM_DATA = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_panel_radio);
		setActionBarCenterTitle(getResources().getString(
				R.string.bs_radio_music));
		initListView();
	}

	private void initListView() {
		m_gridView = (GridView) findViewById(R.id.gv_radio);
		m_listItems = new ArrayList<BSAlbum>();
		m_adapter = new RadioBaseAdapter();
		m_gridView.setAdapter(m_adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bs_menu_radio_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		case R.id.menu_add:
			Intent intent = new Intent(BSRadioPanelActivity.this,
					BSRadioAddOnlineMusicActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ALBUM_DATA);
			break;

		default:
			break;
		}

		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_ALBUM_DATA
				&& resultCode == RESULT_CODE_ALBUM_DATA) {
			if (data.getBooleanExtra("data_selected", false)) {
				m_listItems = BSApplication.getData();
				m_adapter.notifyDataSetChanged();
			}
		}
	}

	private class RadioBaseAdapter extends BaseAdapter {

		private TextView mTitleTV;
		private TextView mArtistTV;
		private ImageView mImgAlbum;
		private FrameLayout mFrameLayout;

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

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = getLayoutInflater().inflate(R.layout.bs_radio_music_item,
					null);
			mTitleTV = (TextView) v.findViewById(R.id.tv_radio_title);
			mArtistTV = (TextView) v.findViewById(R.id.tv_radio_artist);
			mFrameLayout = (FrameLayout) v.findViewById(R.id.fll_radio_album);
			mImgAlbum = (ImageView) mFrameLayout.getChildAt(0);

			BSAlbum album = m_listItems.get(position);

			mTitleTV.setText(album.getTitle());
			mArtistTV.setText(album.getArtist());
			mImgAlbum.setImageDrawable(album.getCover());

			return v;
		}
	}

}
