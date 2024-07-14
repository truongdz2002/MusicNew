package component
import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappmusic.component.CustomCircleImage
import theme.primaryColor
import config.Const
import kotlinx.coroutines.launch
import stateManager.LocalToggle


@Composable
fun LoadingIndicator( valueProcessingUpload:Int) {
    val toggleState= LocalToggle.current
    val coroutineScope = rememberCoroutineScope()
    val animationSpec = remember { TweenSpec<Float>(durationMillis =1000) }
    val progress = remember { Animatable(0f) }
    val isSuccess = remember{ mutableStateOf(false) }
    LaunchedEffect(Unit) {
        coroutineScope.launch { 
            launch {
                while (true) {
                    progress.animateTo(
                        targetValue = 1f,
                        animationSpec = animationSpec
                    )
                    progress.snapTo(0f)
                }
            }
            launch { 
                toggleState.isSuccess.collect{
                    stateSuccessCurrent->
                    isSuccess.value=stateSuccessCurrent
                }
            }
        }
       
    }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if(isSuccess.value){
                    CustomCircleImage(painter = painterResource(id = Const.KEY_ICON_SUCCESSFUL))
                }
                else{
                    CircularProgressIndicator(progress = progress.value,
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .size(50.dp), color = primaryColor
                    )
                    Text(text = "$valueProcessingUpload%", color = primaryColor, modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            fontSize = 12.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                        
                    }


                }




