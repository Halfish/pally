package com.smarttoy.bluetoothspeaker.ui.radio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;

public class BSRadioPanelActivity extends BSActionBarActivity {

	private ActionBar m_actionBar;
	private ListView m_listView;
	private RadioBaseAdapter m_adapter;
	private List<Map<String, String>> m_listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bs_activity_panel_radio);
		setActionBarCenterTitle(getResources().getString(R.string.bs_radio));
		initActionBar();
		initListView();
	}

	private void initActionBar() {
		m_actionBar = getActionBar();
		m_actionBar.setTitle(R.string.bs_radio_music);
	}

	private void initListView() {
		m_listView = (ListView) findViewById(R.id.lv_radio);

		m_listItems = new ArrayList<Map<String, String>>();

		Map<String, String> item = new HashMap<String, String>();
		item.put("title1", getResources().getString(R.string.bs_radio_title1));
		item.put("title2", getResources().getString(R.string.bs_radio_title2));
		m_listItems.add(item);

		item = new HashMap<String, String>();
		item.put("title1", getResources().getString(R.string.bs_radio_title3));
		item.put("title2", getResources().getString(R.string.bs_radio_title4));
		m_listItems.add(item);

		item = new HashMap<String, String>();
		item.put("title1", getResources().getString(R.string.bs_radio_title5));
		item.put("title2", getResources().getString(R.string.bs_radio_title6));
		m_listItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("title1", getResources().getString(R.string.bs_radio_title7));
		item.put("title2", getResources().getString(R.string.bs_radio_title8));
		m_listItems.add(item);
		
		item = new HashMap<String, String>();
		item.put("title1", getResources().getString(R.string.bs_radio_title9));
		item.put("title2", getResources().getString(R.string.bs_radio_title10));
		m_listItems.add(item);

		m_adapter = new RadioBaseAdapter();
		m_listView.setAdapter(m_adapter);
		m_listView.setItemsCanFocus(true);

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
			startActivity(intent);
			break;

		default:
			break;
		}

		return false;
	}

	private class RadioBaseAdapter extends BaseAdapter {

		private TextView mTitleTextView1;
		private TextView mTitleTextView2;
		private FrameLayout mLeftFly; 
		private FrameLayout mRightFly; 

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
			View v = getLayoutInflater().inflate(R.layout.bs_radio_panel_item,
					null);
			mTitleTextView1 = (TextView) v.findViewById(R.id.tv_radio_title1);
			mTitleTextView1.setText((String) m_listItems.get(position).get(
					"title1"));

			mTitleTextView2 = (TextView) v.findViewById(R.id.tv_radio_title2);
			mTitleTextView2.setText((String) m_listItems.get(position).get(
					"title2"));

			mLeftFly = (FrameLayout) v.findViewById(R.id.fll_radio_left);
			mRightFly = (FrameLayout) v.findViewById(R.id.fll_radio_right);
			
			mLeftFly.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					View view = ((ViewGroup) v).getChildAt(1);
					if (view.isShown()) {
						view.setVisibility(View.INVISIBLE);
					} else {
						view.setVisibility(View.VISIBLE);
					}
				}
			});
			
			mRightFly.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					View view = ((ViewGroup) v).getChildAt(1);
					if (view.isShown()) {
						view.setVisibility(View.INVISIBLE);
					} else {
						view.setVisibility(View.VISIBLE);
					}
				}
			});
			
			return v;
		}
	}

}
