package stateManager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ToggleState: ViewModel() {
    private val _isShowOptionSleep= MutableStateFlow(false)
    val isShowOptionSleep: StateFlow<Boolean> =  _isShowOptionSleep
    private val _isShowOptionForUser= MutableStateFlow(false)
    val isShowOptionForUser: StateFlow<Boolean> =  _isShowOptionForUser
    private val _showScreenPlayerMusic= MutableStateFlow<Boolean>(false)
    val showScreenPlayerMusic: StateFlow<Boolean> = _showScreenPlayerMusic
    private val _showSnackBarPlayerMusic= MutableStateFlow<Boolean>(false)
    val showSnackBarPlayerMusic: StateFlow<Boolean> = _showSnackBarPlayerMusic
    private val _showLoading= MutableStateFlow<Boolean>(false)
    val showLoading: StateFlow<Boolean> = _showLoading
    private val _showAlertDialog= MutableStateFlow<Boolean>(false)
    val showAlertDialog: StateFlow<Boolean> = _showAlertDialog
    private val _isConfirm= MutableStateFlow<Boolean>(false)
    val isConfirm: StateFlow<Boolean> = _isConfirm
    private val _isSuccess= MutableStateFlow<Boolean>(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    init {
        initToggleState()
    }

    private fun initToggleState() {
        _isShowOptionSleep.value=false
        _isShowOptionForUser.value=false
        _showLoading.value=false
        _showAlertDialog.value=false
    }
    fun updateToggleShowOptionSleep(value:Boolean){
        viewModelScope.launch {
            _isShowOptionSleep.emit(value)
        }
    }

    fun updateToggleShowLoading(value:Boolean){
        viewModelScope.launch {
            _showLoading.emit(value)
        }
    }

    fun updateIsSuccess(value:Boolean){
        viewModelScope.launch {
            _isSuccess.emit(value)
        }
    }
    fun updateToggleConfirm(value:Boolean){
        viewModelScope.launch {
            _isConfirm.emit(value)
        }
    }
    fun updateToggleAlertDialog(value:Boolean){
        viewModelScope.launch {
            _showAlertDialog.emit(value)
        }
    }
    fun updateToggleShowOptionForUser(value:Boolean){
        viewModelScope.launch {
            _isShowOptionForUser.emit(value)
        }
    }
    fun toggleUpdateShowScreenPlayerMusic(value:Boolean) {
        viewModelScope.launch {
            _showScreenPlayerMusic.emit(value)
        }

    }
    fun toggleUpdateShowSnackBarPlayerMusic(value:Boolean) {
        viewModelScope.launch {
            _showSnackBarPlayerMusic.emit(value)
        }

    }
}