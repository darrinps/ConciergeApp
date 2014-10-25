/**
 * BuildConfig.java
 */

package com.aa.build.gradle

/**
 * This class has all of the properties we use for the build.
 */
public final class BuildConfig {

    /** AA base version for manifest. Can be modified by passing in buildNumber to gradle upon build. */
    public static final String AA_BASE_VERSION = '3.9.0'

    /** The Android build tools version used to make the apk. */
    public static final String BUILD_TOOLS_VERSION = '21.0.1'

    /** The google play services version we are using for the main app. */
    public static final String APP_GOOGLE_PLAY_SERVICES_VERSION = '6.1.11'

    public static final int APP_COMPILE_SDK_VERSION = 21
    public static final int APP_MIN_SDK_VERSION = 19
    public static final int APP_TARGET_SDK_VERSION = 21
    public static final String APP_ANDROID_SUPPORT_VERSION = '21.0.0'

    /** The google play services version we are using for the wear app. */
    public static final String WEAR_GOOGLE_PLAY_SERVICES_VERSION = '6.1.11'

    public static final int WEAR_COMPILE_SDK_VERSION = 21
    public static final int WEAR_MIN_SDK_VERSION = 20
    public static final int WEAR_TARGET_SDK_VERSION = 21
    public static final String WEAR_ANDROID_SUPPORT_VERSION = '21.0.0'
    public static final String WEAR_UI_SUPPORT_VERSION = '1.0.0'

    private BuildConfig() {}
}
