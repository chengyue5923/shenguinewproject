package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.app.Activity;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

public class AudioMessagePlayer implements MotionMonitorUtils.MotionMonitorUtilsDelegate {
    private static final String TAG = AudioMessagePlayer.class.getSimpleName();
    private static AudioMessagePlayer instance = null;

    public static interface AudioMessagePlayerDelegate {
        Activity getAudioMessagePlayerActivity();

        void playComplete();

    }

    private MediaPlayer mAudioPlayer;
    private boolean mAuidoPlayerIsPrepared = false;
    private int mAudioMessageID = -1;
    private String mCurrentAudioPlayerUrl = "";
    private AudioMessagePlayerDelegate mDelegate;

    public AudioMessagePlayer() {


        mAudioPlayer = new MediaPlayer();
        mAudioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mAuidoPlayerIsPrepared = false;
                if (mDelegate != null) {
                    mDelegate.playComplete();
                }
            }
        });

    }

    public void setmDelegate(AudioMessagePlayerDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public static AudioMessagePlayer getInstance() {
        if (null == instance) {
            synchronized (AudioMessagePlayer.class) {
                instance = new AudioMessagePlayer();
            }
        }
        return instance;
    }

    public void clickAudioMessageView(String audioUrl) {


        startAudioPlayer(audioUrl);
    }


    private void startAudioPlayer(String audioUrl) {
        if (!mAuidoPlayerIsPrepared) {
            mAudioPlayer.reset();
            if (!TextUtils.isEmpty(audioUrl)) {
                mCurrentAudioPlayerUrl = audioUrl;
                try {
                    mAudioPlayer.setDataSource(audioUrl);
                } catch (IOException e) {
                    Log.e(TAG, "IOException " + e);
                }

                try {
                    mAudioPlayer.prepare();
//                     mMotionMonitorUtils.startMotionMonitor();
                    mAuidoPlayerIsPrepared = true;

                    mAudioPlayer.setScreenOnWhilePlaying(true);
                    mAudioPlayer.start();

                } catch (Exception e) {

                }

            }
        } else {
            mAuidoPlayerIsPrepared = false;

            if (TextUtils.equals(audioUrl, mCurrentAudioPlayerUrl)) {
                mCurrentAudioPlayerUrl = "";

                mAudioPlayer.stop();
                if (mDelegate != null) {
                    mDelegate.playComplete();
                }


            } else {
                mAudioPlayer.reset();
                if (!TextUtils.isEmpty(audioUrl)) {
                    mCurrentAudioPlayerUrl = audioUrl;
                    try {
                        mAudioPlayer.setDataSource(audioUrl);
                    } catch (IOException e) {
                        Log.e(TAG, "IOException " + e);
                    }

                    mAudioPlayer.prepareAsync();
                } else {
                    mCurrentAudioPlayerUrl = "";
                }
            }
        }
    }

    public void stopAudioPlayer() {
        try {
            mCurrentAudioPlayerUrl = "";
            if (mAudioPlayer != null) {
                mAudioPlayer.stop();
            }
        } catch (Exception e) {

        }

    }

    public void releaseAudioPlayer() {
        try {
            mAudioPlayer.release();
        } catch (Exception e) {
        }

    }

    @Override
    public boolean audioIsPlaying() {
        return mAuidoPlayerIsPrepared && mAudioPlayer.isPlaying();
    }

    public boolean isStop() {
        return !mAuidoPlayerIsPrepared && !mAudioPlayer.isPlaying();
    }

    public boolean isPrepared() {
        return mAuidoPlayerIsPrepared && !mAudioPlayer.isPlaying();
    }

    @Override
    public Activity getMotionMonitorUtilsActivity() {
        if (mDelegate != null) {
            return mDelegate.getAudioMessagePlayerActivity();
        }

        return null;
    }
}
