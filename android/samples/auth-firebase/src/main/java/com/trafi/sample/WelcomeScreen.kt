package com.trafi.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.trafi.ui.component.Button
import com.trafi.ui.component.SecondaryButton
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.Spacing

@Composable
fun WelcomeScreen(
    inProgress: Boolean,
    onLaterClick: () -> Unit,
    onContinueWithGoogleClick: () -> Unit,
) {
    val asset = ImageVector.vectorResource(id = R.drawable.waving_hand)
    ConstraintLayout(
        modifier = Modifier
            .background(MaasTheme.colors.background)
            .fillMaxSize()
    ) {

        val (later, image, text, buttons) = createRefs()
        TextButton(
            onClick = onLaterClick,
            modifier = Modifier
                .padding(Spacing.md)
                .constrainAs(later) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
        ) {
            Text(
                "Later",
                color = MaasTheme.colors.onBackground,
                style = MaasTheme.typography.textL.copy(fontWeight = FontWeight.Bold),
            )
        }

        val childModifier = Modifier.padding(horizontal = MaasTheme.spacing.globalMargin)

        createVerticalChain(image, text, buttons)

        Image(
            asset,
            contentDescription = null,
            modifier = childModifier.constrainAs(image) {
                centerHorizontallyTo(parent)
            },
        )
        Text(
            text = "Log in or sign up",
            textAlign = TextAlign.Center,
            color = MaasTheme.colors.onBackground,
            style = MaasTheme.typography.headingXL,
            modifier = childModifier.constrainAs(text) {
                centerHorizontallyTo(parent)
            },
        )

        Column(
            modifier = childModifier.constrainAs(buttons) {
                centerHorizontallyTo(parent)
            },
            verticalArrangement = Arrangement.spacedBy(Spacing.lg),
        ) {
            Button(
                text = "Continue with Google",
                onClick = onContinueWithGoogleClick,
                loading = inProgress,
                enabled = !inProgress,
            )
            SecondaryButton(
                text = "Other ways to sign up",
                enabled = false,
                onClick = { },
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen(
        inProgress = false,
        onLaterClick = {},
        onContinueWithGoogleClick = {},
    )
}
