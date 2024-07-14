package mainmenu.library

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.music.R
import com.example.myappmusic.component.CustomCircleImage
import com.example.myappmusic.component.CustomItemListMusic
import com.example.myappmusic.component.CustomRecentlyTitle
import config.Const
import service.ServiceFeature
import theme.primaryColor
import kotlinx.coroutines.launch
import model.Audio
import preferenceManager.PreferenceManager
import stateManager.LocalMusicPlayerState
import stateManager.LocalToggle

@Composable
fun ArtistsScreen() {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var artistNameClicked by remember {
        mutableStateOf<String>("")
    }
    fun updateDisplayNameClicked(artistName: String) {
        artistNameClicked = artistName
        coroutineScope.launch {
            scrollState.animateScrollToItem(1)
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = true,
        state = scrollState,
    ) {
        item {
            ChildArtistsScreen(
                onClick = {
                    value->updateDisplayNameClicked(value)
                },

            )
        }
        item {
            SongScreenOfChildArtistScreen(artistName = artistNameClicked)
        }
    }

}

@Composable
fun ChildArtistsScreen(onClick: (String) -> Unit = {}) {
    val serviceFeature = ServiceFeature()
    var listArtistName by remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(Unit) {
        listArtistName = serviceFeature.getUniqueArtistNames()

    }
    Column(
        modifier = Modifier
            .width(400.dp)
            .padding(16.dp)
    ) {
        Row {
            Text(text = Const.KEY_TITLE_SORT_BY)
            Spacer(modifier = Modifier.weight(1f))
            CustomRecentlyTitle(title = Const.KEY_TITLE_RECENTLY_PLAYER, slotIcon = "end", onClick = {
            })
        }
        ListArtistRecently(listArtistName = listArtistName, onClick = onClick)
    }
}
@Composable
fun SongScreenOfChildArtistScreen(artistName:String){
 val serviceFeature= ServiceFeature()
    val playerState= LocalMusicPlayerState.current
    val toggleState= LocalToggle.current
    var musicList by remember { mutableStateOf<List<Audio>>(emptyList())}
    LaunchedEffect(Unit) {
        musicList = serviceFeature.getSongsByArtistName(artistName =artistName )
    }
    Column(
        modifier = Modifier
            .width(400.dp)
            .padding(16.dp)
    ) {
        Text(text=artistName, fontSize = 24.sp, color = primaryColor)
        LazyColumn(modifier = Modifier.padding(top=16.dp)) {
               items(musicList.size){index->
                   CustomItemListMusic(item=musicList[index], onClick = {
                       PreferenceManager.saveData(key = Const.KEY_STATE_LISTENING, value = Const.KEY_STATE_PLAY)
                       PreferenceManager.saveData( key = Const.KEY_ID_SONG, value =musicList[index].id.toString() )
                       playerState.toggleUpdateIdSong()
                       playerState.togglePlay()
                       toggleState.toggleUpdateShowScreenPlayerMusic(true)
                   })
               }
        }
    }
}
@Composable
fun ListArtistRecently(
    onClick: (String) -> Unit = {},
    listArtistName: List<String>
) {
    val drawableList = listOf(
        R.drawable.img_singer1,
        R.drawable.img_singer2,
        R.drawable.img_singer3,
        R.drawable.img_singer5,
        R.drawable.img_singer1,
    )
    val playerState= LocalMusicPlayerState.current
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listArtistName.size) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = {
                    onClick(listArtistName[index])
                    PreferenceManager.saveData(key = Const.KEY_TYPE_LIST, value = Const.KEY_DATA_TYPE_LIST_ARTIST)
                    PreferenceManager.saveData(key = Const.KEY_DATA_NAME_ARTIST, value = listArtistName[index])
                    playerState.toggleUpdateListType()
                    playerState.toggleUpdateArtistName()
                })
            ) {
                CustomCircleImage(painter = painterResource(id = drawableList.random()))
                Text(
                    text = listArtistName[index],
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
