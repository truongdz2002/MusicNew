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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import config.Const
import kotlinx.coroutines.launch
import stateManager.LocalValueDialog
import theme.DeepBlack
import theme.primaryColor

@Composable
fun CustomAlertDialogCreateContent(
    statusMessage:String = Const.KEY_LOADING,
    nameButtonConfirm:String="Confirm",
    nameButtonCancel:String="Cancel",
    onConfirm:()->Unit={},
    onCancel:()->Unit={})
{
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
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp,
                        bottomStart = 16.dp
                    )
                )
                .height(200.dp)
                .width(300.dp),
            color = DeepBlack,
            tonalElevation = 8.dp // Điều chỉnh độ nổi của Surface
        ) {
            Column(
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(statusMessage, fontSize = 20.sp, color = primaryColor)
                OptionCreate()
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    CustomButton(text = nameButtonCancel, onClick = onCancel)
                    CustomButton(text = nameButtonConfirm, onClick = onConfirm)
                }
            }
        }
    }
}

@Composable
fun OptionCreate() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    val contentCreate= remember {
         mutableStateOf("")
    }
    val valueAlertDialog= LocalValueDialog.current
    LaunchedEffect(Unit) {
        scope.launch {
            launch {
                valueAlertDialog.valueNamePlayerListCreate.collect{
                    valueCurrent->contentCreate.value=valueCurrent
                }
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        TextField(
            value = contentCreate.value,
            onValueChange = { newText ->
                valueAlertDialog.updateValueNamePlayerListCreate(newText)
            },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text("Enter edit") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = primaryColor,
                unfocusedTextColor=Color.Black,
                unfocusedContainerColor =Color.Transparent,
                focusedContainerColor = Color.Transparent,
                cursorColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp), // Set the corner radius here
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )

        )
    }
}
