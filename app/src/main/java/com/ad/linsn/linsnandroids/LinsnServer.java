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
    private int SleepTime = 5;


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
                        AnalysisOrder(buffer,inputStream,outputStream);

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

    private void AnalysisOrder(String str,InputStream inputStream, OutputStream outputStream){
        Log.e(TAG,"AnalysisOrder start");
        if(str.substring(0,4).equals("GET+")) {
            int i = Integer.getInteger(str.substring(4));
            if(i > 10 )
                return;
            GetOrder(i);
            try {
                outputStream.write(GetOrder(i).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(str.substring(0,4).equals("SET+")){


        }else Log.e("TAG","get order error");


    }

    private String GetOrder(int i) {
        switch (i){
            case 0:{
                DeviceInfo di = new DeviceInfo(getApplicationContext());
                return di.getDevInfo();
            }

            case 1:{
                //wifi scaninfo

            }

            case 2:{
                // ethernet info

            }

            case 3:{
                //ap info
                APManager apManager = new APManager(getApplicationContext());
                return apManager.getApInfo();
            }

            case 4:{
                // hdmi info
                OtherFunction otherFunction = new OtherFunction(getApplicationContext());
                return otherFunction.get_hdmi_mode(0);
            }

            case 5:{
                // audio info
                AudioManage audioManage = new AudioManage(getApplicationContext());
                return audioManage.get_volume();
            }

            case 6:{
                //time
                return String.valueOf(SleepTime);
            }

            case 7:{
                OtherFunction otherFunction = new OtherFunction(getApplicationContext());
                return otherFunction.get_hdmi_mode(1);
            }

        }




    }


    //清空packageName，使Thread一直运行下去，如果接收到新的参数，则结束线程，重新开始。
    class appMonitor extends Thread{

        public void run(){
            AppMonitor am = new AppMonitor(getApplicationContext(), packageName);
            packageName = null;

            do {
                am.appMonitorStart();

                try {
                    Thread.sleep(SleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }while(packageName == null);
        }
    }

}
