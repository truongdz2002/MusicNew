package component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import config.Const
import theme.DeepBlack
import theme.primaryColor

@Composable
  fun CustomAlertDialogConfirm( statusMessage:String = Const.KEY_LOADING,
                                onConfirm:()->Unit={},
                                onCancel:()->Unit={}                        ) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .clickable {
                }
        ) {}
        Surface(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                .height(150.dp)
                .width(300.dp),
            color = DeepBlack,
            tonalElevation = 8.dp // Điều chỉnh độ nổi của Surface
        ) {
            Column(
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(statusMessage, fontSize = 16.sp, color = primaryColor)
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    CustomButton(text = "Cancel", onClick = onCancel)
                    CustomButton(text = "Confirm", onClick = onConfirm)
                }
            }
        }
    }
  }






