import androidx.lifecycle.ViewModel
import config.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthState : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(Api.auth.currentUser!=null)
    //private val _isAuthenticated = MutableStateFlow(true)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    fun setAuthenticated(value: Boolean) {
        _isAuthenticated.value = value
    }
}
