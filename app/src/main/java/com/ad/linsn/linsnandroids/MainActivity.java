package com.ad.linsn.linsnandroids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("MainActivity","Activity start");

        Intent intent = new Intent(MainActivity.this, LinsnServer.class);
        startService(intent);

    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();
//        if(UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))
//        {
//            Intent it = new Intent(context, LinsnServer.class);
//            context.startService(new Intent(this, LinsnServer.class););
//        }
//
//
//    }
}
