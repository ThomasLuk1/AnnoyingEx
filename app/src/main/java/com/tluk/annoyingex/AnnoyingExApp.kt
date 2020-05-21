package com.tluk.annoyingex

import android.app.Application

class AnnoyingExApp: Application() {

    lateinit var workerManager: WorkerManager
        private set
    lateinit var exNotificationManager: ExNotificationManager
        private set
    var messages: ArrayList<String> = arrayListOf<String>()


    override fun onCreate() {
        super.onCreate()
        workerManager = WorkerManager(this)
        exNotificationManager = ExNotificationManager(this)
    }

}