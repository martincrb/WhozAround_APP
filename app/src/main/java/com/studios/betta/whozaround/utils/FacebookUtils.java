package com.studios.betta.whozaround.utils;

import android.content.Context;
import android.util.Log;

import com.facebook.FacebookSdk;

/**
 * Created by Martin on 09/04/2016.
 */
public class FacebookUtils {
    private static Context context_;

    public static void FacebookUtils(Context context) {
        context_ = context;

    }

    public static void Init() {
        if (context_ != null) {
            FacebookSdk.sdkInitialize(context_);
        }
    }

    public static void printDevHash(String tag) {
        //FB dev hash
        Log.d(tag, FacebookSdk.getApplicationSignature(context_));
    }

}
