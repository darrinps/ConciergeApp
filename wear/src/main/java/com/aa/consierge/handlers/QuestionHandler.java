package com.aa.consierge.handlers;

import android.content.Context;

import com.aa.android.common.SimpleWearHandler;
import com.aa.android.common.data.SharedDataEvent;
import com.aa.android.common.data.SharedQuestion;
import com.aa.android.util.DebugLog;
import com.aa.consierge.MainActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;

/**
 * Created by layne on 10/24/14.
 */
public class QuestionHandler extends SimpleWearHandler {
    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent) {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent) {
        String path = dataEvent.getPath();
        DebugLog.d("question", "path: %s", path);
        if (path != null && path.startsWith(SharedQuestion.PUT_PATH_PREFIX)) {
            SharedQuestion question = dataEvent.getSharedData(SharedQuestion.class);
            MainActivity.start(context, question);
            return true;
        } else {
            MainActivity.start(context, null);
            return true;
        }
    }
}
