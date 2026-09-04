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
fun UpdateDialogController() {
    val context = LocalContext.current
    val currentVersion = try { context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "0.1.0" } catch (e: Exception) { "0.1.0" }
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
                    Text("Version ${updateInfo!!.latestVersion} is now available.", fontSize = 16.sp)
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
    try {
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open update link.", Toast.LENGTH_SHORT).show()
    }
}


