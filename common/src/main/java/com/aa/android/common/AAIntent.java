/*
 * AAIntent.java
 * American Airlines Android App
 *
 * Created on 4/7/14 10:14 AM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.android.common;

public final class AAIntent {
    private static final String BASE = "com.aa.concierge.intent.";

    private static final String ACTION         = BASE + "action.";
    public static final  String ACTION_GET     = ACTION + "GET";
    public static final  String ACTION_POST    = ACTION + "POST";
    public static final  String ACTION_PUT     = ACTION + "PUT";
    public static final  String ACTION_DELETE  = ACTION + "DELETE";
    public static final  String ACTION_REFRESH = ACTION + "REFRESH";
    public static final  String ACTION_UPDATE  = ACTION + "UPDATE";
    public static final  String ACTION_DISMISS = ACTION + "DISMISS";

    private static final String EXTRA = BASE + "extra.";

    private AAIntent() {}
}
