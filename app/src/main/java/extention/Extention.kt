package extention

import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object Extension {
    fun hideSystemUI(activity: ComponentActivity) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        val windowInsetsController = WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun showSystemUI(activity: ComponentActivity) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, true)
        val controller = WindowInsetsControllerCompat(activity.window, activity.window.decorView)
        controller.show(WindowInsetsCompat.Type.systemBars())
    }

}