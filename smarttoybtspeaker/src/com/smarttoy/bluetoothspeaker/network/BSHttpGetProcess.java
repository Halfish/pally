package com.smarttoy.bluetoothspeaker.network;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class BSHttpGetProcess {
	public String getDataFromUri(String uri, int timeOut) {
		String result = null;

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri);
		HttpResponse response = null;
		if (timeOut > 0) {
			HttpConnectionParams.setConnectionTimeout(client.getParams(),
					timeOut);
			HttpConnectionParams.setSoTimeout(client.getParams(), timeOut);
		}

		try {
			response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result == null) {
			result = "";
		}
		return result;
	}
}
