package com.felipe.santos.safemap.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.felipe.santos.safemap.domain.model.AlertType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuAlertType(
    selected: AlertType,
    onSelected: (AlertType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selected.title,
            onValueChange = {},
            readOnly = true,
            label = { Text("Type") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            AlertType.entries.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.title) },
                    onClick = {
                        onSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}