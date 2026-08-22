package com.unsmoke.app.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val UnSmokeShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
// Usage guide:
// Chips/tags: extraSmall (8dp)
// Small cards: small (12dp)  
// Buttons, standard cards: medium (16dp)
// Feature cards: large (20dp)
// Hero cards, bottom sheets: extraLarge (28dp)
