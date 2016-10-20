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

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private Context context;
    private NotificationManager notificationManager;

    public BluetoothBroadcastReceiver(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationManager = notificationManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String bluetoothStateIntent = intent.getStringExtra(MainActivity.BLUETOOTH_STATE);

        ((MainActivity) context).setBluetoothState(bluetoothStateIntent);

        if (bluetoothStateIntent.equals(MainActivity.ENABLED)) {
            Notification bluetoothNotification = NotificationCreator.createNotification(
                    context, MainActivity.BLUETOOTH_CONTENT_INTENT_ID, R.drawable.ic_bluetooth_white_48dp, true
                    , "Bluetooth", "Bluetooth in enabled"
                    , BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_bluetooth_white_48dp)
                    , "Big Brother wants to contact you!", RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                    , new long[]{0, 100, 700, 100, 700});

            notificationManager.notify(MainActivity.BLUETOOTH_NOTIFICATION_ID, bluetoothNotification);
        }
    }
}
