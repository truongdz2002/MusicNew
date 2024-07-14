import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myappmusic.component.CustomCircleImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import navigation.NavigationMenu
import theme.AbsoluteBlack
import theme.DarkCharcoal
import theme.MidnightBlue
import theme.primaryColor
import theme.primaryColorBackGround
import com.google.android.gms.auth.api.identity.Identity
import component.CustomIconButton
import component.CustomSnackBarPlayMusic
import config.Const
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mainmenu.library.SongsPlaylist
import model.BottomNavigationBarItem
import model.bottomNavigationBarItemList
import musicplayer.MusicPlayerScreen
import musicplayer.OptionForUser
import preferenceManager.PreferenceManager
import stateManager.LocalMusicPlayerState
import stateManager.LocalToggle
import theme.DeepBlack


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuScreen(navControllerApp: NavController){
    val context= LocalContext.current
    val playerState= LocalMusicPlayerState.current
    val toggleState= LocalToggle.current
    val coroutineScope= rememberCoroutineScope()
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var idItemBottomBarClicked by remember {
        mutableIntStateOf(1)
    }
    var isShowScreenPlayerMusic= remember {
        mutableStateOf<Boolean>(false)
    }
    var isShowScreenSongsPlayList= remember {
        mutableStateOf<Boolean>(false)
    }
    var idSong= remember {
        mutableStateOf(value = PreferenceManager.getData(key=Const.KEY_ID_SONG))
    }
    fun updateValueIdItemBottomBar(value:Int){
        idItemBottomBarClicked=value
    }
    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }
    LaunchedEffect(Unit) {
        coroutineScope.launch {

            launch {
                playerState.dataIdSong.collect{
                        idSongCurrent-> idSong.value=idSongCurrent
                }
            }
            launch {
                toggleState.showScreenPlayerMusic.collect{
                    isShowCurrent->
                    isShowScreenPlayerMusic.value=isShowCurrent
                }
            }
            launch {
                toggleState.isShowScreenSongsPlayList.collect{
                        isShowCurrent->
                    isShowScreenSongsPlayList.value=isShowCurrent
                }
            }
        }

    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        ModalNavigationDrawer(
            drawerState=drawerState,
            drawerContent = {
                CustomDrawerContent(drawerState,navControllerApp)
            }) {
            Scaffold(
                topBar={
                    CustomTopBarApp(idItemBottomBarClicked,onClick = {
                        coroutineScope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    })
                },
                snackbarHost = {
                    if(idSong.value.isNotEmpty()){
                        CustomSnackBarPlayMusic()
                    }
                },
                bottomBar = {
                    CustomBottomBar(itemBottomBarClick = {
                            value->updateValueIdItemBottomBar(value)
                    },idItemBottomBarClicked)

                }
            )
            {
                Box(modifier =Modifier
                    .padding(top = 80.dp, ) ){
                    NavigationMenu(idItemBottomBarClicked)

                }
            }

        }
        AnimatedVisibility(
            visible = isShowScreenPlayerMusic.value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
           MusicPlayerScreen()
        }
        AnimatedVisibility(
            visible = isShowScreenSongsPlayList.value,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            SongsPlaylist()
        }
    }


}
@Composable
fun CustomBottomBar(
    itemBottomBarClick: (Int) -> Unit,
    idItemBottomBarClicked: Int){

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = DeepBlack
            )) {
      bottomNavigationBarItemList().forEach{
          itemBottomBar->ItemBottomBar(
          idItemBottomBarClicked,
          bottomNavigationBarItem=itemBottomBar,
          itemClick ={
              value->itemBottomBarClick(value)
      })
      }
    }
}
@Composable
fun ItemBottomBar(
    idItemBottomBarClicked: Int,
    bottomNavigationBarItem: BottomNavigationBarItem,
    itemClick: (Int) -> Unit
){
    val valueColor=if(idItemBottomBarClicked==bottomNavigationBarItem.id) primaryColor else Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomIconButton(
            painter= painterResource(id = bottomNavigationBarItem.icon),
            colorIcon = valueColor,
            modifier = Modifier.padding(5.dp),
            onClick = {
                itemClick(bottomNavigationBarItem.id)
            })
        Text(
            text=bottomNavigationBarItem.content,
            color = valueColor)
    }
}
@Composable
fun ItemDrawer(
    modifier: Modifier = Modifier,
    iconSize: Dp = 30.dp,
    iconTint: Color = primaryColor,
    text: String = "",
    onClick: (() -> Unit)? = null,
    painter: Painter
) {
    Row(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = "no title",
            modifier = Modifier.size(iconSize),
            tint = iconTint
        )
        Text(
            text,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
@Composable
fun CustomTopBarApp(idItemBottomBarClicked: Int, onClick: () -> Unit) {
    val context= LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)
    val authService = AuthService(oneTapClient)
    val painter = painterResource(id = Const.KEY_LOGO)
    val iconBar2 = painterResource(id = Const.KEY_ICON_BAR_2)
    val iconBell = painterResource(id = Const.KEY_ICON_NOTIFICATION)
    val iconSetting = painterResource(id = Const.KEY_ICON_SETTING)
    val coroutineScope = rememberCoroutineScope()
    var userName by remember { mutableStateOf("Loading") }
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            authService.fetchUserName { name ->
                userName=name
            }
        }
    }
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
        when(idItemBottomBarClicked)
        {
            1->Row(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            ) {
                CustomCircleImage(painter=painter, onClick = onClick)
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(text = Const.KEY_TITLE_WELCOME_BACK)
                    Text(text = userName)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    CustomIconButton(painter=iconBar2)
                    CustomIconButton(painter=iconBell)
                    CustomIconButton(painter=iconSetting, onClick =  {

                    })
                }

            }
            2->Row(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            ){
                Image(
                    painter = painter,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(50.dp) // Set size of the Image
                        .clip(CircleShape)

                )
                Text(text="Search",
                    fontSize = 28.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold)
            }
            3->Row(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            ){
                Image(
                    painter = painter,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(50.dp) // Set size of the Image
                        .clip(CircleShape)

                )
                Text(text="Your library",
                    fontSize = 28.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold)
            }

        }

    }
}
@Composable
fun CustomDrawerContent(drawerState: DrawerState,navControllerApp :NavController) {
    val context= LocalContext.current
    val playerState= LocalMusicPlayerState.current
    val oneTapClient = Identity.getSignInClient(context)
    val authService = AuthService(oneTapClient)
    val coroutineScope = rememberCoroutineScope()
    val authState :AuthState= viewModel()
    val painter = painterResource(id = Const.KEY_LOGO)
    var userName by remember { mutableStateOf("Loading") }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            authService.fetchUserName { name ->
                userName=name
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth(0.8f)
        .fillMaxHeight()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    primaryColorBackGround.copy(alpha = 0.7f),
                    AbsoluteBlack.copy(alpha = 0.5f)
                ),
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            )
        )

    )
    {
        Column {
            Row(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp) // Set size of the Box
                        .clickable {
                            coroutineScope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, primaryColor), CircleShape)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp) // Set size of the Image
                            .clip(CircleShape)

                    )
                }
                Text(text = userName,
                    modifier = Modifier.padding(start = 16.dp))
            }
            ItemDrawer(painter=painterResource(id = Const.KEY_ICON_EXPLORE),
                text = Const.KEY_TITLE_UPLOAD_FILE,
                onClick = {
                    navControllerApp.navigate("upload_screen")
                })
            ItemDrawer(painter=painterResource(id = Const.KEY_ICON_SIGN_OUT),
                text = Const.KEY_TITLE_SIGN_OUT,
                onClick = {
//                    PreferenceManager.saveData(key=Const.KEY_ID_SONG,value="")
//                    PreferenceManager.saveData(key=Const.KEY_STATE_LISTENING,value=Const.KEY_STATE_PAUSE)
//                    playerState.togglePause()
//                    playerState.toggleUpdateIdSong()
                    authService.signOut()
                    authState.setAuthenticated(false)

                })

        }
    }
}








