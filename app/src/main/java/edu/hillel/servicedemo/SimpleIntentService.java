package edu.hillel.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by yuriy on 24.08.16.
 */
public class SimpleIntentService extends IntentService {

    private static final String TAG = SimpleIntentService.class.getSimpleName();

    public SimpleIntentService() {
        super("MyThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int n = intent.getIntExtra("counter", 1);
        Log.d(TAG, "start execute task: " + n);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "finish execute task: " + n);
    }
}
