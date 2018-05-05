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



    public NightscoutDataEvent(DataBundle dataBundle) {
        date = (Long) dataBundle.get("date");
        sgv = dataBundle.getString("sgv");
        delta = dataBundle.getString("delta");
        islow = (Boolean) dataBundle.get("islow");
        ishigh = (Boolean) dataBundle.get("ishigh");
    }

    public Long getDate() {
        return date;
    }
    public void setDate(Long date) {
        this.date = date;
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

    public Boolean getIslow() {
        return islow;
    }
    public void seIslow(Boolean islow) {this.islow= islow;   }

    public Boolean getIshigh() {
        return ishigh;
    }
    public void seIshigh(Boolean ishigh) {this.ishigh= ishigh;   }


}
