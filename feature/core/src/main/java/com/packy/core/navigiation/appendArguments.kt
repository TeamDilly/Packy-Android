package com.packy.core.navigiation

import androidx.navigation.NamedNavArgument

fun String.appendArguments(navArguments: List<NamedNavArgument>) = buildString {
    append(this@appendArguments)

    // 필수 인자를 추가한다.
    navArguments
        .filter { it.argument.defaultValue == null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
        ?.let {
            println("LOGEE 1 $it")
            append(it) }

    // 선택 인자를 추가한다.
    navArguments
        .filter { it.argument.defaultValue != null }
        .takeIf { it.isNotEmpty() }
        ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
        ?.let { append(it) }
}