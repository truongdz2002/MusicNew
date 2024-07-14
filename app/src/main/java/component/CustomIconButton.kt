package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CustomIconButton(
    painter: Painter,
    sizeButton:Int=40,
    onClick: () -> Unit={},
    sizeIcon:Int=24,
    backGroundColor: Color = Color.Transparent,
    modifier: Modifier=Modifier,
    colorIcon:Color=Color.White


) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(sizeButton.dp)
            .clip(CircleShape)
            .background(backGroundColor)
    ) {
        Icon(
            painter =painter ,
            contentDescription = "",
            modifier = modifier.size(sizeIcon.dp),
            tint = colorIcon

        )
    }
}
