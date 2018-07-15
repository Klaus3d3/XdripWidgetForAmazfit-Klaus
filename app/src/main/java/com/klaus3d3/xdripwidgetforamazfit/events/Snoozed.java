package com.klaus3d3.xdripwidgetforamazfit.events;


import com.huami.watch.transport.DataBundle;

public class Snoozed {
    private int snoozetime;




    public Snoozed(DataBundle dataBundle) {
        snoozetime = dataBundle.getInt("snoozetime");

    }

    public int getsnoozetime() {
        return snoozetime;
    }
    public void setsnoozetime(int snoozetime) {
        this.snoozetime = snoozetime;
    }

}
