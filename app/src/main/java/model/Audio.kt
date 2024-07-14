package model

import config.Api
import com.google.firebase.Timestamp

data class Audio(
    val uri: String="",
    val displayName:String="",
    val id: Long = generateRandomId(),
    val artist:String="",
    val data:String="",
    val duration:Int=0,
    val title:String="",
    val urlFileAudio:String="",
    val uerIdUploadFile:String= Api.auth.currentUser.toString(),
    val viewListen:Int=0,
    val typeMusic: TypeMusic = TypeMusic.OTHER,
    val timeUpload:Timestamp=Timestamp.now(),
    val listLove:List<String> = emptyList(),
    val listRecently:List<String> = emptyList(),
)
{
    constructor() : this("","",  0, "","",0,"","","",0, TypeMusic.OTHER,Timestamp.now())
}
enum class TypeMusic(val displayName: String) {
    ROCK("Rock"),
    POP("Pop"),
    JAZZ("Jazz"),
    CLASSICAL("Classical"),
    HIPHOP("Hip-hop"),
    ELECTRONIC("Electronic"),
    OTHER("Other")
}
fun generateRandomId(length: Int = 10): Long {
    val allowedChars = "0123456789"
    val randomString = (1..length)
        .map { allowedChars.random() }
        .joinToString("")

    return randomString.toLong()
}