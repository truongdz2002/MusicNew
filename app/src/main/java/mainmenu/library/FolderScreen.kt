package com.example.myappmusic.library

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myappmusic.component.CustomRecentlyTitle
import component.ItemOptionFolderLibrary
import config.Const

@Composable
 fun FolderScreen(){
     val iconAdd=painterResource(id = Const.KEY_ICON_ADD)
     val iconLove=painterResource(id = Const.KEY_ICON_LOVE)
     Column(
         modifier = Modifier.fillMaxSize()
             .padding(16.dp)
     ) {
         ItemOptionFolderLibrary(title = Const.KEY_TITLE_ADD , painter =iconAdd )
         ItemOptionFolderLibrary(title = Const.KEY_TITLE_RECENTLY , painter =iconLove )
         CustomRecentlyTitle(title =Const.KEY_TITLE_RECENTLY_PLAYER, slotIcon = "end", onClick = {
         })
     }
 }
