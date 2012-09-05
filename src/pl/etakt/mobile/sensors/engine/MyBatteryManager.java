package pl.etakt.mobile.sensors.engine;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class MyBatteryManager {
	
	IntentFilter ifilter;
	Intent batteryStatus;
	
	public MyBatteryManager(){
		
		ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = context.registerReceiver(null, ifilter);
		
	}
	
	public boolean isCharging(){
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
		                     status == BatteryManager.BATTERY_STATUS_FULL;
		return isCharging;
	}

}
