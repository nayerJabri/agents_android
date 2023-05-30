package com.forme.agents.DTO;

import android.content.Context;
import android.provider.Settings;

public class DeviceUUID {

    public static String UUIDDevice ="d00708e5-ea6e-450c-9819-63ed2df8691a"; //This is a default value
    public static String devicetype = "1" ; //This is a default value
    public static String getDeviceId(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


}
