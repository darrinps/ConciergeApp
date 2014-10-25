package com.aa.consierge.gcm;

/**
 * Created by layne on 10/25/14.
 */
public class GcmMessage {
    public final String title;
    public final String message;

    public GcmMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
