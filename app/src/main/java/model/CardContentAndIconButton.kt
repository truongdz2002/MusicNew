package model

import com.example.music.R
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
        CardContentAndIconButton(id = 5, content = Const.KEY_TITLE_MOON,icon= Const.KEY_ICON_MOON),

    )
    return listCardContentAndIconButton
}
val drawableList = listOf(
    R.drawable.img_singer1,
    R.drawable.img_singer2,
    R.drawable.img_singer3,
    R.drawable.img_singer5,
    R.drawable.img_singer1,
)
val drawableListIMAGE = listOf(
    R.drawable.img1_type1,
    R.drawable.img1_type2,
    R.drawable.img1_type3,
    R.drawable.img2_type1,
    R.drawable.img2_type2,
    R.drawable.img2_type3,
    R.drawable.img3_type1,
    R.drawable.img4_type1,
)

