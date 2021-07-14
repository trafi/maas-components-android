package com.trafi.ui.theme.internal

import com.trafi.ui.theme.internal.type.BasicFontWeight
import com.trafi.ui.theme.internal.type.Color
import com.trafi.ui.theme.internal.type.Shadow
import com.trafi.ui.theme.internal.type.ShadowStyle
import com.trafi.ui.theme.internal.type.alpha
import com.trafi.ui.theme.internal.type.dark
import com.trafi.ui.theme.internal.type.dp

object ColorPalette {
    val White = Color(0xffffffff)
    val Black = Color(0xff000000)
    val Warning = Color(0xffffbe15)
    val Danger = Color(0xfff22e46)
    val Success = Color(0xff00afaf)

    object Default {
        val Primary = Color(0xffff1499)
        val PrimaryVariant = Color(0xffd20077)
        val Secondary = Color(0xff73008b)
        val SecondaryVariant = Color(0xff59006c)
        val Background = White dark Black
        val Surface = White dark Black
        val Error = Danger
        val OnPrimary = White
        val OnSecondary = Black
        val OnBackground = Color(0xff212121) dark White
        val OnSurface = Color(0xff212121) dark White
        val OnError = White
        val GrayScale = grayScale {
            gray100 = Color(0xfff5f5f5)
            gray200 = Color(0xffeeeeee)
            gray300 = Color(0xffe0e0e0)
            gray400 = Color(0xffbdbdbd)
            gray500 = Color(0xff9e9e9e)
            gray600 = Color(0xff757575)
            gray700 = Color(0xff616161)
            gray800 = Color(0xff424242)
            gray900 = Color(0xff212121)
        } dark grayScale {
            gray100 = Color(0xff212121)
            gray200 = Color(0xff424242)
            gray300 = Color(0xff616161)
            gray400 = Color(0xff757575)
            gray500 = Color(0xff9e9e9e)
            gray600 = Color(0xffbdbdbd)
            gray700 = Color(0xffe0e0e0)
            gray800 = Color(0xffeeeeee)
            gray900 = Color(0xfff5f5f5)
        }
    }
}

object SpacingScale {
    val xxxs = 2.dp
    val xxs = 4.dp
    val xs = 8.dp
    val sm = 12.dp
    val md = 16.dp
    val lg = 20.dp
    val xl = 24.dp
    val xxl = 32.dp
    val xxxl = 48.dp

    object Default {
        val GlobalMargin = xl
    }
}

object CornerRadiusScale {
    val none = 0.dp
    val xxs = 4.dp
    val xs = 6.dp
    val sm = 8.dp
    val lg = 12.dp
    val xl = 20.dp
    val round = maxValue.dp

    object Default {
        val ButtonRadius = round
    }
}

object TypographyScale {
    val headingXXL = textStyle {
        fontWeight = BasicFontWeight.Bold
        fontSize = 40
        lineHeight = 50
    }
    val headingXL = textStyle {
        fontWeight = BasicFontWeight.Bold
        fontSize = 32
        lineHeight = 40
    }
    val headingL = textStyle {
        fontWeight = BasicFontWeight.Bold
        fontSize = 28
        lineHeight = 36
    }
    val headingM = textStyle {
        fontWeight = BasicFontWeight.Bold
        fontSize = 18
        lineHeight = 22
    }
    val textL = textStyle {
        fontWeight = BasicFontWeight.Normal
        fontSize = 16
        lineHeight = 24
    }
    val textM = textStyle {
        fontWeight = BasicFontWeight.Normal
        fontSize = 14
        lineHeight = 20
    }
    val textS = textStyle {
        fontWeight = BasicFontWeight.Normal
        fontSize = 12
        lineHeight = 16
    }
    val label = textStyle {
        fontWeight = BasicFontWeight.Bold
        fontSize = 14
        lineHeight = 18
    }
}

object ShadowPalette {

    val ambientLight = ShadowStyle(
        listOf(
            Shadow(color = ColorPalette.Black.alpha(0.14f), radius = 8.dp, x = 0.dp, y = 0.dp),
            Shadow(color = ColorPalette.Black.alpha(0.02f), radius = 8.dp, x = 0.dp, y = 2.dp),
        )
    )

    val ambientMedium = ShadowStyle(
        listOf(
            Shadow(color = ColorPalette.Black.alpha(0.12f), radius = 12.dp, x = 0.dp, y = 0.dp),
            Shadow(color = ColorPalette.Black.alpha(0.04f), radius = 12.dp, x = 0.dp, y = 4.dp),
        )
    )

    val ambientLarge = ShadowStyle(
        listOf(
            Shadow(color = ColorPalette.Black.alpha(0.12f), radius = 16.dp, x = 0.dp, y = 0.dp),
            Shadow(color = ColorPalette.Black.alpha(0.04f), radius = 16.dp, x = 0.dp, y = 6.dp),
        )
    )
}
