package com.rcdeivys.clevertest.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcdeivys.clevertest.common.logError
import com.rcdeivys.clevertest.repositories.CleverRepository
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationsViewModel constructor(
    private val repository: CleverRepository
) : ViewModel() {

    private val action = MutableStateFlow<HomeActions>(HomeActions.Empty)
    fun getAction(): StateFlow<HomeActions> = action

    private var totalPages = 1
    private var page = 2

    fun getLocations() {
        viewModelScope.launch {
            action.value = HomeActions.SetShowProgress(true)
            repository.getLocations().collect { response ->
                if (response.isSuccessful) {
                    response.body()?.let { locationsResponse ->
                        totalPages = locationsResponse.info?.pages ?: 0
                        locationsResponse.results?.let {
                            action.value = HomeActions.SetLocations(it)
                            action.value = HomeActions.SetShowProgress(false)
                        }
                    }
                } else {
                    action.value = HomeActions.SetShowProgress(false)
                    action.value = HomeActions.ShowMessageText("Error getting locations")
                }
            }
        }
    }

    fun getLocationsPaginated() {
        if (page < totalPages) {
            viewModelScope.launch {
                repository.getLocationsPaginated(page).collect { response ->
                    if (response.isSuccessful) {
                        page++
                        response.body()?.let { locationsResponse ->
                            locationsResponse.results?.let {
                                action.value = HomeActions.UpdateLocations(it)
                            }
                        }
                    } else {
                        logError("Error getting locations")
                    }
                }
            }
        }
    }
}