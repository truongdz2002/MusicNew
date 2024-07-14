package component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import config.Const
import dev.vivvvek.seeker.Seeker
import dev.vivvvek.seeker.SeekerDefaults
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import stateManager.LocalMusicPlayerState
import theme.primaryColor

@Composable
fun ProcessBarCustom (modifier: Modifier=Modifier) {
    var valueSeeker by remember { mutableFloatStateOf(0f) }
    val playerState= LocalMusicPlayerState.current
    val coroutineScope= rememberCoroutineScope()
    LaunchedEffect(Unit) {
         coroutineScope.launch {
               playerState.dataSeekBar.collect{
                   valueSeekBarCurrent->valueSeeker=valueSeekBarCurrent
                   delay(1000L)
               }
                }}

    Seeker(
        modifier=modifier,
        value = valueSeeker,
        range = 0f..1f,
        colors = SeekerDefaults.seekerColors(trackColor = Color.White, progressColor = primaryColor, thumbColor = primaryColor),
        onValueChange = {
            valueSeeker = it
            playerState.mediaPlayer.seekTo((it *  playerState.mediaPlayer.duration).toInt())
        }
    )
}