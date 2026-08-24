import re

file_path = r'E:\Projects\Unsmoke\app\src\main\kotlin\com\unsmoke\app\feature\onboarding\OnboardingScreen.kt'
with open(file_path, 'r', encoding='utf-8') as f:
    code = f.read()

# Update the call to BaselineStep in the step switch
call_pattern = r'''(3 -> BaselineStep\()
\s*cigsPerDay = state\.cigarettesPerDay,
\s*onCigsChange = viewModel::updateCigarettesPerDay,
\s*ciggPrice = state\.packPrice,
\s*onPriceChange = viewModel::updatePackPrice,
\s*onNext = \{ viewModel\.updateStep\(4\) \}\n\s*\)'''

new_call = '''3 -> BaselineStep(
                        cigsPerDay = state.cigarettesPerDay,
                        onCigsChange = viewModel::updateCigarettesPerDay,
                        ciggPrice = state.packPrice,
                        onPriceChange = viewModel::updatePackPrice,
                        substanceType = state.substanceType,
                        onSubstanceChange = viewModel::updateSubstanceType,
                        nicotineStrengthMg = state.nicotineStrengthMg,
                        onNicotineChange = viewModel::updateNicotineStrength,
                        onNext = { viewModel.updateStep(4) }
                    )'''

code = re.sub(call_pattern, new_call, code, flags=re.MULTILINE)

# Update the BaselineStep signature and body
def_pattern = r'''(@Composable
private fun BaselineStep\(
    cigsPerDay: String,
    onCigsChange: \(String\) -> Unit,
    ciggPrice: String,
    onPriceChange: \(String\) -> Unit,
    onNext: \(\) -> Unit
\) = ScrollableOnboardingColumn \{
    Spacer\(Modifier\.height\(24\.dp\)\)
    Text\(
        "Your smoking baseline",
        fontSize = 28\.sp,
        fontWeight = FontWeight\.Bold,
        color = Color\.White
    \)
    Spacer\(Modifier\.height\(8\.dp\)\)
    Text\(
        "These numbers make your savings and health improvements personal\.",
        fontSize = 14\.sp,
        color = Color\.White\.copy\(alpha = 0\.6f\)
    \)
    Spacer\(Modifier\.height\(32\.dp\)\)

    DarkOutlinedField\(cigsPerDay, onCigsChange, "Cigarettes per day", KeyboardType\.Number\)
    Spacer\(Modifier\.height\(16\.dp\)\)
    DarkOutlinedField\(ciggPrice, onPriceChange, "Cost of one cigarette \(\\u20B9\)", KeyboardType\.Decimal\))'''

new_def = '''@Composable
private fun BaselineStep(
    cigsPerDay: String,
    onCigsChange: (String) -> Unit,
    ciggPrice: String,
    onPriceChange: (String) -> Unit,
    substanceType: String,
    onSubstanceChange: (String) -> Unit,
    nicotineStrengthMg: String,
    onNicotineChange: (String) -> Unit,
    onNext: () -> Unit
) = ScrollableOnboardingColumn {
    Spacer(Modifier.height(24.dp))
    Text(
        "Your baseline",
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

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DateOptionButton(
            label = "Cigarettes",
            isSelected = substanceType == "CIGARETTE",
            onClick = { onSubstanceChange("CIGARETTE") }
        )
        DateOptionButton(
            label = "Vaping",
            isSelected = substanceType == "VAPING",
            onClick = { onSubstanceChange("VAPING") }
        )
    }
    
    Spacer(Modifier.height(32.dp))

    if (substanceType == "CIGARETTE") {
        DarkOutlinedField(cigsPerDay, onCigsChange, "Cigarettes per day", KeyboardType.Number)
        Spacer(Modifier.height(16.dp))
        DarkOutlinedField(ciggPrice, onPriceChange, "Cost of one cigarette (₹)", KeyboardType.Decimal)
    } else {
        DarkOutlinedField(cigsPerDay, onCigsChange, "Pods/Vapes finished per week", KeyboardType.Number)
        Spacer(Modifier.height(16.dp))
        DarkOutlinedField(ciggPrice, onPriceChange, "Cost of one pod/vape (₹)", KeyboardType.Decimal)
        Spacer(Modifier.height(16.dp))
        DarkOutlinedField(nicotineStrengthMg, onNicotineChange, "Nicotine Strength (mg/ml, optional)", KeyboardType.Decimal)
    }'''

code = re.sub(def_pattern, new_def, code, flags=re.MULTILINE)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(code)

print("Patch applied.")