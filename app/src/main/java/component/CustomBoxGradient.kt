package component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import theme.AbsoluteBlack
import theme.DarkCharcoal
import theme.MidnightBlue

@Composable
fun CustomBoxGradient(
    content: @Composable () -> Unit={}
){
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
        content
    }
}