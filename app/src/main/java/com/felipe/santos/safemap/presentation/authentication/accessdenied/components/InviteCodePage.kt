package com.felipe.santos.safemap.presentation.authentication.accessdenied.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipe.santos.safemap.R
import com.felipe.santos.safemap.presentation.ui.theme.SafeMapTheme

@Composable
fun InviteCodePage(
    code: String,
    onCodeChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource( id = R.drawable.onboarding_step_5),
            contentDescription = "",
            modifier = Modifier.size(240.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.size(50.dp))
        Text(
            text = "Enter your invite code",
            style = typography.headlineMedium,
            color = colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            label = { Text("Invite code", color = colorScheme.onSurfaceVariant) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground.copy(alpha = 0.8f),
                disabledTextColor = colorScheme.onSurface.copy(alpha = 0.4f),
                errorTextColor = colorScheme.error,

                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
                errorContainerColor = colorScheme.surface,

                cursorColor = colorScheme.primary,
                errorCursorColor = colorScheme.error,

                focusedIndicatorColor = colorScheme.primary,
                unfocusedIndicatorColor = colorScheme.outline,
                disabledIndicatorColor = colorScheme.outlineVariant,
                errorIndicatorColor = colorScheme.error,

                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant,
                disabledLabelColor = colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                errorLabelColor = colorScheme.error,

                focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                disabledPlaceholderColor = colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                errorPlaceholderColor = colorScheme.error
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSubmit,
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray,
            )
        ) {
            Text("Verify")
        }
    }
}