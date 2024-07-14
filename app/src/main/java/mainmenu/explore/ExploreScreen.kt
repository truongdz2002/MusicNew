package mainmenu.explore

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.CustomAlertDialogConfirm
import config.Const
import mainmenu.home.GridDisplay
import mainmenu.home.List2Display
import mainmenu.home.ListDisplay
import model.createDataExplore
import stateManager.LocalToggle

@Composable
fun ExploreScreen(){
    val toggleState= LocalToggle.current
    val isShowAlertDialog= remember {
        mutableStateOf(value = false)
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomListContentExplore()
        AnimatedVisibility(
            visible = isShowAlertDialog.value,
            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
        ) {
            CustomAlertDialogConfirm(
                statusMessage = Const.KEY_CONTENT_DIALOG_CONFIRM_DOWNLOAD_FILE,
                onConfirm = {
                    toggleState.updateToggleAlertDialog(value = false)
                    toggleState.updateToggleConfirm(value = true)
                },
                onCancel = {
                    toggleState.updateToggleAlertDialog(value = false)
                })
        }
    }
}

@Composable
fun CustomListContentExplore(){
    createDataExplore().forEach { itemList->
        Text(text = itemList.nameCategory, modifier = Modifier.padding(16.dp))
        when (itemList.type) {
            "grid" -> GridDisplay(items = itemList.itemsContent)
            "list" -> ListDisplay(items = itemList.itemsContent)
            "list2" -> List2Display(items = itemList.itemsContent)
        }
    }
}