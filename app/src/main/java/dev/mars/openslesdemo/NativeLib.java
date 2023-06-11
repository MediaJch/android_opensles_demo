package dev.mars.openslesdemo;

import android.content.Context;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ma.xuanwei on 2017/3/7.
 */

public class NativeLib {

    private AtomicBoolean isRecording = new AtomicBoolean(false);
    private AtomicBoolean isPlaying = new AtomicBoolean(false);
    private AtomicBoolean isRecordAndPlay=new AtomicBoolean(false);
    static {
        System.loadLibrary("native");
    }

    public void setIsRecording(boolean v){
        isRecording.set(v);
        LogUtils.DEBUG("setIsRecording "+v);
    }

    public void setIsRecordingAndPlaying(boolean v){
        isRecordAndPlay.set(v);
        LogUtils.DEBUG("setIsRecordingAndPlaying "+v);
    }

    public boolean isRecording(){
        return isRecording.get();
    }

    public void setIsPlaying(boolean b){
        isPlaying.set(b);
    }

    public boolean isPlaying(){
        return isPlaying.get();
    }

    public boolean isRecordingAndPlaying(){
        return isRecordAndPlay.get();
    }

    public native void startRecording(Context context, int sampleRate, int period, int channels, String path);
    public native void stopRecording();
    public native void playRecording(Context context, int sampleRate, int period, int channels, String path);
    public native void stopPlaying();
    public native int encode(String pcm,String speex);
    public native int decode(String speex,String pcm);

    public native int recordAndPlayPCM(Context context, boolean enableProcess,boolean enableEchoCancel, int period, int sampleRate, int channels);

    public native int stopRecordingAndPlaying() ;
}
