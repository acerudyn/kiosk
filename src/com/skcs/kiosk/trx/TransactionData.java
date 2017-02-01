package com.skcs.kiosk.trx;


import com.skcs.kiosk.pinpad.Pinpad;

/**
 * Created by adeirwansiah on 11/15/16.
 */
public class TransactionData {

    private static TransactionData instance = null;

    String track2;
    String tranTitle;

    protected TransactionData() {
        // Exists only to defeat instantiation.
    }

    public static TransactionData getInstance() {
        if (instance == null) {
            instance = new TransactionData();
        }
        return instance;
    }

    public String getTrack2() {
        return track2;
    }

    public void setTrack2(String track2) {
        this.track2 = track2;
    }

    public String getTranTitle() {
        return tranTitle;
    }

    public void setTranTitle(String tranTitle) {
        this.tranTitle = tranTitle;
    }

    public void initData() {
        track2 = "";
        tranTitle = "";
    }
}
