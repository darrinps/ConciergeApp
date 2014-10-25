package com.aa.consierge;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aa.android.common.WearActivity;
import com.aa.android.common.data.SharedDataEvent;
import com.aa.android.util.ContactHolder;
import com.aa.android.util.SmsUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;


public class MainActivity extends WearActivity {
    private String[] iBeacons = {
            "F1:60:22:42:5A:69",
            "F9:48:81:1A:89:57",
            "F3:2D:A5:17:63:BD",
            "E1:9C:D9:00:46:C9"
    };

    private static final String TAG = MainActivity.class.getName();
    private BluetoothAdapter mBluetoothAdapter;
    private static final long SCAN_PERIOD = 10000;
    private Handler mHandler;
    private boolean mScanning;
    private ContactHolder phil;
    private ContactHolder limo;


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device,
                int rssi,
                byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (String test : iBeacons) {
                        if (test.equals(device.getAddress())) {
                            Log.i(TAG, "Found device: " + device.getAddress() + " name: " + device.getName());

                            SmsUtils.sendSMSMessage(limo.getNumber(), "I'm at the gate!", getApplicationContext());
                        }
                    }

                    //Log.i(TAG, "Found device: " + device.getAddress() + " name: " + device.getName());
                }
            });
        }
    };

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        //Generate some fake data
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.phil_easter);

        ContactHolder holder = new ContactHolder("Phil Easter", "9403956423", bm);
        SmsUtils.addContact(holder);
        phil = holder;

        bm = BitmapFactory.decodeResource(getResources(), R.drawable.limo);

        holder = new ContactHolder("Big Als Limo", "2145977609", bm);
        SmsUtils.addContact(holder);
        limo = holder;

        //SmsUtils.sendSMSMessage(holder.getNumber(), "TEST", getApplicationContext());

        //startService(new Intent(this, BeaconFinderService.class));
        mHandler = new Handler();

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Enable BLE and restart", Toast.LENGTH_LONG).show();
        } else {
            scanLeDevice(true);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanLeDevice(false);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent) {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent) {
        return false;
    }
}
