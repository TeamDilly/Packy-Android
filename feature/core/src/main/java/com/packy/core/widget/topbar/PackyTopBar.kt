package com.packy.core.widget.topbar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.packy.core.common.NoRippleTheme
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

class PackyTopBar {
    data class Builder(
        private var showLogo: Boolean = false,
        private var startIconButton: (@Composable () -> Unit)? = null,
        private var startTitle: String? = null,
        private var centerTitle: String? = null,
        private var endIconButton: (@Composable () -> Unit)? = null,
        private var endIconButton2: (@Composable () -> Unit)? = null,
        private var endTextButton: (@Composable () -> Unit)? = null,
    ) {
        fun showLogo() = this.apply {
            showLogo = true
        }

        fun startIconButton(
            showShadow: Boolean = false,
            @DrawableRes icon: Int,
            onClick: () -> Unit
        ) = this.apply {
            startIconButton = {
                IconButton(
                    modifier = Modifier
                        .size(ICON_BUTTON_CLICKABLE_SIZE)
                        .apply {
                            if (showShadow) {
                                this
                                    .clip(CircleShape)
                                    .shadow(2.dp)
                            }
                        },
                    onClick = onClick
                ) {
                    Icon(
                        modifier = Modifier.size(ICON_BUTTON_SIZE),
                        painter = painterResource(id = icon),
                        contentDescription = "Icon Button",
                        tint = PackyTheme.color.gray900
                    )
                }
            }
        }

        fun startTitle(title: String) = this.apply {
            startTitle = title
        }

        fun centerTitle(title: String) = this.apply {
            centerTitle = title
        }

        fun endIconButton(
            showShadow: Boolean = false,
            @DrawableRes icon: Int,
            onClick: () -> Unit
        ) = this.apply {
            endIconButton = {
                IconButton(
                    modifier = Modifier
                        .size(ICON_BUTTON_CLICKABLE_SIZE)
                        .apply {
                            if (showShadow) {
                                this
                                    .clip(CircleShape)
                                    .shadow(2.dp)
                            }
                        },
                    onClick = onClick
                ) {
                    Icon(
                        modifier = Modifier.size(ICON_BUTTON_SIZE),
                        painter = painterResource(id = icon),
                        contentDescription = "Icon Button",
                        tint = PackyTheme.color.gray900
                    )
                }
            }
        }

        fun endIconButton2(
            showShadow: Boolean = false,
            @DrawableRes icon: Int,
            onClick: () -> Unit
        ) = this.apply {
            endIconButton2 = {
                IconButton(
                    modifier = Modifier
                        .size(ICON_BUTTON_CLICKABLE_SIZE)
                        .apply {
                            if (showShadow) {
                                this
                                    .clip(CircleShape)
                                    .shadow(2.dp)
                            }
                        },
                    onClick = onClick
                ) {
                    Icon(
                        modifier = Modifier.size(ICON_BUTTON_SIZE),
                        painter = painterResource(id = icon),
                        contentDescription = "Icon Button",
                        tint = PackyTheme.color.gray900
                    )
                }
            }
        }

        fun endTextButton(
            text: String,
            onClick: () -> Unit
        ) = this.apply {
            endTextButton = {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp)
                        .clickable(onClick = onClick),
                    text = text,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.gray600
                )
            }
        }

        @SuppressLint("ComposableNaming")
        @Composable
        fun build(modifier: Modifier = Modifier) {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(TOP_BAR_HEIGHT)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (showLogo) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_black),
                                contentDescription = "TopBar Logo"
                            )
                        }
                        startIconButton?.invoke()
                        if (startTitle != null) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = startTitle!!,
                                style = PackyTheme.typography.body01,
                                color = PackyTheme.color.gray900
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    if (centerTitle != null) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = centerTitle!!,
                            style = PackyTheme.typography.body01,
                            color = PackyTheme.color.gray900
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        endIconButton?.invoke()
                        endIconButton2?.invoke()
                        endTextButton?.invoke()
                    }
                }
            }
        }
    }

    companion object {
        private val TOP_BAR_HEIGHT = 48.dp
        private val ICON_BUTTON_SIZE = 24.dp
        private val ICON_BUTTON_CLICKABLE_SIZE = 40.dp
    }
}

