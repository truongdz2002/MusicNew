package navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import config.Const
import mainmenu.explore.ExploreScreen
import com.example.myappmusic.library.LibraryScreen
import mainmenu.home.HomeScreen

@Composable
fun NavigationMenu(idItemBottomBarClicked: Int) {
    val showHomeScreen = remember { mutableStateOf(idItemBottomBarClicked == 1) }
    val showExploreScreen = remember { mutableStateOf(idItemBottomBarClicked == 2) }
    val showLibraryScreen = remember { mutableStateOf(idItemBottomBarClicked == 3) }

    // Update visibility states based on idItemBottomBarClicked changes
    showHomeScreen.value = (idItemBottomBarClicked == 1)
    showExploreScreen.value = (idItemBottomBarClicked == 2)
    showLibraryScreen.value = (idItemBottomBarClicked == 3)

    Box {
        AnimatedVisibility(
            visible = showHomeScreen.value,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            HomeScreen()
        }

        AnimatedVisibility(
            visible = showExploreScreen.value,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            ExploreScreen()
        }

        AnimatedVisibility(
            visible = showLibraryScreen.value,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            LibraryScreen()
        }
    }
}