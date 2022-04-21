package net.ibtikar.task.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import net.ibtikar.task.R
import net.ibtikar.task.ui.MainActivity

class AlarmBroadcastReceiver : BroadcastReceiver() {

    var chanelId = "chanelId"
    var chanelName = "ToDo Ibtikar"
    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    fun showNotification(context: Context?) {
        var builder = NotificationCompat.Builder(context?.applicationContext!!, chanelId)
        val notifiIntent = Intent(context, MainActivity::class.java)
        val bundle = Bundle()
        notifiIntent.putExtras(bundle)
        notifiIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        val contentIntent =
            PendingIntent.getActivity(context, 0, notifiIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= 26) {
            val chanel =
                NotificationChannel(chanelId, chanelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(chanel)
            builder.setContentTitle("ToDo Ibtikar")
        } else {
            builder.setContentTitle("ToDo Ibtikar").priority = Notification.DEFAULT_ALL
        }
        builder.setContentIntent(contentIntent).setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Check your ToDo List Please").setAutoCancel(true)
        notificationManager.notify(1, builder.build())
    }
}

