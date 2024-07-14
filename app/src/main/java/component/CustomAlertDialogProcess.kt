package component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import config.Const
import theme.DeepBlack

@Composable
fun CustomAlertDialogProcess(
    statusMessage:String = Const.KEY_LOADING,
    valueProcessingUpload:Int
){

    AlertDialog(
        onDismissRequest = {},
        title = {
            LoadingIndicator(valueProcessingUpload=valueProcessingUpload)
        },
        confirmButton = {},
        modifier = Modifier.size(180.dp),
        containerColor = DeepBlack
    )

}