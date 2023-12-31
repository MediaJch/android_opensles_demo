package dev.mars.openslesdemo;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mars_ma on 2017/3/16.
 */

public class AudioUtils {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private NativeLib nativeBridge;

    public AudioUtils(){
        nativeBridge = new NativeLib();
    }

    public boolean recordAndPlayPCM(final Context context, final boolean enable1, final boolean enable2, final int periodFrame, final int frameRate, final int channels){
        if(!nativeBridge.isRecordingAndPlaying()) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    nativeBridge.recordAndPlayPCM(context, enable1,enable2, periodFrame, frameRate, channels);
                }
            });
            return true;
        }else{
            return false;
        }
    }

    public boolean stopRecordAndPlay(){
        if(!nativeBridge.isRecordingAndPlaying()) {
            return false;
        }else{
            nativeBridge.stopRecordingAndPlaying();
            return true;
        }
    }
}
