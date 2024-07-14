package com.example.myappmusic.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import config.Const
import theme.primaryColor

@Composable
fun CustomRecentlyTitle(
    slotIcon:String,
    title:String,
    onClick:()->Unit={}
) {
    val valueSlot= slotIcon== Const.KEY_SLOT_ICON
    val painter=painterResource(id = Const.KEY_ICON_SORT)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        if(valueSlot)
        {

            Icon( painter =painter,
                contentDescription = "",
                tint = Color.White,
                  modifier = Modifier
                      .size(20.dp)
                      .clickable {
                          onClick
                      },
                      )
        }
        Text(text=title,
            fontSize = 16.sp,
            color = primaryColor
            )
        if(!valueSlot)
        {

            Icon( painter =painter,
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onClick
                    },
                tint = Color.White

            )
        }

    }
}