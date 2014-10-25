package com.aa.consierge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aa.android.common.WearActivity;
import com.aa.android.common.data.SharedDataEvent;
import com.aa.android.util.ContactHolder;
import com.aa.android.util.SmsUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;


public class MainActivity extends WearActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        SmsUtils.sendSMSMessage(holder.getNumber(), "TEST", getApplicationContext());
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

    @Override
    public boolean handleMessageEvent(Context context, GoogleApiClient client, MessageEvent messageEvent) {
        return false;
    }

    @Override
    public boolean handleDataEvent(Context context, GoogleApiClient client, SharedDataEvent dataEvent) {
        return false;
    }
}
