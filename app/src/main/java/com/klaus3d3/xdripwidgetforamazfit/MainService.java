package com.klaus3d3.xdripwidgetforamazfit;

import android.app.Service;
import android.content.Intent;
import android.content.Context;


import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import com.klaus3d3.xdripwidgetforamazfit.events.NightscoutDataEvent;
import com.klaus3d3.xdripwidgetforamazfit.events.NightscoutRequestSyncEvent;

import com.huami.watch.transport.DataBundle;
import com.huami.watch.transport.TransportDataItem;

import com.kieronquinn.library.amazfitcommunication.Transporter;
import com.kieronquinn.library.amazfitcommunication.TransporterClassic;
import com.klaus3d3.xdripwidgetforamazfit.events.SnoozeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by edoardotassinari on 04/04/18.
 * modded by klaus3d3 for xdrip
 */

public class MainService extends Service { //} implements Transporter.ChannelListener, Transporter.ServiceConnectionListener {

    private TransporterClassic companionTransporter;

    private Context context;

    private Map<String, Class> messages = new HashMap<String, Class>() {{
        put(Constants.ACTION_XDRIP_SYNC, NightscoutDataEvent.class);
    }};

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
        //HermesEventBus.getDefault().post(new NightscoutRequestSyncEvent());
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

                Log.d(Constants.TAG, "action: " + action + ", module: " + transportDataItem.getModuleName());

                if (action == null) {
                    return;
                }
                //HermesEventBus.getDefault().post(new NightscoutRequestSyncEvent());
                //HermesEventBus.getDefault().post(new SnoozeEvent());
                Class messageClass = messages.get(action);

                if (messageClass != null) {
                    Class[] args = new Class[1];
                    args[0] = DataBundle.class;

                    try {
                        Constructor eventContructor = messageClass.getDeclaredConstructor(args);
                        Object event = eventContructor.newInstance(transportDataItem.getData());

                        Log.d(Constants.TAG, "posting event " + event.toString());
                        HermesEventBus.getDefault().post(event);
                    } catch (NoSuchMethodException e) {
                        Log.w(Constants.TAG, "event mapped with action \"" + action + "\" doesn't have constructor with DataBundle as parameter");
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        if (!companionTransporter.isTransportServiceConnected()) {
            Log.d(Constants.TAG, "connecting companionTransporter to transportService");
            companionTransporter.connectTransportService();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestNightscoutSync(NightscoutRequestSyncEvent event) {

        DataBundle databundle = new DataBundle();
        databundle.putInt("heart_rate",99);
        databundle.putInt("heart_acuracy",1);
        databundle.putInt("steps",2345);

        companionTransporter.send(Constants.ACTION_Amazfit_Healthdata, databundle);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Snooze(SnoozeEvent event) {

        DataBundle databundle = new DataBundle();
        databundle.putInt("UUID",99);

        Toast.makeText(this, "snoozing now", Toast.LENGTH_LONG).show();
        companionTransporter.send(Constants.ACTION_Amazfit_Snooze, databundle);

    }





}
