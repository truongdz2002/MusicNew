package model

import config.Const

data class ButtonLibrary(
    val idButton:Int,
    val content:String,
    val route:String
)
fun createButtonLibraryList(): List<ButtonLibrary> {
    val buttonList = listOf(
        ButtonLibrary(idButton = 1, content = "Folders",route="folder_screen"),
        ButtonLibrary(idButton = 2, content = "Playlists",route="folder_screen"),
        ButtonLibrary(idButton = 3, content = "Artists",route="artists_screen"),
        ButtonLibrary(idButton = 4, content = "Albums",route="folder_screen"),
        ButtonLibrary(idButton = 5, content = "Podcasts & Shows",route="folder_screen"),

    )
    return buttonList
}



