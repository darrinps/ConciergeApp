package com.aa.consierge;

import android.app.Service;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by layne on 10/25/14.
 */
public class QuestionHandler implements GcmMessageHandler {
    @Override
    public boolean canHandleType(GcmMessageType type) {
        return true;
    }

    @Override
    public boolean handleGcmMessage(Service service,
            String collapseKey,
            String from,
            GcmMessageType type,
            String contentMessage,
            String contentJson) {

        NotificationCompat.Builder builder = NotificationUtils.defaultBuilder(service)
                .setContentText(contentMessage);


        NotificationManagerCompat manager = NotificationManagerCompat.from(service);
        manager.notify(1, builder.build());
        return true;
    }
}
