package login


import AuthService
import AuthState
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import component.CustomButton
import theme.DeepBlack
import theme.SilverSand
import theme.primaryColor
import com.google.android.gms.auth.api.identity.Identity
import config.Const
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(){
    val painter = painterResource(id = Const.KEY_LOGO)
    val context= LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)
    val authService = AuthService(oneTapClient)
    val coroutineScope = rememberCoroutineScope()
    val authState: AuthState = viewModel()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            coroutineScope.launch {
                val signInResult = authService.getSignInResultFromIntent(data ?: return@launch)
                     signInResult.data?.let {user->
                         authService.uploadUserToFirestore(user)
                     }
                authState.setAuthenticated(true)
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .padding(40.dp)
                .size(200.dp)
        )
        Text(text = Const.KEY_TITLE_LOGIN,
             modifier = Modifier.padding( 50.dp),
              textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
           )
        CustomButtonLogin(text = Const.KEY_CONTENT_BUTTON_GOOGLE,
            imagePainter=painterResource(id = Const.KEY_ICON_GOOGLE),
            onClick = {
                coroutineScope.launch {
                    val intentSender = authService.signIn()
                    if (intentSender != null) {
                        val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                        launcher.launch(intentSenderRequest)
                    }
                }


              }
            )
        CustomButtonLogin(text =Const.KEY_CONTENT_BUTTON_FACEBOOK,
            imagePainter=painterResource(id = Const.KEY_ICON_FACEBOOK),

        )
        CustomButtonLogin(text =Const.KEY_CONTENT_BUTTON_APPLE,
            imagePainter=painterResource(id = Const.KEY_ICON_APPLE),
            onClick = {

            }
        )
        OrDivider()
        CustomButton(text =Const.KEY_CONTENT_BUTTON_WITH_PASSWORD)
        CustomTextToSignIn()


    }



}
@Composable
fun OrDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(20.dp), // Thêm padding bottom 100dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = SilverSand)
                .weight(1f) // Chiếm một phần
        )
        Text(
            text = "OR",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = SilverSand)
                .weight(1f) // Chiếm một phần
        )
    }
}



@Composable
fun CustomButtonLogin(
    text: String,
    imagePainter: Painter,
    onClick:  () -> Unit= {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
             containerColor = DeepBlack
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(fraction = 0.9f)
            .border(
                BorderStroke(0.2.dp, color = SilverSand),
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth(0.62f)
                    .padding(5.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}


@Composable
  fun CustomTextToSignIn(){
      Row(
          modifier = Modifier
              .padding(20.dp),
      ){
          Text(
              text =Const.KEY_TITLE_CONTENT_NO_ACCOUNT,
              style = TextStyle(
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
              )
          )
          Text(
              text = Const.KEY_TITLE_CONTENT_SING_UP,
              modifier = Modifier
                  .padding(start = 5.dp),
              style = TextStyle(
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = primaryColor
              )
          )
      }
  }

