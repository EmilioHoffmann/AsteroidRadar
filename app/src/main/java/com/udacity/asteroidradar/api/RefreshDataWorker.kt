package com.udacity.asteroidradar.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.haroldadmin.cnradapter.NetworkResponse
import com.udacity.asteroidradar.db.AsteroidsDao
import org.koin.java.KoinJavaComponent
import retrofit2.HttpException

class RefreshDataWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    private val database: AsteroidsDao by KoinJavaComponent.inject(AsteroidsDao::class.java)
    private val service: NasaApiService by KoinJavaComponent.inject(NasaApiService::class.java)

    override suspend fun doWork(): Result {
        return try {
            when (val result = service.getAsteroids()) {
                is NetworkResponse.Success -> {
                    database.insert(parseAsteroidsJsonResult(result.body))
                    Result.success()
                }
                else -> {
                    Result.retry()
                }
            }
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
