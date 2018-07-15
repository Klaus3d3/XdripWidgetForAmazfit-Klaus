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
    private int snooze_time;
    private int default_snooze;

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
        default_snooze=getIntent().getIntExtra("default_snooze",30);
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
       if (snooze_time==0){snooze_time=default_snooze;Snooze(snooze_time);}
        super.finish();
    }
    @OnClick(R2.id.tenmin)
    public void clicktenmin() {snooze_time=10;Snooze(snooze_time);}
    @OnClick(R2.id.twenmin)
    public void clicktwenmin() {snooze_time=20;Snooze(snooze_time);}
    @OnClick(R2.id.thirtymin)
    public void clickthirtynmin() {snooze_time=30;Snooze(snooze_time);}
    @OnClick(R2.id.fourtyfivemin)
    public void clickfourtyfivemin() {snooze_time=45;Snooze(snooze_time);}
    @OnClick(R2.id.onehour)
    public void clickonehour() {snooze_time=60;Snooze(snooze_time);}
    @OnClick(R2.id.twohour)
    public void clicktwohour() {snooze_time=120;Snooze(snooze_time);}
    @OnClick(R2.id.threehour)
    public void clickthreehour() {snooze_time=180;Snooze(snooze_time);}
    @OnClick(R2.id.fourhour)
    public void clickfourhour() {snooze_time=240;Snooze(snooze_time);}
    @OnClick(R2.id.sixhour)
    public void clicksixhour() {snooze_time=360;Snooze(snooze_time);}
    @OnClick(R2.id.eighthour)
    public void clickeigthhour() {snooze_time=480;Snooze(snooze_time);}

    private void Snooze(int Snooze_Minutes){
        DataBundle db = new DataBundle();
        db.putInt("snoozetime",Snooze_Minutes);
        Snoozed event = new Snoozed(db);
        HermesEventBus.getDefault().post(event);
        finish();
    }
}
