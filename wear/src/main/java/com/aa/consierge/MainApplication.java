package com.aa.consierge;

import android.app.Application;

import com.aa.android.common.WearService;
import com.aa.android.util.DebugLog;
import com.aa.consierge.handlers.QuestionHandler;

/**
 * Created by layne on 10/24/14.
 */
public class MainApplication extends Application {

    static {
        // Initialize logging
        DebugLog.setAllEnabled(BuildConfig.DEBUG);

        // register handlers
        WearService.registerHandlers(
                new QuestionHandler()
                // Add handlers as needed
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
