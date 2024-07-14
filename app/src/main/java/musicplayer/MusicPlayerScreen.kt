package musicplayer

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.example.myappmusic.component.CustomCircleImage
import component.CustomAlertDialogConfirm
import component.CustomAlertDialogProcess

import component.CustomIconButton
import component.CustomUiControlPlayerMusic
import component.ProcessBarCustom


import service.ServiceFeature
import theme.AbsoluteBlack
import theme.DarkCharcoal
import theme.MidnightBlue
import config.Const
import dev.vivvvek.seeker.Seeker
import dev.vivvvek.seeker.SeekerColors
import dev.vivvvek.seeker.SeekerDefaults
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Audio
import model.listCardContentAndIconButton
import model.sleepTimerList
import preferenceManager.PreferenceManager
import stateManager.LocalMusicPlayerState
import stateManager.LocalToggle
import theme.DeepBlack
import theme.primaryColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MusicPlayerScreen() {
    val context= LocalContext.current
    val activity=context as ComponentActivity
    val playerState= LocalMusicPlayerState.current
    val toggleState= LocalToggle.current
    val serviceFeature= ServiceFeature()
    val coroutineScope = rememberCoroutineScope()
    val musicState = remember { mutableStateOf<Audio?>(null) }
    var processUpLoad by remember { mutableIntStateOf(0) }
    val showOptionForUser= remember {
        mutableStateOf(value = false)
    }
    val showOptionSleep= remember {
        mutableStateOf(value = false)
    }
    val isShowLoading= remember {
        mutableStateOf(value = false)
    }
    val isShowAlertDialog= remember {
        mutableStateOf(value = false)
    }
    val titleTopAppBar= remember {
        mutableStateOf("....Loading")
    }
    fun updateProcessDownload(value :Int){
        processUpLoad=value
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            launch {
                playerState.dataIdSong.collect{
                        idSongCurrent->
                    val music = serviceFeature.getMusicByIdSong(idSongCurrent.toLong())
                    musicState.value = music
                }
            }
            launch {
                playerState.dataStateTypeList.collect{
                        typeListCurrent->
                    titleTopAppBar.value=typeListCurrent
                }
            }
            launch {
                toggleState.isShowOptionSleep.collect{
                    isShowOptionSleepCurrent->
                    showOptionSleep.value=isShowOptionSleepCurrent
                }
            }
            launch {
                toggleState.isShowOptionForUser.collect{
                    isShowOptionForUserCurrent->
                    showOptionForUser.value=isShowOptionForUserCurrent
                }
            }
            launch {
                toggleState.showLoading.collect{
                    isShowLoadingCurrent->
                    isShowLoading.value=isShowLoadingCurrent
                }
            }
            launch {
                toggleState.showAlertDialog.collect{
                        isShowAlertDialogCurrent->
                    isShowAlertDialog.value=isShowAlertDialogCurrent

                }
            }
        }


    }

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
                           toggleState.toggleUpdateShowScreenPlayerMusic(false)
                           activity.enableEdgeToEdge()
                       }) {
                           Icon(
                               painter = painterResource(id =Const.KEY_ICON_ARROW_DOWN),
                               contentDescription = "",
                               tint = Color.White,
                               modifier = Modifier.size(24.dp)
                           )
                       }

                       Text(text= titleTopAppBar.value,
                           modifier = Modifier.weight(1f))

                       IconButton(onClick = {
                          toggleState.updateToggleShowOptionForUser(value = true)
                       }) {
                           Icon(
                               painter = painterResource(id = Const.KEY_ICON_OPTION),
                               contentDescription = "",
                               tint = Color.White
                           )
                       }
                   }
               }
           },
           content = {
               Box {
                   musicState.value?.let { audio -> ContentMusicPlayer(audio = audio, updateProcessDL = {
                       value->updateProcessDownload(value)
                   }) }
                   AnimatedVisibility(
                       visible = showOptionForUser.value,
                       enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                       exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                   ) {
                       OptionForUser()
                   }
                   AnimatedVisibility(
                       visible = showOptionSleep.value,
                       enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                       exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                   ) {
                       OptionSleep()
                   }
                   if( isShowLoading.value)
                   {
                       CustomAlertDialogProcess(statusMessage = Const.KEY_CONTENT_DIALOG_PROCESSING_DOWNLOAD_FILE,
                           valueProcessingUpload=processUpLoad)

                   }
                   AnimatedVisibility(
                       visible = isShowAlertDialog.value,
                       enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                       exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                   ) {
                       CustomAlertDialogConfirm(
                           statusMessage = Const.KEY_CONTENT_DIALOG_CONFIRM_DOWNLOAD_FILE,
                           onConfirm = {
                               toggleState.updateToggleAlertDialog(value=false)
                               toggleState.updateToggleConfirm(value=true)
                           },
                           onCancel ={
                               toggleState.updateToggleAlertDialog(value=false)
                           } )
                   }


               }
           })
   }

@Composable
fun OptionSleep() {
    val configuration = LocalConfiguration.current
    val playerState= LocalMusicPlayerState.current
    val toggleState= LocalToggle.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .clickable {
                    toggleState.updateToggleShowOptionSleep(value = false)
                }
        )
        {

        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .height(screenHeight * 2 / 4)
                .fillMaxWidth(),
            color = DeepBlack,
            tonalElevation =8.dp // Điều chỉnh độ nổi của Surface
        ) {
            Column {
                Text(text =Const.KEY_TITLE_STOP_AUDIO,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)
                Text(text =Const.KEY_TITLE_END_OF_TRACK,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    )

                LazyColumn(
                ){
                    items(sleepTimerList().size)
                    {
                            index->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 20.dp, top = 20.dp)
                                .clickable {
                                    toggleState.updateToggleShowOptionSleep(value = false)
                                    playerState.startAutoPauseTimer(minutes = sleepTimerList()[index].value)

                                }
                        ) {
                            Text(text =sleepTimerList()[index].content,
                                modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

        }


    }

}


@Composable
fun OptionForUser( ) {
    val configuration = LocalConfiguration.current
    val toggleState= LocalToggle.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(modifier = Modifier
        .fillMaxSize()
        ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .clickable {
                    toggleState.updateToggleShowOptionForUser(value = false)
                }
        )
        {

        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .height(screenHeight * 2 / 4)
                .fillMaxWidth(),
            color = DeepBlack,
            tonalElevation =8.dp // Điều chỉnh độ nổi của Surface
        ) {
            LazyColumn(
            ){
                items(listCardContentAndIconButton().size)
                {
                        index->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            toggleState.updateToggleShowOptionForUser(value = false)
                            toggleState.updateToggleShowOptionSleep(value = true)
                        }
                    ) {
                        CustomIconButton(painter = painterResource(id =  listCardContentAndIconButton()[index].icon), onClick = {

                        })
                        Text(text =listCardContentAndIconButton()[index].content,
                            modifier = Modifier.weight(1f))
                    }
                }
            }
        }


    }
  }



@Composable
fun ContentMusicPlayer(audio:Audio,updateProcessDL:(Int)->Unit){
    val context = LocalContext.current
    val painter= painterResource(id = Const.KEY_LOGO)
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val playerState= LocalMusicPlayerState.current
    val coroutineScope = rememberCoroutineScope()
    val toggleState= LocalToggle.current
    val serviceFeature = ServiceFeature()


    val optionPlayerMusic = remember { mutableStateOf(PreferenceManager.getData(key=Const.KEY_OPTION_PLAYER_MUSIC)) }
    suspend fun downloadFileMusic(){
        try {
            toggleState.updateToggleShowLoading(value=true)
            serviceFeature.downloadFileFromUrl(
                context = context,
                fileUrl = audio.urlFileAudio,
                fileName = audio.displayName,
                onProgress = { currentProgress ->
                    updateProcessDL(currentProgress)
                },
                onSuccess = { file ->
                    toggleState.updateIsSuccess(value=true)
                    coroutineScope.launch {
                        delay(2000)
                        toggleState.updateIsSuccess(value=false)
                        toggleState.updateToggleConfirm(value=false)
                        toggleState.updateToggleShowLoading(value=false)
                    }

                    Log.d(
                        "FileDownload",
                        "Download successful: ${file.absolutePath}"
                    )

                },
                onFailure = { exception ->
                    Log.d(
                        "FileDownload",
                        "Download failed: ${exception.message}"
                    )
                }
            )
        } catch (e: Exception) {
            Log.d("FileDownload", "Download failed: ${e.message}")
        }
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            launch {
                playerState.dataOptionPlayerMusic.collect{
                        optionPlayerMusicCurrent->
                    optionPlayerMusic.value=optionPlayerMusicCurrent
                }
            }

            launch {
                toggleState.isConfirm.collect{
                        stateConfirmCurrent->
                    Log.d("MP3File","$stateConfirmCurrent")
                    if(stateConfirmCurrent){
                        Log.d("MP3File","Ok")
                        downloadFileMusic()
                    }
                }
            }
        }

    }


    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = screenHeight / 10)
                    .height(screenHeight / 2),
                    contentAlignment = Alignment.Center
            ) {
                CustomCircleImage(painter=painter, size = 200.dp)
            }
        }
        item{
            Column(modifier = Modifier.padding(10.dp)) {
                Text(audio.displayName, fontSize = 24.sp, modifier = Modifier.padding(vertical = 10.dp))
                 Row(
                  modifier = Modifier.fillMaxWidth(),
                     verticalAlignment =Alignment.CenterVertically
                 ){
                     Text(audio.artist, color = Color.Gray.copy(0.7f),
                         modifier =Modifier.weight(1f))
                     CustomIconButton(painter = painterResource(id = Const.KEY_ICON_DOWNLOAD), onClick = {
                         toggleState.updateToggleAlertDialog(value=true)

                     })
                     CustomIconButton(painter = painterResource(id = Const.KEY_ICON_SHARE), onClick = {
                         serviceFeature.shareLink(context=context,link=audio.urlFileAudio)
                     })
                     CustomIconButton(painter = painterResource(id = Const.KEY_ICON_LOVE), onClick = {
                         serviceFeature.getAllMp3Files(context=context)
                     })
                 }
            }
        }
        item {
            ProcessBarCustom()
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                 when(optionPlayerMusic.value){
                   Const.KEY_DATA_STATE_LISTENING_REPEAT-> CustomIconButton(painter = painterResource(id = Const.KEY_ICON_SEQUENTIAL),
                       onClick = {
                           PreferenceManager.saveData(key = Const.KEY_OPTION_PLAYER_MUSIC, value = Const.KEY_DATA_STATE_LISTENING_SEQUENTIAL)
                           playerState.toggleUpdateOptionPlayerMusic()
                       })

                   Const.KEY_DATA_STATE_LISTENING_SEQUENTIAL-> CustomIconButton(painter = painterResource(id = Const.KEY_ICON_RANDOM),
                       onClick = {
                           PreferenceManager.saveData(key = Const.KEY_OPTION_PLAYER_MUSIC, value =  Const.KEY_DATA_STATE_LISTENING_RANDOM)
                           playerState.toggleUpdateOptionPlayerMusic()
                       })

                   Const.KEY_DATA_STATE_LISTENING_RANDOM-> CustomIconButton(painter = painterResource(id = Const.KEY_ICON_REPEAT),
                     onClick = {
                         PreferenceManager.saveData(key = Const.KEY_OPTION_PLAYER_MUSIC, value = Const.KEY_DATA_STATE_LISTENING_REPEAT)
                         playerState.toggleUpdateOptionPlayerMusic()
                     })

                 }
                 CustomUiControlPlayerMusic(modifier =
                 Modifier
                     .weight(1f)
                     .padding(start = screenWidth / 10))
                 CustomIconButton(painter = painterResource(id = Const.KEY_ICON_EQUALIZER))
                 CustomIconButton(painter = painterResource(id = Const.KEY_ICON_ADD))
            }

        }
        item{
            Column(
                modifier = Modifier.padding(10.dp)

            ) {
                Text(text=Const.KEY_TITLE_LYRICS)
            }
        }

    }




}
