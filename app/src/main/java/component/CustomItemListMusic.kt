package com.example.myappmusic.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import config.Const
import model.Audio
import model.drawableListIMAGE

@Composable
fun CustomItemListMusic(onClick:()->Unit={},item: Audio) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(top = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            painter = painterResource(id = drawableListIMAGE.random()) ,
            contentDescription = "")
        //CustomCircleImage(painter = painterResource(id =Const.KEY_LOGO))
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = item.displayName,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = item.artist,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
    }
}