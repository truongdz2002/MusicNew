package mainmenu.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.ItemContent
import model.createDataHome
import theme.colorItemHome

@Composable
fun HomeScreen() {

    Column(
        Modifier.fillMaxSize()
    ) {
        CustomListContentHome()

    }
}

@Composable
fun CustomListContentHome(){


            createDataHome().forEach { itemList->
                Text(text = itemList.nameCategory, modifier = Modifier.padding(16.dp))
                when (itemList.type) {
                    "grid" -> GridDisplay(items = itemList.itemsContent)
                    "list" -> ListDisplay(items = itemList.itemsContent)
                    "list2" -> List2Display(items = itemList.itemsContent)
                }
            }
}
@Composable
fun GridDisplay(items: List<ItemContent>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        items(items.size) { index ->
            ItemGridView(itemContent = items[index])
        }
    }
}

@Composable
fun ItemGridView(itemContent: ItemContent) {
    val painter= painterResource(id = itemContent.imgContent)
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(color = colorItemHome.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp)),

    )
    {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painter ,
                contentDescription ="",
                modifier = Modifier.size(50.dp))
            Text(text = itemContent.nameContent,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White)
            )

        }
    }

}

@Composable
fun ListDisplay(items: List<ItemContent>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) { index ->
            ItemListView(itemContent = items[index])
        }
    }
}

@Composable
fun List2Display(items: List<ItemContent>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) { index ->
            ItemListView2(itemContent = items[index])
        }
    }
}
@Composable
fun ItemListView(itemContent: ItemContent) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = itemContent.imgContent),
            contentDescription = itemContent.nameContent,
            modifier = Modifier
                .size(150.dp)
        )
        Text(
            text = itemContent.nameContent,
            modifier = Modifier
                .padding(8.dp) // Khoảng cách giữa chữ và biểu tượng
                .align(Alignment.TopStart), // Đặt chữ ở góc trên bên trái của Box
            style = TextStyle(color = Color.White) // Optional: Thiết lập màu chữ
        )
    }
}
@Composable
fun ItemListView2(itemContent: ItemContent) {
        Image(
            painter = painterResource(id = itemContent.imgContent),
            contentDescription = itemContent.nameContent,
            modifier = Modifier
                .size(width = 150.dp, height = 300.dp)
        )
}



