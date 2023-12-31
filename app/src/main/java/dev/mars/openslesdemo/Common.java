package dev.mars.openslesdemo;

import android.content.Context;
import android.os.Environment;

/**
 * Created by 37550 on 2017/3/8.
 */

public class Common {
    public static final int SAMPLERATE = 48000; //bit/s
    public static final int CHANNELS = 1; //1:单/2:双声道
    public static final int PERIOD_TIME = 10; //ms


    public static final String DEFAULT_PCM_FILE_PATH(Context context){
        return context.getExternalCacheDir().getAbsolutePath()+ "/test_pcm.pcm";
    }
    public static final String DEFAULT_PCM_OUTPUT_FILE_PATH(Context context){
        return context.getExternalCacheDir().getAbsolutePath()+"/output_pcm.pcm";
    }
    public static final String DEFAULT_SPEEX_FILE_PATH(Context context){
        return context.getExternalCacheDir().getAbsolutePath()+"/test_speex.raw";
    }
}
