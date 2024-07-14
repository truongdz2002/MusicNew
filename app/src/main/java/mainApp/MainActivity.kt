package mainApp

import MusicPlayerState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.firebase.FirebaseApp
import preferenceManager.PreferenceManager
import navigation.NavigationApp
import stateManager.LocalMusicPlayerState
import stateManager.LocalToggle
import stateManager.LocalValueDialog
import stateManager.ToggleState
import stateManager.ValueAlertDialog
import theme.MusicTheme
import theme.primaryColorBackGround

class MainActivity : ComponentActivity() {
    private val musicPlayerState: MusicPlayerState by viewModels()
    private val toggleState:ToggleState by viewModels()
    private val valueDialog:ValueAlertDialog by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.init(this)

        // Configure window insets and behavior
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Enable edge-to-edge display
        enableEdgeToEdge()
        // Set content using Compose
        setContent {
            MusicTheme {
                Surface(
                    color = primaryColorBackGround
                ) {
                    CompositionLocalProvider(
                        LocalMusicPlayerState provides musicPlayerState,
                        LocalToggle provides toggleState,
                        LocalValueDialog provides valueDialog
                    ) {
                        NavigationApp()
                    }

                    // Load your navigation or main UI component here
                }
            }
        }
    }
}
