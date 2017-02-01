package com.skcs.kiosk.pinpad;

/**
 * Created by adeirwansiah on 11/15/16.
 */
public class PinpadResult {
    public int result;
    private byte[] respData;

    public PinpadResult(int result) {
        this.result = result;
    }

    public PinpadResult(int result, byte[] respData) {
        this.result = result;
        this.respData = respData.clone();
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public byte[] getRespData() {
        return respData;
    }

    public void setRespData(byte[] respData) {
        this.respData = respData.clone();
    }

    public String getString() {
        return new String(this.respData);
    }

    public byte[] getBytes() {
        return this.respData;
    }

    @Override
    public String toString() {
        String str = "result=[" + getResult() + "], respData=[" + ((this.respData == null) ? "null" : getString()) + "]";
        return str;
    }
}
