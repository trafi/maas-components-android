@file:Suppress("unused")

package com.trafi.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable val Grey100 = Color(0xfff5f5f5)
@Stable val Grey200 = Color(0xffeeeeee)
@Stable val Grey300 = Color(0xffe0e0e0)
@Stable val Grey400 = Color(0xffbdbdbd)
@Stable val Grey500 = Color(0xff9e9e9e)
@Stable val Grey600 = Color(0xff757575)
@Stable val Grey700 = Color(0xff616161)
@Stable val Grey800 = Color(0xff424242)
@Stable val Grey900 = Color(0xff212121)

fun MaasTheme.lightColors(
    primary: Color = Color(0xffff1499),
    primaryVariant: Color = Color(0xffd20077),
    secondary: Color = Color(0xff73008b),
    secondaryVariant: Color = Color(0xff59006c),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xfff22e46),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Grey900,
    onSurface: Color = Grey900,
    onError: Color = Color.White
): MaasColorPalette = MaasColorPalette(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError,
    isLight = true
)

fun MaasTheme.darkColors(
    primary: Color = Color(0xffff1499),
    primaryVariant: Color = Color(0xffd20077),
    secondary: Color = Color(0xff73008b),
    secondaryVariant: Color = Color(0xff59006c),
    background: Color = Color.Black,
    surface: Color = Color.Black,
    error: Color = Color(0xfff22e46),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.White,
    onSurface: Color = Color.White,
    onError: Color = Color.White
): MaasColorPalette = MaasColorPalette(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError,
    isLight = false
)

object CornerRadius {
    @Stable val none: Dp = 0.dp
    @Stable val xxs: Dp = 4.dp
    @Stable val xs: Dp = 6.dp
    @Stable val sm: Dp = 8.dp
    @Stable val lg: Dp = 12.dp
    @Stable val xl: Dp = 20.dp
    @Stable val round: Dp = Dp.Infinity
}

@Immutable
data class MaasCornerRadius(
    val buttonRadius: Dp = CornerRadius.round
)