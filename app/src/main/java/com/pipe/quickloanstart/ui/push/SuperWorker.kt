package com.pipe.quickloanstart.ui.push
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pipe.quickloanstart.R
import com.pipe.quickloanstart.extensions.SharedPrefs
import com.pipe.quickloanstart.extensions.getLocaleStringResource
import com.pipe.quickloanstart.ui.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*


@HiltWorker
class SuperWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.i("SuperWorker ->", "start")
        if (SharedPrefs(applicationContext).getSizeList() > 0) showNotification()
        return Result.success()
    }

    private fun showNotification() {
        val local = Locale(SharedPrefs(applicationContext).getLanguage())
        val dateText = SharedPrefs(applicationContext).getTextPush()
        val textTitle = applicationContext.getLocaleStringResource(local, R.string.send_message_str)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(applicationContext, "ChannelId-111")
        notificationManager.cancel(111)

        val remoteView = RemoteViews(applicationContext.packageName, R.layout.notification_layout)
        remoteView.setTextViewText(R.id.tv_title, textTitle)
        remoteView.setTextViewText(R.id.tv_text, dateText)

        val switchIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingSwitchIntent =
            PendingIntent.getBroadcast(applicationContext, 1212, switchIntent, 0)

        builder.setSmallIcon(R.drawable.ic_money)
        builder.setContentText(SharedPrefs(applicationContext).getTextPush())
        builder.setFullScreenIntent(pendingSwitchIntent, true)
        builder.priority = Notification.PRIORITY_HIGH
        builder.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
        builder.setContent(remoteView)

        val channelId = "ChannelId"
        val channel =
            NotificationChannel(channelId, "MyChannelName", NotificationManager.IMPORTANCE_HIGH)

        notificationManager.createNotificationChannel(channel)
        builder.setChannelId(channelId)
        val notification2 = builder.build()
        notificationManager.notify(111, notification2)
    }
}