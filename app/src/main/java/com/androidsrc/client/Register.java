package com.androidsrc.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usn= (EditText) findViewById(R.id.USNvalue);
        final EditText pass= (EditText) findViewById(R.id.PASSvalue);
        final EditText mac= (EditText) findViewById(R.id.MACvalue);
        Button register= (Button) findViewById(R.id.register);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://app.automated-attendance.tk/student-register/";

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String state = sharedpreferences.getString("STATE","null");
        if(state.equals("NEXT")){
            Intent inent = new Intent(getApplicationContext(), Courses.class);
            startActivity(inent);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url+usn.getText().toString()+"/"+mac.getText().toString()+"/"+pass.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
//                                mTextView.setText("Response is: "+ response.substring(0,500));
                                if(response.equals("OK")){
                                    Toast.makeText(getApplicationContext() , "Loged in", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedpreferences;
                                    sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("usn", usn.getText().toString());
                                    editor.putString("STATE", "NEXT");
                                    editor.commit();
                                    Intent inent = new Intent(getApplicationContext(), Fingerprint.class);  //Courses
                                    inent.putExtra("source","course");
                                    startActivity(inent);

                                }
                                else {
                                    Toast.makeText(getApplicationContext() , response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mTextView.setText("That didn't work!");
                        Toast.makeText(getApplicationContext() , "RError", Toast.LENGTH_SHORT).show();
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String state = sharedpreferences.getString("STATE","null");
        if(state.equals("NEXT")){
            this.finish();
        }

    }
}
