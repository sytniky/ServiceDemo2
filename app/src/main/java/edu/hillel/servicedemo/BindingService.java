package edu.hillel.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yuriy on 24.08.16.
 */
public class BindingService extends Service {

    private static final String TAG = BindingService.class.getSimpleName();

    public class MyBinder extends Binder {

        BindingService mService;
        public MyBinder(BindingService service) {
            mService = service;
        }

        public BindingService getService() {
            return mService;
        }
    }

    private int mSleepTime;
    private int mCurrentTime;
    private BindingService thisService;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        mSleepTime = intent.getExtras().getInt("sleepTime");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                separateThreadFunction();
            }
        });
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void separateThreadFunction() {
        try {
            int mCurrentTime = 0;
            while (mCurrentTime < mSleepTime) {
                Log.d("Thread", String.valueOf(mCurrentTime));
                Thread.sleep(1000);
                mCurrentTime++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(this);
    }

    public void addSleepTime(int value) {
        mSleepTime += value;
    }

    public int getTimeLeft() {
        return mSleepTime - mCurrentTime;
    }
}
