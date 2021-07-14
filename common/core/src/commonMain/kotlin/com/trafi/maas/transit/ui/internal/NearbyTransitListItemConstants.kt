package com.trafi.maas.transit.ui.internal

import com.trafi.ui.theme.internal.CurrentAccessibilityFontScale.ExtraLarge
import com.trafi.ui.theme.internal.CurrentAccessibilityFontScale.Large
import com.trafi.ui.theme.internal.CurrentAccessibilityFontScale.Normal
import com.trafi.ui.theme.internal.CurrentTheme
import com.trafi.ui.theme.internal.SpacingScale

class NearbyTransitListItemConstants(theme: CurrentTheme) {
    val titleTextStyle = theme.typographyScale.textL
    val titleTextLineLimit = when (theme.accessibility.fontScale) {
        Normal -> 1
        Large, ExtraLarge -> 2
    }

    val subtitleTextStyle = theme.typographyScale.textM
    val subtitleTextColor = theme.colorPalette.grayScale.gray600
    val subtitleTextLineLimit = when (theme.accessibility.fontScale) {
        Normal -> 1
        Large, ExtraLarge -> 2
    }

    val verticalContentSpacing = SpacingScale.xs
    val verticalTextSpacing = SpacingScale.xxs
}
