package com.felipe.santos.safemap.presentation.authentication.accessdenied

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felipe.santos.safemap.presentation.authentication.accessdenied.components.InviteCodePage
import com.felipe.santos.safemap.presentation.authentication.accessdenied.components.OnboardingPage1
import com.felipe.santos.safemap.presentation.authentication.accessdenied.components.OnboardingPage2
import com.felipe.santos.safemap.presentation.authentication.accessdenied.components.OnboardingPage3
import com.felipe.santos.safemap.presentation.authentication.accessdenied.components.PagerIndicator


@Composable
fun AccessDeniedScreen(
    navController: NavController,
    viewModel: AccessDeniedViewModel = hiltViewModel()
) {
    val pageCount = 4
    val pagerState = rememberPagerState { pageCount }
    val inviteCode = viewModel.inviteCode.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage.value) {
        errorMessage.value?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { page ->
                    when (page) {
                        0 -> OnboardingPage1()
                        1 -> OnboardingPage2()
                        2 -> OnboardingPage3()
                        3 -> InviteCodePage(
                            code = inviteCode.value,
                            onCodeChange = viewModel::onInviteCodeChange,
                            onSubmit = { viewModel.submitCode(navController) }
                        )
                    }
                }

                PagerIndicator(
                    currentPage = pagerState.currentPage,
                    pageCount = pageCount
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
}