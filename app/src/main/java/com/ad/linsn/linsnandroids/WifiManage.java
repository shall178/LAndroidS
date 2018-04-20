package com.ad.linsn.linsnandroids;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiManage {
    private Context mContext;

    public WifiManage(Context context){
        this.mContext = context;
    }

    public void closeWifi(){
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(false);
    }

    public void openWifi(String ssid, String password){
        int type = -1;
        WifiManager mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        List<ScanResult> scanResult = mWifiManager.getScanResults();
        for (ScanResult sr: scanResult) {
            if(sr.SSID.equals(ssid)) {
                type = getType(sr);
                break;
            }
        }

        if(type < 0)
            return;

        WifiConfiguration configuration = configWifiInfo("SSID", "password", type);
        int netId = configuration.networkId;
        if (netId == -1) {
            netId = mWifiManager.addNetwork(configuration);
        }
        mWifiManager.enableNetwork(netId, true);
    }


    private WifiConfiguration configWifiInfo(String ssid, String password, int type){
        WifiConfiguration config = null;
        WifiManager mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager != null) {
            List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
            for (WifiConfiguration existingConfig : existingConfigs) {
                if (existingConfig == null) continue;
                if (existingConfig.SSID.equals("\"" + ssid + "\"")  /*&&  existingConfig.preSharedKey.equals("\""  +  password  +  "\"")*/) {
                    config = existingConfig;
                    break;
                }
            }
        }
        if (config == null) {
            config = new WifiConfiguration();
        }
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + ssid + "\"";
        // 分为三种情况：0没有密码1用wep加密2用wpa加密
        if (type == 0) {// WIFICIPHER_NOPASSwifiCong.hiddenSSID = false;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (type == 1) {  //  WIFICIPHER_WEP
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == 2) {   // WIFICIPHER_WPA
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;

    }

    private int getType(ScanResult scanResult){
        if (scanResult.capabilities.contains("WPA"))
            return 2;
        else if (scanResult.capabilities.contains("WEP"))
            return 1;
        else
            return 0;
    }

    public void getScanList(){
        int i =0;
        List<String> wifilist;
        WifiManager mwifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if(!mwifiManager.isWifiEnabled())
            mwifiManager.setWifiEnabled(true);

        mwifiManager.startScan();

        List<ScanResult> mWifiList = mwifiManager.getScanResults();
        if(mWifiList == null)
            return;

        for ( ScanResult sr : mWifiList ) {
            i++;
//            String str = sr.SSID + "+" +
        }
    }




}
