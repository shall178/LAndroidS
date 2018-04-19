package com.ad.linsn.linsnandroids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("MainActivity","Activity start");

        Intent intent = new Intent(MainActivity.this, LinsnServer.class);
        startService(intent);

    }
}
