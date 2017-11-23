package com.androidsrc.client;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView response;
	EditText editTextAddress, editTextPort;
	Button buttonConnect, buttonClear;
	int id;
	int status=0;


	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		editTextAddress = (EditText) findViewById(R.id.addressEditText);
//		editTextPort = (EditText) findViewById(R.id.portEditText);
		buttonConnect = (Button) findViewById(R.id.connectButton);
		buttonClear = (Button) findViewById(R.id.clearButton);
		response = (TextView) findViewById(R.id.responseTextView);
		WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
						wifiManager.disconnect();


		buttonConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {



				final String ssid="Oneplus3";
				String key="1234567a";
				WifiConfiguration wifiConfig = new WifiConfiguration();
				wifiConfig.SSID = String.format("\"%s\"", ssid);
				wifiConfig.preSharedKey = String.format("\"%s\"", key);

				final WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
//remember id
				final int netId = wifiManager.addNetwork(wifiConfig);
				id=netId;
				int i=0;

				while(true) {
					wifiManager.disconnect();
					wifiManager.setWifiEnabled(true);
					wifiManager.enableNetwork(netId, true);
					wifiManager.reconnect();

				try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}


					ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					WifiInfo info = wifiManager.getConnectionInfo ();
					String cssid  = info.getSSID();
					String mssid = "\""+ssid+"\"";

//					Toast.makeText(getApplicationContext() ,String.valueOf(mWifi.isConnected()), Toast.LENGTH_SHORT).show();
//				    Toast.makeText(getApplicationContext() ,mssid, Toast.LENGTH_SHORT).show();
//					Toast.makeText(getApplicationContext() ,cssid, Toast.LENGTH_SHORT).show();

					if (mWifi.isConnected() && cssid.equals(mssid)) {
						// Do whatever
						Toast.makeText(getApplicationContext() , "Connection Established", Toast.LENGTH_SHORT).show();
						Client myClient = new Client("192.168.43.1", 8080, response,MainActivity.this);
						myClient.execute();
						status=1;
						break;
					}
					i++;
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (i > 29) {
						status=0;
						break;
					}

				}

				if(status==0){
					response.setText("could not connect");
				}


//				ThreadManager.runInBackgroundThenUi(new Runnable() {
//					@Override
//					public void run() {
//						// wait for that tasty WiFi
//						Toast.makeText(getApplicationContext() , "First Block", Toast.LENGTH_SHORT).show();
//					}
//				}, new Runnable() {
//					@Override
//					public void run() {
//						// GUYS I HAVE THE WIFI!!
//						Toast.makeText(getApplicationContext() , "Second Block", Toast.LENGTH_SHORT).show();
//					}
//				});


//				while(true) {
//					wifiManager.enableNetwork(netId, true);
//					wifiManager.reconnect();
//
//					ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//					NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//					if (mWifi.isConnected()) {
//						// Do whatever
////						Client myClient = new Client("192.168.1.1", 80, response,MainActivity.this);
////						myClient.execute();
//						Log.d("log","connected");
//						response.setText("connected");
//						break;
//					}
//
//				}
			}
		});

		buttonClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				response.setText("");
			}
		});
	}

	public void disconnect(){
		WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
		wifiManager.removeNetwork(id);
		wifiManager.saveConfiguration();
		wifiManager.setWifiEnabled(false);
	}

	public void onBackPressed(){
		Intent a = new Intent(Intent.ACTION_MAIN);
		a.addCategory(Intent.CATEGORY_HOME);
		a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(a);

	}
}
