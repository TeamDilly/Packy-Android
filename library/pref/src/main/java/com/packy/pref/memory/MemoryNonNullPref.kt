package com.packy.pref.memory

import com.packy.pref.NonNullPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass


class MemoryNonNullPref : NonNullPref {

    private val _memoryPref = MutableStateFlow<MutableMap<String, Any?>>(mutableMapOf())
    private val memoryPref: StateFlow<MutableMap<String, Any?>> get() = _memoryPref

    @Suppress("UNCHECKED_CAST")
    override suspend fun <T> get(key: String, defaultValue: T, type: KClass<*>): Flow<T> =
        memoryPref.map { it[key] as? T ?: defaultValue }

    override suspend fun <T> put(key: String, data: T) {
        val map = _memoryPref.value
        map[key] = data
        _memoryPref.update { map }
    }

    override suspend fun clear(key: String, type: KClass<*>) {
        val map = _memoryPref.value
        map.remove(key)
        _memoryPref.update { map }
    }

    override suspend fun <T> reset(
        key: String,
        defaultValue: T
    ) {
        put(key, defaultValue)
    }

    override suspend fun clearAll() {
        val map = _memoryPref.value
        map.clear()
        _memoryPref.update { map }
    }
}