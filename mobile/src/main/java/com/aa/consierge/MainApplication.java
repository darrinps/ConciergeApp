package com.aa.consierge;

import android.app.Application;

import com.aa.android.common.WearService;
import com.aa.android.util.DebugLog;
import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.push.IBMPush;

/**
 * Created by layne on 10/24/14.
 */
public class MainApplication extends Application {

    static {
        // Initialize logging
        DebugLog.setAllEnabled(BuildConfig.DEBUG);

        // register handlers
        WearService.registerHandlers(
                // Add handlers as needed
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IBMBluemix.initialize(this,
                "6ad7d56a-1520-430f-b392-10e785743a3c",
                "552d67aee35f152477e3a0194cffb75095c27be0",
                "Concierge.mybluemix.net");
//        IBMCloudCode.initializeService();
//        IBMData.initializeService();
        IBMPush.initializeService();

        String regId = "testregid";
        IBMPush.getService().register("Concierge", regId);
    }
}
