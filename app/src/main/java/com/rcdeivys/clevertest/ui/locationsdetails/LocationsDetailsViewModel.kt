package com.rcdeivys.clevertest.ui.locationsdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rcdeivys.clevertest.common.getIdResident
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.repositories.CleverRepository
import com.rcdeivys.clevertest.ui.home.actions.HomeActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationsDetailsViewModel constructor(
    private val repository: CleverRepository
) : ViewModel() {

    private val action = MutableStateFlow<HomeActions>(HomeActions.Empty)
    fun getAction(): StateFlow<HomeActions> = action

    private var allCharacters = mutableListOf<Result>()

    fun getCharacterById(residents: List<String>?) {
        allCharacters.clear()
        viewModelScope.launch {
            residents?.let { residentsNotNull ->
                if (residentsNotNull.isNotEmpty()) {
                    action.value = HomeActions.SetShowProgress(true)
                    residentsNotNull.forEach { resident ->
                        repository.getCharacterById(resident.getIdResident()).collect { response ->
                            if (response.isSuccessful) {
                                response.body()?.let { result ->
                                    allCharacters.add(result)
                                }
                            } else {
                                action.value = HomeActions.SetShowProgress(false)
                                action.value =
                                    HomeActions.ShowMessageText("Error getting characters")
                            }
                        }
                    }
                    action.value = HomeActions.SetShowProgress(false)
                    action.value = HomeActions.SetCharacters(allCharacters)
                }
            }
        }
    }
}