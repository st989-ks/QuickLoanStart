package com.pipe.quickloanstart.ui.push
import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.pipe.quickloanstart.extensions.REPEAT_INTERVAL_MINUTE
import com.pipe.quickloanstart.extensions.TAG_PERIOD_WORK
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class PushWorker @Inject constructor(
    private val application: Application,
) {

    fun getPush() {
        val constraints = Constraints.Builder().setRequiresBatteryNotLow(true).build()
        val periodWork = PeriodicWorkRequest.Builder(
            SuperWorker::class.java, REPEAT_INTERVAL_MINUTE,
            TimeUnit.MINUTES
        )
            .addTag(TAG_PERIOD_WORK)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(application).enqueueUniquePeriodicWork(
            TAG_PERIOD_WORK,
            ExistingPeriodicWorkPolicy.KEEP, periodWork
        )
    }
}