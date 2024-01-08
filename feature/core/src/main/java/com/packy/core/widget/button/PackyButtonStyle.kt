package com.packy.core.widget.button

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.packy.core.theme.defaultPackyTypography
import com.packy.core.theme.lightPackyColor
import javax.annotation.concurrent.Immutable

val buttonStyle get() = PackyButtonStyle().buttonSizeType

@Immutable
class PackyButtonStyle internal constructor() {

    lateinit var buttonSize: ButtonSize
        private set
    lateinit var buttonColor: ButtonColor
        private set

    val buttonSizeType = ButtonSizeType()
    val buttonColorType = ButtonColorType()

    inner class ButtonSizeType {
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

    inner class ButtonColorType {
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

    enum class ButtonSize(
        val height: Dp,
        val radius: Dp,
        val iconSize: Dp,
        val contentPadding: Dp,
        val textStyle: TextStyle
    ) {
        Large(
            height = 58.dp,
            radius = 16.dp,
            iconSize = 20.dp,
            contentPadding= 12.dp,
            textStyle = defaultPackyTypography.body02
        ),
        Medium(
            height = 46.dp,
            radius = 8.dp,
            iconSize = 16.dp,
            contentPadding= 8.dp,
            textStyle = defaultPackyTypography.body04
        ),
        Small(
            height = 46.dp,
            radius = 8.dp,
            iconSize = 10.dp,
            contentPadding= 4.dp,
            textStyle = defaultPackyTypography.body06
        ),
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
