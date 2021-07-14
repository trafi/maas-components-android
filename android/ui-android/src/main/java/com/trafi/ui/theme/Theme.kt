package com.trafi.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
public fun MaasTheme(
    colors: MaasColorPalette = MaasTheme.colors,
    typography: MaasTypography = MaasTheme.typography,
    spacing: MaasSpacing = MaasTheme.spacing,
    cornerRadius: MaasCornerRadius = MaasTheme.cornerRadius,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }.apply { updateColorsFrom(colors) }

        MaterialTheme(
            colors = colors.materialColors,
            typography = typography.materialTypography,
            shapes = cornerRadius.materialShapes,
            content = content
        )
}

public object MaasTheme {
    public val colors: MaasColorPalette
        @Composable get() = AmbientColor

    public val typography: MaasTypography
        @Composable get() = AmbientTypography

    public val spacing: MaasSpacing
        @Composable get() = AmbientSpacing

    public val cornerRadius: MaasCornerRadius
        @Composable get() = AmbientCornerRadius
}

private val AmbientColor = MaasTheme.lightColors()

private val AmbientTypography = MaasTypography()

private val AmbientSpacing = MaasSpacing()

private val AmbientCornerRadius = MaasCornerRadius()
