import os

file_path = r"E:\Projects\Unsmoke\app\src\main\kotlin\com\unsmoke\app\feature\home\HomeScreen.kt"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

target = """            // Hero Progress Ring
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                ProgressRing(progress = 1.0f, size = 240.dp) 
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = uiState.smokeFreeDays.toString(), fontSize = 64.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(text = "Days Free", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                }
            }"""

replacement = """            // Hero Progress Ring
            Box(
                modifier = Modifier.size(240.dp),
                contentAlignment = Alignment.Center
            ) {
                ProgressRing(progress = 1.0f, size = 240.dp) 
                uiState.startEpochMillis?.let { startEpoch ->
                    LiveTimerContent(startEpoch = startEpoch)
                } ?: run {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "0", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text(text = "Days Free", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
                    }
                }
            }"""

if target in content:
    content = content.replace(target, replacement)
else:
    print("TARGET 1 NOT FOUND")

# Append LiveTimerContent at the end
live_timer_content = """

@Composable
fun LiveTimerContent(startEpoch: Long) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }
    
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = System.currentTimeMillis()
        }
    }
    
    val diff = kotlin.math.max(0L, currentTime - startEpoch)
    val totalSeconds = diff / 1000
    val days = totalSeconds / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = days.toString(), fontSize = 64.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(text = "Days Free", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
        
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            TimeBlock(hours.toString().padStart(2, '0'), "h")
            Text(":", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            TimeBlock(minutes.toString().padStart(2, '0'), "m")
            Text(":", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            TimeBlock(seconds.toString().padStart(2, '0'), "s")
        }
    }
}

@Composable
fun TimeBlock(value: String, unit: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(text = value, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = unit, color = MaterialTheme.colorScheme.outline, fontSize = 12.sp, modifier = Modifier.padding(bottom = 2.dp))
    }
}
"""

content += live_timer_content

with open(file_path, "w", encoding="utf-8") as f:
    f.write(content)
print("SUCCESS")