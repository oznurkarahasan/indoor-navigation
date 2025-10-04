package com.example.zimzezor;

import java.util.Objects;

public class DeviceInfo {
    private String deviceName;
    private int rssi;

    public DeviceInfo(String deviceName, int rssi) {
        this.deviceName = deviceName;
        this.rssi = rssi;
    }

    public String getName() {
        return deviceName;
    }

    public int getRssi() {
        return rssi;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return deviceName + "\nRSSI: " + rssi + " dBm";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeviceInfo that = (DeviceInfo) obj;
        return deviceName.equals(that.deviceName); // Sadece cihaz adı kontrol ediliyor
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceName); // Sadece cihaz adı üzerinden hash üretiliyor
    }
}
