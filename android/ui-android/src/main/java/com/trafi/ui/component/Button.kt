package com.trafi.ui.component

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trafi.ui.R
import com.trafi.ui.component.internal.ButtonConstants
import com.trafi.ui.theme.MaasCornerRadius
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.currentTheme
import com.trafi.ui.theme.isRound
import com.trafi.ui.theme.lightColors

private val constants
    @Composable get() = ButtonConstants(currentTheme)

@OptIn(ExperimentalAnimationApi::class)
@Composable
public fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = Color.Unspecified,
    elevation: Dp? = null,
    small: Boolean = false,
    wrapped: Boolean = false,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    androidx.compose.material.Button(
        onClick = onClick,
        enabled = enabled,
        shape = if (constants.cornerRadius.isRound) {
            RoundedCornerShape(percent = 50)
        } else {
            RoundedCornerShape(constants.cornerRadius)
        },
        colors = androidx.compose.material.ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor.takeOrElse { constants.defaultBackgroundColor },
            disabledBackgroundColor = backgroundColor.takeOrElse { constants.disabledBackgroundColor },
            contentColor = contentColor.takeOrElse { constants.defaultContentColor },
            disabledContentColor = contentColor.takeOrElse { constants.disabledContentColor },
        ),
        elevation = androidx.compose.material.ButtonDefaults.elevation(
            defaultElevation = elevation ?: 2.dp,
            pressedElevation = elevation ?: 8.dp,
        ),
        contentPadding = PaddingValues(
            start = constants.paddingHorizontal,
            top = if (small) constants.paddingVerticalSmall else constants.paddingVertical,
            end = constants.paddingHorizontal,
            bottom = if (small) constants.paddingVerticalSmall else constants.paddingVertical,
        ),
        modifier = modifier.defaultMinSize(minHeight = if (small) constants.heightSmall else constants.height)
            .run { if (wrapped) this else fillMaxWidth() }
    ) {
        @Suppress("NAME_SHADOWING")
        val contentColor = contentColor.takeOrElse { constants.contentColor(enabled) }
        val iconModifier = Modifier
            .padding(end = constants.spaceBetween)
            .size(if (small) constants.iconSizeSmall else constants.iconSize)
            .align(Alignment.CenterVertically)
        AnimatedVisibility(loading || icon != null) {
            if (loading) {
                CircularProgressIndicator(
                    color = contentColor,
                    strokeWidth = 1.5.dp,
                    modifier = iconModifier,
                )
            } else icon?.let {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = iconModifier,
                )
            }
        }
        Text(
            text,
            color = contentColor,
            style = if (small) constants.textStyleSmall else constants.textStyle,
        )
    }
}

@Composable
private fun ButtonConstants.contentColor(enabled: Boolean): Color =
    if (enabled) defaultContentColor else disabledContentColor

@Composable
public fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    wrapped: Boolean = false,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        backgroundColor = MaasTheme.colors.grayScale.gray100,
        contentColor = if (enabled) {
            MaasTheme.colors.grayScale.gray900
        } else {
            MaasTheme.colors.grayScale.gray400
        },
        elevation = 0.dp,
        wrapped = wrapped,
        loading = loading,
        enabled = enabled,
    )
}

@Composable
public fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color = Color.Unspecified,
    icon: Painter? = null,
    wrapped: Boolean = false,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    Button(
        text = text,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        backgroundColor = Color.Transparent,
        contentColor = contentColor.takeOrElse {
            if (enabled) {
                MaasTheme.colors.grayScale.gray900
            } else {
                MaasTheme.colors.grayScale.gray400
            }
        },
        elevation = 0.dp,
        wrapped = wrapped,
        loading = loading,
        enabled = enabled,
    )
}

@Preview
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
public fun ButtonPreview() {
    MaasTheme {
        Button("Unlock now", onClick = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun DisabledButtonPreview() {
    MaasTheme {
        Button("Unlock now", onClick = {}, enabled = false)
    }
}

@Preview
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
public fun ButtonStyledCornerRadiusPreview() {
    MaasTheme(cornerRadius = MaasCornerRadius(buttonRadius = 0.dp)) {
        Button("Unlock now", onClick = {})
    }
}

@Preview
@Composable
private fun ButtonStyledColorPreview() {
    val jelbiColors = MaasTheme.lightColors(
        primary = Color(0xff6ca130),
        primaryVariant = Color(0xfff0d722)
    )
    MaasTheme(colors = jelbiColors) {
        Button("Unlock now", onClick = {})
    }
}

@Preview
@Composable
private fun ButtonWrappedPreview() {
    MaasTheme {
        Button("Unlock now", onClick = {}, wrapped = true)
    }
}

@Preview
@Composable
private fun ButtonWrappedIconPreview() {
    MaasTheme {
        Button(
            text = "Unlock now",
            onClick = {},
            icon = painterResource(R.drawable.ic_help_info_s),
            wrapped = true,
        )
    }
}

@Preview
@Composable
private fun ButtonWrappedIconSmallPreview() {
    MaasTheme {
        Button(
            text = "Unlock now",
            onClick = {},
            icon = painterResource(R.drawable.ic_help_info_s),
            wrapped = true,
            small = true,
        )
    }
}

@Preview
@Composable
private fun ButtonDynamicPreview() {
    MaasTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaasTheme.spacing.globalMargin),
            modifier = Modifier
                .fillMaxSize()
                .background(MaasTheme.colors.background)
                .padding(MaasTheme.spacing.globalMargin)
        ) {
            var small by remember { mutableStateOf(false) }
            var wrapped by remember { mutableStateOf(false) }
            var loading by remember { mutableStateOf(false) }
            var enabled by remember { mutableStateOf(true) }
            var icon: Painter? by remember { mutableStateOf(null) }

            val defaultIcon = painterResource(R.drawable.ic_help_info_s)
            val toggleIcon = painterResource(R.drawable.ic_route_search_switch_20)

            Row {
                TertiaryButton(
                    text = "small",
                    onClick = { small = !small },
                    wrapped = true,
                )
                TertiaryButton(
                    text = "wrapped",
                    onClick = { wrapped = !wrapped },
                    wrapped = true,
                )
                TertiaryButton(
                    text = "loading",
                    onClick = { loading = !loading },
                    wrapped = true,
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(MaasTheme.spacing.globalMargin)) {
                SecondaryButton(
                    text = "enabled",
                    onClick = { enabled = !enabled },
                    wrapped = true,
                )
                SecondaryButton(
                    text = "icon",
                    onClick = { icon = if (icon == null) defaultIcon else null },
                    icon = if (icon != null) toggleIcon else null,
                    wrapped = true,
                )
            }

            Button(
                text = if (loading) "Buttoning.." else "Button",
                onClick = {},
                icon = icon,
                small = small,
                wrapped = wrapped,
                loading = loading,
                enabled = enabled,
            )
        }
    }
}
