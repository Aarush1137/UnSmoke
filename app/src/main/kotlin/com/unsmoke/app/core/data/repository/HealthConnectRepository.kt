package com.unsmoke.app.core.data.repository

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthConnectRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }

    fun isAvailable(): Boolean {
        return HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE
    }

    suspend fun getRestingHeartRateRecords(startTime: Instant, endTime: Instant): List<RestingHeartRateRecord> {
        if (!isAvailable()) return emptyList()
        return try {
            val request = ReadRecordsRequest(
                recordType = RestingHeartRateRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
            val response = healthConnectClient.readRecords(request)
            response.records
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSleepSessionRecords(startTime: Instant, endTime: Instant): List<SleepSessionRecord> {
        if (!isAvailable()) return emptyList()
        return try {
            val request = ReadRecordsRequest(
                recordType = SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
            val response = healthConnectClient.readRecords(request)
            response.records
        } catch (e: Exception) {
            emptyList()
        }
    }
}