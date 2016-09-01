package com.example.hari.mirrormirroronthewall

import android.app.Notification
import android.app.NotificationManager
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.AsyncTask
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.NotificationCompat
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory
import com.amazonaws.regions.Regions
import java.util.*


class MainActivity : AppCompatActivity() {
    //get the storage
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //create Notifcation
        preferences = getSharedPreferences("com.example.hari.mirrormirrorontthewall", Context.MODE_PRIVATE)
        //check for first run
        if (preferences.getBoolean("firstuse", true)) {
            val intent = Intent(this, FirststartactivityActivity::class.java)
            startActivity(intent)
            this.finish()
        } else {
            LocalBroadcastManager.getInstance(this).registerReceiver(rec, IntentFilter("msgfuck"))
            val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val ncomp = Notification.Builder(this)
            ncomp.setContentTitle("My Notification")
            ncomp.setContentText("Notification Listener Service Example")

            ncomp.setTicker("Notification Listener Service Example u fucking idiot noob")
            val bm = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            ncomp.setLargeIcon(bm)

            ncomp.setSmallIcon(R.mipmap.ic_launcher)
            //ncomp.setLargeIcon((applicationContext.resources.getDrawable(R.mipmap.ic_launcher,null)  as BitmapDrawable).bitmap)
            ncomp.setAutoCancel(true)

            nManager.notify(System.currentTimeMillis().toInt() + 1000, ncomp.build())
        }
    }
override fun onDestroy(){
    super.onDestroy();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(rec)
}
    private val rec = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            preferences = getSharedPreferences("com.example.hari.mirrormirrorontthewall", Context.MODE_PRIVATE)
            val shit = intent.getBundleExtra("data")
            val data_to_send = shit.getString("android.text")


            val text = preferences.getString("code", "code")
            Log.d("FUCKU", text)
            val info = DuhInfo()
            info.text = data_to_send
            info.title = shit.getString("android.title")
            info.code = text
            info.isChrome = "no"
            info.img = intent.getStringExtra("imgbytes")
            Log.d("bytarris", info.img);




            val real_data = "{'name':'$data_to_send', 'code': '$text'}"
            try {
                initliazeAmazon(info)
            } catch (e: Exception) {
                Log.e("sooo migh", e.message)

            }

            Log.d("fuckshit", real_data)
        }


    }

    override fun onResume() {
        super.onResume()
        if (preferences.getBoolean("firstuse", true)) {
            val intent = Intent(this, FirststartactivityActivity::class.java)
            startActivity(intent)

        }
    }

    fun initliazeAmazon(info: DuhInfo) {
        val credentialsProvider = CognitoCachingCredentialsProvider(
                this.applicationContext,
                "aws-id",
                Regions.US_EAST_1)

        val factory = LambdaInvokerFactory(
                this.applicationContext,
                Regions.US_EAST_1,
                credentialsProvider)
        val inter = factory.build<LambdaCall>(LambdaCall::class.java)
        object : AsyncTask<DuhInfo, Void, String>() {
            override fun doInBackground(vararg params: DuhInfo): String? {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return inter.TestFunction(params[0])
                } catch (lfe: LambdaFunctionException) {
                    Log.e("failure", "Failed to invoke echo", lfe)
                    return null
                }

            }

            override fun onPostExecute(result: String?) {
                if (result == null) {
                    return
                }
            }
        }.execute(info)
    }
}