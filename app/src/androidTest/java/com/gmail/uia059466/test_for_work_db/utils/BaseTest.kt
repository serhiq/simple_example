package com.gmail.uia059466.test_for_work_db.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith

@Ignore("Ignoring BaseTest")
@RunWith(AndroidJUnit4::class)
abstract class BaseTest {
    
    lateinit var device: UiDevice
    @Before
    fun setUp() {
        device = startDevice()
    }
    
    private fun startDevice(): UiDevice {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()
    
        // Wait for launcher
        val launcherPackage: String = getLauncherPackageName()
        Assert.assertThat(launcherPackage, CoreMatchers.notNullValue())
        device.wait<Boolean>(
                Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT.toLong()
                            )
        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(pkg)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)
   
        // Wait for the app to appear
        device.wait<Boolean>(
                Until.hasObject(
                        By.pkg(pkg)
                                .depth(0)
                               ), LAUNCH_TIMEOUT
                            )
    
        return device
    }


    val activity: Activity?
        get() {
            var activity: Activity? = null
            InstrumentationRegistry.getInstrumentation().runOnMainSync {
                activity = ActivityLifecycleMonitorRegistry
                        .getInstance()
                        .getActivitiesInStage(Stage.RESUMED)
                        .first()
                
            }
            return activity
        }
    
    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    fun getLauncherPackageName(): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        
        val pm = ApplicationProvider.getApplicationContext<Context>().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo!!.activityInfo.packageName
    }
    

    
    fun sleep(millis: Long){
        Thread.sleep(millis)
    }
   
    companion object {
        private const val LAUNCH_TIMEOUT = 5_000L
        const val pkg            = "com.gmail.uia059466.test_for_work_db"
    }
}