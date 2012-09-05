package pl.etakt.mobile.sensors.engine;

import pl.etakt.mobile.sensors.SensorsActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class MyBatteryManager {
	
	private IntentFilter ifilter;
	private Intent batteryStatus;
	
	private SensorsActivity instance;
	
	private Context context;
	
	public MyBatteryManager(SensorsActivity instance){
		
		this.instance = instance;
		
		context = instance.getApplicationContext();
		
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
