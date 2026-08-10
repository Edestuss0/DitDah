package com.ditdah.core.morse.data.audio

import android.media.AudioAttributes
import android.media.AudioAttributes.USAGE_GAME
import android.media.AudioFormat
import android.media.AudioTrack
import com.ditdah.core.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.PI
import kotlin.math.sin

internal class MorseAudioPlayer @Inject constructor(
    @ApplicationScope private val scope: CoroutineScope
) {
    private val sampleRate = 44100
    private val frequency = 700.0
    private val maxVolume = Short.MAX_VALUE * 0.6

    private var audioTrack: AudioTrack? = null
    private var playJob: Job? = null

    @Volatile private var isPlaying = false
    @Volatile private var isStopping = false

    init {
        val minBufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)

        audioTrack = AudioTrack.Builder().setAudioAttributes(
            AudioAttributes.Builder().setUsage(USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        ).setAudioFormat(
            AudioFormat.Builder().setEncoding(AudioFormat.ENCODING_PCM_16BIT).setSampleRate(sampleRate).setChannelMask(AudioFormat.CHANNEL_OUT_MONO).build()
        ).setBufferSizeInBytes(minBufferSize).setTransferMode(AudioTrack.MODE_STREAM).build()
    }

    fun startTone() {
        if (isPlaying) return
        isPlaying = true
        isStopping = false

        audioTrack?.play()

        playJob = scope.launch {
            val bufferSize = 1024
            val buffer = ShortArray(bufferSize)
            var phase = 0.0
            val phaseIncrement = 2.0 * PI * frequency / sampleRate

            val envelopeSamples = (sampleRate * 0.005).toInt()
            var currentSample = 0
            var stopSampleCounter = 0

            while (isActive && isPlaying) {
                for (i in 0 until bufferSize) {
                    var volume = maxVolume
                    if (currentSample < envelopeSamples) {
                        volume *= (currentSample.toDouble() / envelopeSamples)
                    }

                    if (isStopping) {
                        val remaining = (envelopeSamples - stopSampleCounter).coerceAtLeast(0)
                        volume *= (remaining.toDouble() / envelopeSamples)
                        stopSampleCounter++
                    }

                    buffer[i] = (sin(phase) * volume).toInt().toShort()

                    phase += phaseIncrement
                    if (phase >= 2.0 * PI) {
                        phase -= 2.0 * PI
                    }

                    currentSample++

                    if (isStopping && stopSampleCounter >= envelopeSamples) {
                        isPlaying = false
                        break
                    }
                }

                audioTrack?.write(buffer, 0, bufferSize)

                if (!isPlaying) break
            }

            audioTrack?.stop()
            audioTrack?.flush()
        }
    }

    fun stopTone() {
        if (!isPlaying || isStopping) return
        isStopping = true
    }

    fun release() {
        isPlaying = false
        playJob?.cancel()
        audioTrack?.release()
        audioTrack = null
    }
}