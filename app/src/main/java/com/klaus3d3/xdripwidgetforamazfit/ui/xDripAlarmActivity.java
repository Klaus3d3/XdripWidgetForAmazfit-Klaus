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


import com.klaus3d3.xdripwidgetforamazfit.R2;
import com.klaus3d3.xdripwidgetforamazfit.events.SnoozeEvent;
import com.klaus3d3.xdripwidgetforamazfit.events.xDripCancelConfirmation;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class xDripAlarmActivity extends Activity {

    private Handler handler;
    private Vibrator vibrator;

    @BindView(R2.id.Alarm_text)
    TextView Alarmtext;
    @BindView(R2.id.SGV)
    TextView sgv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        setContentView(R2.layout.xdripalarmactivity);
        ButterKnife.bind(this);
        try {
        if(this.getIntent().getExtras().getInt("kill")==1) finish();
        }catch (NullPointerException ex) {}
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


    }
    @Override
    public void onStart() {
        super.onStart();
        try {
            Alarmtext.setText(getIntent().getStringExtra("Alarmtext"));
            sgv.setText(getIntent().getStringExtra("sgv"));

        }catch (NullPointerException ex) {}
        vibrate();
    }
    @Override
    public void onPause() {
        super.onPause();
        if(vibrator != null) {
            vibrator.cancel();                };
        finish();

    }


    @OnClick(R2.id.snooze_button)
    public void clickSnooze() {
        if(vibrator != null) {
            vibrator.cancel();                };
        HermesEventBus.getDefault().post(new SnoozeEvent());

        finish();
    }

    private void vibrate(){if(vibrator != null) {
        long[] pattern = {0, 600, 200};
        vibrator.vibrate(pattern, 0);               };
    };

    @Override
    public void finish() {
        super.finish();
    }

}
