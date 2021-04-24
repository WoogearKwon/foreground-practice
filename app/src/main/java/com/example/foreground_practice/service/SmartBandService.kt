package com.example.foreground_practice.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.foreground_practice.R
import com.example.foreground_practice.helper.WorkerListener
import com.example.foreground_practice.ui.MainActivity
import com.example.foreground_practice.workers.*
import java.time.LocalDateTime

class SmartBandService : Service() {
    companion object {
        private lateinit var workerListener: WorkerListener

        private const val INPUT_EXTRA_KEY = "inputExtra"
        private const val CHANNEL_ID = "foreground_service_channel"
        fun startService(context: Context, message: String, callback: WorkerListener) {
            val intent = Intent(context, SmartBandService::class.java)
            intent.putExtra(INPUT_EXTRA_KEY, message)
            ContextCompat.startForegroundService(context, intent);
            workerListener = callback
        }

        fun stopService(context: Context) {
            val intent = Intent(context, SmartBandService::class.java)
            context.stopService(intent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, getNotification(intent))
        sleep(3 * 1000L)

        val pref = applicationContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        val number = pref.getInt(PREF_RECORD_NUMBER, 0)
        pref.edit().putInt(PREF_RECORD_NUMBER, number + 1).apply()
        pref.edit().putString(PREF_RECORD_TIME, getCurrentTime()).apply()

        workerListener.onWorkCompleted()
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun getNotification(intent: Intent?): Notification {
        createNotificationChannel()

        val input = intent?.getStringExtra(INPUT_EXTRA_KEY)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Example")
            .setContentText(input)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Example",
                NotificationManager.IMPORTANCE_LOW
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}