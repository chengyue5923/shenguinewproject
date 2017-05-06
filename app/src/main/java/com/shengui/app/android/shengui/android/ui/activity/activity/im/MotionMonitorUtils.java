package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.shengui.app.android.shengui.android.base.ShenGuiApplication;


public class MotionMonitorUtils {
    private static final String TAG = MotionMonitorUtils.class.getSimpleName();
    
    public static interface MotionMonitorUtilsDelegate {
        boolean audioIsPlaying();
        
        Activity getMotionMonitorUtilsActivity();
    }
    
//    private static final float kAccelerationThreshold = 2.5f;
//    private static final float kInvalidAccelerationValue = -1000.f;
//    private float mLastAccelerationX = kInvalidAccelerationValue;
//    private float mLastAccelerationY = kInvalidAccelerationValue;
//    private float mLastAccelerationZ = kInvalidAccelerationValue;
//    private AccelerometerFilter mAccelerometerFilter = new AccelerometerFilter();
//    private int mLastDeviceMotionTime = 0;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorEventListener;
    private MotionMonitorUtilsDelegate mDelegate;
    private Sensor mProximitySensor;
//    private Sensor mAccelerometerSensor;
    
    private boolean mShowing;
    private View mBlackView;
    
    public MotionMonitorUtils(MotionMonitorUtilsDelegate delegate) {
        mDelegate = delegate;

        mSensorManager = (SensorManager) ShenGuiApplication.getInstance().getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager != null) {
            mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        mSensorEventListener = new SensorEventListener() {
            
            @Override
            public void onSensorChanged(SensorEvent event) {
//                if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                    float valuex = event.values[SensorManager.DATA_X];
//                    float valuey = event.values[SensorManager.DATA_Y];
//                    float valuez = event.values[SensorManager.DATA_Z];
//                    
//                    mAccelerometerFilter.addAcceleration(valuex, valuey, valuez);
//                    
//                    if(Math.abs(kInvalidAccelerationValue - mLastAccelerationX)  < Float.MIN_NORMAL) {
//                        mLastAccelerationX = mAccelerometerFilter.getX();
//                        mLastAccelerationY = mAccelerometerFilter.getY();
//                        mLastAccelerationZ = mAccelerometerFilter.getZ();
//                    } else {
//                        float deltaX = Math.abs(mLastAccelerationX - mAccelerometerFilter.getX());
//                        float deltaY = Math.abs(mLastAccelerationY - mAccelerometerFilter.getY());
//                        float deltaZ = Math.abs(mLastAccelerationZ - mAccelerometerFilter.getZ());
//                        if(deltaX + deltaY + deltaZ > kAccelerationThreshold) {
//                            mLastAccelerationX = mAccelerometerFilter.getX();
//                            mLastAccelerationY = mAccelerometerFilter.getY();
//                            mLastAccelerationZ = mAccelerometerFilter.getZ();
//                            
//                            mLastDeviceMotionTime = (int)(System.currentTimeMillis() / 1000);
//                            Log.v(TAG, "start monitor proximitySensor");
//                        }
//                    }
//                    
//                } else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) 
                {
                    float valuex = event.values[0];
                    float maxValuex = mProximitySensor.getMaximumRange();
                    
//                    int deltaTime = (int)(System.currentTimeMillis() / 1000) - mLastDeviceMotionTime;
//                    if (maxValuex > valuex && valuex >= 0.0 && deltaTime < 4) 
                    if (valuex >= maxValuex - Float.MIN_VALUE && valuex <= maxValuex + Float.MIN_VALUE) {
                        AudioManager audioManager = (AudioManager) ShenGuiApplication.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setMode(AudioManager.MODE_NORMAL);
                        audioManager.setSpeakerphoneOn(true);
                        dismissBlackView();
                    } else if (mDelegate != null && mDelegate.audioIsPlaying()) {
                        AudioManager audioManager = (AudioManager) ShenGuiApplication.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setMode(AudioManager.STREAM_MUSIC);
                        audioManager.setSpeakerphoneOn(false);

                        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
                        if(audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) != maxVolume) {
                            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, maxVolume, AudioManager.FLAG_SHOW_UI);
                        }
                        showBlackView();
                    }
                }
            }
            
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    
    public void startMotionMonitor() {
        
        if(mSensorManager != null && mProximitySensor != null /* && mAccelerometerSensor != null*/) {
            mSensorManager.registerListener(mSensorEventListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
//            mSensorManager.registerListener(mSensorEventListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }
    
    public void stopMotionMonitor() {
        dismissBlackView();
        
//        mLastAccelerationX = kInvalidAccelerationValue;
//        mLastAccelerationY = kInvalidAccelerationValue;
//        mLastAccelerationZ = kInvalidAccelerationValue;
//        mLastDeviceMotionTime = 0;
        
        if(mSensorManager != null && mProximitySensor != null /* && mAccelerometerSensor != null*/) {
//            mSensorManager.unregisterListener(mSensorEventListener, mAccelerometerSensor);
            mSensorManager.unregisterListener(mSensorEventListener, mProximitySensor);
        }
    }
    
    @SuppressWarnings("deprecation")
    public void showBlackView() {
        if (mShowing || mDelegate == null || mDelegate.getMotionMonitorUtilsActivity() == null) {
            return;
        }
        
        if(mBlackView == null) {
            Log.v(TAG, "motion utils activit = " + mDelegate.getMotionMonitorUtilsActivity());
            mBlackView = new View(mDelegate.getMotionMonitorUtilsActivity());
            mBlackView.setBackgroundColor(Color.BLACK);
        }
        
        WindowManager windowManager =
                mDelegate.getMotionMonitorUtilsActivity().getWindowManager();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        try {
            windowManager.addView(mBlackView, lp);
            mShowing = true;
        } finally {
        }        
    }
    
    public void dismissBlackView() {
        if (!mShowing || mDelegate == null || mDelegate.getMotionMonitorUtilsActivity() == null) {
            return;
        }

        WindowManager windowManager = mDelegate.getMotionMonitorUtilsActivity().getWindowManager();
        
        try {
            windowManager.removeView(mBlackView);
        } finally {
            mShowing = false;
        }
    }
}
