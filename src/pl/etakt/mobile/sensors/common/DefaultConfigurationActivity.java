package pl.etakt.mobile.sensors.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

/**
 * Simple default configuration activity: can be used for any sensor suite that doesn't have any 
 * specific configuration functionality: simply extend this class and declare it in your manifest
 * with the appropriate IntentFilter.
 *  
 * @author Olufemi Omojola <oluseyi@alum.mit.edu>
 */
public class DefaultConfigurationActivity extends Activity implements OnClickListener 
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	 
	    RelativeLayout layout = new RelativeLayout(this);
	    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
	    layout.setLayoutParams(lp);
	    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	    lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    TextView tv = new TextView(this);
	    tv.setText("No configuration is necessary for this sensor.");
	    layout.addView(tv, lp);
	    Button b = new Button(this);
	    b.setText("Done");
	    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    layout.addView(b, lp);
	    b.setOnClickListener(this);
	    setContentView(layout);
	}
	
	public void onClick(View v) {
		setResult(Activity.RESULT_OK);
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
	    	finish();
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
