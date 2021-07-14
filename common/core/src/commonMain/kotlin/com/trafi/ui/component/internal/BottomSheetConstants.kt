package com.trafi.ui.component.internal

import com.trafi.ui.theme.internal.ColorPalette
import com.trafi.ui.theme.internal.CornerRadiusScale
import com.trafi.ui.theme.internal.CurrentTheme
import com.trafi.ui.theme.internal.ShadowPalette
import com.trafi.ui.theme.internal.SpacingScale
import com.trafi.ui.theme.internal.type.BasicFontWeight
import com.trafi.ui.theme.internal.type.Dp
import com.trafi.ui.theme.internal.type.alpha
import com.trafi.ui.theme.internal.type.copy
import com.trafi.ui.theme.internal.type.dp

class BottomSheetConstants(theme: CurrentTheme) {

    val knobColor = theme.colorPalette.grayScale.gray200

    val backgroundColor = ColorPalette.Black.alpha(0.60f)

    val sheetCornerRadius = CornerRadiusScale.xl
    val sheetContentVerticalPadding = sharedContentVerticalSpacing
    val sheetContentHorizontalPadding = SpacingScale.xl
    val sheetBackgroundColor = theme.colorPalette.background

    val sheetShadowStyle = ShadowPalette.ambientLarge
}

class AlertBottomSheetConstants(theme: CurrentTheme) {

    val titleTextStyle = theme.typographyScale.headingL
    val subtitleTextStyle = theme.typographyScale.textL.copy(fontWeight = BasicFontWeight.Normal)
    val imageMaxWidth = 375.dp
    val contentVerticalSpacing = sharedContentVerticalSpacing
}

private val sharedContentVerticalSpacing: Dp = SpacingScale.lg
