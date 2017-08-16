package com.beijing.qchealth.qchealth_vip.event;

/**
 * Created by lyq on 2016/6/1.
 */
public class UpDataMessEvent {
   
    public static final int MESSAGE_COUNT=1;
    public static final int ADDRESS_COUNT=2;
    public static final int CHAT_MESS=0x03;
    private Object message;
    private int state;

    public UpDataMessEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
