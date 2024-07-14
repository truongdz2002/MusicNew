package mainmenu.library

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.music.R
import com.example.myappmusic.component.CustomCircleImage
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import config.Const
import model.Playlist
import model.drawableList
import model.drawableListIMAGE
import preferenceManager.PreferenceManager
import service.ServiceFeature
import stateManager.LocalToggle


@Composable
fun PlaylistScreen() {
    var playlists = remember { mutableStateOf<List<Playlist>>(emptyList()) }
    val scope = rememberCoroutineScope()

    val serviceFeature=ServiceFeature()
    LaunchedEffect(Unit) {
        scope.launch {
            playlists.value =serviceFeature.getPlaylistsFromFirestore()
            Log.d("TestPlaylist",playlists.value.toString())
        }
    }
    Box(modifier = Modifier.fillMaxSize() ){
        Column {
            ListPlaylistScreen(playlists.value)
        }

    }
}
@SuppressLint("SuspiciousIndentation")
@Composable
fun ListPlaylistScreen(playlists:List<Playlist>) {
    val scope = rememberCoroutineScope()
    val serviceFeature=ServiceFeature()
    val toggleState= LocalToggle.current

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)) {
                items(playlists.size) { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                scope.launch {
                                    PreferenceManager.saveData(key= Const.KEY_ID_PLAY_LIST, value =playlists[index].id.toString() )
                                    serviceFeature.updatePlaylistTimestamp()
                                    toggleState.updateToggleShowScreenSongsPlayList(true)
                                }

                            }
                    ){
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(10.dp)),
                            painter = painterResource(id = drawableListIMAGE.random()) ,
                            contentDescription = "")
                        Text(text = playlists[index].namePlaylist, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }


