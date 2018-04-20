package com.ad.linsn.linsnandroids;

import android.content.Context;
import android.media.AudioManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DeviceInfo {
    private boolean sdcard;
    private Context context;

    public DeviceInfo(Context context){
        this.sdcard = false;
        this.context = context;
    };

    private String get_system_size(){
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSizeLong();
        long blockCount = sf.getBlockCountLong();
        long availCount = sf.getAvailableBlocksLong();
        long all_size = (blockSize * blockCount)/(1024*1024);
        long all_avail = (blockSize * availCount)/(1024*1024);

        String system_size = "sys" + "{" + all_size + "+"+all_avail+"}";

        return system_size;
    }

    private String get_sdcard_size(){
        String state = Environment.getExternalStorageState();
        String sdcard_size = null;

        if(Environment.MEDIA_MOUNTED.equals(state)) {
            this.sdcard = true;
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSizeLong();
            long blockCount = sf.getBlockCountLong();
            long availCount = sf.getAvailableBlocksLong();
            long all_size = (blockSize * blockCount)/(1024*1024);
            long all_avail = (blockSize * availCount)/(1024*1024);

            sdcard_size = "sdc" + "{" + all_size + "+" + all_avail+"}";
        }

        if(!this.sdcard)
            return null;
        else
            return sdcard_size;
    }
//
//    private String  get_hdmi_info(){
//        byte buffer[] = new byte[50];
//        File file = new File("/sys/class/display/HDMI/mode");
//        try {
//            FileInputStream is  = new FileInputStream(file);
//            is.read(buffer, 0 , 50);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String hdmi_info = "hdmi"+ buffer.toString();
//
//        return hdmi_info;
//    }




    public String getDevInfo(){

        return get_system_size() + get_sdcard_size();

    }

}
