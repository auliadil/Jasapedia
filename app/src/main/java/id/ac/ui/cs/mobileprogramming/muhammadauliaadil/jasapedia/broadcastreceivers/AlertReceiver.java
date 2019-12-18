package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.helpers.NotificationHelper;


public class AlertReceiver extends BroadcastReceiver {

    private String title, text;

    public void init(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationHelper notificationHelper = new NotificationHelper(context, title, text);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
            notificationHelper.getManager().notify(1, nb.build());
        }
    }
}
