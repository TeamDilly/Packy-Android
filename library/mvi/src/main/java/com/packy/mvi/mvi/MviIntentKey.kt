package com.packy.mvi.mvi

data class MviIntentKey<T : MviIntent>(
    val clazz: Class<T>
)