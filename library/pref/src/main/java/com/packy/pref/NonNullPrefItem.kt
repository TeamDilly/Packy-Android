package com.packy.pref

import com.packy.pref.manager.PrefStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.reflect.KClass

data class NonNullPrefItem<T>(
    private val key: String,
    private val defaultValue: T,
    private val prefStrategy: PrefStrategy,
    private val dataStoreNonNullPref: NonNullPref,
    private val memoryNonNullPref: NonNullPref,
    private val type: KClass<*>
) {

    suspend fun putData(data: T) {
        getPrefItem(prefStrategy).put(
            key,
            data
        )
    }

    suspend fun getData(): Flow<T> {
        return getPrefItem(prefStrategy).get(
            key,
            defaultValue,
            type
        )
    }

    suspend fun clear(
        key: String,
        strategy: PrefStrategy
    ) {
        getPrefItem(strategy).clear(
            key,
            type
        )
    }

    suspend fun clearAll(
        key: String,
        strategy: PrefStrategy
    ) {
        getPrefItem(strategy).clearAll()
    }

    private fun getPrefItem(strategy: PrefStrategy) = when (strategy) {
        PrefStrategy.MEMORY_ONLY -> memoryNonNullPref
        PrefStrategy.FILE_ONLY -> dataStoreNonNullPref
    }
}
