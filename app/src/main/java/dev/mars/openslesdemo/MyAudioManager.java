package dev.mars.openslesdemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;

public class MyAudioManager {

    private static final int DEFAULT_SAMPLE_RATE_HZ = 16000;

    // Default audio data format is PCM 16 bit per sample.
    // Guaranteed to be supported by all devices.
    private static final int BITS_PER_SAMPLE = 16;

    private static final int DEFAULT_FRAME_PER_BUFFER = 256;



    static int getLatencyInputBufferSize(
            Context context, int sampleRate, int numberOfInputChannels) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return isLowLatencyInputSupported(context)
                ? getLowLatencyFramesPerBuffer(audioManager)
                : getMinInputFrameSize(sampleRate, numberOfInputChannels);
    }


    static int getLatencyOutputBufferSize(Context context, int sampleRate, int channels){
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return isLowLatencyOutputSupported(context)
                ? getLowLatencyFramesPerBuffer(audioManager)
                : getMinOutputFrameSize(sampleRate, channels);
    }

    private static int getLowLatencyFramesPerBuffer(AudioManager audioManager) {
        if (Build.VERSION.SDK_INT < 17) {
            return DEFAULT_FRAME_PER_BUFFER;
        }
        String framesPerBuffer =
                audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
        return framesPerBuffer == null ? DEFAULT_FRAME_PER_BUFFER : Integer.parseInt(framesPerBuffer);
    }

    private static boolean isLowLatencyInputSupported(Context context) {
        // TODO(henrika): investigate if some sort of device list is needed here
        // as well. The NDK doc states that: "As of API level 21, lower latency
        // audio input is supported on select devices. To take advantage of this
        // feature, first confirm that lower latency output is available".

        return Build.VERSION.SDK_INT >= 21 && isLowLatencyOutputSupported(context);
    }

    private static boolean isLowLatencyOutputSupported(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY);
    }

    /**
     *
     * @param sampleRateInHz
     * @param numChannels
     * @return
     */
    private static int getMinOutputFrameSize(int sampleRateInHz, int numChannels) {
        final int bytesPerFrame = numChannels * (BITS_PER_SAMPLE / 8);
        final int channelConfig =
                (numChannels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO);
        return AudioTrack.getMinBufferSize(
                sampleRateInHz, channelConfig, AudioFormat.ENCODING_PCM_16BIT)
                / bytesPerFrame;
    }

    /**
     * 获取低延时
     * @param sampleRateInHz
     * @param numChannels
     * @return
     */
    private static int getMinInputFrameSize(int sampleRateInHz, int numChannels) {
        final int bytesPerFrame = numChannels * (BITS_PER_SAMPLE / 8);
        final int channelConfig =
                (numChannels == 1 ? AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO);
        return AudioRecord.getMinBufferSize(
                sampleRateInHz, channelConfig, AudioFormat.ENCODING_PCM_16BIT)
                / bytesPerFrame;
    }
}
