package com.packy.common.authenticator.ext

import android.net.Uri

fun String.removeQueryParameters(): String {
    val uri = Uri.parse(this)
    return uri.buildUpon().clearQuery().toString()
}