package com.example.myappmusic.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.primaryColor

@Composable
fun CustomCircleImage(
    painter: Painter,
    onClick:()->Unit={},
    size: Dp =50.dp
) {
    Box(
        modifier = Modifier
            .size(size) // Set size of the Box
            .clickable {
                onClick
            }
            .clip(CircleShape)
            .border(BorderStroke(2.dp, primaryColor), CircleShape)
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .size(size) // Set size of the Image
                .clip(CircleShape)

        )
    }
}