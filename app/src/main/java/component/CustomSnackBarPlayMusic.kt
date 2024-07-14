package component


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import config.Const
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Audio
import preferenceManager.PreferenceManager
import service.ServiceFeature
import stateManager.LocalMusicPlayerState
import stateManager.LocalToggle
import theme.AbsoluteBlack
import theme.DarkCharcoal
import theme.MidnightBlue
import theme.primaryColor

@Composable
fun CustomSnackBarPlayMusic() {
    val data = PreferenceManager.getData( key = Const.KEY_ID_SONG)
    val playerState= LocalMusicPlayerState.current
    val toggleState= LocalToggle.current
    val musicState = remember { mutableStateOf<Audio?>(null) }
    val serviceFeature = ServiceFeature()
    val coroutineScope= rememberCoroutineScope()
    LaunchedEffect(data) {

        coroutineScope.launch {
            launch {
                playerState.dataIdSong.collect{
                        idSongCurrent->
                    val music = serviceFeature.getMusicByIdSong(idSongCurrent.toLong())
                    musicState.value = music
                }
            }

        }

    }
    Box(modifier = Modifier.height(height = 80.dp)){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    toggleState.toggleUpdateShowScreenPlayerMusic(true)
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MidnightBlue.copy(alpha = 0.7f),
                            DarkCharcoal.copy(alpha = 0.36f),
                            AbsoluteBlack.copy(alpha = 0.5f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        {

            musicState.value?.displayName?.let { Text(text = it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center) }
            CustomUiControlPlayerMusic()
        }
        ProcessBarCustom(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .fillMaxWidth()
        )
    }
}
