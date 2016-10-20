package com.example.pk.broadcastrecievers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

public class NotificationCreator {

    public static Notification createNotification(Context context, int contentIntentId
            , int smallIcon, boolean autoCancel, String contentTitle, String contentText
            , Bitmap largeIcon, String ticker, Uri soundUri, long[] vibratePattern) {

        Intent notificationIntent = new Intent(context, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, contentIntentId
                , notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(smallIcon)
                .setAutoCancel(autoCancel)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setLargeIcon(largeIcon)
                .setTicker(ticker)
                .setSound(soundUri)
                .setVibrate(vibratePattern);

        return builder.build();
    }
}
