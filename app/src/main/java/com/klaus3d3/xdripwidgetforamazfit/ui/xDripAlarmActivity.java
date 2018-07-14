package com.klaus3d3.xdripwidgetforamazfit.ui;

import android.app.Activity;

import android.os.Bundle;

import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;

import com.klaus3d3.xdripwidgetforamazfit.R2;
import com.klaus3d3.xdripwidgetforamazfit.events.SnoozeEvent;
import com.klaus3d3.xdripwidgetforamazfit.events.xDripCancelConfirmation;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class xDripAlarmActivity extends Activity {


    private Vibrator vibrator;
    private String Alarmtext_view;
    private String SGV_view;

    @BindView(R2.id.Alarm_text)
    TextView Alarmtext;
    @BindView(R2.id.SGV)
    TextView sgv;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            finish();
        }

    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        registerReceiver(mMessageReceiver, new IntentFilter("close_alarm_dialog"));
        try {
            if(this.getIntent().getExtras().getInt("kill")==1) finish();
        }catch (NullPointerException ex) {}
        super.onCreate(savedInstanceState);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        Alarmtext_view =getIntent().getStringExtra("Alarmtext");
        SGV_view= getIntent().getStringExtra("sgv");
            setContentView(R2.layout.xdripalarmactivity);
            ButterKnife.bind(this);

    }



    @Override
    public void onStart() {
        super.onStart();
        try {
            if(this.getIntent().getExtras().getInt("kill")==1) finish();
        }catch (NullPointerException ex) {}
        Alarmtext.setText(Alarmtext_view);
        sgv.setText(SGV_view);
        vibrate();

    }


    @OnClick(R2.id.snooze_button)
    public void clickSnooze() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(vibrator != null) {
            vibrator.cancel();                };
        HermesEventBus.getDefault().post(new SnoozeEvent());

        finish();
    }

    private void vibrate(){if(vibrator != null) {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 600, 200};
        vibrator.vibrate(pattern, 0);               };
    };

    @Override
    public void finish() {
        if(vibrator != null) {
            vibrator.cancel();                };
       // HermesEventBus.getDefault().post(new SnoozeEvent());
        super.finish();
    }

}
