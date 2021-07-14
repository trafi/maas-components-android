package com.trafi.ui.component.internal

import com.trafi.ui.theme.internal.CurrentTheme
import com.trafi.ui.theme.internal.SpacingScale
import com.trafi.ui.theme.internal.type.BasicFontWeight.Bold
import com.trafi.ui.theme.internal.type.BasicFontWeight.Normal
import com.trafi.ui.theme.internal.type.alpha
import com.trafi.ui.theme.internal.type.copy
import com.trafi.ui.theme.internal.type.dp

class RouteSearchTabConstants(theme: CurrentTheme) {
    val contentColorPrimary = theme.colorPalette.onSurface
    val contentColorSecondary = theme.colorPalette.grayScale.gray600
    val defaultBackgroundColor = theme.colorPalette.grayScale.gray100
    val activeBackgroundColor = theme.colorPalette.primary.alpha(0.1f)

    val titleStyle = theme.typographyScale.textM.copy(fontWeight = Bold)
    val subtitleStyle = theme.typographyScale.textS.copy(fontWeight = Normal)

    val padding = SpacingScale.xxs

    val spacerBelowIcon = SpacingScale.xxs
    val spacerBelowTitle = SpacingScale.xxxs

    val minTabWidth = 64.dp
    val minTabHeight = 74.dp

    val borderRadius = SpacingScale.xs
    val iconWidth = 24.dp
    val iconHeight = 24.dp

    val borderColorActive = theme.colorPalette.primary
    val borderWidth = 1.5.dp
}
