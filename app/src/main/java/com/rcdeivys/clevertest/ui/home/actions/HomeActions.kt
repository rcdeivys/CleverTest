package com.rcdeivys.clevertest.ui.home.actions

import com.rcdeivys.clevertest.models.Result

sealed class HomeActions {
    object Empty : HomeActions()
    data class ShowMessageText(val message: String) : HomeActions()
    data class SetShowProgress(val isVisible: Boolean) : HomeActions()

    data class SetCharacters(val characters: List<Result>) : HomeActions()
}