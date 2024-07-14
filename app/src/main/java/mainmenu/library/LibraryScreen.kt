package com.example.myappmusic.library

import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mainmenu.library.LibraryChildScreen
import service.ServiceFeature
import model.ButtonLibrary
import model.createButtonLibraryList
import theme.primaryColor
import model.Audio

@Composable
fun LibraryScreen(){
    val context= LocalContext.current
    val  serviceFeature= ServiceFeature()
    val activity = context as ComponentActivity
    var idItemClicked by remember {
        mutableIntStateOf(1)
    }
    var showPlayMusic by remember {
        mutableStateOf(true)
    }
    var musicList by remember { mutableStateOf<List<Audio>>(emptyList())}
    LaunchedEffect(Unit) {
        musicList = serviceFeature.getMusicList()
    }
    fun upDateValueShowPlayMusic(value:Boolean){
        showPlayMusic=value
        activity.enableEdgeToEdge()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp)) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            items(createButtonLibraryList().size) { index ->
                ItemButtonLibrary(buttonLibrary = createButtonLibraryList()[index],
                    onClick = {
                    idItemClicked= createButtonLibraryList()[index].idButton
                },
                    idItemClicked=idItemClicked)

            }
        }
        LibraryChildScreen(idButton =idItemClicked, showScreenPlayerMusic = {
         value->  upDateValueShowPlayMusic(value)
        })
    }
}

@Composable
fun ItemButtonLibrary(buttonLibrary: ButtonLibrary, onClick: () -> Unit, idItemClicked:Int) {
    val valueBackground=if(idItemClicked==buttonLibrary.idButton) primaryColor else Color.Transparent
    val valueBorder=if(idItemClicked==buttonLibrary.idButton)  Color.Transparent  else Color.White
    Button(
        onClick = onClick,
        //shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = valueBackground
        ),
        modifier = Modifier
            .padding(10.dp)
            .shadow(10.dp)
            .border(
                BorderStroke(2.dp, color = valueBorder),
                shape = RoundedCornerShape(20.dp)
            )


    ) {
        Text(
            text = buttonLibrary.content,
            modifier = Modifier
                .padding(5.dp),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            overflow = TextOverflow.Ellipsis
        )
    }


}




