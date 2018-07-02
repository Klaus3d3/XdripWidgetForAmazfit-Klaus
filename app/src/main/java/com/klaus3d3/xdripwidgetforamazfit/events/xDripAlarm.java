package com.klaus3d3.xdripwidgetforamazfit.events;
import com.huami.watch.transport.DataBundle;
public class xDripAlarm {

    private String reply_message;
    private String alarmtext;
    private String uuid;
    public xDripAlarm(DataBundle databundle){
        alarmtext=databundle.getString("alarmtext");
        reply_message = databundle.getString("reply_message");
        uuid=databundle.getString("uuid");
    }

    public String getReply_message() { return reply_message;}
    public void setReply_message(String reply_message) {this.reply_message = reply_message;  }

    public String getalarmtext() { return alarmtext;}
    public void setalarmtext(String alarmtext) {this.alarmtext = alarmtext;  }

    public String getuuid() { return uuid;}
    public void setuuid(String uuid) {this.uuid = uuid;  }
}
