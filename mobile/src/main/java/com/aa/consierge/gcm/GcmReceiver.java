package com.aa.consierge.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.aa.android.util.DebugLog;

/**
 * Created by layne on 10/25/14.
 */
public class GcmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DebugLog.e("GcmReceiver", "" + ((intent != null) ? intent.getExtras() : intent));
        // Explicitly specify that GcmReceiverService will handle the intent.
        intent.setComponent(new ComponentName(context.getPackageName(), GcmService.class.getName()));
        startWakefulService(context, intent);
        setResultCode(Activity.RESULT_OK);
    }
}
