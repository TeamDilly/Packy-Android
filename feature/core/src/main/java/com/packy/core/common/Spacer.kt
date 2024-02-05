package com.packy.core.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.Spacer(width: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(width))

@Composable
fun RowScope.Spacer(weight: Float) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight))

@Composable
fun ColumnScope.Spacer(height: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(height))

@Composable
fun ColumnScope.Spacer(weight: Float) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight))

@Composable
fun LazyItemScope.Spacer(
    width: Dp = 0.dp,
    height: Dp = 0.dp,
) = androidx.compose.foundation.layout.Spacer(
    modifier = Modifier
        .width(width)
        .height(height)
)
