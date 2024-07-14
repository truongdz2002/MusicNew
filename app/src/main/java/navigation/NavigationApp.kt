package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mainApp.NaviGateScreen
import mainApp.SplashScreen
import config.Const
import mainmenu.UploadFileScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Const.KEY_ROUTE_SPLASH_NAME) {
        composable(Const.KEY_ROUTE_SPLASH_NAME) {
            SplashScreen(navController)
        }
        composable(Const.KEY_ROUTE_NAVIGATE_NAME) {
            NaviGateScreen(navController)
        }
        composable(Const.KEY_ROUTE_UPLOAD_FILE_NAME) {
            UploadFileScreen(navController)
        }
    }

}