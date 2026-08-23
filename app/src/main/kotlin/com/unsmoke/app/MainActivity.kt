package com.unsmoke.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.biometric.BiometricPrompt
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.designsystem.UnSmokeTheme
import com.unsmoke.app.feature.update.UpdateDialogController
import com.unsmoke.app.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    
    @Inject
    lateinit var dataStore: UserPreferencesDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isUnlocked by remember { mutableStateOf(false) }
            var isChecking by remember { mutableStateOf(true) }
            val selectedTheme by dataStore.theme.collectAsState(initial = "DARK")
            val selectedAccent by dataStore.accentColor.collectAsState(initial = "MINT")

            LaunchedEffect(Unit) {
                val biometricsEnabled = dataStore.appLockEnabled.first()

                if (biometricsEnabled) {
                    showBiometricPrompt { success ->
                        isUnlocked = success
                        isChecking = false
                    }
                } else {
                    isUnlocked = true
                    isChecking = false
                }
            }

            val darkTheme = when (selectedTheme) {
                "LIGHT" -> false
                "SYSTEM" -> isSystemInDarkTheme()
                else -> true // DARK and AMOLED currently share the dark colour scheme.
            }

            UnSmokeTheme(darkTheme = darkTheme, accentName = selectedAccent) {
                if (isChecking) {
                    // Empty loading state while checking biometrics
                    Box(Modifier.fillMaxSize())
                } else if (isUnlocked) {
                    UpdateDialogController()
                    AppNavGraph()
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("App Locked")
                    }
                }
            }
        }
    }

    private fun showBiometricPrompt(onResult: (Boolean) -> Unit) {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onResult(true)
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onResult(false) // Or handle error
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock UnSmoke")
            .setSubtitle("Confirm your identity to access your private data.")
            .setDeviceCredentialAllowed(true)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
