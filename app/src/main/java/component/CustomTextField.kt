import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import theme.DeepBlack
import theme.primaryColor

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError:Boolean=false,
    keyboardOptions:KeyboardOptions
) {
    Column {

        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label,style = TextStyle(
                color = primaryColor
            )) },
            isError =isError ,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = DeepBlack,
                unfocusedContainerColor = DeepBlack,
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = primaryColor,
                cursorColor = primaryColor,
                errorContainerColor = DeepBlack,
                errorIndicatorColor =Color.Transparent ,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent

            ),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(color = DeepBlack)// Chiều rộng của TextField là fillMaxWidth
        )
        if (isError) {
            Text(
                text = "This field cannot be empty",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
