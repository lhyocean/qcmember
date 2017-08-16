package com.beijing.qchealth.qchealth_vip.event;

/**
 * Created by lyq on 2016/6/1.
 */
public class TestEvent {
    public static final int ADDRESS_ADD=1;
    public static final int ADDRESS_DELETE=2;
    public static final int ADDRESS_UPDATA=3;
    public static final int ADDRESS_SELECT=4;
    private Object message;
    private int state;

    public TestEvent(int state) {
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
