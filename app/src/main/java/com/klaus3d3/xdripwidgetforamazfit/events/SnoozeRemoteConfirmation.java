package com.klaus3d3.xdripwidgetforamazfit.events;
import com.huami.watch.transport.DataBundle;

public class SnoozeRemoteConfirmation {
    private String reply_message;

    public SnoozeRemoteConfirmation(DataBundle dataBundle) {

        reply_message = dataBundle.getString("reply_message");
    }

    public String getReply_message() { return reply_message;}
    public void setReply_message(String reply_message) {
        this.reply_message = reply_message;
    }
}
