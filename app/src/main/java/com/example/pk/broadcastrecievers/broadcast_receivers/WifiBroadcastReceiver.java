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

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private NotificationManager notificationManager;

    public WifiBroadcastReceiver(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String wifiStateIntent = intent.getStringExtra(MainActivity.WIFI_STATE);

        ((MainActivity) context).setWifiState(wifiStateIntent);

        if (wifiStateIntent.equals(MainActivity.ENABLED)) {
            Notification wifiNotification = NotificationCreator.createNotification(
                    context, MainActivity.WIFI_CONTENT_INTENT_ID, R.drawable.ic_perm_scan_wifi_white_48dp, true
                    , "Wifi", "Wifi is enabled"
                    , BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_perm_scan_wifi_white_48dp)
                    , "Big brother is watching you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    , new long[]{0, 200, 500, 200, 500, 1000});

            notificationManager.notify(MainActivity.WIFI_NOTIFICATION_ID, wifiNotification);
        }
    }
}
