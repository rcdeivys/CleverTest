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

    private var totalPages = 1
    private var page = 2

    fun getCharacter() {
        viewModelScope.launch {
            action.value = HomeActions.SetShowProgress(true)
            repository.getCharacter().collect { response ->
                if (response.isSuccessful) {
                    response.body()?.let { charactersResponse ->
                        totalPages = charactersResponse.info?.pages ?: 0
                        charactersResponse.results?.let {
                            action.value = HomeActions.SetCharacters(it)
                            action.value = HomeActions.SetShowProgress(false)
                        }
                    }
                } else {
                    action.value = HomeActions.SetShowProgress(false)
                    action.value = HomeActions.ShowMessageText("Error getting characters")
                }
            }
        }
    }

    fun getCharacterPaginated() {
        if (page < totalPages) {
            viewModelScope.launch {
                repository.getCharacterPaginated(page).collect { response ->
                    if (response.isSuccessful) {
                        page++
                        response.body()?.let { charactersResponse ->
                            charactersResponse.results?.let {
                                action.value = HomeActions.UpdateCharacters(it)
                            }
                        }
                    } else {
                        logError("Error getting characters")
                    }
                }
            }
        }
    }
}