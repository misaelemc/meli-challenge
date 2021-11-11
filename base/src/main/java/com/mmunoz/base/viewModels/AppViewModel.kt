package com.mmunoz.base.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    private val _data = MutableLiveData<Actions>()
    val data: LiveData<Actions> = _data

    fun setAction(action: Actions) {
        _data.value = action
    }

    sealed class Actions {

        object ClearSearch: Actions()
        data class OnSubCategorySelected(val id: String, val name: String) : Actions()
    }
}