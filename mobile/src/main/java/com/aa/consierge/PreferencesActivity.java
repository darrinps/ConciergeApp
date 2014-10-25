package com.aa.consierge;

import android.content.Context;
import android.os.Bundle;

import com.aa.android.common.data.SharedDataEvent;
import com.aa.consierge.BaseActivity;
import com.aa.consierge.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;

public class PreferencesActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.concierge_preferences);
    }

    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent) {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent) {
        return false;
    }
}
