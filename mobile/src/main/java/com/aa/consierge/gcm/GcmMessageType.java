/*
 * GcmMessageType.java
 * American Airlines Android App
 *
 * Created on 3/23/14 1:34 PM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.consierge.gcm;

public enum GcmMessageType {
    TAXI,
    TSA,
    ADM,
    DRINK,
    UNKNOWN;

    public static GcmMessageType fromString(String string) {
        for (GcmMessageType type : values()) {
            if (type.name().equalsIgnoreCase(string)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
