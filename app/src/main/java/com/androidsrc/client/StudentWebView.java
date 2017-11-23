package com.androidsrc.client;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

public class StudentWebView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_web_view);

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String usn=sharedpreferences.getString("usn","null");

        WebView mWebview = (WebView) findViewById(R.id.studentweb1);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview .loadUrl("http://automated-attendance.tk/sprofile/e9bdcecc9821945aab949553d30af2fc/"+usn);
    }
}
