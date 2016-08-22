package edu.hillel.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by yuriy on 17.08.16.
 */
public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        final int counter = intent.getIntExtra("counter", 0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                separateThreadFunction(counter);
            }
        });
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private void separateThreadFunction(int counter) {
        try {
            Thread.sleep(10000);
            stopSelf();
            Log.d("Thread", String.valueOf(counter));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
