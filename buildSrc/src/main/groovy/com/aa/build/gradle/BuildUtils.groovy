package com.aa.build.gradle

import static com.android.SdkConstants.FN_ANDROID_MANIFEST_XML
import static com.android.builder.model.AndroidProject.*

import com.android.build.gradle.AndroidHelper
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BasePlugin
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.tasks.GenerateApkDataTask
import com.android.build.gradle.internal.variant.ApplicationVariantData
import com.android.build.gradle.internal.variant.BaseVariantData
import org.gradle.api.Project
import org.gradle.api.file.FileCollection

public final class BuildUtils {

    private BuildUtils() {}

    public static String getVersionNameSuffix(Project project, String suffix = "") {
        String id = project.hasProperty('buildIdentifier') ? ".${project.buildIdentifier}" : ""
        println "buildIdentifier is set to $id"
        return suffix + id
    }

    public static FileCollection getJavaClasspath(Project project, BaseVariant variant) {
        AppPlugin plugin = project.plugins.findPlugin(AppPlugin)

        List<String> _boot_paths = plugin.androidBuilder.bootClasspath
        String[] boot_paths = _boot_paths.toArray(new String[_boot_paths.size()])
        FileCollection boot_path = project.files(boot_paths)

        return project.files(
                boot_path,
                variant.javaCompile.destinationDir,
                variant.javaCompile.classpath
        )
    }

    public static void addWearableToApp(Project app, String wearableProjectName) {
        Project rootProject = app.rootProject
        Project wear = rootProject.project(wearableProjectName)

        AppPlugin plugin = app.plugins.findPlugin(AppPlugin)
        app.afterEvaluate {
            // We have to first make sure Android tasks are created
            if (AndroidHelper.createAndroidTasks(plugin)) {
                println "creating wear tasks for ${app}"
                for (BaseVariantData variantData : AndroidHelper.variantDataList(plugin)) {
                    if (!(variantData instanceof ApplicationVariantData)) continue;

                    String assembleTask = "assemble${variantData.variantConfiguration.fullName.capitalize()}"
                    def dependency = "${wearableProjectName}:${assembleTask}"
                    println "creating wear dependency: ${dependency}"
                    createGenerateMicroApkDataTask(plugin, app, variantData, wear, dependency)
                }
            } else {
                println "was not able to create android tasks!!"
            }
        }
    }

    private static void createGenerateMicroApkDataTask(BasePlugin plugin,
                                                       Project app,
                                                       BaseVariantData variantData,
                                                       Project wear,
                                                       Object dependency) {
        GenerateApkDataTask task = app.tasks.create(
                "handle${variantData.variantConfiguration.fullName.capitalize()}MicroApk",
                GenerateApkDataTask)

        variantData.generateApkDataTask = task

        task.plugin = plugin
        task.conventionMapping.resOutputDir = {
            app.file("$app.buildDir/${FD_GENERATED}/res/microapk/${variantData.variantConfiguration.dirName}")
        }
        // get apk file
        task.conventionMapping.apkFile = {
            wear.file(wearApkFilename(wear, variantData))
        }
        task.conventionMapping.manifestFile = {
            app.file(
                    "$app.buildDir/${FD_INTERMEDIATES}/${FD_GENERATED}/manifests/microapk/${variantData.variantConfiguration.dirName}/${FN_ANDROID_MANIFEST_XML}")
        }
        task.conventionMapping.mainPkgName = {
            variantData.variantConfiguration.getApplicationId()
        }

        task.dependsOn dependency
        variantData.resourceGenTask.dependsOn task
    }

    private static String wearApkFilename(Project wear, BaseVariantData variantData) {
        return "${wear.buildDir}/${FD_OUTPUTS}/apk/${wear.name}-${variantData.variantConfiguration.baseName}.apk";
    }

    public static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        return stringBuilder.toString();
    }
}
