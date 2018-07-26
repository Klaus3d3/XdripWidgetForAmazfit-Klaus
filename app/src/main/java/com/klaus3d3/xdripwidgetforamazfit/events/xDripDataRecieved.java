package com.klaus3d3.xdripwidgetforamazfit.events;

import com.huami.watch.transport.DataBundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import android.util.Base64;
import java.nio.ByteBuffer;
import android.provider.Settings;

public class xDripDataRecieved {
    private Long date;
    private String sgv;
    private String delta;
    private String direction;
    private Boolean islow;
    private Boolean ishigh;
    private Boolean isstale;
    private Boolean from_plugin;
    private String plugin_name;
    private Boolean alert;
    private String reply_message;
    private String low_predicted;
    private String in;
    private String space_mins;
    private String phone_battery;
    private double low_occurs_at;
    private Bitmap bitmap;
    private String bitmapstring;




    public xDripDataRecieved(DataBundle dataBundle) {
        date = (Long) dataBundle.get("date");
        sgv = dataBundle.getString("sgv");
        delta = dataBundle.getString("delta");

        islow = (Boolean) dataBundle.get("islow");
        ishigh = (Boolean) dataBundle.get("ishigh");
        isstale = (Boolean) dataBundle.get("isstale");
        from_plugin = (Boolean) dataBundle.get("fromplugin");
        plugin_name = dataBundle.getString("plugin_name");
        alert = dataBundle.getBoolean("activealarm");
        reply_message = dataBundle.getString("reply_message");
        low_predicted = dataBundle.getString("low_predicted");
        in = dataBundle.getString("in");
        space_mins = dataBundle.getString("space_mins");
        low_occurs_at = dataBundle.getDouble("low_occurs_at");
        phone_battery = dataBundle.getString("phone_battery");
        bitmapstring = dataBundle.getString("SGVGraph");
    }

    public Long getDate() {
        return date;
    }
    public void setDate(Long date) {
        this.date = date;
    }

    public String getPlugin_name() {
        return plugin_name;
    }
    public void setPlugin_name(String plugin_name) {
        this.plugin_name = plugin_name;
    }


    public String getPhone_battery() {
        return phone_battery;
    }
    public void setPhone_battery(String phone_battery) {
        this.phone_battery = phone_battery;
    }

    public String getReply_message() { return reply_message;}
    public void setReply_message(String reply_message) {
        this.reply_message = reply_message;
    }

    public Boolean getAlert() {
        return alert;
    }
    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public String getSgv() {
        return sgv;
    }
    public void setSgv(String sgv) {
        this.sgv = sgv;
    }

    public String getDelta() {
        return delta;
    }
    public void setDelta(String delta) {
        this.delta = delta;
    }

    public Boolean getIsstale() {
        return isstale;
    }
    public void setIsstale(Boolean isstale) {this.isstale= isstale;   }

    public Boolean getFrom_plugin() {
        return from_plugin;
    }
    public void setFrom_plugin(Boolean from_plugin) {this.from_plugin= from_plugin;   }

    public Boolean getIslow() {
        return islow;
    }
    public void setIslow(Boolean islow) {this.islow= islow;   }

    public Boolean getIshigh() {
        return ishigh;
    }
    public void setIshigh(Boolean ishigh) {this.ishigh= ishigh;   }

    public String getlow_predicted() {
        return low_predicted;
    }
    public void setlow_predicted(String low_predicted) {
        this.low_predicted = low_predicted;
    }

    public String getin() {
        return in;
    }
    public void setin(String in) {
        this.in = in;
    }

    public String getspace_mins() {
        return space_mins;
    }
    public void setspace_mins(String space_mins) {
        this.space_mins = space_mins;
    }

    public double getlow_occurs_at() {
        return low_occurs_at;
    }
    public void setlow_occurs_at(double low_occurs_at) {
        this.low_occurs_at = low_occurs_at;
    }

    public Bitmap getBitmap() {
        return StringToBitMap(bitmapstring);
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
