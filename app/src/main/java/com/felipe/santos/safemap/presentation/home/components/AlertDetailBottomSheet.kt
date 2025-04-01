package com.felipe.santos.safemap.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipe.santos.safemap.presentation.ui.theme.SafeMapTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDetailBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    timestamp: String,
    confirmations: Int,
    hasUserConfirmed: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {},
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Reported on: $timestamp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (hasUserConfirmed) {
                    Button(onClick = {}, enabled = false) {
                        Text("You confirmed this ($confirmations)")
                    }
                } else {
                    Button(onClick = onConfirm) {
                        Text("Confirm I witnessed this ($confirmations)")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlertDetailBottomSheet() {
    SafeMapTheme {
        AlertDetailBottomSheet(
            title = "Verbal harassment",
            description = "Man screaming with a woman in the street",
            timestamp = "2025-04-01 08:16",
            confirmations = 5,
            onConfirm = {},
            onDismiss = {},
            hasUserConfirmed = true,
        )
    }
}
