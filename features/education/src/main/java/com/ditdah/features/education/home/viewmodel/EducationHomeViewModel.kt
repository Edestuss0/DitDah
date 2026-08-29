package com.ditdah.features.education.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ditdah.core.education.domain.usecase.GetLessonUseCase
import com.ditdah.core.settings.domain.entity.Language
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EducationHomeViewModel @Inject internal constructor(
    private val getLesson: GetLessonUseCase,
    private val settings: GetSettingsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EducationHomeState())
    val state = _state.asStateFlow()
    private val _effects = Channel<EducationEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    init {
        viewModelScope.launch {
            settings().collect { flow ->
                _state.update { it.copy(language = flow.language) }
                getData()
            }
        }
    }

    fun onEvent(event: EducationEvent) {
        when (event) {
            is EducationEvent.Refresh -> {
                getData()
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val lesson = getLesson(state.value.language ?: Language.EN)
            _state.update { it.copy(lesson = lesson.getOrNull(), isLoading = false) }
            lesson.exceptionOrNull()?.let {
                _effects.send(
                    EducationEffect.Error(
                        it.message ?: "Произошла непредвиденная ошибка"
                    )
                )
            }
        }
    }
}