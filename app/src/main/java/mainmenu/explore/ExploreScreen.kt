package mainmenu.explore

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappmusic.component.CustomItemListMusic
import config.Const
import kotlinx.coroutines.launch
import mainmenu.home.GridDisplay
import mainmenu.home.List2Display
import mainmenu.home.ListDisplay
import model.Audio
import model.createDataExplore
import preferenceManager.PreferenceManager
import service.ServiceFeature
import stateManager.LocalMusicPlayerState
import stateManager.LocalToggle
import theme.primaryColor

@Composable
fun ExploreScreen(){
    var searchText = remember { mutableStateOf("") }

    var musicList = remember { mutableStateOf<List<Audio>>(emptyList())}
    fun updateValueTextSearch(value:String){
        searchText.value=value
    }
    fun updateListAudioSearch(value:List<Audio>){
        musicList.value=value
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchElement(searchText.value,
            updateValue = {
            value->updateValueTextSearch(value)
        },
            updateListAudio = {
                value->updateListAudioSearch(value)
            })
        if(searchText.value.isNotEmpty()){
            SongSearchScreen(searchText.value,musicList.value)
        }
        else
        {
            CustomListContentExplore()

        }
    }
}

@Composable
fun CustomListContentExplore(){
    createDataExplore().forEach { itemList->
        Text(text = itemList.nameCategory, modifier = Modifier.padding(16.dp))
        when (itemList.type) {
            "grid" -> GridDisplay(items = itemList.itemsContent)
            "list" -> ListDisplay(items = itemList.itemsContent)
            "list2" -> List2Display(items = itemList.itemsContent)
        }
    }
}

@Composable
fun SearchElement(searchText : String,updateValue:(String)->Unit,updateListAudio:(List<Audio>)->Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val serviceFeature= ServiceFeature()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        TextField(
            value = searchText,
            onValueChange = { newText ->
                updateValue(newText)
                scope.launch {
                    val songs = serviceFeature.getSongsByNameSongAndNameArtist(newText)
                    updateListAudio(songs)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Songs, Artists...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedTextColor=Color.Black,
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.Gray,
                cursorColor = Color.White


            ),
            shape = RoundedCornerShape(20.dp), // Set the corner radius here
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
            ,
            keyboardActions = KeyboardActions(
                onSearch = {
                    scope.launch {
                       val songs = serviceFeature.getSongsByArtistName(searchText)
                        updateListAudio(songs)
                        Log.d("searchText",songs.size.toString())
                        keyboardController?.hide()
                    }

                }
            )
        )
    }
}
@Composable
fun SongSearchScreen(valueSearch: String, musicList: List<Audio>,){
    val playerState= LocalMusicPlayerState.current
    val toggleState= LocalToggle.current


    Column(
        modifier = Modifier
            .width(400.dp)
            .padding(16.dp)
    ) {
        Text(text=valueSearch, fontSize = 24.sp, color = primaryColor)
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