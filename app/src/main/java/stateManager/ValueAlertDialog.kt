package stateManager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ValueAlertDialog() : ViewModel()  {
    private val _valueNamePlayerListCreate= MutableStateFlow<String>("")
    val valueNamePlayerListCreate: MutableStateFlow<String> = _valueNamePlayerListCreate

    fun updateValueNamePlayerListCreate(value:String){
        viewModelScope.launch {
            _valueNamePlayerListCreate.emit(value)
        }
    }
}
