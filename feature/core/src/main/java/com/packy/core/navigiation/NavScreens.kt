package com.packy.core.navigiation

import androidx.navigation.NamedNavArgument

abstract class NavScreens(
    private val _route: String,
    private val _navArguments: List<NamedNavArgument>,
) {
    val name: String = _route.appendArguments(navArguments = _navArguments)
}