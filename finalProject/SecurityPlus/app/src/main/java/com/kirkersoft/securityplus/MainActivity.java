package com.kirkersoft.securityplus;

import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.EditText;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.kirkersoft.securityplus.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the buttons */
    public void sendMessage1(View view) {
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        Intent intent = new Intent(this, TimeClock.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);
        startActivity(intent);
    }

    public void sendMessage2(View view) {
        // Do something in response to button
    }

    public void sendMessage3(View view) {
        // Do something in response to button
    }

}
