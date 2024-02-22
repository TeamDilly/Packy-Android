package com.packy.core.common

import kotlinx.serialization.json.Json

val PackyJson = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}