package com.ditdah.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    val isDarkTheme = getSettingsUseCase().map { it.isDarkTheme }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}