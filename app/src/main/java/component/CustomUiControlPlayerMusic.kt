package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import config.Const
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import preferenceManager.PreferenceManager
import stateManager.LocalMusicPlayerState
import theme.primaryColor

@Composable
fun  CustomUiControlPlayerMusic(
    modifier: Modifier=Modifier
){
    val playerState= LocalMusicPlayerState.current
    val iconPlay= painterResource(id = Const.KEY_ICON_PLAY)
    val iconPause= painterResource(id = Const.KEY_ICON_PAUSE)
    val iconPrecious= painterResource(id = Const.KEY_ICON_PRECIOUS)
    val iconNext= painterResource(id = Const.KEY_ICON_NEXT)
    var statePlay by remember {
        mutableStateOf<Boolean>(false)
    }
    val coroutineScope= rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            playerState.dataStatePlayerMusic.collect{
                statePlayerMusicCurrent->
                statePlay=statePlayerMusicCurrent==Const.KEY_STATE_PLAY
            }
        }

    }
    Row(
        modifier=modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomIconButton(painter =iconPrecious, onClick = {
            playerState.togglePreciousSong()
        }, modifier = Modifier.padding(10.dp))
        if(statePlay)
        {
            CustomIconButton(painter =iconPause, backGroundColor = primaryColor, onClick = {
                PreferenceManager.saveData(key = Const.KEY_STATE_LISTENING, value = Const.KEY_STATE_PAUSE)
                playerState.togglePause()
                statePlay=false

            },
                modifier = Modifier.padding(10.dp))
        }

        else {
            CustomIconButton(painter =iconPlay, backGroundColor = primaryColor, onClick =
            {
                PreferenceManager.saveData(key = Const.KEY_STATE_LISTENING, value = Const.KEY_STATE_PLAY)
                playerState.togglePlay()
                statePlay=true

            },
                modifier = Modifier.padding(10.dp))
        }

        CustomIconButton(painter =iconNext, onClick ={
            playerState.toggleNextSong()
        },modifier = Modifier.padding(10.dp))
    }
}