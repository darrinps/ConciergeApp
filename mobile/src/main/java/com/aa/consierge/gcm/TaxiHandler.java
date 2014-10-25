package com.aa.consierge.gcm;

import android.app.Service;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.aa.consierge.NotificationUtils;
import com.aa.consierge.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by layne on 10/25/14.
 */
public class TaxiHandler implements GcmMessageHandler {
    private static final AtomicInteger sCount = new AtomicInteger();

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

        int drawable = R.drawable.taxi;
        switch (type) {
            case TAXI:
                break;
            case TSA:
                drawable = R.drawable.tsa_precheck;
                break;
            case ADM:
                drawable = R.drawable.admiralsclub;
                break;
            case DRINK:
                drawable = R.drawable.titos_vodka;
                break;
        }
        NotificationCompat.WearableExtender extender = NotificationUtils.defaultExtender(service, drawable);

        NotificationCompat.Builder builder = NotificationUtils.defaultBuilder(service)
                .setContentText(contentMessage)
                .extend(extender);

        NotificationManagerCompat manager = NotificationManagerCompat.from(service);
        manager.notify(sCount.incrementAndGet(), builder.build());

        return true;
    }
}
