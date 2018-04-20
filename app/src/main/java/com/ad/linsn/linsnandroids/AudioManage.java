package com.ad.linsn.linsnandroids;

import android.content.Context;
import android.media.AudioManager;

public class AudioManage {
    private Context mContext;

    public AudioManage(Context context){
        this.mContext = context;
    }

    public String get_volume(){
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        int max = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int current = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);

        String volume_info = "audio" + "{" + max + "+" + current + "}";
        return volume_info;
    }

    /*
     * function: 设置声音的大小。
     * @volume : audio absolute value,
     * @mute:    true    close system_voice
     *           false   do nothing
     */
    public void set_audio(int volume, boolean mute)
    {
        AudioManager am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        int max = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        int current = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);

        if(mute){
            am.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
            return;
        }

        if(volume > max || volume <= 0)
            return;

        am.setStreamVolume(AudioManager.STREAM_SYSTEM, volume, 0);
    }


}
