package com.shengui.app.android.shengui.android.ui.utilsview;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author ymw
 * 
 */
public class Request {
	public static InputStream HandlerData(String url) {
		InputStream inStream = null;

		try {
			URL feedUrl = new URL(url);
			URLConnection conn = feedUrl.openConnection();
			conn.setConnectTimeout(10 * 1000);
			inStream = conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inStream;
	}

	public static Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
			Log.d("test", e.getMessage());
		}
		if (drawable == null) {
			Log.d("test", "null drawable");
		} else {
			Log.d("test", "not null drawable");
		}

		return drawable;
	}
}