package com.tluk.annoyingex

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class NotificationsWorker(private val context: Context, workParams: WorkerParameters): Worker(context , workParams) {
    override fun doWork(): Result {
        Log.i("info", "HEHEHEHAHAHA")
        val annoyingExApp = (context.applicationContext as AnnoyingExApp)
        val exNotificationManager = annoyingExApp.exNotificationManager
        val messages = annoyingExApp.messages
        var notificationText = "unable to retrieve message"
        if (messages.size > 0) {
            val randomIndex = Random.nextInt(messages.size)
            notificationText = messages.get(randomIndex)
        }
        exNotificationManager.postNotification(notificationText)
        Log.i("info", notificationText)
        return Result.success()
    }
}