package com.trafi.ui.theme.internal

import com.trafi.ui.theme.GrayScale
import com.trafi.ui.theme.internal.CurrentAccessibilityFontScale.ExtraLarge
import com.trafi.ui.theme.internal.CurrentAccessibilityFontScale.Large
import com.trafi.ui.theme.internal.CurrentAccessibilityFontScale.Normal
import com.trafi.ui.theme.internal.type.Color
import com.trafi.ui.theme.internal.type.Dp
import com.trafi.ui.theme.internal.type.TextStyle

/**
 * A snapshot of the theme in a specific context.
 */
class CurrentTheme(
    internal val colorPalette: CurrentColorPalette,
    internal val typographyScale: CurrentTypographyScale,
    internal val spacingScale: CurrentSpacingScale,
    internal val cornerRadiusScale: CurrentCornerRadiusScale,
    internal val accessibility: CurrentAccessibility,
)

class CurrentColorPalette(
    internal val primary: Color,
    internal val primaryVariant: Color,
    internal val secondary: Color,
    internal val secondaryVariant: Color,
    internal val background: Color,
    internal val surface: Color,
    internal val error: Color,
    internal val onPrimary: Color,
    internal val onSecondary: Color,
    internal val onBackground: Color,
    internal val onSurface: Color,
    internal val onError: Color,
    internal val grayScale: GrayScale,
)

class CurrentTypographyScale(
    internal val headingXXL: TextStyle,
    internal val headingXL: TextStyle,
    internal val headingL: TextStyle,
    internal val headingM: TextStyle,
    internal val textL: TextStyle,
    internal val textM: TextStyle,
    internal val textS: TextStyle,
    internal val label: TextStyle,
)

class CurrentSpacingScale(
    internal val globalMargin: Dp,
)

class CurrentCornerRadiusScale(
    internal val buttonRadius: Dp,
)

class CurrentAccessibility(
    fontScale: Float,
) {
    internal val fontScale: CurrentAccessibilityFontScale = when {
        fontScale >= 1.5f -> ExtraLarge
        fontScale >= 1.15f -> Large
        else -> Normal
    }
}

enum class CurrentAccessibilityFontScale {
    Normal,
    Large,
    ExtraLarge,
}
