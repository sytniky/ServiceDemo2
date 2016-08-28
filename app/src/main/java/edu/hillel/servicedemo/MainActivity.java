package edu.hillel.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int counter = 0;
    private BindingService mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BindingService.MyBinder binder = (BindingService.MyBinder ) iBinder;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyService();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMyService();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSleepTime(2);
            }
        });
    }

    private void addSleepTime(int time) {
        if (mService != null) {
            mService.addSleepTime(time);
            Log.d(TAG, "Time to sleep left = " + mService.getTimeLeft());
        }
    }

    private void stopMyService() {
        unbindService(mConnection);
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    private void startMyService() {
//        Intent intent = new Intent(this, MyService.class);
//        Intent intent = new Intent(this, SimpleIntentService.class);
        Intent intent = new Intent(this, BindingService.class);
        intent.putExtra("sleepTime", 5);
//        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }
}
