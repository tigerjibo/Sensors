package pl.etakt.mobile.sensors.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Sensor responder that allows a single BroadcastReceiver to be used to 
 * return information about multiple supported sensors. 
 *  
 * @author Olufemi Omojola <oluseyi@alum.mit.edu>
 */
public abstract class MultiSensorInformationResponder extends SensorInformationResponder 
{	
	private Bundle info;
	private int offset;
	
	protected MultiSensorInformationResponder()
	{
		info = new Bundle();
		info.putString(VERSION, "1");
		offset = 0;
	}
	
	protected final void setName(String name)
	{
        info.putString(NAME, name);
	}
	
	protected final void add(String sensorDescription, String sensorMimeType, 
			String[] schemes, String[] actions)
	{
		String key = String.valueOf(offset);
		info.putStringArray(key, new String[]{ sensorDescription, sensorMimeType });
		info.putStringArray(key+".schemes", schemes);
		info.putStringArray(key+".actions", actions);
		++offset;
		info.putInt(ENTRIES, offset);
	}
			
	@Override
    public final void onReceive(Context context, Intent intent) {
        Bundle results = getResultExtras(true);
        results.putBundle(String.valueOf(results.size()), info);
    }
}
