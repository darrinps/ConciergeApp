/*
 * GcmMessageType.java
 * American Airlines Android App
 *
 * Created on 3/23/14 1:34 PM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.consierge;

public enum GcmMessageType
{
    MBP, UNKNOWN;

    public static GcmMessageType fromString(String string)
    {
        if (MBP.name().equalsIgnoreCase(string))
        {
            return MBP;
        }
        return UNKNOWN;
    }
}
