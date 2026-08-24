import os

file_path = r"E:\Projects\Unsmoke\app\src\main\kotlin\com\unsmoke\app\feature\onboarding\OnboardingScreen.kt"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

# Replace 1: Update the function call
call_target = """                    3 -> BaselineStep(
                        cigsPerDay = state.cigarettesPerDay,
                        onCigsChange = viewModel::updateCigarettesPerDay,
                        packPrice = state.packPrice,
                        onPriceChange = viewModel::updatePackPrice,
                        onNext = { viewModel.updateStep(4) }
                    )"""

call_replacement = """                    3 -> BaselineStep(
                        cigsPerDay = state.cigarettesPerDay,
                        onCigsChange = viewModel::updateCigarettesPerDay,
                        cigsPerPack = state.cigarettesPerPack,
                        onCigsPerPackChange = viewModel::updateCigarettesPerPack,
                        packPrice = state.packPrice,
                        onPriceChange = viewModel::updatePackPrice,
                        onNext = { viewModel.updateStep(4) }
                    )"""
content = content.replace(call_target, call_replacement)

# Replace 2: Update the function signature
sig_target = """private fun BaselineStep(
    cigsPerDay: String,
    onCigsChange: (String) -> Unit,
    packPrice: String,
    onPriceChange: (String) -> Unit,
    onNext: () -> Unit
)"""

sig_replacement = """private fun BaselineStep(
    cigsPerDay: String,
    onCigsChange: (String) -> Unit,
    cigsPerPack: String,
    onCigsPerPackChange: (String) -> Unit,
    packPrice: String,
    onPriceChange: (String) -> Unit,
    onNext: () -> Unit
)"""
content = content.replace(sig_target, sig_replacement)

# Replace 3: Update the UI to include the new field and logic
ui_target = """    DarkOutlinedField(cigsPerDay, onCigsChange, "Cigarettes per day", KeyboardType.Number)
    Spacer(Modifier.height(16.dp))
    DarkOutlinedField(packPrice, onPriceChange, "Price per pack (\u20B9)", KeyboardType.Decimal)

    val cigsNum = cigsPerDay.toDoubleOrNull() ?: 0.0
    val priceNum = packPrice.toDoubleOrNull() ?: 0.0
    if (cigsNum > 0 && priceNum > 0) {
        val dailyCost = (cigsNum / 20.0) * priceNum"""

ui_replacement = """    DarkOutlinedField(cigsPerDay, onCigsChange, "Cigarettes per day", KeyboardType.Number)
    Spacer(Modifier.height(16.dp))
    DarkOutlinedField(cigsPerPack, onCigsPerPackChange, "Cigarettes per pack (usually 10 or 20)", KeyboardType.Number)
    Spacer(Modifier.height(16.dp))
    DarkOutlinedField(packPrice, onPriceChange, "Price per pack (\u20B9)", KeyboardType.Decimal)

    val cigsNum = cigsPerDay.toDoubleOrNull() ?: 0.0
    val packNum = cigsPerPack.toDoubleOrNull() ?: 20.0
    val priceNum = packPrice.toDoubleOrNull() ?: 0.0
    if (cigsNum > 0 && priceNum > 0 && packNum > 0) {
        val dailyCost = (cigsNum / packNum) * priceNum"""
content = content.replace(ui_target, ui_replacement)

# Replace 4: Update the button validation
btn_target = """        cigsPerDay.toDoubleOrNull()?.let { it > 0.0 } == true &&
            packPrice.toDoubleOrNull()?.let { it >= 0.0 } == true"""

btn_replacement = """        cigsPerDay.toDoubleOrNull()?.let { it > 0.0 } == true &&
            packPrice.toDoubleOrNull()?.let { it >= 0.0 } == true &&
            cigsPerPack.toDoubleOrNull()?.let { it > 0.0 } == true"""
content = content.replace(btn_target, btn_replacement)

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)