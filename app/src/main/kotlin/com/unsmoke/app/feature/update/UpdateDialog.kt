package com.unsmoke.app.feature.update

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.unsmoke.app.core.network.UpdateChecker
import kotlinx.coroutines.launch

@Composable
fun UpdateDialogController(currentVersion: String = "0.1.0") {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var updateInfo by remember { mutableStateOf<UpdateChecker.UpdateInfo?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val info = UpdateChecker.checkForUpdate(currentVersion)
            if (info != null && info.isUpdateAvailable) {
                updateInfo = info
                showDialog = true
            }
        }
    }

    if (showDialog && updateInfo != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Update Available", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            text = {
                Column {
                    Text("Version \ is now available.", fontSize = 16.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("What's new:", fontWeight = FontWeight.SemiBold)
                    Text(updateInfo!!.releaseNotes, fontSize = 14.sp)
                }
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    startDownload(context, updateInfo!!.downloadUrl, updateInfo!!.latestVersion)
                }) {
                    Text("Update Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Later")
                }
            }
        )
    }
}

private fun startDownload(context: Context, url: String, version: String) {
    Toast.makeText(context, "Downloading UnSmoke v\...", Toast.LENGTH_LONG).show()
    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle("UnSmoke Update v\")
        .setDescription("Downloading latest version")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "UnSmoke_v\.apk")
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)

    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}
