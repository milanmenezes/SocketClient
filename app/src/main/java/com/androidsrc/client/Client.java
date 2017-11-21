package com.androidsrc.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import android.os.AsyncTask;
import android.widget.TextView;

public class Client extends AsyncTask<Void, Void, Void> {

	String dstAddress;
	int dstPort;
	String response = "";
	TextView textResponse;
	MainActivity activity;

	Client(String addr, int port,TextView textResponse,MainActivity activity) {
		dstAddress = addr;
		dstPort = port;
		this.textResponse=textResponse;
		this.activity=activity;
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		Socket socket = null;

		try {
			socket = new Socket(dstAddress, dstPort);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
					1024);
			byte[] buffer = new byte[1024];

			int bytesRead;
			InputStream inputStream = socket.getInputStream();

			/*
			 * notice: inputStream.read() will block if no data return
			 */
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, bytesRead);
				response += byteArrayOutputStream.toString("UTF-8");
			}


//			activity.runOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//					if(response=="OK"){
//					activity.disconnect();
//					}
//				}
//			});

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = "UnknownHostException: " + e.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = "IOException: " + e.toString();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			activity.disconnect();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		textResponse.setText(response);
		super.onPostExecute(result);

	}

}
