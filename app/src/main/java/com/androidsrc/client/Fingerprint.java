package com.androidsrc.client;

import android.app.Activity;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class Fingerprint extends Activity {

    String source="";
    FingerPrintAuthHelper mFingerPrintAuthHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, new FingerPrintAuthCallback() {
            @Override
            public void onNoFingerPrintHardwareFound() {
                Toast.makeText(getApplicationContext(),"No Fingerprint Hardware",Toast.LENGTH_SHORT);
            }

            @Override
            public void onNoFingerPrintRegistered() {
                Toast.makeText(getApplicationContext(),"No Fingerprint Entries",Toast.LENGTH_SHORT);
            }

            @Override
            public void onBelowMarshmallow() {
                Toast.makeText(getApplicationContext(),"You need a newer device.",Toast.LENGTH_SHORT);
            }

            @Override
            public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
                Intent i;
                if (source.equals("course")){
                    i = new Intent(getApplicationContext(),Courses.class);
                }else{
                    i = new Intent(getApplicationContext(),MainActivity.class);
                }
                startActivity(i);
            }

            @Override
            public void onAuthFailed(int i, String s) {
                Toast.makeText(getApplicationContext(),"Invalid Fingerprint.",Toast.LENGTH_SHORT);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        source = getIntent().getStringExtra("source");
        if (source.equals("course")){
            ((TextView) findViewById(R.id.fp_text)).setText("Register Fingerprint");
        }else
            ((TextView) findViewById(R.id.fp_text)).setText("Authenticate Fingerprint");

    }

    @Override
    protected void onResume() {
        super.onResume();
        //start finger print authentication
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

}
