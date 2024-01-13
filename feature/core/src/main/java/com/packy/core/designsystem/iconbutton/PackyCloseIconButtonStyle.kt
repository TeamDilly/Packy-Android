package com.packy.core.designsystem.iconbutton


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.packy.core.theme.lightPackyColor
import javax.annotation.concurrent.Immutable

val closeIconButtonStyle get() = PackyCloseIconButtonStype().buttonSizeType

@Immutable
class PackyCloseIconButtonStype internal constructor() {

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
    }

    inner class ButtonColorType {
        val white: PackyCloseIconButtonStype
            get() {
                buttonColor = ButtonColor.White
                return this@PackyCloseIconButtonStype
            }
        val black: PackyCloseIconButtonStype
            get() {
                buttonColor = ButtonColor.Black
                return this@PackyCloseIconButtonStype
            }
    }

    enum class ButtonSize(
        val size: Dp,
        val iconSize: Dp
    ) {
        Large(
            40.dp,
            24.dp
        ),
        Medium(
            24.dp,
            16.dp
        )
    }

    enum class ButtonColor(
        val default: Color,
        val pressed: Color,
        val disabled: Color,
        val defaultContentColor: Color,
        val pressedContentColor: Color,
        val disabledContentColor: Color
    ) {
        White(
            default = lightPackyColor.white,
            pressed = lightPackyColor.gray100,
            disabled = lightPackyColor.white,
            defaultContentColor = lightPackyColor.gray900,
            pressedContentColor = lightPackyColor.gray900,
            disabledContentColor = lightPackyColor.gray300,
        ),
        Black(
            default = lightPackyColor.gray900,
            pressed = lightPackyColor.gray800,
            disabled = lightPackyColor.gray300,
            defaultContentColor = lightPackyColor.white,
            pressedContentColor = lightPackyColor.white,
            disabledContentColor = lightPackyColor.white,
        ),
    }
}