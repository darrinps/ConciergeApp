package com.android.build.gradle

import com.android.build.gradle.internal.variant.BaseVariantData
import org.gradle.api.GradleException

public class AndroidHelper {
    def static List<BaseVariantData> variantDataList(BasePlugin basePlugin) {
        return basePlugin.variantDataList
    }

    public static boolean createAndroidTasks(BasePlugin basePlugin) {
        basePlugin.createAndroidTasks(false)
        try {
            basePlugin.checkTasksAlreadyCreated()
            return false
        } catch (GradleException e) {
            return true
        }
    }
}
