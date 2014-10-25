package com.aa.android.common;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Helper class designed to help facility easier message event path creation and parsing.
 * <p/>
 * Created by layne on 10/21/14.
 */
public class MessageEvents {
    private static final String TAG = MessageEvents.class.getSimpleName();
    private static final String PATH_START_ACTIVITY_PREFIX = "/start-activity";
    private static final String PATH_REQUEST_UPDATE_PREFIX = "/request_update";

    public static final class StartQuestionActivity {
        public static final String PATH = PATH_START_ACTIVITY_PREFIX + "/question";

        public static PendingResult<MessageApi.SendMessageResult> send(GoogleApiClient apiClient, String nodeId) {
            return Wearable.MessageApi.sendMessage(apiClient, nodeId, PATH, new byte[0]);
        }
    }
}
