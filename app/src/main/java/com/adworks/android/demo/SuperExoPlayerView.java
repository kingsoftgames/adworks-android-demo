package com.adworks.android.demo;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

/**
 * @Author : LZD
 * @Date on  : 5/17/21
 * @Desc :
 */
public class SuperExoPlayerView extends PlayerView implements IVideoPlayerController ,IMediaPlayerExtendFunction, Player.EventListener {
    private Context mContext;
    private SimpleExoPlayer mPlayer;
    private DataSource.Factory mDataSourceFactory;
    private String mVideoPath;
    private MediaSource mVideoSource;

    private IMediaPlayerExtendListener mExtendListener;

    public SuperExoPlayerView(Context context) {
        super(context);
    }

    public SuperExoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        requestFocus();
        mPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        setPlayer(mPlayer);

        // Produces DataSource instances through which media data is loaded.
        mDataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "ad-works"));
        mPlayer.addListener(this);
        mPlayer.addVideoListener(new VideoListener() {
            @Override
            public void onRenderedFirstFrame() {
            }
        });


    }

    @Override
    public void setVideoPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        this.mVideoPath = path;
    }

    @Override
    public void start() {
        mVideoSource = new ProgressiveMediaSource.Factory(mDataSourceFactory)
                .createMediaSource(Uri.parse(mVideoPath));
        mPlayer.setPlayWhenReady(true);
        mPlayer.prepare(mVideoSource);
    }

    @Override
    public void pause() {
        mPlayer.setPlayWhenReady(false);
    }

    @Override
    public void resume() {
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void release() {
        mPlayer.stop(true);
        mPlayer.removeListener(this);
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void setExtendListener(IMediaPlayerExtendListener listener) {
        this.mExtendListener = listener;
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playWhenReady && playbackState == Player.STATE_READY){
            if (mExtendListener!=null){
                mExtendListener.onPlayStart();
            }
        }
        if (playbackState == Player.STATE_ENDED){
            if (mExtendListener!=null){
                mExtendListener.onPlayEnd();
            }
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        if (mExtendListener!=null){
            mExtendListener.onPlayError(error.getMessage());

        }
    }
}
