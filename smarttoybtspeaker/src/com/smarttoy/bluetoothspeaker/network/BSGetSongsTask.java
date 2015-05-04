package com.smarttoy.bluetoothspeaker.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smarttoy.bluetoothspeaker.network.BSHttpGetResult.OnGetResultCallBack;

public class BSGetSongsTask implements OnGetResultCallBack {
	public interface OnSongsGetCallBack {
		public void onSongsGet(List<Map<String, Object>> list, int channelSeq);
	}

	private OnSongsGetCallBack m_callback;
	private BSHttpGetResult m_result;
	private String m_uri;
	private String m_channelId;
	private int m_channelSeq;

	public BSGetSongsTask() {
	}

	public BSGetSongsTask(String id, int channelSeq, OnSongsGetCallBack callback) {
		m_channelId = id;
		m_channelSeq = channelSeq;
		m_callback = callback;
	}

	public void start() {
		m_uri = String.format(Locale.CHINA, "http://douban.fm/j/mine/playlist?"
				+ "from=mainsite&channel=%s&kbps=128&type=n", m_channelId);
		m_result = new BSHttpGetResult(m_uri, this);
		m_result.queryBegin(3000);
	}

	@Override
	public void onResult(String result) {

		List<Map<String, Object>> songList = new ArrayList<Map<String, Object>>();
		Map<String, Object> songMap;

		try {
			JSONObject json = new JSONObject(result);
			JSONArray songs = json.getJSONArray("song");

			for (int i = 0; i < songs.length(); i++) {
				String picture = songs.getJSONObject(i).getString("picture");
				String artist = songs.getJSONObject(i).getString("artist");
				String url = songs.getJSONObject(i).getString("url");
				String title = songs.getJSONObject(i).getString("title");

				songMap = new HashMap<String, Object>();
				songMap.put("channel_id", m_channelId);
				songMap.put("song_picture", picture);
				songMap.put("song_artist", artist);
				songMap.put("song_url", url);
				songMap.put("song_title", title);

				songList.add(songMap);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		m_callback.onSongsGet(songList, m_channelSeq);
	}
}
