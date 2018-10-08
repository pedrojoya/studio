package es.iessaladillo.pedrojoya.pr134.base;

import android.content.Intent;
import android.os.BatteryManager;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class BatteryStatus {

    private final int status;
    private final boolean loading;
    private final int plugged;
    private final boolean pluggedToUsb;
    private final boolean pluggedToAc;
    private final int level;
    private final int scale;
    private final int health;
    private final float porcentage;

    BatteryStatus(@NonNull Intent intent) {
        status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        loading =
                status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
        plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        pluggedToUsb = plugged == BatteryManager.BATTERY_PLUGGED_USB;
        pluggedToAc = plugged == BatteryManager.BATTERY_PLUGGED_AC;
        level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        porcentage = level / (float)scale;
    }

    public int getStatus() {
        return status;
    }

    public boolean isLoading() {
        return loading;
    }

    public int getPlugged() {
        return plugged;
    }

    public boolean isPluggedToUsb() {
        return pluggedToUsb;
    }

    public boolean isPluggedToAc() {
        return pluggedToAc;
    }

    public int getLevel() {
        return level;
    }

    public int getScale() {
        return scale;
    }

    public int getHealth() {
        return health;
    }

    public float getPorcentage() {
        return porcentage;
    }

    public boolean hasColdHealth() {
        return health == BatteryManager.BATTERY_HEALTH_COLD;
    }

    public boolean hasDeadHealth() {
        return health == BatteryManager.BATTERY_HEALTH_DEAD;
    }

    public boolean hasGoodHealth() {
        return health == BatteryManager.BATTERY_HEALTH_GOOD;
    }

    public boolean hasOverheatHealth() {
        return health == BatteryManager.BATTERY_HEALTH_OVERHEAT;
    }

    public boolean hasOverVoltageHealth() {
        return health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE;
    }

    public boolean hasUnspecifiedFailureHealth() {
        return health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE;
    }

    public boolean hasUnknownHealth() {
        return health == BatteryManager.BATTERY_HEALTH_UNKNOWN;
    }

}
