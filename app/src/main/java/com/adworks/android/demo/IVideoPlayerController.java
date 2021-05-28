package com.adworks.android.demo;

/**
 * @author lzd
 * Create on 2021/5/20
 */
public interface IVideoPlayerController {


    void setVideoPath(String path);

    void start();

    void pause();

    void resume();

    boolean isRunning();

    boolean isPlaying();

    void release();

}
