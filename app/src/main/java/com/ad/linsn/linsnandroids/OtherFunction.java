package com.ad.linsn.linsnandroids;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class OtherFunction {
    private Context mContext;


    public OtherFunction(Context context){
        this.mContext = context;
    }

/*
* get_hdmi_mode
* @param   type
*       0  ,        get current hdmi mode
*       1 ,         get hdmi support modes
*/

    public String get_hdmi_mode(int type){
        String hdmiModeFile;
        if (type == 0)
              hdmiModeFile = "/sys/class/display/HDMI/mode";
        else
             hdmiModeFile = "/sys/class/display/HDMI/modes";

        byte buffer[] = new byte[20*30];
        File mode_file = new File(hdmiModeFile);

        try {
            FileInputStream fi = new FileInputStream(mode_file);
            fi.read(buffer,0,20*30);
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(buffer.length==0)
            return null;

        String mode = buffer.toString();

        return mode;
    }

    public void set_hdmi_mode(String mode){
        final String hdmiModeFile = "/sys/class/display/HDMI/mode";

        try {
            FileOutputStream os = new FileOutputStream(hdmiModeFile);
            os.write(mode.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



/*
* FUNC:   install apk
*
*
*
* */
    public void install_apk(String path)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.parse(path),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public void unistall_apk(String pack)
    {
        new Thread(){

            public void run(){
                final String stop_order = "am force-stop com.gf.test.videoplayer";
                final String unistall_order = "pm uninstall com.gf.test.videoplayer";

                try {
                    Runtime.getRuntime().exec(stop_order);
                    Thread.sleep(1000);
                    Runtime.getRuntime().exec(unistall_order);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }.start();
    }
}
