package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.primaryColor

@Composable
fun ItemOptionFolderLibrary(title:String,painter: Painter){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom =16.dp)
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp) // Kích thước của hình tròn
                .background(primaryColor, shape = CircleShape),

            )
        {
            Icon(
                painter = painter,
                contentDescription = "Centered Icon",
                modifier = Modifier.size(24.dp)
            )
        }
        Text(text=title,
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 16.sp
        )
    }
}