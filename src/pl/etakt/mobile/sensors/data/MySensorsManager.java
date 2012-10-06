package pl.etakt.mobile.sensors.data;

import pl.etakt.mobile.sensors.SensorsActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class MySensorsManager {
	
	private SensorsActivity instance;
	
	private Sensor ACCELEROMETER = null;
	private Sensor AMBIENT_TEMPERATURE = null;
	private Sensor GRAVITY = null;
	private Sensor GYROSCOPE = null;
	private Sensor LIGHT = null;
	private Sensor LINEAR_ACCELERATION = null;
	private Sensor MAGNETIC_FIELD = null;
	private Sensor ORIENTATION = null;
	private Sensor PRESSURE = null; //9
	private Sensor PROXIMITY = null;
	private Sensor RELATIVE_HUMIDITY = null;
	private Sensor ROTATION_VECTOR = null;
	private Sensor TEMPERATURE = null;
	
	private Object SOUND_SENSOR = null;
	
	private static final String TAG = "MySensorsManager";
	
	private SensorManager sensorManager;
	
	public MySensorsManager(SensorsActivity instance){
		this.instance = instance;
	}
	
	public boolean initiate(){
		return Boolean.TRUE;
	}

	/**
	 * @return the aCCELEROMETER
	 */
	public Sensor getACCELEROMETER() {
		return ACCELEROMETER;
	}

	/**
	 * @param aCCELEROMETER the aCCELEROMETER to set
	 */
	public void setACCELEROMETER(Sensor aCCELEROMETER) {
		ACCELEROMETER = aCCELEROMETER;
	}

	/**
	 * @return the aMBIENT_TEMPERATURE
	 */
	public Sensor getAMBIENT_TEMPERATURE() {
		return AMBIENT_TEMPERATURE;
	}

	/**
	 * @param aMBIENT_TEMPERATURE the aMBIENT_TEMPERATURE to set
	 */
	public void setAMBIENT_TEMPERATURE(Sensor aMBIENT_TEMPERATURE) {
		AMBIENT_TEMPERATURE = aMBIENT_TEMPERATURE;
	}

	/**
	 * @return the gRAVITY
	 */
	public Sensor getGRAVITY() {
		return GRAVITY;
	}

	/**
	 * @param gRAVITY the gRAVITY to set
	 */
	public void setGRAVITY(Sensor gRAVITY) {
		GRAVITY = gRAVITY;
	}

	/**
	 * @return the gYROSCOPE
	 */
	public Sensor getGYROSCOPE() {
		return GYROSCOPE;
	}

	/**
	 * @param gYROSCOPE the gYROSCOPE to set
	 */
	public void setGYROSCOPE(Sensor gYROSCOPE) {
		GYROSCOPE = gYROSCOPE;
	}

	/**
	 * @return the lIGHT
	 */
	public Sensor getLIGHT() {
		return LIGHT;
	}

	/**
	 * @param lIGHT the lIGHT to set
	 */
	public void setLIGHT(Sensor lIGHT) {
		LIGHT = lIGHT;
	}

	/**
	 * @return the lINEAR_ACCELERATION
	 */
	public Sensor getLINEAR_ACCELERATION() {
		return LINEAR_ACCELERATION;
	}

	/**
	 * @param lINEAR_ACCELERATION the lINEAR_ACCELERATION to set
	 */
	public void setLINEAR_ACCELERATION(Sensor lINEAR_ACCELERATION) {
		LINEAR_ACCELERATION = lINEAR_ACCELERATION;
	}

	/**
	 * @return the mAGNETIC_FIELD
	 */
	public Sensor getMAGNETIC_FIELD() {
		return MAGNETIC_FIELD;
	}

	/**
	 * @param mAGNETIC_FIELD the mAGNETIC_FIELD to set
	 */
	public void setMAGNETIC_FIELD(Sensor mAGNETIC_FIELD) {
		MAGNETIC_FIELD = mAGNETIC_FIELD;
	}

	/**
	 * @return the oRIENTATION
	 */
	public Sensor getORIENTATION() {
		return ORIENTATION;
	}

	/**
	 * @param oRIENTATION the oRIENTATION to set
	 */
	public void setORIENTATION(Sensor oRIENTATION) {
		ORIENTATION = oRIENTATION;
	}

	/**
	 * @return the pRESSURE
	 */
	public Sensor getPRESSURE() {
		return PRESSURE;
	}

	/**
	 * @param pRESSURE the pRESSURE to set
	 */
	public void setPRESSURE(Sensor pRESSURE) {
		PRESSURE = pRESSURE;
	}

	/**
	 * @return the pROXIMITY
	 */
	public Sensor getPROXIMITY() {
		return PROXIMITY;
	}

	/**
	 * @param pROXIMITY the pROXIMITY to set
	 */
	public void setPROXIMITY(Sensor pROXIMITY) {
		PROXIMITY = pROXIMITY;
	}

	/**
	 * @return the rELATIVE_HUMIDITY
	 */
	public Sensor getRELATIVE_HUMIDITY() {
		return RELATIVE_HUMIDITY;
	}

	/**
	 * @param rELATIVE_HUMIDITY the rELATIVE_HUMIDITY to set
	 */
	public void setRELATIVE_HUMIDITY(Sensor rELATIVE_HUMIDITY) {
		RELATIVE_HUMIDITY = rELATIVE_HUMIDITY;
	}

	/**
	 * @return the rOTATION_VECTOR
	 */
	public Sensor getROTATION_VECTOR() {
		return ROTATION_VECTOR;
	}

	/**
	 * @param rOTATION_VECTOR the rOTATION_VECTOR to set
	 */
	public void setROTATION_VECTOR(Sensor rOTATION_VECTOR) {
		ROTATION_VECTOR = rOTATION_VECTOR;
	}

	/**
	 * @return the tEMPERATURE
	 */
	public Sensor getTEMPERATURE() {
		return TEMPERATURE;
	}

	/**
	 * @param tEMPERATURE the tEMPERATURE to set
	 */
	public void setTEMPERATURE(Sensor tEMPERATURE) {
		TEMPERATURE = tEMPERATURE;
	}

	/**
	 * @return the sOUND_SENSOR
	 */
	public Object getSOUND_SENSOR() {
		return SOUND_SENSOR;
	}

	/**
	 * @param sOUND_SENSOR the sOUND_SENSOR to set
	 */
	public void setSOUND_SENSOR(Object sOUND_SENSOR) {
		SOUND_SENSOR = sOUND_SENSOR;
	}


}
