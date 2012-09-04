package pl.etakt.mobile.sensors.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Sensor responder that allows a single BroadcastReceiver to be used to 
 * return information about a single supported sensors. 
 *  
 * @author Olufemi Omojola <oluseyi@alum.mit.edu>
 */
public abstract class SingleSensorInformationResponder extends SensorInformationResponder 
{	
	protected SingleSensorInformationResponder()
	{
	}
	
	/**
	 * The display name of the sensor suite that provides this BroadcastResponder.
	 * 
	 * @return The display name of the sensor suite that provides this BroadcastResponder.
	 */ 
	public abstract String getName();

	/**
	 * The display description of the particular sensor data associated with this data type: this would be something like "Heart Rate"
	 * 
	 * @return The display description of the particular sensor data associated with this data type.
	 */ 
	public abstract String getSensorDescription();
	
	/**
	 * The mime type for the particular sensor data returned by this sensor. For heart rate, this would be application/vnd.sensor.pulse
	 * 
	 * @return The mime type for the particular sensor data returned by this sensor.
	 */
	public abstract String getSensorDataType();
	
	/**
	 * A list of the communication schemes this package supports.
	 * 
	 * @return A list of the communication schemes this package supports. 
	 */
	public abstract String[] getSchemes();
	
	/**
	 * A list of the Intent actions this package supports. At a minimum all sensor support packages
	 * are required to support the Intent.ACTION_EDIT, Intent.ACTION_VIEW, and Intent.ACTION_GET_CONTENT
	 * actions. Additional actions may be supported and can be made available to client packages with
	 * additional development effort. 
	 * 
	 * @return A list of the Intent actions this package supports.
	 */
	public abstract String[] getActions();
		
	/**
	 * Implements the actual BroadcastResponder onReceive() method, populating the 
	 * result bundle that describes this sensor's data and adding it to the 
	 * broadcast results.
	 */
	@Override
    public final void onReceive(Context context, Intent intent) {
        Bundle b = new Bundle();
        b.putString(VERSION, "1");
        b.putString(NAME, getName());
        b.putStringArray("0", new String[]{ getSensorDescription(), getSensorDataType() });
        b.putStringArray("0.schemes", getSchemes());
        b.putStringArray("0.actions", getActions());
        b.putInt(ENTRIES, 1);
        Bundle results = getResultExtras(true);
        results.putBundle(String.valueOf(results.size()), b);
    }
}
