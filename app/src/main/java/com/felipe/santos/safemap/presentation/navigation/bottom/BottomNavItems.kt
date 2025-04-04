package com.felipe.santos.safemap.presentation.navigation.bottom


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.felipe.santos.safemap.presentation.navigation.Alerts
import com.felipe.santos.safemap.presentation.navigation.SafeMap
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable



@Serializable
sealed class BottomNavItems<T>(val label: String, val icon: @Contextual ImageVector, val route: T) {
    @Serializable
    data object Home : BottomNavItems<SafeMap>(
        label = HOME_LABEL,
        icon = Icons.Filled.Home,
        route = SafeMap
    )

    @Serializable
    data object Favorites : BottomNavItems<Alerts>(
        label = ALERT_LABEL,
        icon = Icons.Filled.Notifications,
        route = Alerts
    )

    companion object {
        private const val HOME_LABEL = "Home"
        private const val ALERT_LABEL = "Alerts"
    }

}
