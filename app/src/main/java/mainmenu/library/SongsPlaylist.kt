package mainmenu.library

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappmusic.component.CustomItemListMusic
import config.Const
import kotlinx.coroutines.launch
import model.Audio
import model.Playlist
import model.drawableListIMAGE
import preferenceManager.PreferenceManager
import service.ServiceFeature
import stateManager.LocalToggle
import theme.AbsoluteBlack
import theme.DarkCharcoal
import theme.MidnightBlue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SongsPlaylist() {
    val toggleState= LocalToggle.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
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
                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        toggleState.updateToggleShowScreenSongsPlayList(false)
                    }) {
                        Icon(
                            painter = painterResource(id = Const.KEY_ICON_ARROW_DOWN),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(text= "Play list for you",
                        modifier = Modifier.weight(1f))

                }
            }
        }, content = {
              ContentPlayList()
        }
    )
}

@Composable
fun ContentPlayList() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val serviceFeature=ServiceFeature()
    val coroutineScope= rememberCoroutineScope()
    var playList by remember {
        mutableStateOf<Playlist?>(null)
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            playList=serviceFeature.getPlaylistById()
        }
    }
    Column(
        modifier = Modifier
            .padding(top = screenHeight / 10, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = painterResource(id = drawableListIMAGE.random()) ,
            contentDescription = "")
        Text(text = playList?.namePlaylist ?: "Playlist not found", fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 15.dp))
        ListSongsPlaylist()

    }
}

@Composable
fun ListSongsPlaylist() {
    var musicList by remember { mutableStateOf<List<Audio>>(emptyList()) }
    val coroutineScope= rememberCoroutineScope()
    val serviceFeature=ServiceFeature()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            musicList= serviceFeature.getAudiosForPlaylist(playlistId =PreferenceManager.getData(key=Const.KEY_ID_PLAY_LIST).toLong())
        }
    }
    LazyColumn(modifier = Modifier.padding(top=16.dp)) {
        items(musicList.size){index->
            CustomItemListMusic(item=musicList[index], onClick = {
            })
        }
    }
}
