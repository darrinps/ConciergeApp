/*
 * GcmMessageHandler.java
 * American Airlines Android App
 *
 * Created on 3/27/14 2:46 PM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.consierge;

import android.app.Service;

public interface GcmMessageHandler {
    boolean canHandleType(GcmMessageType type);

    boolean handleGcmMessage(Service service,
            String collapseKey,
            String from,
            GcmMessageType type,
            String contentMessage,
            String contentJson);
}
