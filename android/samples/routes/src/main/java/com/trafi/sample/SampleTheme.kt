package com.trafi.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.trafi.ui.theme.MaasSpacing
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.Spacing
import com.trafi.ui.theme.darkColors
import com.trafi.ui.theme.lightColors

@Composable
fun SampleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    narrowScreen: Boolean = isScreenWidthNarrow(),
    content: @Composable () -> Unit
) {
    MaasTheme(
        colors = if (darkTheme) MaasTheme.darkColors() else MaasTheme.lightColors(),
        spacing = MaasSpacing(globalMargin = if (narrowScreen) Spacing.md else Spacing.xl),
        content = content
    )
}

@Composable
fun isScreenWidthNarrow(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp <= 280
}
