package com.packy.core.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun tickerFlow(delay: Long): Flow<Unit> = flow {
    while (true) {
        emit(Unit)
        delay(delay)
    }
}