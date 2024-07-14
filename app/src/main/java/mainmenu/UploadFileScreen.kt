package mainmenu
import CustomTextField
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import component.CustomAlertDialogProcess
import component.CustomButton
import config.Const
import service.ServiceFeature
import theme.AbsoluteBlack
import theme.DarkCharcoal

import theme.MidnightBlue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UploadFileScreen(navController: NavController){

    Scaffold(
        topBar = {
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
                Row {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = Const.KEY_ICON_BACK),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    Text(Const.KEY_TITLE_APP_BAR_UPLOAD_SCREEN, modifier = Modifier.padding(16.dp))
                }
            }
        },
        content = {
            ContentUploadFile()
        }
    )
}

@Composable
fun ContentUploadFile() {
    var displayName by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    val  serviceFeature= ServiceFeature()
    var isLoading by remember { mutableStateOf(false) }
    var processUpLoad by remember { mutableIntStateOf(0) }
    var isSuccess by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        fileUri = uri
        uri?.let {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, it)
            val embeddedPicture = retriever.embeddedPicture
            bitmap = embeddedPicture?.let { bytes ->
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
        }
    }
    fun updateProcessUpLoadFile(value: Int){
        processUpLoad=value
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        fileUri?.let {
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            launcher.launch("audio/mpeg")
                        }
                )
            } ?: run {
                Text("No image found in the music file")
            }
        } ?: run {
            Image(
                painter = painterResource(id = Const.KEY_ICON_EXPLORE),
                contentDescription = "Explore Icon",
                modifier = Modifier
                    .size(150.dp)
                    .clickable {
                        launcher.launch("audio/mpeg")
                    }
            )
        }


        Spacer(modifier = Modifier.height(16.dp)) // Dùng Spacer để tạo khoảng cách
        CustomTextField(
            value = displayName,
            label = Const.KEY_TITLE_DISPLAY_NAME_SONG_UPLOAD_FILE,
            isError = isError,
            onValueChange = { newValue->
                displayName=newValue
                if (displayName.isNotEmpty()) {
                    isError = false
                }
                            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp)) // Dùng Spacer để tạo khoảng cách
        CustomTextField(
            value = artist,
            label = Const.KEY_TITLE_ARTIST_NAME_UPLOAD_FILE,
            isError = isError,
            onValueChange = { newValue->
                artist=newValue
                if (artist.isNotEmpty()) {
                    isError = false
                }           },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )


        Spacer(modifier = Modifier.height(16.dp)) // Dùng Spacer để tạo khoảng cách

        CustomButton(text = Const.KEY_CONTENT_BUTTON_UPLOAD_FILE, onClick = {
            if(artist.isEmpty() || displayName.isEmpty())
            {
                isError=true
                return@CustomButton
            }
            fileUri?.let {
                isLoading=true
                serviceFeature.uploadFileToFirebase(
                    displayName=displayName,
                    artist=artist,
                    uriFileAudio=it,
                onSuccess = {
                    isSuccess=true
                    isLoading=false
                    displayName=""
                    artist=""
                    fileUri=null

            },
                onFailure = {
                    isLoading=false
            },
                    onProcess = {
                        value->updateProcessUpLoadFile(value)
                        Log.d("process",value.toString())
                    })

            }
        }
        )
        if(isLoading)
        {
            CustomAlertDialogProcess(statusMessage = Const.KEY_CONTENT_DIALOG_PROCESSING_UPLOAD_FILE,
            valueProcessingUpload=processUpLoad)

        }
    }
}
