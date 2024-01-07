package com.packy.core.widget.button

import android.annotation.SuppressLint
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.packy.core.theme.lightPackyColor
import javax.annotation.concurrent.Immutable

val buttonStyle get() = PackyButtonStyle().buttonSize

@Immutable
class PackyButtonStyle internal constructor() {

    lateinit var buttonSize: ButtonSize
        private set
    lateinit var buttonColor: ButtonColor
        private set

    private val buttonColorType = ButtonColorType()

    private inner class ButtonSizeType {
        val large: ButtonColorType
            get() {
                buttonSize = ButtonSize.Large
                return buttonColorType
            }
        val medium: ButtonColorType
            get() {
                buttonSize = ButtonSize.Medium
                return buttonColorType
            }
        val small: ButtonColorType
            get() {
                buttonSize = ButtonSize.Small
                return buttonColorType
            }
    }

    private inner class ButtonColorType {
        val purple: PackyButtonStyle
            get() {
                buttonColor = ButtonColor.Purple
                return this@PackyButtonStyle
            }
        val black: PackyButtonStyle
            get() {
                buttonColor = ButtonColor.Black
                return this@PackyButtonStyle
            }
        val lightPurple: PackyButtonStyle
            get() {
                buttonColor = ButtonColor.LightPurple
                return this@PackyButtonStyle
            }
    }

    enum class ButtonSize(val height: Dp, val radius: Dp) {
        Large(height = 16.dp, radius = 16.dp),
        Medium(height = 12.dp, radius = 8.dp),
        Small(height = 8.dp, radius = 8.dp);
    }

    enum class ButtonColor(
        val default: Color,
        val pressed: Color,
        val disabled: Color,
        val defaultContentColor: Color,
        val pressedContentColor: Color,
        val disabledContentColor: Color
    ) {
        Purple(
            default = lightPackyColor.purple500,
            pressed = lightPackyColor.purple600,
            disabled = lightPackyColor.gray300,
            defaultContentColor = lightPackyColor.white,
            pressedContentColor = lightPackyColor.white,
            disabledContentColor = lightPackyColor.white,
        ),
        Black(
            default = lightPackyColor.gray900,
            pressed = lightPackyColor.gray800,
            disabled = lightPackyColor.gray300,
            defaultContentColor = lightPackyColor.white,
            pressedContentColor = lightPackyColor.white,
            disabledContentColor = lightPackyColor.white,
        ),
        LightPurple(
            default = lightPackyColor.purple100,
            pressed = lightPackyColor.purple200,
            disabled = lightPackyColor.gray300,
            defaultContentColor = lightPackyColor.purple600,
            pressedContentColor = lightPackyColor.purple600,
            disabledContentColor = lightPackyColor.white,
        ),
    }
}
