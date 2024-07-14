package mainApp

import AuthState
import MenuScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import config.Const
import login.LoginScreen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    val painter = painterResource(id = Const.KEY_LOGO)
    LaunchedEffect(Unit) {
        delay(3000) // Chờ 3 giây
        navController.navigate("navigate_screen")
        {
            popUpTo("splash_screen") { inclusive = true }
        }
    }
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
        )
    {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun NaviGateScreen(navController: NavController) {
    val authState = viewModel<AuthState>()
    val isAuthenticated by authState.isAuthenticated.collectAsState()

    Box {
        AnimatedVisibility(
            visible = isAuthenticated,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            MenuScreen(navControllerApp = navController)
        }

        AnimatedVisibility(
            visible = !isAuthenticated,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })

        ) {
            LoginScreen()
        }
    }
}