package com.klaus3d3.xdripwidgetforamazfit;

import android.app.Service;
import android.content.Intent;
import android.content.Context;

import android.os.PowerManager;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.util.Log;



import com.klaus3d3.xdripwidgetforamazfit.events.SnoozeRemoteConfirmation;
import com.klaus3d3.xdripwidgetforamazfit.events.xDripAlarm;
import com.klaus3d3.xdripwidgetforamazfit.events.xDripDataRecieved;
import com.klaus3d3.xdripwidgetforamazfit.events.xDripCancelConfirmation;

import com.huami.watch.transport.DataBundle;
import com.huami.watch.transport.TransportDataItem;

import com.kieronquinn.library.amazfitcommunication.Transporter;
import com.kieronquinn.library.amazfitcommunication.TransporterClassic;
import com.klaus3d3.xdripwidgetforamazfit.events.SnoozeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by edoardotassinari on 04/04/18.
 * modded by klaus3d3 for xdrip
 */

public class MainService extends Service { //} implements Transporter.ChannelListener, Transporter.ServiceConnectionListener {

    private TransporterClassic companionTransporter;

    private Context context;



    @Override
    public void onCreate() {
        Log.d(Constants.TAG, "EventBus init");

        HermesEventBus.getDefault().init(this);
        HermesEventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "MainService started");

        if (companionTransporter == null) {
            initTransporter();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    //@Override
    public void onChannelChanged(boolean b) {

    }

    private void initTransporter() {
        companionTransporter = (TransporterClassic) Transporter.get (this, Constants.TRANSPORTER_MODULE);
        companionTransporter.addChannelListener(new Transporter.ChannelListener() {
            @Override
            public void onChannelChanged(boolean ready) {

            }
        });
        //companionTransporter.addServiceConnectionListener(this);
        companionTransporter.addDataListener(new Transporter.DataListener() {
            @Override
            public void onDataReceived(TransportDataItem transportDataItem) {
                String action = transportDataItem.getAction();
                DataBundle db = transportDataItem.getData();
                Log.d(Constants.TAG, "action: " + action + ", module: " + transportDataItem.getModuleName());

                if (action == null) {
                    return;
                }
                if (action.equals(Constants.ACTION_XDRIP_SYNC))
                {   HermesEventBus.getDefault().post(new xDripDataRecieved(db));
                    confirm_sgv_data(db.getString("reply_message"));

                }
                if (action.equals(Constants.ACTION_XDRIP_ALARM))
                {   HermesEventBus.getDefault().post(new xDripAlarm(db));
                    confirm_sgv_data(db.getString("reply_message"));


                }
                if (action.equals(Constants.ACTION_XDRIP_SNOOZE_CONFIRMATION)) HermesEventBus.getDefault().post(new SnoozeRemoteConfirmation(db));

                if (action.equals(Constants.ACTION_XDRIP_CANCEL_CONFIRMATION))
                {   HermesEventBus.getDefault().post(new xDripCancelConfirmation(db));
                    confirm_sgv_data(db.getString("reply_message"));

                }
                if (action.equals(Constants.ACTION_XDRIP_COMMUNICATION_CHECK))
                {   confirm_sgv_data(db.getString("Yes, i am here"));

                }
            }
        });

        if (!companionTransporter.isTransportServiceConnected()) {
            Log.d(Constants.TAG, "connecting companionTransporter to transportService");
            companionTransporter.connectTransportService();
        }

    }




    public void confirm_sgv_data(String message) {
        DataBundle db = new DataBundle();
        db.putString("reply_message",message);
        companionTransporter.send(Constants.ACTION_XDRIP_DATA_CONFIRMATION,db);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Snooze(SnoozeEvent event) {

        companionTransporter.send(Constants.ACTION_Amazfit_Snooze);

    }






}
