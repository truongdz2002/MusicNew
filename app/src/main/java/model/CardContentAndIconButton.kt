package model

import config.Const

data class CardContentAndIconButton(
    val id:Int,
    val content:String,
    val icon:Int,
)
fun listCardContentAndIconButton(): List<CardContentAndIconButton> {
    val  listCardContentAndIconButton= listOf(
        CardContentAndIconButton(id = 1, content = Const.KEY_TITLE_ADD_TO_PLAY_LIST,icon= Const.KEY_ICON_MUSIC_NOTE),
        CardContentAndIconButton(id = 2, content = Const.KEY_TITLE_ADD_TO_QUEUE,icon= Const.KEY_ICON_HAMBURGER_MENU),
        CardContentAndIconButton(id = 3, content = Const.KEY_TITLE_REMOVE_FOR_PLAYLIST,icon= Const.KEY_ICON_REMOVE),
        CardContentAndIconButton(id = 4, content = Const.KEY_TITLE_SHARE,icon= Const.KEY_ICON_SHARE),
        CardContentAndIconButton(id = 4, content = Const.KEY_TITLE_MOON,icon= Const.KEY_ICON_MOON),

    )
    return listCardContentAndIconButton
}

