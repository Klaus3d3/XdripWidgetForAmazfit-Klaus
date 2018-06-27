package com.klaus3d3.xdripwidgetforamazfit.springboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.klaus3d3.xdripwidgetforamazfit.Constants;
import com.klaus3d3.xdripwidgetforamazfit.R;
import com.klaus3d3.xdripwidgetforamazfit.R2;
import com.klaus3d3.xdripwidgetforamazfit.events.NightscoutDataEvent;
import com.klaus3d3.xdripwidgetforamazfit.events.NightscoutRequestSyncEvent;
import com.klaus3d3.xdripwidgetforamazfit.events.SnoozeEvent;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import com.mikepenz.iconics.Iconics;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import clc.sliteplugin.flowboard.AbstractPlugin;
import clc.sliteplugin.flowboard.ISpringBoardHostStub;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by edoardotassinari on 09/04/18.
 * modded by klaus3d3 for xdrip
 */

public class NightscoutPage extends AbstractPlugin {

    private Context mContext;
    private View mView;
    private boolean mHasActive = false;
    private ISpringBoardHostStub mHost = null;

    private boolean eventBusConnected;
    private Long lastDate;

    private String trendArrow;
    private String lastSgv;
    private String lastDelta;
    private Boolean lastishigh;
    private Boolean lastislow;
    private Boolean lastisstale;
    private Boolean lastfrom_plugin;
    private String lastExtra_string;
    private String last_plugin_name;
    private int last_warning;
    private final String plugin_symbol = "℗ ";
    private String lastalert;





    @BindView(R2.id.nightscout_sgv_textview)
    TextView sgv;
    @BindView(R2.id.nightscout_date_textview)
    TextView date;
    @BindView(R2.id.nightscout_delta_text_view)
    TextView delta;
    @BindView(R2.id.prediction_text)
    TextView prediction;



    //Much like a fragment, getView returns the content view of the page. You can set up your layout here
    @Override
    public View getView(Context paramContext) {
        mContext = paramContext;

        initIcons(paramContext);

        Log.d(Constants.TAG_NIGHTSCOUT_PAGE, "getView()" + paramContext.getPackageName());

        mView = LayoutInflater.from(paramContext).inflate(R.layout.nightscoout_page, null);

        //nightscoutPageViewHolder = new NightscoutPageViewHolder();
        try {
            ButterKnife.bind(this, mView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mView;
    }


    @OnClick(R2.id.nightscout_sgv_textview)
    public void sgv_click() {
        // HermesEventBus.getDefault().post(new NightscoutRequestSyncEvent());
        Toast.makeText(mContext, "Plugin: "+ last_plugin_name + " Alert" + lastalert, Toast.LENGTH_LONG).show();
    }
    @OnClick(R2.id.snooze_button)
    public void snooze_click() {
        HermesEventBus.getDefault().post(new SnoozeEvent());
       // Toast.makeText(mContext, "snoozing...", Toast.LENGTH_LONG).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(NightscoutDataEvent nightscoutDataEvent) {
        Log.d(Constants.TAG_NIGHTSCOUT_PAGE, "NightscoutDataEvent received");

// getting the data from Hermes

        lastDate = nightscoutDataEvent.getDate();

        lastfrom_plugin=nightscoutDataEvent.getFrom_plugin();

        last_plugin_name=nightscoutDataEvent.getPlugin_name();
        if (lastfrom_plugin) lastSgv =  plugin_symbol + String.valueOf(nightscoutDataEvent.getSgv());
        else lastSgv = String.valueOf(nightscoutDataEvent.getSgv());

        lastDelta = nightscoutDataEvent.getDelta();
        lastishigh = nightscoutDataEvent.getIshigh();
        lastislow = nightscoutDataEvent.getIslow();
        lastisstale= nightscoutDataEvent.getIsstale();
        lastalert = nightscoutDataEvent.getAlert();

        lastExtra_string=nightscoutDataEvent.getExtrastring();
        last_warning=nightscoutDataEvent.getWarning();

            sgv.setText(lastSgv);
            delta.setText(lastDelta);

            if (date != null) {
                date.setText(TimeAgo.using(lastDate));
            }
            if (lastislow || lastishigh || lastisstale){sgv.setTextColor(Color.RED);}else{sgv.setTextColor(Color.WHITE);}
            if (lastisstale){
                sgv.setPaintFlags(sgv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);}
                else{
                sgv.setPaintFlags(sgv.getPaintFlags()  & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }
            prediction.setText(lastExtra_string);

    }



    //Return the icon for this page, used when the page is disabled in the app list. In this case, the launcher icon is used
    @Override
    public Bitmap getWidgetIcon(Context paramContext) {
        return ((BitmapDrawable) this.mContext.getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
    }

    //Return the launcher intent for this page. This might be used for the launcher as well when the page is disabled?
    @Override
    public Intent getWidgetIntent() {
        Intent localIntent = new Intent();
        /*localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        localIntent.setAction("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        localIntent.setComponent(new ComponentName(this.mContext.getPackageName(), "com.huami.watch.deskclock.countdown.CountdownListActivity"));*/
        return localIntent;
    }

    //Return the title for this page, used when the page is disabled in the app list. In this case, the app name is used
    @Override
    public String getWidgetTitle(Context paramContext) {
        return this.mContext.getResources().getString(R.string.app_name);
    }

    //Called when the page is shown
    @Override
    public void onActive(Bundle paramBundle) {
        super.onActive(paramBundle);
        //Check if the view is already inflated (reloading)
        if ((!this.mHasActive) && (this.mView != null)) {
            //It is, simply refresh
            refreshView();
        }

        if (!eventBusConnected) {
            initIPC(mHost.getHostWindow().getContext());
        }

        if (date != null) {
            date.setText(TimeAgo.using(Long.valueOf(lastDate)));
        }
        if (sgv !=null) {
            if(System.currentTimeMillis()-lastDate > 600000){
                sgv.setTextColor(Color.RED);
                sgv.setPaintFlags(sgv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);}
        }

        //Store active state
        this.mHasActive = true;
    }

    private void refreshView() {
        //Called when the page reloads, check for updates here if you need to
        //Done :-) now it gets updated every time we enter the widget

        HermesEventBus.getDefault().post(new NightscoutRequestSyncEvent());
    }

    //Returns the springboard host
    public ISpringBoardHostStub getHost() {
        return mHost;
    }

    //Called when the page is loading and being bound to the host
    @Override
    public void onBindHost(ISpringBoardHostStub paramISpringBoardHostStub) {
        Log.d(Constants.TAG, "onBindHost");
        //Store host
        mHost = paramISpringBoardHostStub;
    }

    //Called when the page is destroyed completely (in app mode). Same as the onDestroy method of an activity
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Called when the page becomes inactive (the user has scrolled away)
    @Override
    public void onInactive(Bundle paramBundle) {
        super.onInactive(paramBundle);
        //Store active state
        this.mHasActive = false;
    }

    //Called when the page is paused (in app mode)
    @Override
    public void onPause() {
        super.onPause();
        this.mHasActive = false;
    }

    //Not sure what this does, can't find it being used anywhere. Best leave it alone
    @Override
    public void onReceiveDataFromProvider(int paramInt, Bundle paramBundle) {
        super.onReceiveDataFromProvider(paramInt, paramBundle);
    }

    //Called when the page is shown again (in app mode)
    @Override
    public void onResume() {
        super.onResume();
        //Check if view already loaded
        if ((!this.mHasActive) && (this.mView != null)) {
            //It is, simply refresh
            this.mHasActive = true;
            refreshView();
        }
        //Store active state
        this.mHasActive = true;
    }

    //Called when the page is stopped (in app mode)
    @Override
    public void onStop() {
        super.onStop();
        this.mHasActive = false;
    }

    private void initIPC(Context context) {
        Log.d(Constants.TAG_NIGHTSCOUT_PAGE, "initIPC");

        if (context.getApplicationContext() == null) {
            Log.w(Constants.TAG_NIGHTSCOUT_PAGE, "application context null!!!");
            return;
        }

        try {
            HermesEventBus.getDefault().connectApp(context, Constants.PACKAGE_NAME);
            HermesEventBus.getDefault().register(this);

            eventBusConnected = true;
            Log.d(Constants.TAG_NIGHTSCOUT_PAGE, "eventBus connected");
        } catch (Exception ex) {
            Log.d(Constants.TAG_NIGHTSCOUT_PAGE, "initIPC failed");
            ex.printStackTrace();
        }
    }

    private void initIcons(Context context) {
        Log.d(Constants.TAG_NIGHTSCOUT_PAGE, "initIcons");

        Iconics.init(context);
    }
}
