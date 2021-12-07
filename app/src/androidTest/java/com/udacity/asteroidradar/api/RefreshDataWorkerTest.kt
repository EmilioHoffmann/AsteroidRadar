package com.udacity.asteroidradar.api

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RefreshDataWorkerTest {

    @Before
    fun setUp() {
        val context = getApplicationContext<Context>()
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testToForceWorkerRun() {
        val request = OneTimeWorkRequestBuilder<RefreshDataWorker>()
            .build()

        val workManager = WorkManager.getInstance(getApplicationContext())
        runBlocking {
            workManager.enqueue(request).result.get()
            delay(15000)
        }

        val workInfo = workManager.getWorkInfoById(request.id).get()
        assert(workInfo.state == WorkInfo.State.SUCCEEDED)
    }
}
