package com.example.hari.mirrormirroronthewall;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.util.Arrays;

/**
 * Created by hari on 7/21/16.
 */
public class NLService extends NotificationListenerService {

    Context context;
@Override

public  void onCreate(){
    super.onCreate();
    context = getApplicationContext();
}
    @Override
    public  void onNotificationPosted(StatusBarNotification sb){

        Intent msg = new Intent("msgfuck");
        Log.d("the sb is",sb.getNotification().toString());
        msg.putExtra("herro",sb.getNotification().tickerText +"/t");
        msg.putExtra("package name",sb.getPackageName());
        Bundle extras = sb.getNotification().extras;
        msg.putExtra("titler",extras.getCharSequence("android.title").toString());
        Log.d("extras are",extras.toString());
        msg.putExtra("data",extras);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msg);
    }


}
