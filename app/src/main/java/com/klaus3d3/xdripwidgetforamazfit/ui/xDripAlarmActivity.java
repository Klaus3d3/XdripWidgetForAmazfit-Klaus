package com.klaus3d3.xdripwidgetforamazfit.ui;

import android.app.Activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import com.huami.watch.transport.DataBundle;


import com.klaus3d3.xdripwidgetforamazfit.R2;
import com.klaus3d3.xdripwidgetforamazfit.events.Snoozed;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class xDripAlarmActivity extends Activity {


    private Vibrator vibrator;
    private String Alarmtext_view;
    private String SGV_view;
    private boolean eventBusConnected;

    @BindView(R2.id.Alarm_text)
    TextView Alarmtext;
    @BindView(R2.id.SGV)
    TextView sgv;
    Context context;


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
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        registerReceiver(mMessageReceiver, new IntentFilter("close_alarm_dialog"));


            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        Alarmtext_view =getIntent().getStringExtra("Alarmtext");
        SGV_view= getIntent().getStringExtra("sgv");
            setContentView(R2.layout.xdripalarmactivity);
            ButterKnife.bind(this);
        try {
            HermesEventBus.getDefault().init(this);
            HermesEventBus.getDefault().register(this);

            eventBusConnected = true;

        } catch (Exception ex) {}

    }


    @Override
    public void onStart() {
        super.onStart();

        Alarmtext.setText(Alarmtext_view);
        sgv.setText(SGV_view);

        vibrate();

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
    @OnClick(R2.id.tenmin)
    public void clicktenmin() {Snooze("10");}
    @OnClick(R2.id.twenmin)
    public void clicktwenmin() {Snooze("20");}
    @OnClick(R2.id.thirtymin)
    public void clickthirtynmin() {Snooze("30");}
    @OnClick(R2.id.fourtyfivemin)
    public void clickfourtyfivemin() {Snooze("45");}
    @OnClick(R2.id.onehour)
    public void clickonehour() {Snooze("60");}
    @OnClick(R2.id.twohour)
    public void clicktwohour() {Snooze("120");}
    @OnClick(R2.id.threehour)
    public void clickthreehour() {Snooze("180");}
    @OnClick(R2.id.fourhour)
    public void clickfourhour() {Snooze("240");}
    @OnClick(R2.id.sixhour)
    public void clicksixhour() {Snooze("360");}
    @OnClick(R2.id.eighthour)
    public void clickeigthhour() {Snooze("480");}

    private void Snooze(String Snooze_Minutes){
        DataBundle db = new DataBundle();
        db.putString("snoozetime",Snooze_Minutes);
        Snoozed event = new Snoozed(db);
        HermesEventBus.getDefault().post(event);
        finish();
    }
}
