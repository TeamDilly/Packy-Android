package com.packy.pref

import com.packy.pref.manager.PrefStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlin.reflect.KClass

data class NullablePrefItem<T>(
    private val key: String,
    private val defaultValue: T?,
    private val prefStrategy: PrefStrategy,
    private val dataStoreNullablePref: NullablePref,
    private val memoryNullablePref: NullablePref,
    private val type: KClass<*>
) {

    suspend fun putData(data: T) {
        getPrefItem(prefStrategy).put(
            key,
            data
        )
    }

    suspend fun getData(): Flow<T?> {
        getPrefItem(prefStrategy)
        val memoryResult = memoryNullablePref.get(
            key,
            defaultValue,
            type
        ).first()

        return if (memoryResult == defaultValue) {
            dataStoreNullablePref.get(
                key,
                defaultValue,
                type
            )
        } else {
            memoryNullablePref.get(
                key,
                defaultValue,
                type
            )
        }
    }

    suspend fun clear() {
        getPrefItem(prefStrategy).clear(
            key,
            type
        )
    }

    suspend fun reset() {
        getPrefItem(prefStrategy).reset(
            key,
            defaultValue
        )
    }


    private fun getPrefItem(strategy: PrefStrategy) = when (strategy) {
        PrefStrategy.MEMORY_ONLY -> memoryNullablePref
        PrefStrategy.FILE_ONLY -> dataStoreNullablePref
    }
}
