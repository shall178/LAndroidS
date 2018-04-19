package com.ad.linsn.linsnandroids;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LinsnServer extends Service {
    final String TAG = "LinsnServer";
    private final int PORT = 9090;
    private static ServerSocket serverSocket = null;
    private String packageName = "com.gf.test.videoplayer";
    private int SleepTime = 30;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"LinsnServer onBind");
        return null;
    }

    public  int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(TAG,"LinsnServer onStartCommand");

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        SerThread st = new SerThread();
        st.start();

//        appMonitor appm = new appMonitor();
//        appm.start();

        return START_STICKY;
    }


    public void onDestroy() {
        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    class SerThread extends Thread{
        Socket socket;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte buffer[] = new byte[1024];

        public void run(){
            while(true){
                try {

                    socket = serverSocket.accept();
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();


                    inputStream.read(buffer,0,1023);
                    if(buffer.length > 0)
                        AnalysisOrder();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(inputStream != null) inputStream.close();
                        if(outputStream !=null) outputStream.close();
                        if(socket != null) socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void AnalysisOrder(){
        Log.e(TAG,"AnalysisOrder start");


    }


    class appMonitor extends Thread{

        public void run(){
            AppMonitor am = new AppMonitor(getApplicationContext(), packageName);
            packageName = null;

            do {
                if(am.isAppRunning()) {
                    try {
                        Thread.sleep(SleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                am.restartApp();
            }while(packageName.isEmpty());
        }
    }

}
