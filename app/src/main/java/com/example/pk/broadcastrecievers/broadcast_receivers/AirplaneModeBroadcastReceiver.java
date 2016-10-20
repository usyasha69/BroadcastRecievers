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

public class AirplaneModeBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private NotificationManager notificationManager;

    public AirplaneModeBroadcastReceiver(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String amStateIntent = intent.getStringExtra(MainActivity.AIRPLANE_MODE_STATE);

        ((MainActivity) context).setAirplaneModeState(amStateIntent);

        if (amStateIntent.equals(MainActivity.ENABLED)) {
            Notification amNotification = NotificationCreator.createNotification(
                    context, MainActivity.AIRPLANE_MODE_CONTENT_INTENT_ID
                    , R.drawable.ic_airplanemode_active_white_48dp, true
                    , "Airplane mode", "Airplane mode is enabled"
                    , BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_airplanemode_active_white_48dp)
                    , "Big Brother protects you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                    , new long[]{0, 400, 600, 400, 600, 1000});

            notificationManager.notify(MainActivity.AIRPLANE_MODE_NOTIFICATION_ID, amNotification);
        }
    }
}
