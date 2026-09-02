package com.unsmoke.app.core.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorManager @Inject constructor() {
    private val _errors = MutableSharedFlow<String>(extraBufferCapacity = 10)
    val errors = _errors.asSharedFlow()

    fun emitError(message: String) {
        _errors.tryEmit(message)
    }
}
