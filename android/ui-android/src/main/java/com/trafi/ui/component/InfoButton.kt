package com.trafi.ui.component

import androidx.annotation.DrawableRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trafi.ui.R
import com.trafi.ui.component.internal.InfoButtonConstants
import com.trafi.ui.theme.MaasCornerRadius
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.currentTheme
import com.trafi.ui.theme.darkColors
import com.trafi.ui.theme.isRound

private val constants
    @Composable get() = InfoButtonConstants(currentTheme)

@Composable
public fun InfoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int = R.drawable.ic_help_info_s,
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = constants.defaultContentColor,
    disabledContentColor: Color = constants.disabledContentColor,
    textStyle: TextStyle = constants.textStyle,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        shape = if (constants.cornerRadius.isRound) {
            RoundedCornerShape(percent = 50)
        } else {
            RoundedCornerShape(constants.cornerRadius)
        },
        colors = androidx.compose.material.ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
        ),
        contentPadding = with(constants) {
            PaddingValues(
                start = paddingHorizontal,
                top = paddingVertical,
                end = paddingHorizontal,
                bottom = paddingVertical,
            )
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            modifier = modifier
                .padding(end = constants.spaceBetween)
                .size(width = constants.iconWidth, height = constants.iconHeight),
        )
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = constants.maxLinesCount,
            style = textStyle,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff, widthDp = 150)
@Composable
private fun InfoButtonPreview() {
    MaasTheme {
        InfoButton(
            text = "Info view view view",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun InfoButtonStyledCornerRadiusPreview() {
    MaasTheme(cornerRadius = MaasCornerRadius(buttonRadius = 0.dp)) {
        InfoButton(
            text = "Info",
            onClick = {}
        )
    }
}

@Preview
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
public fun InfoButtonDisabledPreview() {
    MaasTheme {
        InfoButton(
            text = "Info",
            onClick = {},
            enabled = false
        )
    }
}

@Preview
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
public fun InfoButtonStyledDarkPreview() {
    MaasTheme(colors = MaasTheme.darkColors()) {
        InfoButton(
            text = "How it works",
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
private fun InfoButtonStyledDarkDisabledPreview() {
    MaasTheme(colors = MaasTheme.darkColors()) {
        InfoButton(
            text = "How it works",
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(widthDp = 150)
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
public fun InfoButtonLongTextPreview() {
    MaasTheme {
        InfoButton(
            text = "Info view view view",
            onClick = {}
        )
    }
}

@Preview
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
public fun InfoButtonThemedPreview() {
    MaasTheme {
        InfoButton(
            text = "Info",
            onClick = {},
            contentColor = Color.Magenta
        )
    }
}
