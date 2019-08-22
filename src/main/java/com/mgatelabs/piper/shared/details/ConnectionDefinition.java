package com.mgatelabs.piper.shared.details;

import com.mgatelabs.piper.shared.util.AdbShell;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by @mgatelabs (Michael Fuller) on 9/20/2017 for Phone-Piper
 */
public class ConnectionDefinition {

    private String ip;
    private int adbPort = 5555;
    private int helperPort = 8080;
    private String adb = "adb";
    private String direct;
    private boolean wifi;
    private int throttle = 250;
    private boolean useHelper;

    public ConnectionDefinition() {

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public String getAdb() {
        return adb;
    }

    public void setAdb(String adb) {
        this.adb = adb;
    }

    public int getThrottle() {
        return throttle;
    }

    public void setThrottle(int throttle) {
        this.throttle = throttle;
    }

    public int getAdbPort() {
        return adbPort;
    }

    public void setAdbPort(int adbPort) {
        this.adbPort = adbPort;
    }

    public int getHelperPort() {
        return helperPort;
    }

    public void setHelperPort(int helperPort) {
        this.helperPort = helperPort;
    }

    public boolean isUseHelper() {
        return useHelper;
    }

    public void setUseHelper(boolean useHelper) {
        this.useHelper = useHelper;
    }

    public void push() {
        if (StringUtils.isNotBlank(adb)) {
            AdbShell.ADB_PATH = adb;
        } else {
            AdbShell.ADB_PATH = "adb";
        }

        if (StringUtils.isNotBlank(direct)) {
            AdbShell.ADB_DIRECT = direct;
        } else {
            AdbShell.ADB_DIRECT = "";
        }
    }
}
