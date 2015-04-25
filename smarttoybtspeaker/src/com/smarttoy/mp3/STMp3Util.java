package com.smarttoy.mp3;

public class STMp3Util {

	public static final int IMG_UNKNOWN = 0;
	public static final int IMG_PNG = 1;
	public static final int IMG_JPEG = 2;
	
	private String m_file;
	
	public STMp3Util(String filePath) {
		m_file = filePath;
	}
	
	public String getMp3File() {
		return m_file;
	}
	
	public void setMp3File(String path) {
		m_file = path;
	}
	
	public native static String sayHello();
	
	public native boolean init();
	public native void uninit();
	//mp3��غ���
	public native String getTitle();		// ��ȡ ����
	public native String getArtist();		// ��ȡ������
	public native String getAlbum();		// ��ȡר��
	public native String getAlbumArtist();	// ��ȡר��������
//	public native String getGenre();		// ��ȡ����
//	public native String getTrack();		//
//	public native String getYear();			// 
	public native String getComment();		// ��ȡע��
//	public native String getDiscNumber();
//	public native String getComposer();
	public native byte[] getAlbumCover();	// ��ȡ����ͼƬ��Ϣ(�п��ܷ��ؿ�)
	public native int getAlbumCoveType();	// ����ͼƬ�����ͣ�������ͼƬ�Ļ�ȡ���ؿ�ʱ���˷���ֵ������ ��
	
	static {
		System.loadLibrary("mp3util");
	}
}
