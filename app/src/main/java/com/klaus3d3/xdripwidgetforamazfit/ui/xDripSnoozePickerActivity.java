package com.klaus3d3.xdripwidgetforamazfit.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.huami.watch.transport.DataBundle;
import com.klaus3d3.xdripwidgetforamazfit.R2;
import com.klaus3d3.xdripwidgetforamazfit.events.Snoozed;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaofei.library.hermeseventbus.HermesEventBus;

public class xDripSnoozePickerActivity extends Activity {


    private Vibrator vibrator;
    private boolean eventBusConnected;
    private int snooze_time;
    private int default_snooze;


    Context context;



    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        default_snooze=getIntent().getIntExtra("default_snooze",30);
            setContentView(R2.layout.xdripsnoozepickeractivity);
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


    }





    @Override
    public void finish() {

       if (snooze_time==0){snooze_time=default_snooze;Snooze(snooze_time);}
        super.finish();
    }


    private void Snooze(int Snooze_Minutes){
        DataBundle db = new DataBundle();
        db.putInt("snoozetime",Snooze_Minutes);
        Snoozed event = new Snoozed(db);
        HermesEventBus.getDefault().post(event);
        finish();
    }

    public void didTapButton(Button button) {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R2.anim.bounce);
        button.startAnimation(myAnim);}

    @OnClick(R2.id.tenmin)
    public void clickten() {
        Button button =findViewById(R2.id.tenmin);
        didTapButton(button);
        snooze_time=10;Snooze(snooze_time);}
    @OnClick(R2.id.twenmin)
    public void clicktwen() {
        Button button =findViewById(R2.id.twenmin);
        didTapButton(button);
        snooze_time=20;Snooze(snooze_time);}
    @OnClick(R2.id.thirtymin)
    public void clickthirty() {
        Button button =findViewById(R2.id.thirtymin);
        didTapButton(button);
        snooze_time=30;Snooze(snooze_time);}
    @OnClick(R2.id.fourtyfivemin)
    public void clickfourtyfive() {
        Button button =findViewById(R2.id.fourtyfivemin);
        didTapButton(button);
        snooze_time=45;Snooze(snooze_time);}
    @OnClick(R2.id.onehour)
    public void clickone() {
        Button button =findViewById(R2.id.onehour);
        didTapButton(button);
        snooze_time=60;Snooze(snooze_time);}
    @OnClick(R2.id.twohour)
    public void clicktwo() {
        Button button =findViewById(R2.id.twohour);
        didTapButton(button);
        snooze_time=120;Snooze(snooze_time);}
    @OnClick(R2.id.threehour)
    public void clickthree() {
        Button button =findViewById(R2.id.threehour);
        didTapButton(button);
        snooze_time=180;Snooze(snooze_time);}
    @OnClick(R2.id.fourhour)
    public void clickfour() {
        Button button =findViewById(R2.id.fourhour);
        didTapButton(button);
        snooze_time=240;Snooze(snooze_time);}
    @OnClick(R2.id.sixhour)
    public void clicksix() {
        Button button =findViewById(R2.id.sixhour);
        didTapButton(button);
        snooze_time=360;Snooze(snooze_time);}
    @OnClick(R2.id.eighthour)
    public void clickeight() {
        Button button =findViewById(R2.id.eighthour);
        didTapButton(button);
        snooze_time=480;Snooze(snooze_time);}
}