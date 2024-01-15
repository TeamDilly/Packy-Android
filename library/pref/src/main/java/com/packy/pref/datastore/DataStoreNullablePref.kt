package com.packy.pref.datastore

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.packy.pref.NullablePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlin.reflect.KClass

class DataStoreNullablePref(
    private val context: Context,
    private val domain: String
) : NullablePref {

    private val dataStore = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(domain)
    }

    private suspend fun <T> getFromDataStore(
        key: String,
        defaultValue: T?,
        type: KClass<*>
    ): Flow<T?> {
        return dataStore.data
            .catch { message ->
                throw DataStoreException(cause = message)
            }
            .map { preferences ->
                val value: Any? = when (type) {
                    Int::class -> preferences[intPreferencesKey(key)]
                    Long::class -> preferences[longPreferencesKey(key)]
                    Float::class -> preferences[floatPreferencesKey(key)]
                    Boolean::class -> preferences[booleanPreferencesKey(key)]
                    Double::class -> preferences[doublePreferencesKey(key)]
                    String::class -> preferences[stringPreferencesKey(key)]
                    else -> {
                        val json = preferences[stringPreferencesKey(key)]
                        json?.let { Json.decodeFromString(it) }
                    }
                }

                @Suppress("UNCHECKED_CAST")
                value as? T ?: defaultValue
            }
    }

    private suspend fun <T> putToDataStore(key: String, data: T) {
        when (data) {
            is Int -> safeDataStoreOperation<Int>(key) { preferences ->
                preferences[intPreferencesKey(key)] = data
            }

            is Long -> safeDataStoreOperation<Long>(key) { preferences ->
                preferences[longPreferencesKey(key)] = data
            }

            is Float -> safeDataStoreOperation<Float>(key) { preferences ->
                preferences[floatPreferencesKey(key)] = data
            }

            is Boolean -> safeDataStoreOperation<Boolean>(key) { preferences ->
                preferences[booleanPreferencesKey(key)] = data
            }

            is Double -> safeDataStoreOperation<Double>(key) { preferences ->
                preferences[doublePreferencesKey(key)] = data
            }

            is String -> safeDataStoreOperation<String>(key) { preferences ->
                preferences[stringPreferencesKey(key)] = data
            }

            is KSerializer<*> -> {
                val serializedData = Json.encodeToString(data)
                safeDataStoreOperation<KClass<Any>>(key) { preferences ->
                    preferences[stringPreferencesKey(key)] = serializedData
                }
            }

            else -> throw DataStoreException(message = " This class does not have a serializer.")
        }
    }

    private suspend inline fun <reified T> safeDataStoreOperation(
        key: String,
        crossinline operation: (preferences: MutablePreferences) -> Unit
    ) {
        try {
            dataStore.edit { preferences ->
                operation(preferences)
            }
        } catch (e: Exception) {
            throw DataStoreException(key, e)
        }
    }

    override suspend fun <T> get(key: String, defaultValue: T?, type: KClass<*>): Flow<T?> =
        getFromDataStore<T>(key, defaultValue, type)

    override suspend fun <T> put(key: String, data: T?) = putToDataStore(key, data)
    override suspend fun clear(key: String, type: KClass<*>) {

        dataStore.edit { preferences ->
            when (type) {
                Int::class -> preferences.remove(intPreferencesKey(key))
                Long::class -> preferences.remove(longPreferencesKey(key))
                Float::class -> preferences.remove(floatPreferencesKey(key))
                Boolean::class -> preferences.remove(booleanPreferencesKey(key))
                Double::class -> preferences.remove(doublePreferencesKey(key))
                String::class -> preferences.remove(stringPreferencesKey(key))
                else -> preferences.remove(stringPreferencesKey(key))
            }
        }
    }

    override suspend fun clearAll() {
        dataStore.edit{ preferences ->
            preferences.clear()
        }
    }
}