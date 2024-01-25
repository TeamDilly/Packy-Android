package com.packy.pref.manager

import android.content.Context
import com.packy.pref.NonNullPref
import com.packy.pref.NullablePref
import com.packy.pref.NonNullPrefItem
import com.packy.pref.NullablePrefItem
import com.packy.pref.datastore.DataStoreNonNullPref
import com.packy.pref.datastore.DataStoreNullablePref
import com.packy.pref.memory.MemoryNonNullPref
import com.packy.pref.memory.MemoryNullablePref
import kotlin.reflect.KClass

open class PrefManager(
    private val appContext: Context,
    private val domainName: String
) {
    private val dataStoreNullablePref: NullablePref = DataStoreNullablePref(appContext, domainName)
    private val memoryNullablePref: NullablePref = MemoryNullablePref()

    private val dataStoreNonNullPref: NonNullPref = DataStoreNonNullPref(appContext, domainName)
    private val memoryNonNullPref: NonNullPref = MemoryNonNullPref()

    fun <T> createMemoryPrefItem(key: String, defaultValue: T?, type: KClass<*>) =
        NullablePrefItem(
            key = key,
            defaultValue = defaultValue,
            prefStrategy = PrefStrategy.MEMORY_ONLY,
            dataStoreNullablePref = dataStoreNullablePref,
            memoryNullablePref = memoryNullablePref,
            type = type,
        )

    fun <T> createPreferencePrefItem(key: String, defaultValue: T?, type: KClass<*>) =
        NullablePrefItem(
            key = key,
            defaultValue = defaultValue,
            prefStrategy = PrefStrategy.FILE_ONLY,
            dataStoreNullablePref = dataStoreNullablePref,
            memoryNullablePref = memoryNullablePref,
            type = type,
        )

    fun <T> createNonNullMemoryPrefItem(key: String, defaultValue: T, type: KClass<*>) =
        NonNullPrefItem(
            key = key,
            defaultValue = defaultValue,
            prefStrategy = PrefStrategy.MEMORY_ONLY,
            dataStoreNonNullPref = dataStoreNonNullPref,
            memoryNonNullPref = memoryNonNullPref,
            type = type,
        )

    fun <T> createNonNullPreferencePrefItem(key: String, defaultValue: T, type: KClass<*>) =
        NonNullPrefItem(
            key = key,
            defaultValue = defaultValue,
            prefStrategy = PrefStrategy.FILE_ONLY,
            dataStoreNonNullPref = dataStoreNonNullPref,
            memoryNonNullPref = memoryNonNullPref,
            type = type,
        )
}