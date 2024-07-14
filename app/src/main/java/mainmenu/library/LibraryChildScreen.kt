package mainmenu.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myappmusic.library.FolderScreen

@Composable
fun LibraryChildScreen(idButton: Int, showScreenPlayerMusic: (Boolean) -> Unit) {
    val showFolderScreen = remember { mutableStateOf(idButton == 1) }
    val showArtistsScreen = remember { mutableStateOf(idButton == 3) }
    val showPlaylistScreen = remember { mutableStateOf(idButton == 2) }

    // Update visibility states based on idButton changes
    showFolderScreen.value = (idButton == 1)
    showArtistsScreen.value = (idButton == 3)
    showPlaylistScreen.value = (idButton == 2)

    Box {
        AnimatedVisibility(
            visible = showFolderScreen.value,
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            FolderScreen()
        }

        AnimatedVisibility(
            visible = showArtistsScreen.value,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            ArtistsScreen()
        }

        AnimatedVisibility(
            visible = showPlaylistScreen.value,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            PlaylistScreen()
        }
    }
}