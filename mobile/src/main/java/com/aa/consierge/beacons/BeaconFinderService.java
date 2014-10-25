package com.aa.consierge.beacons;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.aa.consierge.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

/**
 * Created by darri_000 on 10/25/2014.
 */
public class BeaconFinderService extends Service implements BootstrapNotifier, RangeNotifier
{
    public static String TAG = BeaconFinderService.class.getName();

    private BeaconManager mBeaconManager;
    private Region mAllBeaconsRegion;

//    private MonitoringActivity mMonitoringActivity;
//    private RangingActivity mRangingActivity;
//    private BackgroundPowerSaver mBackgroundPowerSaver;

    @SuppressWarnings("unused")
    private RegionBootstrap mRegionBootstrap;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub


        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(verifyBluetooth())
        {
           // BeaconManager.getBeaconParsers().add(new BeaconParser()
           //         .setBeaconLayout("m:2-3:beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        }

        return START_NOT_STICKY;
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> arg0, Region arg1)
    {
        Log.d(TAG, "Beacon detected in region");
    }

    @Override
    public void didDetermineStateForRegion(int arg0, Region arg1)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void didEnterRegion(Region arg0)
    {
        Log.d(TAG, "I just saw a beacon named " + arg0 + " for the first time." );

        try
        {
            Log.d(TAG, "entered region.  starting ranging");

            mBeaconManager.startRangingBeaconsInRegion(mAllBeaconsRegion);
            mBeaconManager.setRangeNotifier(this);
        }
        catch (RemoteException e)
        {
            Log.e(TAG, "Cannot start ranging");
        }
    }

    @Override
    public void didExitRegion(Region arg0)
    {
        Log.d(TAG, "I no longer see a beacon named " + arg0.getUniqueId());
    }



    private boolean verifyBluetooth()
    {

        try
        {
            if (BeaconManager.getInstanceForApplication(this).checkAvailability())
            {
                Toast.makeText(getApplicationContext(), "iBeacon detection enabled", Toast.LENGTH_LONG).show();

                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Enable BLE and restart", Toast.LENGTH_LONG).show();

                return false;
            }
        }
        catch (RuntimeException e)
        {
            Toast.makeText(getApplicationContext(), "BLE is not supported on this device", Toast.LENGTH_LONG).show();

            return false;
        }

    }

}
