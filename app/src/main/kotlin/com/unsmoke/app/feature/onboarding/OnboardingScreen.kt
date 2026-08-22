package com.unsmoke.app.feature.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) {
            onComplete()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            AnimatedContent(
                targetState = state.step,
                transitionSpec = {
                    slideInHorizontally { width -> width } + fadeIn() togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut()
                }, label = "onboarding_step"
            ) { targetStep ->
                when (targetStep) {
                    0 -> WelcomeStep(onNext = { viewModel.updateStep(1) })
                    1 -> BaselineStep(
                        cigsPerDay = state.cigarettesPerDay,
                        onCigsChange = viewModel::updateCigarettesPerDay,
                        packPrice = state.packPrice,
                        onPriceChange = viewModel::updatePackPrice,
                        onNext = { viewModel.updateStep(2) }
                    )
                    2 -> QuitDateStep(
                        currentDate = state.quitDate,
                        onDateChange = viewModel::updateQuitDate,
                        onNext = { viewModel.updateStep(3) }
                    )
                    3 -> ProfileStep(
                        name = state.userName,
                        onNameChange = viewModel::updateUserName,
                        onFinish = { viewModel.completeOnboarding() }
                    )
                }
            }
        }
    }
}

@Composable
private fun WelcomeStep(onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("UnSmoke", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text("One craving at a time.", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("LET'S BEGIN", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun BaselineStep(
    cigsPerDay: String, onCigsChange: (String) -> Unit,
    packPrice: String, onPriceChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text("Let's set your baseline.", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = cigsPerDay,
            onValueChange = onCigsChange,
            label = { Text("Cigarettes per day") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = packPrice,
            onValueChange = onPriceChange,
            label = { Text("Price per pack") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = cigsPerDay.isNotBlank() && packPrice.isNotBlank()
        ) {
            Text("NEXT")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuitDateStep(
    currentDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    onNext: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text("When did you stop smoking?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedButton(
            onClick = { onDateChange(LocalDate.now()) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Today", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = { onDateChange(LocalDate.now().minusDays(1)) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Yesterday", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Choose a date ()", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("NEXT")
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        onDateChange(selectedDate)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun ProfileStep(name: String, onNameChange: (String) -> Unit, onFinish: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Text("What should we call you?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Your name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = name.isNotBlank()
        ) {
            Text("START MY JOURNEY")
        }
    }
}
