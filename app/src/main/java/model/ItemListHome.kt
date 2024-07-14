package model

import com.example.music.R


data class ItemListHome(
    val nameCategory: String,
    val itemsContent: List<ItemContent>,
    val type: String
)

data class ItemContent(
    val nameContent: String,
    val imgContent: Int
)

fun createDataHome(): List<ItemListHome> {
    return listOf(
        ItemListHome(
            nameCategory = "Continue Listening",
            itemsContent = listOf(
                ItemContent(nameContent = "Coffee & Jazz", imgContent = R.drawable.img1_type1),
                ItemContent(nameContent = "Anything Goes", imgContent = R.drawable.img2_type1),
                ItemContent(nameContent = "Harry’s House", imgContent = R.drawable.img2_type3),
                ItemContent(nameContent = "RELEASED", imgContent = R.drawable.img3_type1),
                ItemContent(nameContent = "Anime OSTs", imgContent = R.drawable.img4_type1),
                ItemContent(nameContent = "Lo-Fi Beats", imgContent = R.drawable.img5_type1),
            ),
            type = "grid"
        ),
        ItemListHome(
            nameCategory = "Your Top Mixes",
            itemsContent = listOf(
                ItemContent(nameContent = "Pop Mix", imgContent = R.drawable.img1_type2),
                ItemContent(nameContent = "Chill Mix", imgContent = R.drawable.img2_type2),
            ),
            type = "list"
        ),
        ItemListHome(
            nameCategory = "Based on your recent listening",
            itemsContent = listOf(
                ItemContent(nameContent = "", imgContent = R.drawable.img2_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
            ),
            type = "list2"
        )
    )
}
fun createDataExplore(): List<ItemListHome> {
    return listOf(
        ItemListHome(
            nameCategory = "Your Top Genres",
            itemsContent = listOf(
                ItemContent(nameContent = "Coffee & Jazz", imgContent = R.drawable.img1_type1),
                ItemContent(nameContent = "Anything Goes", imgContent = R.drawable.img2_type1),
                ItemContent(nameContent = "Harry’s House", imgContent = R.drawable.img2_type3),
                ItemContent(nameContent = "RELEASED", imgContent = R.drawable.img3_type1),
                ItemContent(nameContent = "Anime OSTs", imgContent = R.drawable.img4_type1),
                ItemContent(nameContent = "Lo-Fi Beats", imgContent = R.drawable.img5_type1),
                ),
            type = "list"
        ),
        ItemListHome(
            nameCategory = "Your Top Mixes",
            itemsContent = listOf(
                ItemContent(nameContent = "Pop Mix", imgContent = R.drawable.img1_type2),
                ItemContent(nameContent = "Chill Mix", imgContent = R.drawable.img2_type2),
            ),
            type = "list"
        ),
        ItemListHome(
            nameCategory = "Browse All",
            itemsContent = listOf(
                ItemContent(nameContent = "", imgContent = R.drawable.img2_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
                ItemContent(nameContent = "", imgContent = R.drawable.img1_type3),
            ),
            type = "list"
        )
    )
}



