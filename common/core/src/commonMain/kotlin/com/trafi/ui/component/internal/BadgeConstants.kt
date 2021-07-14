package com.trafi.ui.component.internal

import com.trafi.ui.theme.internal.CornerRadiusScale
import com.trafi.ui.theme.internal.CurrentTheme
import com.trafi.ui.theme.internal.SpacingScale
import com.trafi.ui.theme.internal.type.BasicFontWeight.Bold
import com.trafi.ui.theme.internal.type.copy
import com.trafi.ui.theme.internal.type.dp

class BadgeConstants(theme: CurrentTheme) {
    val defaultContentColor = theme.colorPalette.onPrimary
    val disabledColor = theme.colorPalette.grayScale.gray300
    val borderColor = theme.colorPalette.background

    val textStyleMedium = theme.typographyScale.textM.copy(fontWeight = Bold)
    val textStyleSmall = theme.typographyScale.textS.copy(fontWeight = Bold)

    val minHeightMedium = 24.dp
    val minHeightSmall = 16.dp

    val cornerRadiusMedium = CornerRadiusScale.xs
    val cornerRadiusSmall = CornerRadiusScale.xxs

    val iconHeight = 20.dp
    val iconWidth = 20.dp

    val subBadgeIconHeight = 16.dp
    val subBadgeIconWidth = 16.dp

    val spacer = SpacingScale.xxs

    val horizontalPaddingMedium = SpacingScale.xs
    val horizontalPaddingSmall = SpacingScale.xxs

    val verticalPaddingMedium = SpacingScale.xxxs
    val verticalPaddingSmall = 0.dp

    val maxStackedBadgesNumber = 2

    val borderWidth = 1.5.dp
}
