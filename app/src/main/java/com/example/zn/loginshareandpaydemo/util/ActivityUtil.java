package com.example.zn.loginshareandpaydemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by xinwei on 2017/11/12.
 */

public class ActivityUtil {

    private static final String TAG = "AndroidUtil";

    /***
     * 跳转到指定Activity
     *
     * @param
     * @return
     */
    public static boolean gotoActivity(Context context, Class<? extends Activity> clz,
                                       Bundle bundles) {
        try {
            Intent intent = new Intent(context, clz);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (null != bundles) {
                intent.putExtras(bundles);
            }
            context.startActivity(intent);

            return true;
        } catch (Exception ex) {
            Log.d(TAG, "gotoActivity failed", ex);
            return false;
        }
    }
}
