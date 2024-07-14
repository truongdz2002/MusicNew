package component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import config.Const
import kotlinx.coroutines.launch
import mainmenu.library.ListPlaylistScreen
import model.Playlist
import model.drawableListIMAGE
import preferenceManager.PreferenceManager
import service.ServiceFeature
import stateManager.LocalToggle
import theme.DeepBlack
import theme.primaryColor

@Composable
fun CustomAlertDialogShowPlaylist(
 statusMessage:String = Const.KEY_LOADING,
 nameButtonConfirm:String="Confirm",
 nameButtonCancel:String="Cancel",
 onConfirm:()->Unit={},
 onCancel:()->Unit={}
) {
 val configuration = LocalConfiguration.current
 val toggleState= LocalToggle.current
 val screenWidth = configuration.screenWidthDp.dp
 Box(modifier = Modifier
  .fillMaxSize()
  .clickable {

  }
 ) {
  Column(
   modifier = Modifier
    .fillMaxSize()
    .background(color = Color.Transparent)
    .clickable {
     toggleState.updateToggleShowAlertDialogPlayList(false)
    }
  ) {}
  Surface(
   modifier = Modifier
    .align(Alignment.Center)
    .clip(
     RoundedCornerShape(
      topStart = 16.dp,
      topEnd = 16.dp,
      bottomEnd = 16.dp,
      bottomStart = 16.dp
     )
    )
    .height(400.dp)
    .width(screenWidth * 0.8f),
   color = DeepBlack,
   tonalElevation = 8.dp // Điều chỉnh độ nổi của Surface
  ) {
   Column(
    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
   ) {
    Text(statusMessage, fontSize = 20.sp, color = primaryColor)
    ListPlaylistAlertDialog()
    Row(
     modifier = Modifier.padding(10.dp)
    ) {
     CustomButton(text = nameButtonCancel, onClick = onCancel)
     CustomButton(text = nameButtonConfirm, onClick = onConfirm)
    }
   }
  }
 }
}
@Composable
fun ListPlaylistAlertDialog() {
 val scope = rememberCoroutineScope()
 var playlists = remember { mutableStateOf<List<Playlist>>(emptyList()) }
 val serviceFeature= ServiceFeature()
 val context= LocalContext.current


 val toggleState= LocalToggle.current
 LaunchedEffect(Unit) {
  scope.launch {
   playlists.value =serviceFeature.getPlaylistsFromFirestore()
   Log.d("TestPlaylist",playlists.value.toString())
  }
 }
 LazyColumn(
  modifier = Modifier
   .fillMaxSize()
   .padding(top = 10.dp)) {
  items(playlists.value.size) { index ->
   Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
     .padding(10.dp)
     .clickable {
      scope.launch {
       serviceFeature.updatePlaylistAddAudio(context=context,playlistId = playlists.value[index].id,
        audioId = PreferenceManager.getData(key = Const.KEY_ID_SONG).toLong(),
        onFailure = {
         Toast.makeText(
          context,
          "Playlist updated failed!",
          Toast.LENGTH_SHORT
         ).show()
         toggleState.updateToggleShowAlertDialogPlayList(false)
        },
        onSuccess = {
         Toast.makeText(
          context,
          "Playlist updated successfully!",
          Toast.LENGTH_SHORT
         ).show()
         toggleState.updateToggleShowAlertDialogPlayList(false)
        })
      }
     },
   ){
    Image(
     modifier = Modifier
      .size(60.dp)
      .clip(shape = RoundedCornerShape(10.dp)),
     painter = painterResource(id = drawableListIMAGE.random()) ,
     contentDescription = "")
    Text(text = playlists.value[index].namePlaylist, modifier = Modifier.padding(16.dp))
   }
  }
 }
}