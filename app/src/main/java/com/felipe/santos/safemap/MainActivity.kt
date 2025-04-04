package com.felipe.santos.safemap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.felipe.santos.safemap.presentation.navigation.AppNavHost
import com.felipe.santos.safemap.presentation.navigation.bottom.BottomNavBar
import com.felipe.santos.safemap.presentation.ui.theme.SafeMapTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        setContent {
            SafeMapTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {  BottomNavBar(navController = navController)}
                ) { paddingValues ->

                    AppNavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController
                    )



                }
            }
        }
    }
}