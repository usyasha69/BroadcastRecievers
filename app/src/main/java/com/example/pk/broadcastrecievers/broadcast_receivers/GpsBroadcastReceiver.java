package com.example.pk.broadcastrecievers.broadcast_receivers;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;

import com.example.pk.broadcastrecievers.UI.MainActivity;
import com.example.pk.broadcastrecievers.utils.NotificationCreator;
import com.example.pk.broadcastrecievers.R;

public class GpsBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private NotificationManager notificationManager;

    public GpsBroadcastReceiver(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String gpsStateIntent = intent.getStringExtra(MainActivity.GPS_STATE);

        ((MainActivity) context).setGpsState(gpsStateIntent);

        if (gpsStateIntent.equals(MainActivity.ENABLED)) {
            Notification gpsNotification = NotificationCreator.createNotification(
                    context, MainActivity.GPS_CONTENT_INTENT_ID, R.drawable.ic_gps_fixed_white_48dp, true
                    , "GPS", "GPS is enabled"
                    , BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gps_fixed_white_48dp)
                    , "Big Brother spying on you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
                    , new long[]{0, 100, 300, 100, 300, 100, 300});

            notificationManager.notify(MainActivity.GPS_NOTIFICATION_ID, gpsNotification);
        }
    }
}
