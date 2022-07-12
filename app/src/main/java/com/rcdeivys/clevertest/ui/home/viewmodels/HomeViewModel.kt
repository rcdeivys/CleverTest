package com.rcdeivys.clevertest.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcdeivys.clevertest.common.logError
import com.rcdeivys.clevertest.repositories.CleverRepository
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val repository: CleverRepository
) : ViewModel() {

    private val action = MutableStateFlow<HomeActions>(HomeActions.Empty)
    fun getAction(): StateFlow<HomeActions> = action

    fun getCharacter() {
        viewModelScope.launch {
            repository.getCharacter().collect { response ->
                if (response.isSuccessful) {
                    response.body()?.let { charactersResponse ->
                        charactersResponse.results?.let {
                            action.value = HomeActions.SetCharacters(it)
                        }
                    }
                } else {
                    logError("response.error")
                }
            }
        }
    }
}