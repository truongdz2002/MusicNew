package model

import com.google.firebase.Timestamp

data class Playlist(
    val id:Long=generateRandomId(),
    val uid:String,
    val namePlaylist:String,
    val listIdAudio:List<Long>,
    val valueDate:Timestamp
)
{
    constructor():this(0,"","", emptyList(),Timestamp.now())
}
