package com.unsmoke.app.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MoodSelector(
    selectedMood: Int?,
    onMoodSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val moods = listOf(
        1 to "Awful",
        2 to "Bad",
        3 to "Okay",
        4 to "Good",
        5 to "Great"
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moods.forEach { (value, label) ->
            FilterChip(
                selected = selectedMood == value,
                onClick = { onMoodSelected(value) },
                label = { Text(label) }
            )
        }
    }
}
