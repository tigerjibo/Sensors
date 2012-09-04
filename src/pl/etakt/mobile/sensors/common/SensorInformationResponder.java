package pl.etakt.mobile.sensors.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Intent extra keys and sensor scheme definitions.
 *  
 * @author Olufemi Omojola <oluseyi@alum.mit.edu>
 */
abstract class SensorInformationResponder extends BroadcastReceiver 
{
	public static final String VERSION = "SensorInformationResponder.VERSION";
	public static final String NAME = "SensorInformationResponder.NAME";
	public static final String ENTRIES = "SensorInformationResponder.ENTRIES";
	
	/*
	 * Scheme for built-in sensors.
	 */
	public static final String SCHEME_BUILTIN = "sensor+builtin";
	
	/*
	 * Scheme for Bluetooth sensors.
	 */
	public static final String SCHEME_BLUETOOTH = "sensor+bluetooth";
	
	/*
	 * Scheme for sensors with custom communication.
	 */
	public static final String SCHEME_CUSTOM = "sensor+custom";
	
	/*
	 * Scheme for TCP sensors.
	 */
	public static final String SCHEME_TCP = "sensor+tcp";
	
	/*
	 * Scheme for UDP sensors.
	 */
	public static final String SCHEME_UDP = "sensor+udp";
	
	/*
	 * Scheme for HTTP sensors.
	 */
	public static final String SCHEME_HTTP = "sensor+http";
	
	/*
	 * Scheme for HTTPS sensors.
	 */
	public static final String SCHEME_HTTPS = "sensor+https";
}
