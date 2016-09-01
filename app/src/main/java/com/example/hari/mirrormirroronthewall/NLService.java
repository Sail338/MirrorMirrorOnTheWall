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
import java.util.Objects;

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


        Log.d("the sb is",sb.getNotification().toString());
        Intent msg = new Intent("msgfuck");
        msg.putExtra("herro",sb.getNotification().tickerText +"/t");
        msg.putExtra("package name",sb.getPackageName());
        Bundle extras = sb.getNotification().extras;
        msg.putExtra("titler",extras.getCharSequence("android.title").toString());
        //


        //try to get largeicon and convert it to BYte array
        try{

         Bitmap largeic = (Bitmap) sb.getNotification().extras.get(Notification.EXTRA_LARGE_ICON);

             //Drawable bigIconDraw = createPackageContext(sb.getPackageName(),CONTEXT_IGNORE_SECURITY).getResources().getDrawable(largeic,null);
            //Drawable bigIconDraw = context.getDrawable(largeic);
            //Bitmap bitmapLarge = ((BitmapDrawable)  largeic).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            largeic.compress(Bitmap.CompressFormat.JPEG,10,stream);
            byte[] bytesforlarge = stream.toByteArray();
            Log.d("largeiconin",Arrays.toString(bytesforlarge));
            String base64img = Base64.encodeToString(bytesforlarge,Base64.DEFAULT);
            //convert Byte array to base64
           msg.putExtra("imgbytes",base64img);

        } catch (Exception e){
            Log.d("zerror",e.getMessage());
        }
        Log.d("extras are",extras.toString());
        msg.putExtra("data",extras);
        LocalBroadcastManager.getInstance(context).sendBroadcast(msg);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }






}
