package pl.etakt.mobile.sensors.engine;

import android.util.Log;

/**
 * 
 * DebugIndicator, tells wheter the app is in dev stage or not
 * 
 * internal only
 * 
 * @author o.jarczyk
 * @since 1.0
 *
 */
public class DebugIndicator {
	
	private static String TAG = "DebugIndicator";
	
	// tells whether the app is in dev stage or not
	// used for ADMOB module : )
	public static final boolean DEBUG = true;
	
	// tells whether the developer wants to test opengl and camera capture or not
	public static final boolean TEST_ENGINE = false;
	public static final boolean TEST_ENGINE2 = false;
	public static final boolean TEST_ENGINE3 = false;
	
	public static boolean isDebug(){
		Log.d(TAG, "isDebug() returns " + DEBUG);
		return DEBUG;
	}
	
	public static boolean isTestEngine(){
		Log.d(TAG, "isTestEngine() returns " + TEST_ENGINE);
		return TEST_ENGINE;
	}
	
	public static boolean isTestEngine2(){
		Log.d(TAG, "isTestEngine2() returns " + TEST_ENGINE2);
		return TEST_ENGINE2;
	}
	
	public static boolean isTestEngine3(){
		Log.d(TAG, "isTestEngine3() returns " + TEST_ENGINE3);
		return TEST_ENGINE3;
	}

}
