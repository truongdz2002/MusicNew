package config

import com.example.music.R


object Const {

    // Title
    const val KEY_ID_SONG :String  = "id Song" // Song id
    const val KEY_LOADING :String  = "Loading" //Title show dialog processing
    const val KEY_SLOT_ICON :String  = "start" //Slot icon in title content recently
    const val KEY_TITLE_LOGIN:String  = "Let’s get you in" // Title login screen
    const val KEY_TITLE_LYRICS:String  = "Lyrics" // Title Lyrics
    const val KEY_TITLE_OPTION_FOR_YOU:String  = "Option for you" // Title Lyrics
    const val KEY_CONTENT_BUTTON_GOOGLE:String  = "Continue with Google" // Content button google login
    const val KEY_CONTENT_BUTTON_FACEBOOK:String  = "Continue with Facebook" // Content button facebook login
    const val KEY_CONTENT_BUTTON_APPLE:String  = "Continue with Apple" // Content button apple login
    const val KEY_CONTENT_BUTTON_WITH_PASSWORD:String  = "Log in with a password" // Content button  login with password
    const val KEY_TITLE_CONTENT_NO_ACCOUNT:String  = "Don’t have an account?" // Content login not account
    const val KEY_TITLE_CONTENT_SING_UP:String  = "Sign Up" // Content register account
    const val KEY_TITLE_WELCOME_BACK:String  = "Welcome back !" // Content menu screen
    const val KEY_TITLE_APP_BAR_UPLOAD_SCREEN:String  = "Upload music" // Title appbar screen
    const val KEY_TITLE_DISPLAY_NAME_SONG_UPLOAD_FILE:String  = "Display name song" // Title song name Upload file
    const val KEY_TITLE_ARTIST_NAME_UPLOAD_FILE:String  = "Artist name" // Title singer name Upload file
    const val KEY_CONTENT_BUTTON_UPLOAD_FILE:String  = "Upload file" // Content button Upload file
    const val KEY_CONTENT_DIALOG_PROCESSING_UPLOAD_FILE:String  = "Processing upload" // Title dialog when upload file
    const val KEY_CONTENT_DIALOG_PROCESSING_DOWNLOAD_FILE:String  = "Processing download" // Title dialog when upload file
    const val KEY_CONTENT_DIALOG_CONFIRM_DOWNLOAD_FILE:String  = "Do you want download this file ?" // Title dialog when upload file
    const val KEY_TITLE_ADD:String  = "Add New Playlist"// Title add new song playlist
    const val KEY_TITLE_ADD_TO_PLAY_LIST:String  = "Add To Playlist"
    const val KEY_TITLE_ADD_TO_QUEUE:String  = "Add To Queue"
    const val KEY_TITLE_REMOVE_FOR_PLAYLIST:String  = "Remove from playlist"
    const val KEY_TITLE_DOWNLOAD:String  = "Download"
    const val KEY_TITLE_RECENTLY_PLAYER:String  = "Recently played"
    const val KEY_TITLE_SORT_BY:String  = "Recently played"

    const val KEY_TITLE_STOP_AUDIO:String  = "Stop audio in"
    const val KEY_TITLE_END_OF_TRACK:String  = "End of Track"
    const val KEY_TITLE_UPLOAD_FILE:String  = "Upload file"
    const val KEY_TITLE_SIGN_OUT:String  = "Sign Out"

    const val KEY_TITLE_SHARE:String  = "Share"
    const val KEY_TITLE_MOON:String  = "Sleep Timer"
    const val KEY_TITLE_RECENTLY:String  = "Your Liked Songs"// Title recently
    const val KEY_TYPE_LIST:String  = "Type list"
    const val KEY_DATA_TYPE_LIST_LOVE:String  = "List Love"
    const val KEY_DATA_TYPE_LIST_RECENTLY:String  = "List recently"
    const val KEY_DATA_TYPE_LIST_ARTIST:String  = "List artist"
    const val KEY_DATA_TYPE_LIST_SEARCH:String  = "List search"
    const val KEY_DATA_TYPE_LIST_DOWNLOAD:String  = "List download"
    const val KEY_DATA_NAME_ARTIST:String  = "Name artist"
    const val KEY_STATE_LISTENING:String  = "State listening"
    const val KEY_OPTION_PLAYER_MUSIC:String  = "Option player music"
    const val KEY_DATA_STATE_LISTENING_SEQUENTIAL:String  = "Sequential"
    const val KEY_DATA_STATE_LISTENING_REPEAT:String  = "Repeat"
    const val KEY_DATA_STATE_LISTENING_RANDOM:String  = "Random"
    const val KEY_STATE_PAUSE:String  = "PAUSE"
    const val KEY_STATE_PLAY:String  = "PLAY"




    // Image and Icon
    val KEY_LOGO :Int  = R.drawable.ic_logo  // icon logo app
    val KEY_ICON_ADD :Int  = R.drawable.ic_add  // icon logo add
    val KEY_ICON_APPLE :Int  = R.drawable.ic_apple  // icon logo apple
    val KEY_ICON_ARROW_DOWN :Int  = R.drawable.ic_arrow_down // icon logo apple
    val KEY_ICON_BACK :Int  = R.drawable.ic_back // icon logo apple
    val KEY_ICON_BAR_2 :Int  = R.drawable.ic_bar_2 // icon logo apple
    val KEY_ICON_EXPLORE :Int  = R.drawable.ic_explore // icon logo apple
    val KEY_ICON_FACEBOOK :Int  = R.drawable.ic_facebook // icon logo apple
    val KEY_ICON_GOOGLE :Int  = R.drawable.ic_google // icon logo apple
    val KEY_ICON_HOME :Int  = R.drawable.ic_home // icon logo apple
    val KEY_ICON_LOVE :Int  = R.drawable.ic_love // icon logo apple
    val KEY_ICON_NOTIFICATION :Int  = R.drawable.ic_notification // icon logo apple
    val KEY_ICON_OPTION :Int  = R.drawable.ic_option // icon logo apple
    val KEY_ICON_SEARCH :Int  = R.drawable.ic_search // icon logo apple
    val KEY_ICON_SETTING :Int  = R.drawable.ic_setting // icon logo apple
    val KEY_ICON_SIGN_OUT :Int  = R.drawable.ic_sigout // icon logo apple
    val KEY_ICON_SORT :Int  = R.drawable.ic_sort // icon logo apple
    val KEY_ICON_SUCCESSFUL :Int  = R.drawable.ic_successful // icon logo apple
    val KEY_ICON_PRECIOUS :Int  = R.drawable.ic_precious // back song precious
    val KEY_ICON_PLAY :Int  = R.drawable.ic_play // play song
    val KEY_ICON_NEXT :Int  = R.drawable.ic_next // next song
    val KEY_ICON_PAUSE :Int  = R.drawable.ic_pause // pause song
    val KEY_ICON_EQUALIZER :Int  = R.drawable.ic_equalizer
    val KEY_ICON_DOWNLOAD :Int  = R.drawable.ic_download
    val KEY_ICON_REPEAT :Int  = R.drawable.ic_repeat
    val KEY_ICON_RANDOM :Int  = R.drawable.ic_random
    val KEY_ICON_SEQUENTIAL :Int  = R.drawable.ic_sequential
    val KEY_ICON_SHARE :Int  = R.drawable.ic_share
    val KEY_ICON_MUSIC_NOTE :Int  = R.drawable.ic_music_note
    val KEY_ICON_HAMBURGER_MENU :Int  = R.drawable.ic_hamburger_menu
    val KEY_ICON_REMOVE :Int  = R.drawable.ic_remove
    val KEY_ICON_MOON :Int  = R.drawable.ic_moon


    //Route name
    const  val KEY_ROUTE_HOME_NAME :String  = "home_screen"
    const  val KEY_ROUTE_LIBRARY_NAME :String  = "library_screen"
    const  val KEY_ROUTE_SPLASH_NAME :String  = "splash_screen"
    const  val KEY_ROUTE_EXPLORE_NAME :String  = "explore_screen"
    const  val KEY_ROUTE_UPLOAD_FILE_NAME :String  = "upload_screen"
    const  val KEY_ROUTE_NAVIGATE_NAME :String  = "navigate_screen"

}