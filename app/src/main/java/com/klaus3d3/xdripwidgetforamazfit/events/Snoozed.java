package com.klaus3d3.xdripwidgetforamazfit.events;


import com.huami.watch.transport.DataBundle;

public class Snoozed {
    private String snoozetime;




    public Snoozed(DataBundle dataBundle) {
        snoozetime = dataBundle.getString("snoozetime");

    }

    public String getsnoozetime() {
        return snoozetime;
    }
    public void setsnoozetime(String snoozetime) {
        this.snoozetime = snoozetime;
    }

}
