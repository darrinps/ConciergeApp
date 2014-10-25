package com.aa.consierge;

import android.content.Context;
import android.content.Intent;

import com.aa.android.common.WearActivity;
import com.aa.android.common.data.SharedDataEvent;
import com.aa.android.util.DebugLog;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;

/**
 * Created by layne on 10/25/14.
 */
public class BaseActivity extends WearActivity {
    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent) {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent) {
        return false;
    }

    public void onEventMainThread(PreferencesEvent event) {
        DebugLog.d("PREFERENCES", "");
        startActivity(new Intent(this, PreferencesEvent.class));
    }

    public static class PreferencesEvent {

    }
}
