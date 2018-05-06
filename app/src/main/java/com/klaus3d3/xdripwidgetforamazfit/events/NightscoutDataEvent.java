package com.klaus3d3.xdripwidgetforamazfit.events;

import com.huami.watch.transport.DataBundle;
/**
 * Created by edoardotassinari on 10/04/18.
 * modded by klaus3d3 for xdrip
 */

public class NightscoutDataEvent {

    private Long date;
    private String sgv;
    private String delta;
    private String direction;
    private Boolean islow;
    private Boolean ishigh;
    private Boolean isstale;
    private Boolean from_plugin;
    private String plugin_name;
    private String extra_string;
    private int warning;

    public NightscoutDataEvent(DataBundle dataBundle) {
        date = (Long) dataBundle.get("date");
        sgv = dataBundle.getString("sgv");
        delta = dataBundle.getString("delta");
        islow = (Boolean) dataBundle.get("islow");
        ishigh = (Boolean) dataBundle.get("ishigh");
        isstale = (Boolean) dataBundle.get("isstale");
        from_plugin = (Boolean) dataBundle.get("from_plugin");
        plugin_name = dataBundle.getString("plugin_name");
        extra_string = dataBundle.getString("extra_string");
        warning = dataBundle.getInt("warning");
    }

    public Long getDate() {
        return date;
    }
    public void setDate(Long date) {
        this.date = date;
    }

    public int getWarning() {
        return warning;
    }
    public void setWarning (int warning) { this.warning = warning; }

    public String getPlugin_name() {
        return plugin_name;
    }
    public void setPlugin_name(String plugin_name) {
        this.plugin_name = plugin_name;
    }

    public String getExtrastring() {
        return extra_string;
    }
    public void setExtrastring(String extra_string) {
        this.extra_string = extra_string;
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


}
