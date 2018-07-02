package com.klaus3d3.xdripwidgetforamazfit.events;

import com.huami.watch.transport.DataBundle;

    public class xDripCancelConfirmation {
    private String reply_message;


    public xDripCancelConfirmation(DataBundle databundle){

        reply_message = databundle.getString("reply_message");

    }

    public String getReply_message() { return reply_message;}
    public void setReply_message(String reply_message) {this.reply_message = reply_message;  }

}
