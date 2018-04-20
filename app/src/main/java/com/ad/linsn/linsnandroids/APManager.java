package com.ad.linsn.linsnandroids;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class APManager {
    private Context mContext;

    public APManager(Context context){
        this.mContext = context;

    }

    public void closeWifiAp()
    {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        setWifiApEnable(null,false, wifiManager);
    }


    public void openWifiAp(String ssid, String password)
    {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);


        if(ssid.isEmpty() || password.isEmpty() || ssid.length() >=20 || password.length()>=20) {
            if (isWifiApEnable(wifiManager))
                return;
            else{
                if(wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                    setWifiApEnable(null, true,wifiManager);
                    return;
                }
            }
        }

        WifiConfiguration netConfig=new WifiConfiguration();
        netConfig.allowedAuthAlgorithms.clear();
        netConfig.allowedGroupCiphers.clear();
        netConfig.allowedKeyManagement.clear();
        netConfig.allowedPairwiseCiphers.clear();
        netConfig.allowedProtocols.clear();
        netConfig.SSID = ssid;
        netConfig.hiddenSSID = true;
        netConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        netConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        netConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        netConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        netConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        netConfig.status = WifiConfiguration.Status.ENABLED;
        netConfig.preSharedKey = password;

        setWifiApEnable(netConfig, true, wifiManager);
    }

    private void setWifiApEnable(WifiConfiguration wifiConfig, boolean enabled, WifiManager wifiManager){
        Method method = null;
        try {
            method = wifiManager.getClass().getMethod("setWifiApEnabled");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            method.invoke(wifiManager, wifiConfig, enabled);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private boolean isWifiApEnable(WifiManager wifiManager){
        Method method = null;
        try {
            method = wifiManager.getClass().getMethod("getWifiApState");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        int i = 0;
        int value = 0;
        try {
            i = (int) method.invoke(wifiManager);
            Field field = wifiManager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            value = (int) field.get(wifiManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if(i == value)
            return true;

        return false;
    }

    public String getApInfo(){
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        Method method = null;
        WifiConfiguration wifiConfiguration = null;

        try {
            method = wifiManager.getClass().getMethod("getWifiApConfiguration");
            wifiConfiguration = (WifiConfiguration) method.invoke(wifiManager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if(wifiConfiguration == null)
            return null;

        String ssid = wifiConfiguration.SSID;
        String password = wifiConfiguration.preSharedKey;

        String state = null;
        if(isWifiApEnable(wifiManager))
            state = "o";
        else state = "c";

        return state + "+" + ssid + "+" + password;
    }

}
