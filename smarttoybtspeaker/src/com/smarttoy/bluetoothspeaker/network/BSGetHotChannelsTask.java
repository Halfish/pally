package com.smarttoy.bluetoothspeaker.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smarttoy.bluetoothspeaker.network.BSHttpGetResult.OnGetResultCallBack;

public class BSGetHotChannelsTask implements OnGetResultCallBack {
	public interface onHotChannelsGetCallBack {
		public void onHotChannelsGet(List<Map<String, Object>> list);
	}

	private onHotChannelsGetCallBack m_callback;
	private BSHttpGetResult m_result;
	private static final String HOT_CHANNEL_URI = "http://douban.fm/j/explore/hot_channels";

	public BSGetHotChannelsTask() {
	}

	public BSGetHotChannelsTask(onHotChannelsGetCallBack callback) {
		m_callback = callback;
	}

	public void start() {
		m_result = new BSHttpGetResult(HOT_CHANNEL_URI, this);
		m_result.queryBegin(3000);
	}

	@Override
	public void onResult(String result) {

		List<Map<String, Object>> channelList = new ArrayList<Map<String, Object>>();
		Map<String, Object> channelMap;

		try {
			JSONObject json = new JSONObject(result);
			JSONArray channels = json.getJSONObject("data").getJSONArray(
					"channels");

			for (int i = 0; i < channels.length(); i++) {
				String name = channels.getJSONObject(i).getString("name");
				int id = channels.getJSONObject(i).getInt("id");

				channelMap = new HashMap<String, Object>();
				channelMap.put("channel_id", id + "");
				channelMap.put("channel_name", name);
				channelList.add(channelMap);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		m_callback.onHotChannelsGet(channelList);
	}
}
