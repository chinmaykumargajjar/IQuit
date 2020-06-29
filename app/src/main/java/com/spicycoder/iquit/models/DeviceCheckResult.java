package com.spicycoder.iquit.models;

public class DeviceCheckResult {
    boolean result;
    String resultString;

    public DeviceCheckResult(boolean result, String resultString) {
        this.result = result;
        this.resultString = resultString;
    }

    public boolean isResult() {
        return result;
    }

    public String getResultString() {
        return resultString;
    }
}
