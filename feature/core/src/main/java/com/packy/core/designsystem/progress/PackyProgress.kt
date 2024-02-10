package com.packy.core.designsystem.progress

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.packy.core.animations.RotationAnimation
import com.packy.feature.core.R

@Composable
fun PackyProgress(){
    RotationAnimation(
        isPlaying = true
    ){
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.progress_circle),
            contentDescription = "progress circle"
        )
    }
}