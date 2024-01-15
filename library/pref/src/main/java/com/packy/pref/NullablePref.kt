package com.packy.pref

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface NullablePref {
    suspend fun <T> get(key: String, defaultValue: T?, type: KClass<*>): Flow<T?>
    suspend fun <T> put(key: String, data: T?)
    suspend fun clear(key: String, type: KClass<*>)
    suspend fun clearAll()
}