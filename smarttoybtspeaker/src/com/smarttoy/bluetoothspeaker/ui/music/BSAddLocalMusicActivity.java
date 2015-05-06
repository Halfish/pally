package com.smarttoy.bluetoothspeaker.ui.music;

/*
 * @author: BruceZhang
 * @last edit: 2015-3-25
 * @description: add local music files
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smarttoy.bluetoothspeaker.R;
import com.smarttoy.bluetoothspeaker.ui.BSActionBarActivity;
import com.smarttoy.bluetoothspeaker.ui.BSApplication;
import com.smarttoy.mp3.STMp3Util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BSAddLocalMusicActivity extends BSActionBarActivity implements
		OnErrorListener, OnCompletionListener {

	private static final String LOG_TAG = BSAddLocalMusicActivity.class.getName();
	
	private Context m_context;
	private LayoutInflater m_inflater;
	private ListView m_listView;
	private LocalMusicBaseAdapter m_adapter;
	private List<Map<String, Object>> m_listItems;
	private Map<String, Object> item;

	private MediaPlayer m_mediaPlayer;

	private List<String> m_musicList;
	private List<SONG_STATE> m_songStates;
	private List<Boolean> m_songSelected;
	private Handler m_handler;
	private ProgressDialog m_progressDialog;

	public static final int NEW_FILE_FOUND = 1;
	public static final int SEARCH_MUSIC_COMPLETE = 2;
	public static final int MINIMUM_SONG_SIZE_BYTES = 1000 * 1024;

	public enum MEDIA_PLAYER_STATE {
		IDLE, PLAYING, PAUSE
	}

	public enum SONG_STATE {
		IDLE, PLAYING, PAUSE
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bs_activity_music_add_local);
		m_context = getBaseContext();
		m_inflater = getLayoutInflater();
		setActionBarCenterTitle(getResources().getString(
				R.string.bs_music_local));

		initHandler();
		initListView();
		initMediaPlayer();
	}

	@Override
	protected void onDestroy() {
		if (m_mediaPlayer != null) {
			m_mediaPlayer.stop();
			m_mediaPlayer.release();
		}
		super.onDestroy();
	}

	@SuppressLint("HandlerLeak")
	private void initHandler() {
		// TODO need to debug
		m_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case NEW_FILE_FOUND:
					String name = msg.getData().getString("name");
					addNewMusicFile(name);
					break;

				case SEARCH_MUSIC_COMPLETE:
					m_progressDialog.dismiss();
					break;

				default:
					break;
				}
			}
		};
	}

	private void addNewMusicFile(String name) {
		item = new HashMap<String, Object>();

		Bitmap bitmap;
		bitmap = getAlbumCover(m_musicList.get(m_musicList.size() - 1));
		if (bitmap != null) {
			item.put("album", new BitmapDrawable(getResources(), bitmap));
		} else {
			item.put("album", getResources()
					.getDrawable(R.drawable.album_jay_1));
		}
		
		item.put("name", name);
		m_listItems.add(item);
		m_adapter.notifyDataSetChanged();
	}

	private Bitmap getAlbumCover(String path) {
		STMp3Util m_util = new STMp3Util(path);

		if (!m_util.init()) {
			Log.e(LOG_TAG, "getAlbumCover: open mp3 file failed");
			return null;
		}

		byte[] img = m_util.getAlbumCover();
		
		if (img == null) {
			Log.e(LOG_TAG, "getAlbumCover: parse image empty!");
			return null;
		}

		BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
		bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length,
				bitmapOption);
		return bitmap;
	}

	private void initListView() {
		m_listItems = new ArrayList<Map<String, Object>>();
		m_adapter = new LocalMusicBaseAdapter();
		m_listView = (ListView) findViewById(R.id.lv_music_local);
		m_listView.setAdapter(m_adapter);

		m_listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				playSong(position);
				m_adapter.notifyDataSetChanged();
			}
		});

		retrieveMusicFiles();
	}

	private void initMediaPlayer() {
		m_mediaPlayer = new MediaPlayer();
		m_mediaPlayer.setOnErrorListener(this);
		m_mediaPlayer.setOnCompletionListener(this);
	}

	private void playSong(int position) {

		SONG_STATE currState = m_songStates.get(position);

		for (int i = 0; i < m_musicList.size(); ++i) {
			m_songStates.set(i, SONG_STATE.IDLE);
		}

		switch (currState) {
		case IDLE:
			// when idle, begin to play song
			String s = m_musicList.get(position);
			try {
				m_mediaPlayer.reset();
				m_mediaPlayer.setDataSource(s);
				m_mediaPlayer.prepare();
				m_mediaPlayer.start();
				m_songStates.set(position, SONG_STATE.PLAYING);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case PLAYING:
			// when already playing, go to pause
			m_mediaPlayer.pause();
			m_songStates.set(position, SONG_STATE.PAUSE);
			break;

		case PAUSE:
			// resume
			m_mediaPlayer.start();
			m_songStates.set(position, SONG_STATE.PLAYING);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Log.e("Local Music: onError()", "MediaPlayer error");
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("Local Music: onCompletion()", "MediaPlayer complete");
	}

	private void retrieveMusicFiles() {
		// Does SD card exists ?
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			m_progressDialog = new ProgressDialog(this);
			m_progressDialog.setMessage(getResources().getString(
					R.string.bs_music_searching));
			m_progressDialog.show();

			new Thread(new Runnable() {
				// filename extensions
				String[] ext = { ".mp3", ".wav" };
				File file = Environment.getExternalStorageDirectory();

				public void run() {
					if (m_musicList != null) {
						m_musicList.clear();
						m_songStates.clear();
						m_listItems.clear();
						m_songSelected.clear();
					} else {
						m_musicList = new ArrayList<String>();
						m_songStates = new ArrayList<BSAddLocalMusicActivity.SONG_STATE>();
						m_songSelected = new ArrayList<Boolean>();
					}
					doRetrieveFiles(file, ext);
					m_handler.sendEmptyMessage(SEARCH_MUSIC_COMPLETE);
				}
			}).start();

		} else {
			Toast.makeText(m_context, "No sdcard~", Toast.LENGTH_SHORT).show();
		}
	}

	private void doRetrieveFiles(File file, String[] ext) {
		if (file != null) {
			if (file.isDirectory()) {
				File[] listFile = file.listFiles();
				if (listFile != null) {
					for (int i = 0; i < listFile.length; i++) {
						doRetrieveFiles(listFile[i], ext);
					}
				}

			} else {
				String fullname = file.getAbsolutePath();
				for (int i = 0; i < ext.length; i++) {
					if (fullname.endsWith(ext[i])
							&& (file.getTotalSpace() > MINIMUM_SONG_SIZE_BYTES)) {
						m_musicList.add(fullname);
						m_songStates.add(SONG_STATE.IDLE);
						m_songSelected.add(false);
						m_handler.sendMessage(getStringMessage(NEW_FILE_FOUND,
								"name", file.getName()));
						break;
					}
				}
			}
		}
	}

	private Message getStringMessage(int what, String key, String value) {
		Bundle bundle = new Bundle();
		bundle.putString(key, value);
		Message msg = new Message();
		msg.what = what;
		msg.setData(bundle);
		return msg;
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

		case R.id.menu_complete:
			ArrayList<Map<String, Object>> albums = new ArrayList<Map<String,Object>>();
			for (int i = 0; i < m_songSelected.size(); i++) {
				if(m_songSelected.get(i)) {
					albums.add(m_listItems.get(i));
				}
			}
			BSApplication.setSimpleAlbum(albums);
			setResult(BSMusicPanelActivity.RESULT_CODE);
			finish();
			break;

		default:
			break;
		}

		return false;
	}

	private class LocalMusicBaseAdapter extends BaseAdapter {

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

		@SuppressLint({ "InflateParams", "ViewHolder" })
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = m_inflater.inflate(R.layout.bs_music_local_item,
					parent, false);
			ImageView imageView = (ImageView) view.findViewById(R.id.img_album);
			TextView textView = (TextView) view.findViewById(R.id.tv_name);
			ImageView imageButton = (ImageView) view
					.findViewById(R.id.btn_play_music);
			CheckBox checkBox = (CheckBox) view
					.findViewById(R.id.chBox_add_music);

			imageView.setImageDrawable((Drawable) m_listItems.get(position)
					.get("album"));
			textView.setText((String) m_listItems.get(position).get("name"));

			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					m_songSelected.set(position, isChecked);
				}
			});

			switch (m_songStates.get(position)) {
			case IDLE:
				imageButton.setBackgroundResource(R.drawable.bs_music_play);
				break;

			case PLAYING:
				imageButton.setBackgroundResource(R.drawable.bs_music_pause);
				break;

			case PAUSE:
				imageButton.setBackgroundResource(R.drawable.bs_music_play);
				break;

			default:
				break;
			}

			return view;
		}
	}

}
