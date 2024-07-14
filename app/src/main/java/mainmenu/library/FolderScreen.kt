package com.example.myappmusic.library

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myappmusic.component.CustomRecentlyTitle
import component.CustomAlertDialogConfirm
import component.CustomAlertDialogCreateContent
import component.ItemOptionFolderLibrary
import config.Const
import kotlinx.coroutines.launch
import mainmenu.library.ListPlaylistScreen
import model.Playlist
import service.ServiceFeature
import stateManager.LocalToggle
import stateManager.LocalValueDialog

@Composable
 fun FolderScreen(){
     val iconAdd=painterResource(id = Const.KEY_ICON_ADD)
     val iconLove=painterResource(id = Const.KEY_ICON_LOVE)
     var playlists = remember { mutableStateOf<List<Playlist>>(emptyList()) }
     val toggleState= LocalToggle.current
     val serviceFeature=ServiceFeature()
     val valueAlertDialog= LocalValueDialog.current
     val isShowAlertDialog= remember {
        mutableStateOf(value = false)
     }

    val contentCreate= remember {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
                playlists.value=serviceFeature.getPlaylistsSortedByTimestamp()

            launch {
                toggleState.showAlertDialog.collect{
                        isShowAlertDialogCurrent->
                    isShowAlertDialog.value=isShowAlertDialogCurrent

                }
            }
            launch {
                valueAlertDialog.valueNamePlayerListCreate.collect{
                        valueCurrent->contentCreate.value=valueCurrent
                }
            }
        }

    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

    }
     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(16.dp)

     ) {
         ItemOptionFolderLibrary(title = Const.KEY_TITLE_ADD , painter =iconAdd,
             onClick = {
                 toggleState.updateToggleAlertDialog(value = true)
                 Log.d("TestClick","0k")
             }
             )
         ItemOptionFolderLibrary(title = Const.KEY_TITLE_RECENTLY , painter =iconLove,onClick={} )
         CustomRecentlyTitle(title =Const.KEY_TITLE_RECENTLY_PLAYER, slotIcon = "end", onClick = {
         })
         ListPlaylistScreen(playlists.value)

     }
    AnimatedVisibility(
        visible = isShowAlertDialog.value,
        enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        CustomAlertDialogCreateContent(
            statusMessage = Const.KEY_CONTENT_DIALOG_CREATE_PLAY_LIST,
            nameButtonConfirm = Const.KEY_CONTENT_BUTTON_CREATE,
            onConfirm = {
                coroutineScope.launch {
                    serviceFeature.addPlaylistToFirestore(
                        namePlaylist = contentCreate.value,
                        onSuccess = {
                            valueAlertDialog.updateValueNamePlayerListCreate("")
                        },
                        onFailure = {}
                    )
                }

                toggleState.updateToggleAlertDialog(value = false)
            },
            onCancel = {
                valueAlertDialog.updateValueNamePlayerListCreate("")
                toggleState.updateToggleAlertDialog(value = false)
            })
    }
 }
