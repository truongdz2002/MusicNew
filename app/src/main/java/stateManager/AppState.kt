package stateManager

// AppState.kt

import androidx.compose.runtime.compositionLocalOf
import MusicPlayerState

val LocalMusicPlayerState = compositionLocalOf<MusicPlayerState> {
    error("MusicPlayerState not provided")
}
val LocalToggle = compositionLocalOf<ToggleState> {
    error("MusicPlayerState not provided")
}
