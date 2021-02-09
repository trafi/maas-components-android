package com.trafi.sample

import androidx.compose.foundation.Interaction
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.trafi.core.model.Profile
import com.trafi.ui.component.Button
import com.trafi.ui.theme.MaasTheme
import com.trafi.ui.theme.Spacing
import com.trafi.ui.theme.internal.CornerRadiusScale

@Composable
fun ProfileScreen(
    profile: Profile?,
    onUpdateProfileClick: (Profile) -> Unit,
    onLogOutClick: () -> Unit,
    onCorruptTokenClick: () -> Unit,
) {
    var name by remember(profile) { mutableStateOf(profile?.firstName ?: "John") }
    var surname by remember(profile) { mutableStateOf(profile?.lastName ?: "Smith") }

    Column(
        modifier = Modifier
            .background(MaasTheme.colors.background)
            .fillMaxSize()
            .padding(
                horizontal = MaasTheme.spacing.globalMargin,
                vertical = Spacing.xl,
            ),
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        Text(
            "My account",
            color = MaasTheme.colors.onBackground,
            style = MaasTheme.typography.headingXL,
        )

        Input(
            text = name,
            onTextChange = { name = it },
            label = "Name",
            modifier = Modifier.fillMaxWidth(),
        )
        Input(
            text = surname,
            onTextChange = { surname = it },
            label = "Surname",
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.weight(1f))

        TertiaryButton(text = "Update", onClick = {
            onUpdateProfileClick(Profile(
                gender = profile?.gender ?: Profile.Gender.NOT_SPECIFIED,
                ext = profile?.ext.orEmpty(),
                firstName = name,
                lastName = surname,
                displayName = profile?.displayName,
                email = profile?.email,
                address = profile?.address,
                birthDate = profile?.birthDate,
            ))
        })
        TertiaryButton(text = "Log out", onClick = onLogOutClick)
        TertiaryButton(text = "Corrupt token", onClick = onCorruptTokenClick)
    }
}

@Composable
private fun Input(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    val interactionState = remember { InteractionState() }
    val isFocused = interactionState.contains(Interaction.Focused)
    TextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(label) },
        singleLine = true,
        interactionState = interactionState,
        trailingIcon = if (text.isNotEmpty() && isFocused) {
            {
                Icon(
                    vectorResource(id = R.drawable.input_fields_remove),
                    contentDescription = "Clear",
                    modifier = Modifier.clickable { onTextChange("") }
                )
            }
        } else null,
        backgroundColor = if (isFocused) {
            MaasTheme.colors.grayScale.gray300
        } else {
            MaasTheme.colors.grayScale.gray100
        },
        shape = MaterialTheme.shapes.small.copy(
            topLeft = CornerSize(CornerRadiusScale.lg),
            topRight = CornerSize(CornerRadiusScale.lg),
            bottomLeft = ZeroCornerSize,
            bottomRight = ZeroCornerSize,
        ),
        modifier = modifier,
    )
}

@Composable
private fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        text = text,
        onClick = onClick,
        color = MaasTheme.colors.background,
        contentColor = MaasTheme.colors.onBackground,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        profile = null,
        onUpdateProfileClick = {},
        onLogOutClick = {},
        onCorruptTokenClick = {},
    )
}