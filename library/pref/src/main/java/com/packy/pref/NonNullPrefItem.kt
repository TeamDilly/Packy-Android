package com.packy.pref

import com.packy.pref.manager.PrefStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.reflect.KClass

data class NonNullPrefItem<T>(
    private val key: String,
    private val defaultValue: T,
    private val prefStrategy: PrefStrategy,
    private val dataStorePref: Pref,
    private val memoryPref: Pref,
    private val type: KClass<*>
) {

    suspend fun putData(data: T) {
        getPrefItem(prefStrategy).forEach {
            it.put(key, data)
        }
    }

    suspend fun getData(): Flow<T> {
        val memoryResult = memoryPref.get(key, defaultValue, type).first()
        return if (memoryResult == defaultValue) {
            dataStorePref.get(key, defaultValue, type)
        } else {
            memoryPref.get(key, defaultValue, type)
        }
    }

    suspend fun clear(key: String, strategy: PrefStrategy) {
        getPrefItem(strategy).forEach {
            it.clear(key, type)
        }
    }

    suspend fun clearAll(key: String, strategy: PrefStrategy) {
        getPrefItem(strategy).forEach {
            it.clearAll()
        }
    }

    private fun getPrefItem(strategy: PrefStrategy) = when (strategy) {
        PrefStrategy.MEMORY_ONLY -> listOf(memoryPref)
        PrefStrategy.FILE_ONLY -> listOf(dataStorePref)
        PrefStrategy.BOTH_MEMORY_AND_FILE -> listOf(memoryPref, dataStorePref)
    }
}
