package service

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import config.Api
import model.TypeMusic
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import config.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import model.Audio
import model.Playlist
import model.generateRandomId
import preferenceManager.PreferenceManager
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ServiceFeature {
    suspend fun getMusicList(): List<Audio> {
        return try {
            val querySnapshot = Api.firestore.collection("musics").get().await()
            val musicList = mutableListOf<Audio>()

            for (document in querySnapshot.documents) {
                val audio = document.toObject(Audio::class.java)
                if (audio != null) {
                    musicList.add(audio)
                    Log.d("getMusicList", "Music added: $audio")
                } else {
                    Log.d("getMusicList", "Music is null for document: ${document.id}")
                }
            }

            Log.d("getMusicList", "Total music items retrieved: ${musicList.size}")
            musicList
        } catch (e: Exception) {
            Log.e("getMusicList", "Error retrieving music list", e)
            emptyList()
        }
    }

    suspend fun downloadFileFromUrl(
        context: Context,
        fileUrl: String?,
        fileName: String,
        onProgress: (Int) -> Unit,
        onSuccess: (File) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            if (fileUrl == null) {
                throw IllegalArgumentException("File URL cannot be null")
            }

            withContext(Dispatchers.IO) {
                Log.d("FileDownload", "Starting download from URL: $fileUrl")

                val url = URL(fileUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val totalSize = connection.contentLength
                if (totalSize <= 0) {
                    throw IOException("Failed to get the file size")
                }

                val externalStorageDirectory =
                    context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        ?: throw IOException("Failed to get external storage directory")

                val file = File(externalStorageDirectory, "${fileName}.mp3")

                val inputStream = BufferedInputStream(connection.inputStream, 8192)
                val outputStream = FileOutputStream(file)

                val data = ByteArray(1024)
                var count: Int
                var downloadedSize = 0

                while (inputStream.read(data).also { count = it } != -1) {
                    outputStream.write(data, 0, count)
                    downloadedSize += count
                    val progress = (downloadedSize * 100 / totalSize)
                    withContext(Dispatchers.Main) {
                        onProgress(progress)
                    }
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                withContext(Dispatchers.Main) {
                    onSuccess(file)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onFailure(e)
            }
        }
    }

    fun getAllMp3Files(context: Context) {
        val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        externalStorageDir?.walkTopDown()?.forEach {
            if (it.isFile && it.extension.equals("mp3", ignoreCase = true)) {
                Log.d("MP3File", it.name)
            }
        }
    }

    fun shareLink(context: Context, link: String, title: String = "Share audio") {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)

        // Tạo một Intent để hiển thị các ứng dụng có thể xử lý việc chia sẻ
        val chooserIntent = Intent.createChooser(shareIntent, title)

        // Kiểm tra xem có ứng dụng nào có thể xử lý Intent này hay không
        if (shareIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(chooserIntent)
        } else {
            // Xử lý trường hợp không tìm thấy ứng dụng để chia sẻ
            Toast.makeText(context, "No suitable application available", Toast.LENGTH_SHORT).show()
        }
    }


    fun uploadFileToFirebase(
        artist: String,
        displayName: String,
        uriFileAudio: Uri,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit,
        onProcess: (Int) -> Unit
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference
        val musicRef: StorageReference = storageRef.child("musics/${uriFileAudio.lastPathSegment}")

        val uploadTask = musicRef.putFile(uriFileAudio)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            musicRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                Log.d("FirebaseUpload", "Upload successful. Download URL: $downloadUrl")
                addDataMusicUpload(
                    displayName = displayName,
                    artist = artist,
                    urlFileAudio = downloadUrl,
                    typeMusic = TypeMusic.POP,
                )
                onSuccess()
            }.addOnFailureListener { exception ->
                Log.d(
                    "FirebaseUpload",
                    "Failed to get download URL after upload: ${exception.message}"
                )
                onFailure(exception)
            }
        }.addOnFailureListener { exception ->
            Log.d("FirebaseUpload", "Upload failed: ${exception.message}")
            onFailure(exception)
        }.addOnProgressListener { taskSnapshot ->
            val progressPercent =
                (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            onProcess(progressPercent)
        }
    }

    suspend fun getUniqueArtistNames(): List<String> {
        return try {
            val querySnapshot = Api.firestore.collection("musics").get().await()
            val artists = mutableSetOf<String>()  // Using a HashSet to store unique singer names
            for (document in querySnapshot.documents) {
                val audio = document.toObject<Audio>()
                audio?.let { artists.add(it.artist) }
                Log.d("LIST ARTIST NAME", artists.toString())
            }
            artists.toList()

        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSongsByArtistName(artistName: String): List<Audio> {
        return try {
            val querySnapshot =
                Api.firestore.collection("musics").whereEqualTo("artist", artistName).get().await()
            val audioList = mutableListOf<Audio>()
            for (document in querySnapshot.documents) {
                val audio = document.toObject(Audio::class.java)
                if (audio != null) {
                    audioList.add(audio)
                    Log.d("getSongsBySinger", "Music added: $audio")
                } else {
                    Log.d("getSongsBySinger", "Music is null for document: ${document.id}")
                }
            }
            Log.d("getSongsBySinger", "Total music items retrieved: ${audioList.size}")
            audioList
        } catch (e: Exception) {
            Log.e("getSongsBySinger", "Error retrieving music list", e)
            emptyList()
        }
    }

    suspend fun getMusicByIdSong(id: Long): Audio? {
        return try {
            val querySnapshot =
                Api.firestore.collection("musics").whereEqualTo("id", id).get().await()
            for (document in querySnapshot.documents) {
                val audio = document.toObject(Audio::class.java)
                if (audio != null) {
                    //Log.d("getMusicByIdSong", "Music retrieved: $audio")
                    return audio
                } else {
                    Log.d("getMusicByIdSong", "Music is null for document: ${document.id}")
                }
            }
            null
        } catch (e: Exception) {
            Log.e("getMusicByIdSong", "Error retrieving music", e)
            null
        }
    }

    suspend fun getSongsByNameSongAndNameArtist(value: String): List<Audio> {
        return try {
            val querySnapshot1 =
                Api.firestore.collection("musics").whereEqualTo("artist", value).get().await()
            val querySnapshot2 =
                Api.firestore.collection("musics").whereEqualTo("displayName", value).get().await()
            val querySnapshot: QuerySnapshot = if (querySnapshot1.documents.isEmpty()) {
                querySnapshot2
            } else {
                querySnapshot1
            }
            val audioList = mutableListOf<Audio>()
            for (document in querySnapshot.documents) {
                val audio = document.toObject(Audio::class.java)
                if (audio != null) {
                    audioList.add(audio)
                    Log.d("getSongsBySinger", "Music added: $audio")
                } else {
                    Log.d("getSongsBySinger", "Music is null for document: ${document.id}")
                }
            }
            Log.d("getSongsBySinger", "Total music items retrieved: ${audioList.size}")
            audioList
        } catch (e: Exception) {
            Log.e("getSongsBySinger", "Error retrieving music list", e)
            emptyList()
        }
    }

    suspend fun addPlaylistToFirestore(
        namePlaylist: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val playlist = Playlist(
                id = generateRandomId(),
                uid = Api.auth.currentUser?.uid ?: "",
                namePlaylist = namePlaylist,
                listIdAudio = emptyList(),
                valueDate = Timestamp.now()
            )

            // Sử dụng Firestore instance
            Api.firestore.collection("playlists")
                .add(playlist)
                .addOnSuccessListener { documentReference ->
                    // Gọi callback onSuccess khi thành công
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    // Gọi callback onFailure khi thất bại
                    onFailure(e)
                }
        } catch (e: Exception) {
            // Gọi callback onFailure khi có lỗi
            onFailure(e)
        }
    }
    suspend fun getPlaylistsFromFirestore(
    ): List<Playlist> {
        return try {
            val querySnapshot = Api.firestore
                .collection("playlists")
                .whereEqualTo("uid", Api.auth.currentUser?.uid ?: "" )
                .get()
                .await()

            val playlistList = mutableListOf<Playlist>()
            for (document in querySnapshot.documents) {
                val playlist = document.toObject(Playlist::class.java)
                if (playlist != null) {
                    playlistList.add(playlist)
                }
            }
            Log.d("TestPlaylist",playlistList.toString())
            playlistList
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun updatePlaylistAddAudio(
        context:Context,
        playlistId: Long,
        audioId: Long,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val playlistsRef = Api.firestore.collection("playlists")
            val querySnapshot = playlistsRef.whereEqualTo("id", playlistId).get().await()

            if (!querySnapshot.isEmpty) {
                val playlistDoc = querySnapshot.documents.first()
                val playlist = playlistDoc.toObject<Playlist>()

                if (playlist != null) {
                    // Kiểm tra xem audioId đã có trong listIdAudio chưa
                    if (!playlist.listIdAudio.contains(audioId)) {
                        val updatedListIdAudio = playlist.listIdAudio.toMutableList().apply {
                            add(audioId)
                        }

                        playlistsRef.document(playlistDoc.id)
                            .update("listIdAudio", updatedListIdAudio)
                            .await()

                        onSuccess()
                    } else {

                        Toast.makeText(
                            context,
                            "Audio already exists in the playlist!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    onFailure(Exception("Playlist not found"))
                }
            } else {
                onFailure(Exception("Playlist not found"))
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }
    suspend fun getAudiosForPlaylist(playlistId: Long): List<Audio> {
        val playlistRef = Api.firestore.collection("playlists").whereEqualTo("id", playlistId)
        val querySnapshot = playlistRef.get().await()

        if (!querySnapshot.isEmpty) {
            val playlistDoc = querySnapshot.documents.first()
            val playlist = playlistDoc.toObject<Playlist>()

            if (playlist != null) {
                val audioIds = playlist.listIdAudio // Danh sách id audio từ playlist
                // Gọi hàm để lấy danh sách Audio từ danh sách id
                Log.d("testDataPlaylist",getAudiosByIds(audioIds).toString())
                return getAudiosByIds(audioIds)
            } else {
                throw Exception("Playlist not found")
            }
        } else {
            throw Exception("Playlist not found")
        }
    }
    private suspend fun getAudiosByIds(audioIds: List<Long>): List<Audio> {
        val audios = mutableListOf<Audio>()
        try {
            for (audioId in audioIds) {
                val querySnapshot = Api.firestore.collection("musics")
                    .whereEqualTo("id", audioId)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    val audio = document.toObject<Audio>()
                    audio?.let {
                        audios.add(it)
                    }
                }
            }
        } catch (e: Exception) {
            // Xử lý ngoại lệ nếu có lỗi
            e.printStackTrace()
        }
        return audios
    }
    suspend fun getPlaylistById(): Playlist? {
        return try {
            val querySnapshot = Api.firestore.collection("playlists")
                .whereEqualTo("id", PreferenceManager.getData(key=Const.KEY_ID_PLAY_LIST).toLong())
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.toObject<Playlist>()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    suspend fun updatePlaylistTimestamp(
    ) {
        try {

            val playlistQuerySnapshot = Api.firestore.collection("playlists").whereEqualTo("id",PreferenceManager.getData(key=Const.KEY_ID_PLAY_LIST).toLong() ).get().await()

            if (!playlistQuerySnapshot.isEmpty) {
                val playlistDocument = playlistQuerySnapshot.documents[0]
                val playlistRef = playlistDocument.reference

                playlistRef.update("valueDate", Timestamp.now()).await()

            } else {

            }
        } catch (e: Exception) {

        }
    }
    suspend fun getPlaylistsSortedByTimestamp(): List<Playlist> {
        return try {
            val querySnapshot = Api.firestore.collection("playlists")
                //.whereEqualTo("uid",Api.auth.currentUser?.uid ?:"")
                .orderBy("valueDate", Query.Direction.DESCENDING)
                .get()
                .await()

            val playlists = mutableListOf<Playlist>()
            for (document in querySnapshot.documents) {
                val playlist = document.toObject(Playlist::class.java)
                playlist?.let {
                    playlists.add(it)
                }
            }

            playlists
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("testDataPlaylist",e.toString())
            emptyList()
        }
    }

    }

fun addDataMusicUpload(
    displayName: String,
    artist: String,
    urlFileAudio: String,
    typeMusic: TypeMusic,
){

   val audio=Audio(
       displayName = displayName,
       artist = artist,
       urlFileAudio =urlFileAudio,
       typeMusic = typeMusic
   )
    Api.firestore.collection("musics")
        .add(audio)
        .addOnSuccessListener {

        }
        .addOnFailureListener { e ->

        }

}



