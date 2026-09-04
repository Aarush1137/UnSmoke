package com.unsmoke.app.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object UpdateChecker {
    private const val REPO_OWNER = "Aarush1137"
    private const val REPO_NAME = "UnSmoke"
    private const val API_URL = "https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/releases/latest"

    data class UpdateInfo(
        val isUpdateAvailable: Boolean,
        val latestVersion: String,
        val downloadUrl: String,
        val releaseNotes: String
    )

    suspend fun checkForUpdate(currentVersionName: String): UpdateInfo? = withContext(Dispatchers.IO) {
        try {
            val url = URL(API_URL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)
                
                val tagName = json.getString("tag_name").removePrefix("v")
                val releaseNotes = json.optString("body", "Bug fixes and performance improvements.")
                
                val assets = json.getJSONArray("assets")
                var downloadUrl = ""
                for (i in 0 until assets.length()) {
                    val asset = assets.getJSONObject(i)
                    if (asset.getString("name").endsWith(".apk")) {
                        downloadUrl = asset.getString("browser_download_url")
                        break
                    }
                }

                // Simple version string comparison (e.g., "1.2.0" vs "1.1.0")
                val isUpdateAvailable = isNewerVersion(currentVersionName, tagName)

                if (downloadUrl.isNotEmpty()) {
                    return@withContext UpdateInfo(isUpdateAvailable, tagName, downloadUrl, releaseNotes)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }

    private fun isNewerVersion(current: String, latest: String): Boolean {
        val cleanCurrent = current.replace("v", "").substringBefore("-")
        val cleanLatest = latest.replace("v", "").substringBefore("-")
        val currentParts = cleanCurrent.split(".").mapNotNull { it.toIntOrNull() }
        val latestParts = cleanLatest.split(".").mapNotNull { it.toIntOrNull() }

        val length = maxOf(currentParts.size, latestParts.size)
        for (i in 0 until length) {
            val c = currentParts.getOrElse(i) { 0 }
            val l = latestParts.getOrElse(i) { 0 }
            if (l > c) return true
            if (l < c) return false
        }
        return false
    }
}

