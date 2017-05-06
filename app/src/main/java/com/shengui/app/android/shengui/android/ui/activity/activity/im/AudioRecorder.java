package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.media.MediaRecorder;
import android.util.Log;


public class AudioRecorder {


    public static AudioRecorder getInstanse() {


        return new AudioRecorder();

    }

    /**
     * INITIALIZING : recorder is initializing; READY : recorder has been
     * initialized, recorder not yet started RECORDING : recording ERROR :
     * reconstruction needed STOPPED: reset needed
     */

    private MediaRecorder mediaRecorder = null;


    public AudioRecorder() {
        try {

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder
                    .setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            final int mono = 2;
            mediaRecorder.setAudioChannels(mono);
            mediaRecorder.setAudioSamplingRate(8000);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(AudioRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(AudioRecorder.class.getName(),
                        "Unknown error occured while initializing recording");
            }

        }
    }

    /**
     * Sets output file path, call directly after construction/reset.
     *
     * @param
     */
    public void setOutputFile(String argPath) {
        try {

            mediaRecorder.setOutputFile(argPath);

        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(AudioRecorder.class.getName(), e.getMessage());
            } else {
                Log.e(AudioRecorder.class.getName(),
                        "Unknown error occured while setting output path");
            }

        }
    }

    /**
     * Prepares the recorder for recording, in case the recorder is not in the
     * INITIALIZING state and the file path was not set the recorder is set to
     * the ERROR state, which makes a reconstruction necessary. In case
     * uncompressed recording is toggled, the header of the wave file is
     * written. In case of an exception, the state is changed to ERROR
     */
    public void prepare() {
        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
        }

    }

    /**
     * Releases the resources associated with this class, and removes the
     * unnecessary files, when necessary
     */
    public void release() {


        mediaRecorder.stop();

        mediaRecorder.release();

    }


    /**
     * Starts the recording, and sets the state to RECORDING. Call after
     * prepare().
     */
    public void start() {
        mediaRecorder.start();
    }

}
