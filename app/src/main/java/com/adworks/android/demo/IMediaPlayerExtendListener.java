package com.adworks.android.demo;

/**
 * @Author : LZD
 * @Date on  : 5/20/21
 * @Desc :
 */
public interface IMediaPlayerExtendListener {

    void onPlayStart();

    void onPlayEnd();

    void onPlayError(String msg);
}
