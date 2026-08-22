while (True) {
    if (Test-Path "app\build\outputs\apk\debug\app-debug.apk") {
        & "C:\Users\aarus\AppData\Local\Android\Sdk\platform-tools\adb.exe" install -r "app\build\outputs\apk\debug\app-debug.apk"
        break
    }
    Start-Sleep -Seconds 5
}
