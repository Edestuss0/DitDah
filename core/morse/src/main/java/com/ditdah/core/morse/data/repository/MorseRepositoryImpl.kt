package com.ditdah.core.morse.data.repository

import com.ditdah.core.morse.data.audio.MorseAudioPlayer
import com.ditdah.core.morse.domain.repository.MorseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class MorseRepositoryImpl @Inject constructor(
    private val audio: MorseAudioPlayer
) : MorseRepository {
    override fun startMorse() {
        audio.startTone()
    }

    override fun stopMorse() {
        audio.stopTone()
    }
}