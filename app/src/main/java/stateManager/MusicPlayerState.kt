import android.media.AudioManager
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import config.Const
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import model.Audio
import preferenceManager.PreferenceManager
import service.ServiceFeature
import java.util.concurrent.TimeUnit

class MusicPlayerState : ViewModel() {
    private val _dataStatePlayerMusic = MutableStateFlow<String>("")
    val dataStatePlayerMusic: StateFlow<String> = _dataStatePlayerMusic
    private val _dataOptionPlayerMusic = MutableStateFlow<String>("")
    val dataOptionPlayerMusic: StateFlow<String> = _dataOptionPlayerMusic
    private val _dataIdSong = MutableStateFlow<String>("")
    val dataIdSong: StateFlow<String> = _dataIdSong
    private val _dataArtistName = MutableStateFlow<String>("")
    private val dataArtistName: StateFlow<String> = _dataArtistName
    private val _dataStateTypeList= MutableStateFlow<String>("")
    val dataStateTypeList: StateFlow<String> = _dataStateTypeList
    private val _dataSeekBar= MutableStateFlow<Float>(0f)
    val dataSeekBar :StateFlow<Float> = _dataSeekBar
    var mediaPlayer: MediaPlayer = MediaPlayer()
    private var trackIndex = 0
    private var idSong=""
    private var typeList =""
    private var optionPlayerMusic =""
    private var audioList: List<Audio> = listOf()
    val serviceFeature=ServiceFeature()
    private var timer: CountDownTimer? = null
    init {
        initializeMediaPlayer()
    }

    fun togglePause() {
       mediaPlayer.pause()
        viewModelScope.launch {
            _dataStatePlayerMusic.emit(PreferenceManager.getData(key = Const.KEY_STATE_LISTENING))
        }

    }

    fun togglePlay() {
       mediaPlayer.start()
        viewModelScope.launch {
            _dataStatePlayerMusic.emit(PreferenceManager.getData(key = Const.KEY_STATE_LISTENING))
        }
    }
    fun toggleNextSong(){
        if(( trackIndex+1) >= audioList.size)
        {
            PreferenceManager.saveData(key =Const.KEY_ID_SONG, value = audioList.first().id.toString() )
        }
        else
        {
            PreferenceManager.saveData(key =Const.KEY_ID_SONG, value = audioList[trackIndex+1].id.toString() )

        }

        toggleUpdateIdSong()
    }
    private fun toggleSequentialSong(){
        viewModelScope.launch {
            initListAudioMissEmptyList()
            toggleNextSong()
            }
        }

    private suspend fun initListAudioMissEmptyList(){
        audioList=serviceFeature.getSongsByArtistName(artistName =PreferenceManager.getData(key=Const.KEY_DATA_NAME_ARTIST))
        trackIndex = audioList.indexOfFirst { it.id == idSong.toLong() }
    }
    private fun toggleRandomSong(){
        viewModelScope.launch {
            initListAudioMissEmptyList()
            PreferenceManager.saveData(key =Const.KEY_ID_SONG, value = audioList.random().id.toString() )
            toggleUpdateIdSong()
        }

    }
    fun togglePreciousSong(){
        if( trackIndex-1 >= 0)
        {
            PreferenceManager.saveData(key =Const.KEY_ID_SONG, value = audioList[trackIndex-1].id.toString() )
        }
        else
        {
            PreferenceManager.saveData(key =Const.KEY_ID_SONG, value = audioList.last().id.toString() )
        }
        toggleUpdateIdSong()
    }
    fun toggleUpdateListType(){
        viewModelScope.launch {
            _dataStateTypeList.emit(PreferenceManager.getData(key =  Const.KEY_TYPE_LIST))
        }
    }
    fun toggleUpdateIdSong(){
        if(idSong==PreferenceManager.getData(key = Const.KEY_ID_SONG))
        {
            mediaPlayer.start()
            return
        }
        mediaPlayer.reset()
        viewModelScope.launch {
            _dataIdSong.emit(PreferenceManager.getData(key = Const.KEY_ID_SONG))
        }
    }

    fun toggleUpdateArtistName(){
        viewModelScope.launch {
            _dataArtistName.emit(PreferenceManager.getData(key = Const.KEY_DATA_NAME_ARTIST))
        }
    }
    private fun initPlayerMusic(addressMusic:String){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(addressMusic)
        mediaPlayer.prepare()
        if(_dataStatePlayerMusic.value!=Const.KEY_STATE_PAUSE)
        {
            mediaPlayer.start()
        }
    }
    fun getPlaybackSpeed(): Float {
        val playbackParams = mediaPlayer.playbackParams
        return playbackParams.speed
    }
    private fun initializeMediaPlayer() {
        if(PreferenceManager.getData(key=Const.KEY_OPTION_PLAYER_MUSIC).isEmpty()){
            PreferenceManager.saveData(key=Const.KEY_OPTION_PLAYER_MUSIC,value=Const.KEY_DATA_STATE_LISTENING_SEQUENTIAL)
        }
        _dataStatePlayerMusic.value=PreferenceManager.getData(key = Const.KEY_STATE_LISTENING)
        _dataIdSong.value = PreferenceManager.getData(key = Const.KEY_ID_SONG)
        _dataStateTypeList.value = PreferenceManager.getData(key = Const.KEY_TYPE_LIST)
        _dataArtistName.value=PreferenceManager.getData(key=Const.KEY_DATA_NAME_ARTIST)
        _dataOptionPlayerMusic.value=PreferenceManager.getData(key=Const.KEY_OPTION_PLAYER_MUSIC)
        optionPlayerMusic=_dataOptionPlayerMusic.value
        typeList= _dataStateTypeList.value
        viewModelScope.launch {
            launch {
                dataStateTypeList.collect{
                    typeListCurrent->
                    //Log.d("99999999999","Type list:$typeListCurrent")
                    when (typeListCurrent) {
                        Const.KEY_DATA_TYPE_LIST_LOVE->   typeList= Const.KEY_DATA_TYPE_LIST_LOVE
                        Const.KEY_DATA_TYPE_LIST_RECENTLY->typeList=Const.KEY_DATA_TYPE_LIST_RECENTLY
                        Const.KEY_DATA_TYPE_LIST_ARTIST ->typeList=Const.KEY_DATA_TYPE_LIST_ARTIST
                        Const.KEY_DATA_TYPE_LIST_SEARCH-> typeList= Const.KEY_DATA_TYPE_LIST_SEARCH
                        Const.KEY_DATA_TYPE_LIST_DOWNLOAD->typeList=Const.KEY_DATA_TYPE_LIST_DOWNLOAD
                    }
                }
            }
            launch {
                dataIdSong.collect { idSongCurrent ->
                    Log.d("99999999999","ID song:$idSongCurrent")
                    idSong=idSongCurrent
                    if (idSongCurrent.isNotEmpty()) {
                        if(audioList.isNotEmpty()) {
                            trackIndex = audioList.indexOfFirst { it.id == idSongCurrent.toLong() }

                        }
                        else
                        {
                            audioList=serviceFeature.getSongsByArtistName(artistName =PreferenceManager.getData(key=Const.KEY_DATA_NAME_ARTIST))
                            trackIndex = audioList.indexOfFirst { it.id == idSongCurrent.toLong() }
                            Log.d("99999999999","List Audio:$audioList")
                        }
                        initPlayerMusic(audioList[trackIndex].urlFileAudio)
                        Log.d("99999999999","play successful")

                    }
                }
            }
            launch {
                dataArtistName.collect { artistNameCurrent->
                    Log.d("99999999999","Artist name:$artistNameCurrent")
                    Log.d("99999999999","Type list:$typeList")
                    if(typeList==Const.KEY_DATA_TYPE_LIST_ARTIST) {
                        audioList=serviceFeature.getSongsByArtistName(artistName = artistNameCurrent)
                    }
                }
            }
            launch {
                while (true) {
                    if (mediaPlayer.isPlaying) {
                        val currentPosition = mediaPlayer.currentPosition.toFloat()
                        val duration = mediaPlayer.duration.toFloat()
                        if (duration > 0) {
                            updateSeekBarValue(currentPosition / duration)
                        }
                    }
                    delay(1000L) // Cập nhật mỗi giây
                }
            }
            launch {

                dataOptionPlayerMusic.collect{optionPlayerMusicCurrent->
                    Log.d("99999999999","Option player music:$optionPlayerMusicCurrent")
                    processCompletionSong(optionPlayerMusicCurrent)
                }
            }

        }

    }
    private fun updateSeekBarValue(value:Float){
        viewModelScope.launch {
            _dataSeekBar.emit(value)
        }
    }
    fun toggleUpdateOptionPlayerMusic(){
        viewModelScope.launch {
            _dataOptionPlayerMusic.emit(PreferenceManager.getData(key = Const.KEY_OPTION_PLAYER_MUSIC))
        }
    }
    private fun processCompletionSong(optionPlayerMusic:String){
        mediaPlayer.setOnCompletionListener {
            updateSeekBarValue(value=0f)
            when(optionPlayerMusic)
            {
                Const.KEY_DATA_STATE_LISTENING_REPEAT->
                {
                   mediaPlayer.start()
                }
                Const.KEY_DATA_STATE_LISTENING_SEQUENTIAL->{
                    toggleSequentialSong()
                }
                Const.KEY_DATA_STATE_LISTENING_RANDOM->{
                    toggleRandomSong()
                }
            }


        }
    }
     fun startAutoPauseTimer(minutes:Int) {
        timer?.cancel()
        timer = object : CountDownTimer(TimeUnit.MINUTES.toMillis(minutes.toLong()), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Đang đếm ngược, có thể làm gì đó nếu cần thiết
            }

            override fun onFinish() {
                // Khi đếm ngược kết thúc sau 60 phút
                   Log.d("99999999999","Sleep success")
                    mediaPlayer.pause()
                    PreferenceManager.saveData(key = Const.KEY_STATE_LISTENING, value = Const.KEY_STATE_PAUSE)
                    togglePause()
                    timer?.cancel()

            }
        }
        timer?.start()
    }


}




