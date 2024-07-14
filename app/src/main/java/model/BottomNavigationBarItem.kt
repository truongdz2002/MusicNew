package model

import config.Const

data class BottomNavigationBarItem(
    val id:Int,
    val content:String,
    val icon:Int,
    val badgeCount:Int=0
)
fun bottomNavigationBarItemList(): List<BottomNavigationBarItem> {
    val  bottomNavigationBarItemList= listOf(
        BottomNavigationBarItem(id = 1, content = "Home",icon=Const.KEY_ICON_HOME),
        BottomNavigationBarItem(id = 2, content = "Explore",icon=Const.KEY_ICON_SEARCH),
        BottomNavigationBarItem(id = 3, content = "Library",icon=Const.KEY_ICON_EXPLORE),
        )
    return bottomNavigationBarItemList
}
