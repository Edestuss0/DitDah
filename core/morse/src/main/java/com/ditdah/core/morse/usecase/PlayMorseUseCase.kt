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

        text.forEachIndexed { index, i ->
            when (i.toString()) {
                "·" -> {
                    play()
                    delay(settings.getDotDuration())
                    stop()

                    if (index + 1 < text.length && (text[index + 1] == '·' || text[index + 1] == '–')) {
                        delay(settings.getDotDuration())
                    }
                }
                "–" -> {
                    play()
                    delay(settings.getDashDuration())
                    stop()

                    if (index + 1 < text.length && (text[index + 1] == '·' || text[index + 1] == '–')) {
                        delay(settings.getDotDuration())
                    }
                }
                " " -> {
                    delay(settings.getCharDuration())
                }
                "/" -> {
                    delay(settings.getWordDuration())
                }
                else -> return@forEachIndexed
            }
        }
    }
}