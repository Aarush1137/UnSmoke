package com.unsmoke.app.feature.onboarding
import androidx.compose.animation.togetherWith

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Healing
import androidx.compose.material.icons.rounded.MedicalServices
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val MintColor = androidx.compose.ui.graphics.Color(0xFF8FDCD0)
private val AmberColor = androidx.compose.ui.graphics.Color(0xFFD8AC60)
private val DarkSurface = Color(0xFF1E2625)
private val DarkHighlight = Color(0xFF163F3A)
private val TotalSteps = 8f

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isComplete) {
        if (state.isComplete) onComplete()
    }

    Scaffold(containerColor = androidx.compose.ui.graphics.Color(0xFF011113)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            // Step indicator bar
            if (state.step > 0) {
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { state.step / TotalSteps },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = MintColor,
                    trackColor = DarkSurface
                )
                Spacer(Modifier.height(8.dp))
            }

            AnimatedContent(
                targetState = state.step,
                transitionSpec = {
                    slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
                },
                label = "onboarding_step",
                modifier = Modifier.weight(1f)
            ) { step ->
                when (step) {
                    0 -> WelcomeStep(onNext = { viewModel.updateStep(1) })
                    1 -> AlreadyQuitStep(
                        selected = state.alreadyQuit,
                        onSelect = { viewModel.setAlreadyQuit(it) },
                        onNext = { viewModel.updateStep(2) }
                    )
                    2 -> QuitDateStep(
                        alreadyQuit = state.alreadyQuit ?: false,
                        currentDate = state.quitDate,
                        onDateChange = viewModel::updateQuitDate,
                        onNext = { viewModel.updateStep(3) }
                    )
                    3 -> BaselineStep(
                        cigsPerDay = state.cigarettesPerDay,
                        onCigsChange = viewModel::updateCigarettesPerDay,
                        packPrice = state.packPrice,
                        onPriceChange = viewModel::updatePackPrice,
                        onNext = { viewModel.updateStep(4) }
                    )
                    4 -> ChoiceStep(
                        title = "What usually triggers a cigarette?",
                        subtitle = "Choose the situations you expect to need a plan for.",
                        options = TRIGGER_OPTIONS,
                        selected = state.triggers,
                        onToggle = viewModel::toggleTrigger,
                        nextLabel = "BUILD MY COPING PLAN",
                        onNext = { viewModel.updateStep(5) }
                    )
                    5 -> ChoiceStep(
                        title = "What support can you lean on?",
                        subtitle = "We will add these to your personal plan. You can change them later.",
                        options = SUPPORT_OPTIONS,
                        selected = state.supports,
                        onToggle = viewModel::toggleSupport,
                        nextLabel = "CONTINUE",
                        onNext = { viewModel.updateStep(6) }
                    )
                    6 -> NrtStep(
                        selected = state.nrtProduct,
                        onSelect = viewModel::updateNrtProduct,
                        onNext = { viewModel.updateStep(7) }
                    )
                    7 -> GoalsStep(
                        shortTermGoal = state.shortTermGoal,
                        onShortTermChange = viewModel::updateShortTermGoal,
                        longTermGoal = state.longTermGoal,
                        onLongTermChange = viewModel::updateLongTermGoal,
                        onNext = { viewModel.updateStep(8) }
                    )
                    8 -> ProfileStep(
                        name = state.userName,
                        onNameChange = viewModel::updateUserName,
                        quitReason = state.quitReason,
                        onReasonChange = viewModel::updateQuitReason,
                        onFinish = viewModel::completeOnboarding
                    )
                }
            }
        }
    }
}

private val TRIGGER_OPTIONS = listOf(
    "After meals", "Stress", "Coffee or tea", "Alcohol",
    "Driving", "Work break", "With smokers", "Boredom",
    "Morning routine", "Phone calls", "Before bed", "Loneliness"
)
private val SUPPORT_OPTIONS = listOf(
    "A friend or family member", "The craving timer",
    "A walk or movement", "Breathing exercise",
    "India Tobacco Quitline (1800-112-356)", "A clinician or counsellor",
    "Chewing gum or snacks", "Journaling"
)

// ============================= STEP 0: Welcome ==============================
@Composable
private fun WelcomeStep(onNext: () -> Unit) = OnboardingColumn {
    Spacer(Modifier.weight(1f))
    Text(
        "UnSmoke",
        fontSize = 48.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MintColor,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(12.dp))
    Text(
        "One craving at a time.",
        fontSize = 18.sp,
        color = Color.White.copy(alpha = 0.7f),
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(8.dp))
    Text(
        "Your journey, your rules.\nNo shame. No pressure.",
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.5f),
        textAlign = TextAlign.Center,
        lineHeight = 20.sp
    )
    Spacer(Modifier.weight(1f))
    MintButton("LET'S BEGIN", onNext)
    Spacer(Modifier.height(24.dp))
}

// ===================== STEP 1: Have you already quit? =======================
@Composable
private fun AlreadyQuitStep(
    selected: Boolean?,
    onSelect: (Boolean) -> Unit,
    onNext: () -> Unit
) = OnboardingColumn {
    Spacer(Modifier.weight(0.3f))
    Text(
        "Have you already\nstopped smoking?",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
        lineHeight = 36.sp
    )
    Spacer(Modifier.height(12.dp))
    Text(
        "It\u2019s okay either way. This helps us set the right starting point.",
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.6f),
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(40.dp))

    SelectionCard(
        title = "Yes, I already quit",
        subtitle = "I stopped on a specific date and want to track my progress from then.",
        icon = Icons.Rounded.CheckCircle,
        isSelected = selected == true,
        onClick = { onSelect(true) }
    )
    Spacer(Modifier.height(16.dp))
    SelectionCard(
        title = "No, I\u2019m starting fresh",
        subtitle = "Today is my Day 1. Let\u2019s do this.",
        icon = Icons.Rounded.PlayArrow,
        isSelected = selected == false,
        onClick = { onSelect(false) }
    )

    Spacer(Modifier.weight(1f))
    MintButton("NEXT", onNext, enabled = selected != null)
    Spacer(Modifier.height(24.dp))
}

// ========================= STEP 2: Quit Date ================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuitDateStep(
    alreadyQuit: Boolean,
    currentDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    onNext: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    )
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMM yyyy") }

    OnboardingColumn {
        Spacer(Modifier.weight(0.2f))
        if (alreadyQuit) {
            Text(
                "When did you quit?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "We\u2019ll count your smoke-free days from this date.\nYou can backdate as far as you need.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f),
                lineHeight = 20.sp
            )
            Spacer(Modifier.height(32.dp))

            DateOptionButton(
                "Today",
                currentDate == LocalDate.now()
            ) { onDateChange(LocalDate.now()) }
            Spacer(Modifier.height(12.dp))
            DateOptionButton(
                "Yesterday",
                currentDate == LocalDate.now().minusDays(1)
            ) { onDateChange(LocalDate.now().minusDays(1)) }
            Spacer(Modifier.height(12.dp))
            DateOptionButton(
                "Choose another date",
                false
            ) { showDatePicker = true }

            if (currentDate != LocalDate.now() && currentDate != LocalDate.now().minusDays(1)) {
                Spacer(Modifier.height(16.dp))
                Text(
                    "Selected: " + currentDate.format(dateFormatter),
                    color = MintColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        } else {
            Text(
                "Your quit date is today!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MintColor
            )
            Spacer(Modifier.height(12.dp))
            Text(
                LocalDate.now().format(dateFormatter),
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "This is Day 1. Every minute counts from now.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }

        Spacer(Modifier.weight(1f))
        MintButton("NEXT", onNext)
        Spacer(Modifier.height(24.dp))
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateChange(
                            Instant.ofEpochMilli(it)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        )
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

// ========================= STEP 3: Baseline =================================
@Composable
private fun BaselineStep(
    cigsPerDay: String,
    onCigsChange: (String) -> Unit,
    packPrice: String,
    onPriceChange: (String) -> Unit,
    onNext: () -> Unit
) = ScrollableOnboardingColumn {
    Spacer(Modifier.height(24.dp))
    Text(
        "Your smoking baseline",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    Spacer(Modifier.height(8.dp))
    Text(
        "These numbers make your savings and health improvements personal.",
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.6f)
    )
    Spacer(Modifier.height(32.dp))

    DarkOutlinedField(cigsPerDay, onCigsChange, "Cigarettes per day", KeyboardType.Number)
    Spacer(Modifier.height(16.dp))
    DarkOutlinedField(packPrice, onPriceChange, "Price per pack (\u20B9)", KeyboardType.Decimal)

    val cigsNum = cigsPerDay.toDoubleOrNull() ?: 0.0
    val priceNum = packPrice.toDoubleOrNull() ?: 0.0
    if (cigsNum > 0 && priceNum > 0) {
        val dailyCost = (cigsNum / 20.0) * priceNum
        val yearlyCost = dailyCost * 365
        Spacer(Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = DarkHighlight),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "You spend approximately:",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "\u20B9${String.format("%.0f", dailyCost)}/day  \u2022  \u20B9${String.format("%.0f", yearlyCost)}/year",
                    color = AmberColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }

    Spacer(Modifier.height(32.dp))
    MintButton(
        "NEXT",
        onNext,
        cigsPerDay.toDoubleOrNull()?.let { it > 0.0 } == true &&
            packPrice.toDoubleOrNull()?.let { it >= 0.0 } == true
    )
    Spacer(Modifier.height(24.dp))
}

// =================== STEP 4 & 5: Triggers / Supports ========================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChoiceStep(
    title: String,
    subtitle: String,
    options: List<String>,
    selected: Set<String>,
    onToggle: (String) -> Unit,
    nextLabel: String,
    onNext: () -> Unit
) = ScrollableOnboardingColumn {
    Spacer(Modifier.height(24.dp))
    Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
    Spacer(Modifier.height(8.dp))
    Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
    Spacer(Modifier.height(24.dp))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        options.forEach { option ->
            val isSelected = option in selected
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (isSelected) MintColor else DarkSurface)
                    .clickable { onToggle(option) }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.Black else Color.White.copy(alpha = 0.8f),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
    }
    Spacer(Modifier.height(32.dp))
    MintButton(nextLabel, onNext)
    Spacer(Modifier.height(24.dp))
}

// ============================= STEP 6: NRT ==================================
@Composable
private fun NrtStep(
    selected: String,
    onSelect: (String) -> Unit,
    onNext: () -> Unit
) = ScrollableOnboardingColumn {
    Spacer(Modifier.height(24.dp))
    Text(
        "Would you like to\ntrack NRT usage?",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        lineHeight = 32.sp
    )
    Spacer(Modifier.height(8.dp))
    Text(
        "Tracking is for your own pattern data. UnSmoke does not recommend a dose \u2014 follow your product label and clinician\u2019s advice.",
        color = Color.White.copy(alpha = 0.6f),
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
    Spacer(Modifier.height(24.dp))

    NrtCard("NONE", "Not right now", "You can add NRT tracking later from settings.", Icons.Rounded.Close, selected, onSelect)
    Spacer(Modifier.height(12.dp))
    NrtCard("NICOTEX_GUM", "Nicotex Gum (\u20B980/9pc)", "Track your Nicotex usage with before/after craving ratings.", Icons.Rounded.Healing, selected, onSelect)
    Spacer(Modifier.height(12.dp))
    NrtCard("OTHER", "Another NRT product", "Patches, lozenges, sprays, inhalers \u2014 keep a personal record.", Icons.Rounded.MedicalServices, selected, onSelect)

    Spacer(Modifier.height(32.dp))
    MintButton("CONTINUE", onNext)
    Spacer(Modifier.height(24.dp))
}

// ============================= STEP 7: Goals ================================
@Composable
private fun GoalsStep(
    shortTermGoal: String,
    onShortTermChange: (String) -> Unit,
    longTermGoal: String,
    onLongTermChange: (String) -> Unit,
    onNext: () -> Unit
) = ScrollableOnboardingColumn {
    Spacer(Modifier.height(24.dp))
    Text(
        "Set your goals",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    Spacer(Modifier.height(8.dp))
    Text(
        "What are you fighting for? Having a clear target makes the hard moments easier.",
        fontSize = 14.sp,
        color = Color.White.copy(alpha = 0.6f),
        lineHeight = 20.sp
    )
    Spacer(Modifier.height(32.dp))

    // Short term
    Text(
        "SHORT-TERM GOAL",
        fontSize = 12.sp,
        color = MintColor,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
    Spacer(Modifier.height(6.dp))
    Text(
        "What do you want to achieve in the next 1\u20132 weeks?",
        fontSize = 13.sp,
        color = Color.White.copy(alpha = 0.5f)
    )
    Spacer(Modifier.height(8.dp))
    DarkOutlinedField(shortTermGoal, onShortTermChange, "e.g. Survive the first 72 hours")

    Spacer(Modifier.height(28.dp))

    // Long term
    Text(
        "LONG-TERM GOAL",
        fontSize = 12.sp,
        color = AmberColor,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
    Spacer(Modifier.height(6.dp))
    Text(
        "Where do you see yourself in 3\u201312 months?",
        fontSize = 13.sp,
        color = Color.White.copy(alpha = 0.5f)
    )
    Spacer(Modifier.height(8.dp))
    DarkOutlinedField(longTermGoal, onLongTermChange, "e.g. Run a 5K without wheezing")

    Spacer(Modifier.height(32.dp))
    MintButton("ALMOST DONE", onNext)
    Spacer(Modifier.height(24.dp))
}

// ============================= STEP 8: Profile ==============================
@Composable
private fun ProfileStep(
    name: String,
    onNameChange: (String) -> Unit,
    quitReason: String,
    onReasonChange: (String) -> Unit,
    onFinish: () -> Unit
) = ScrollableOnboardingColumn {
    Spacer(Modifier.height(24.dp))
    Text(
        "Make your plan yours",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
    Spacer(Modifier.height(24.dp))
    DarkOutlinedField(name, onNameChange, "What should we call you?")
    Spacer(Modifier.height(16.dp))
    OutlinedTextField(
        value = quitReason,
        onValueChange = onReasonChange,
        label = { Text("Your most important reason to quit", color = Color.Gray) },
        minLines = 2,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.DarkGray,
            focusedBorderColor = MintColor,
            unfocusedContainerColor = DarkSurface,
            focusedContainerColor = DarkSurface,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
    Spacer(Modifier.height(32.dp))
    MintButton(
        "START MY PERSONAL PLAN",
        onFinish,
        name.isNotBlank() && quitReason.isNotBlank()
    )
    Spacer(Modifier.height(24.dp))
}

// ======================== Shared Components ==================================

@Composable
private fun OnboardingColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
private fun ScrollableOnboardingColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
private fun MintButton(label: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MintColor,
            disabledContainerColor = DarkSurface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = if (enabled) Color.Black else Color.Gray
        )
    }
}

@Composable
private fun DarkOutlinedField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.DarkGray,
            focusedBorderColor = MintColor,
            unfocusedContainerColor = DarkSurface,
            focusedContainerColor = DarkSurface,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@Composable
private fun SelectionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) DarkHighlight else DarkSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) Modifier.border(2.dp, MintColor, RoundedCornerShape(20.dp))
                else Modifier
            )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) MintColor.copy(alpha = 0.2f) else Color(0xFF2A3331)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = if (isSelected) MintColor else Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
                Spacer(Modifier.height(2.dp))
                Text(subtitle, color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, lineHeight = 16.sp)
            }
        }
    }
}

@Composable
private fun DateOptionButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) DarkHighlight else Color.Transparent
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MintColor else Color.DarkGray)
    ) {
        Text(
            label,
            color = if (isSelected) MintColor else Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun NrtCard(
    value: String,
    title: String,
    subtitle: String,
    icon: ImageVector,
    selected: String,
    onSelect: (String) -> Unit
) {
    val isSelected = selected == value
    Card(
        onClick = { onSelect(value) },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) DarkHighlight else DarkSurface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) Modifier.border(2.dp, MintColor, RoundedCornerShape(18.dp))
                else Modifier
            )
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) MintColor else Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 15.sp)
                Spacer(Modifier.height(2.dp))
                Text(subtitle, fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f), lineHeight = 16.sp)
            }
        }
    }
}




