package com.felipe.santos.safemap.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.felipe.santos.safemap.domain.model.AlertType
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAlertBottomSheet(
    latLng: LatLng,
    onSubmit: (AlertType, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedType by remember { mutableStateOf(AlertType.VERBAL) }
    var description by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Report Incident", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))

            DropdownMenuAlertType(
                selected = selectedType,
                onSelected = { selectedType = it }
            )

            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                onSubmit(selectedType, description)
            }) {
                Text("Submit Report")
            }
        }
    }
}