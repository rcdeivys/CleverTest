package com.rcdeivys.clevertest.ui.details.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcdeivys.clevertest.repositories.CleverRepository
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel constructor(
    private val repository: CleverRepository
) : ViewModel() {

    private val action = MutableStateFlow<HomeActions>(HomeActions.Empty)
    fun getAction(): StateFlow<HomeActions> = action

    fun getEpisode(idEpisode: Int) {
        viewModelScope.launch {
            action.value = HomeActions.SetShowProgress(true)
            repository.getEpisode(idEpisode).collect { response ->
                if (response.isSuccessful) {
                    response.body()?.let { episodeResponse ->
                        action.value = HomeActions.SetEpisode(episodeResponse)
                        action.value = HomeActions.SetShowProgress(false)
                    }
                } else {
                    action.value = HomeActions.SetShowProgress(false)
                    action.value = HomeActions.ShowMessageText("Error getting the episodes")
                }
            }
        }
    }
}