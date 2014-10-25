package com.aa.consierge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aa.android.util.ContactHolder;
import com.aa.android.util.SmsUtils;
import com.aa.consierge.beacons.BeaconFinderService;


public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        //Generate some fake data
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.phil_easter);

        ContactHolder holder = new ContactHolder("Phil Easter", "9403956423", bm);
        SmsUtils.addContact(holder);

        bm = BitmapFactory.decodeResource(getResources(), R.drawable.limo);

        holder = new ContactHolder("Big Als Limo", "2145977609", bm);
        SmsUtils.addContact(holder);

        //SmsUtils.sendSMSMessage(holder.getNumber(), "TEST", getApplicationContext());

        startService(new Intent(this, BeaconFinderService.class));
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
}
