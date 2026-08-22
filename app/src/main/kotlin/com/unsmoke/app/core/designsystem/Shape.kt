package com.unsmoke.app.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val UnSmokeShapes = Shapes(
    extraSmall = RoundedCornerShape(12.dp), // Small controls (10-12dp)
    small = RoundedCornerShape(16.dp),      // Chips/Tags (12-16dp)
    medium = RoundedCornerShape(18.dp),     // Buttons (16-18dp)
    large = RoundedCornerShape(20.dp),      // Cards (18-22dp)
    extraLarge = RoundedCornerShape(24.dp)  // Hero cards (24dp)
)

val BottomSheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
