package com.ditdah.core.morse.usecase

import com.ditdah.core.morse.repository.MorseRepository
import com.ditdah.core.settings.domain.usecase.GetSettingsUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class PlayMorseUseCase @Inject internal constructor(
    private val repository: MorseRepository,
    private val settings: GetSettingsUseCase
) {
    fun play() = repository.startMorse()
    fun stop() = repository.stopMorse()

    suspend fun playMorse(text: String) {
        val settings = settings().value

        text.forEach {
            when (it.toString()) {
                "·" -> {
                    play()
                    delay(settings.getDotDuration())
                    stop()
                }
                "–" -> {
                    play()
                    delay(settings.getDotDuration() * 3)
                    stop()
                }
                else -> return@forEach
            }
            delay(settings.getDotDuration() * 3)
        }
    }
}