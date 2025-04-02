package com.felipe.santos.safemap.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.felipe.santos.safemap.presentation.home.components.AlertDetailBottomSheet
import com.felipe.santos.safemap.presentation.home.components.NewAlertBottomSheet
import com.felipe.santos.safemap.presentation.mapper.AlertIconMapper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun SafeMapScreen(
    viewModel: SafeMapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val location by viewModel.location.collectAsState()
    val hasPermission by viewModel.hasLocationPermission.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.updatePermissionStatus(isGranted)
        if (!isGranted) {
            Toast.makeText(context, "Location permission is required", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val isGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            permissionLauncher.launch(permission)
        } else {
            viewModel.updatePermissionStatus(true)
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(location) {
        location?.let {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 14f)
            )
        }
    }

    val focusPosition by viewModel.focusPosition.collectAsState()

    LaunchedEffect(focusPosition) {
        focusPosition?.let {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(it, 15f)
            )
        }
    }

    val alerts by viewModel.alerts.collectAsState()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = hasPermission),
        uiSettings = MapUiSettings(zoomControlsEnabled = true),
        onMapLongClick = { latLng ->
            viewModel.onMapClick(latLng)
        }
    ) {
        alerts.forEach { alert ->
            val icon = remember(alert.type) {
                val drawable = ResourcesCompat.getDrawable(
                    context.resources,
                    AlertIconMapper.getIconResId(alert.type),
                    context.theme
                )
                val bitmap = createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                BitmapDescriptorFactory.fromBitmap(bitmap)
            }

            Marker(
                state = rememberMarkerState(position = LatLng(alert.latitude, alert.longitude)),
                title = alert.type.title,
                icon = icon,
                onClick = {
                    viewModel.selectAlert(alert)
                    true
                }
            )
        }
    }

    val selectedUiAlert = viewModel.selectedUiAlert.collectAsState().value
    val hasConfirmed by viewModel.hasUserConfirmed.collectAsState()

    selectedUiAlert?.let { alert ->
        AlertDetailBottomSheet(
            type = alert.type.title,
            description = alert.description,
            timestamp = alert.timestampFormatted,
            confirmations = alert.confirmations,
            hasUserConfirmed = hasConfirmed,
            onConfirm = { viewModel.witnessConfirmation() },
            onDismiss = { viewModel.clearSelectedAlert() }
        )
    }

    val clickedLocation by viewModel.clickedLocation.collectAsState()

    clickedLocation?.let { latLng ->
        NewAlertBottomSheet(
            latLng = latLng,
            onSubmit = { type, description ->
                viewModel.submitNewAlert(type, description, latLng)
            },
            onDismiss = { viewModel.clearClickedLocation() }
        )
    }


}