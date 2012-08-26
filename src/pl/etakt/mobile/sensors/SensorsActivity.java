package pl.etakt.mobile.sensors;

import pl.etakt.mobile.sensors.data.MySensorsManager;
import pl.etakt.sensors.R;
import android.app.Activity;
import android.os.Bundle;

public class SensorsActivity extends Activity {
	
	protected MySensorsManager mySensorsManager;
	
	protected SensorsActivity instance;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	instance = this;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initiate_internals();
    }
    
    private void initiate_internals(){
    	mySensorsManager = new MySensorsManager(instance);
    	mySensorsManager.initiate();
    }
    
    private void initiate_gui(){
    	;
    }
    
    private void initiate_actions(){
    	;
    }
    
    @Override
    public void onPause(){
    	
    }
    
    @Override
    public void onDestroy(){
    	
    }
}