package com.aa.android.util;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by darri_000 on 10/24/2014.
 */
public class SmsUtils
{
    private static final String TAG = SmsUtils.class.getName();

    protected void sendSMSMessage(final String phoneNo, final String message, Context optCtx)
    {
        Log.i(TAG, "Sending SMS");

        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);

            //Toast.makeText(getApplicationContext(), "SMS sent.",
            //Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            if(optCtx != null)
            {
                Toast.makeText(optCtx,
                        "SMS faild, please try again.",
                        Toast.LENGTH_LONG).show();
            }

            Log.w(TAG, "\"Send SMS failure\"");

            e.printStackTrace();
        }
    }
}
