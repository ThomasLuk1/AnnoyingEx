package com.tluk.annoyingex

import android.content.Context
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.tluk.annoyingex.model.MessageList
import java.util.concurrent.TimeUnit

class WorkerManager(private val context: Context) {

    private var workManager = WorkManager.getInstance(context)

    fun startAnnoying() {
        if (isAnnoyingExRunning()) {
            stopWork()
        }
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NotificationsWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5000, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag(TAG)
            .build()

        workManager.enqueue(workRequest)
    }

    private fun isAnnoyingExRunning(): Boolean {
        return when (workManager.getWorkInfosByTag(TAG).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
            else -> false
        }
    }

    fun stopWork() {
        workManager.cancelAllWorkByTag(TAG)
    }

    companion object {
        const val TAG = "ANNOYING_EX_TAG"
    }
}