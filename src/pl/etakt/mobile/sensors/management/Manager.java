package pl.etakt.mobile.sensors.management;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * Sensor management class that lists all available sensors and provides
 * an interface to explore the functionality of a specific sensor suite. 
 *  
 * @author Olufemi Omojola <oluseyi@alum.mit.edu>
 */
public class Manager extends ListActivity
{
	private static final int GET_CONTENT = 1;
	private PackageAdapter adapter;
	private Button refresh;
	private TextView emptyLabel;
	private Bundle sources;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    sources = null;
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.main);
	    adapter = null;
	    emptyLabel = (TextView)findViewById(android.R.id.empty);
	    refresh = (Button)findViewById(R.id.refresh);
	    refresh.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				refresh();		
			}
		});
	}
	
	void refresh()
	{		
		adapter.clear();
		adapter.notifyDataSetChanged();
		new scanPackages(this).execute();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		if(adapter == null){
			adapter = new PackageAdapter(this, R.layout.row, new ArrayList<Bundle>(20));
			setListAdapter(adapter);
			new scanPackages(this).execute();
		}
	}
	
	public void setupProgress()
	{
		refresh.setEnabled(false);
		setProgressBarIndeterminateVisibility(true);
		emptyLabel.setText(getString(R.string.label_scanningforpackages)); 
	}
	
	public void setResults(Bundle results)
	{
		Iterator<String> it = results.keySet().iterator();
		sources = results;
		Bundle b;
		int offset = 0;
		while(it.hasNext()){
			b = results.getBundle(it.next());
			b.putInt("OFFSET", offset);
			adapter.add(b);
			++offset;
		}
	}
	
	public void scanComplete()
	{
		setProgressBarIndeterminateVisibility(false);
		refresh.setEnabled(true);
		adapter.notifyDataSetChanged();
	}
	
	private class PackageAdapter extends ArrayAdapter<Bundle>
	{
		private List<Bundle> packages;
		public PackageAdapter(Context context, int textViewResourceId, List<Bundle> items)
		{
			super(context, textViewResourceId, items);
			packages = items;
		}
	
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, parent, false);
			}
			Bundle b = packages.get(position);
			TextView tv = (TextView) v.findViewById(R.id.description);
			if (tv != null) tv.setText(b.getString(SingleSensorInformationResponder.NAME));
			
			int entrycount = b.getInt(SingleSensorInformationResponder.ENTRIES);
			if(entrycount > 0){
				String[] baseDetails = b.getStringArray(String.valueOf(entrycount - 1));
				tv = (TextView) v.findViewById(R.id.sensorname);
				if (tv != null) tv.setText(baseDetails[0]);
				
				Button btn = (Button)v.findViewById(R.id.configure);
				btn.setTag(b);
				btn.setOnClickListener(new OnClickListener(){ public void onClick(View v){ go(v); }});
				btn.setVisibility(View.VISIBLE);
				
				btn = (Button)v.findViewById(R.id.capture);
				btn.setTag(b);
				btn.setOnClickListener(new OnClickListener(){ public void onClick(View v){ go(v); }});
				btn.setVisibility(View.VISIBLE);
				
				btn = (Button)v.findViewById(R.id.view);
				if(b.containsKey(Intent.ACTION_GET_CONTENT)){
					btn.setTag(b);
					btn.setOnClickListener(new OnClickListener(){ public void onClick(View v){ go(v); }});
					btn.setVisibility(View.VISIBLE);
				}else{
					btn.setVisibility(View.GONE);
				}
			}else{
				tv = (TextView) v.findViewById(R.id.sensorname);
				if (tv != null) tv.setVisibility(View.GONE);
				Button btn = (Button)v.findViewById(R.id.configure);
				btn.setVisibility(View.GONE);
				btn = (Button)v.findViewById(R.id.capture);
				btn.setVisibility(View.GONE);
				btn = (Button)v.findViewById(R.id.view);
				btn.setVisibility(View.GONE);
			}
			return v;
		}    	
	}
	
	private class scanPackages extends AsyncTask<String, Bundle, Boolean>
	{
		private WeakReference<Manager> manager;
		private boolean done;
		
		public scanPackages(Manager manager)
		{ 
			this.manager = new WeakReference<Manager>(manager);
		}
		
		@Override
		protected void onPreExecute()
		{
			if(manager != null){
				Manager m = manager.get();
				if(m != null) m.setupProgress();
			}
		}
		
		@Override
		protected void onPostExecute(Boolean result)
		{
			if(manager != null){
				Manager m = manager.get();
				if(m != null) m.scanComplete();
			}
		}
	
		@Override
		protected void onProgressUpdate(Bundle... progress)
		{
			if(manager != null){
				Manager m = manager.get();
				if(m != null) m.setResults(progress[0]);
			}
		}
		
		protected Boolean doInBackground(String... params)
		{
    		done = false;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(Uri.EMPTY, "application/vnd.sensor");
            sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    publishProgress(getResultExtras(true));
                    done = true;
                }
            }, null, ListActivity.RESULT_OK, null, null);

            do{
            	try{ Thread.sleep(100); }catch(InterruptedException ie){}
            }while(!done);
    		return Boolean.TRUE;
    	}
    }
	
	public void go(View v) {
		Bundle b = (Bundle)v.getTag();
		int entrycount = b.getInt(SingleSensorInformationResponder.ENTRIES);
		if(entrycount > 0){
			String key = String.valueOf(entrycount - 1);
			String[] baseDetails = b.getStringArray(key);
			if(v.getId() == R.id.configure){
				// fire off the relevant Intent
				Intent i = new Intent(Intent.ACTION_EDIT);
				i.setType(baseDetails[1]);
				startActivity(i);
			}else if(v.getId() == R.id.capture){
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.setType(baseDetails[1]);
				startActivityForResult(i, GET_CONTENT+b.getInt("OFFSET"));
			}else if(v.getId() == R.id.view){
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setType(baseDetails[1]);
				loadData(i, b);
				startActivity(i);
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		// find the appropriate list item
		int offset = requestCode - GET_CONTENT;
		if(adapter != null){
			if(adapter.getCount() > offset){
				Bundle b = adapter.getItem(offset);
				if(resultCode == RESULT_OK){
					Toast.makeText(this, getString(R.string.label_data_returned), Toast.LENGTH_LONG).show();
					saveData(b, data);
					adapter.notifyDataSetChanged();
				}else{
					Toast.makeText(this, getString(R.string.label_capture_aborted), Toast.LENGTH_LONG).show();
				}
			}
		}else{
			if(resultCode == RESULT_OK){
				Toast.makeText(this, getString(R.string.label_data_returned_waiting_for_refresh), Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this, getString(R.string.label_capture_aborted_waiting_for_refresh), Toast.LENGTH_LONG).show();
			}
		}
	}
	
	void loadData(Intent target, Bundle source)
	{
		String type = source.getString(Intent.CATEGORY_INFO);
		if(source.containsKey(Intent.ACTION_GET_CONTENT)){
			if("int".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getInt(Intent.ACTION_GET_CONTENT));
			}else if("short".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getShort(Intent.ACTION_GET_CONTENT));
			}else if("long".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getLong(Intent.ACTION_GET_CONTENT));
			}else if("float".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getFloat(Intent.ACTION_GET_CONTENT));
			}else if("double".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getDouble(Intent.ACTION_GET_CONTENT));
			}else if("string".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getString(Intent.ACTION_GET_CONTENT));
			}else if("intarray".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getIntArray(Intent.ACTION_GET_CONTENT));
			}else if("shortarray".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getShortArray(Intent.ACTION_GET_CONTENT));
			}else if("longarray".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getLongArray(Intent.ACTION_GET_CONTENT));
			}else if("floatarray".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getFloatArray(Intent.ACTION_GET_CONTENT));
			}else if("doublearray".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getDoubleArray(Intent.ACTION_GET_CONTENT));
			}else if("stringarray".equals(type)){
				target.putExtra(Intent.ACTION_GET_CONTENT, 
						source.getStringArray(Intent.ACTION_GET_CONTENT));
			}
		}
	}	
	
	void saveData(Bundle target, Intent source)
	{
		String type = source.getStringExtra(Intent.CATEGORY_INFO);
		target.putString(Intent.CATEGORY_INFO, type);
		if(source.hasExtra(Intent.ACTION_GET_CONTENT)){
			if("int".equals(type)){
				target.putInt(Intent.ACTION_GET_CONTENT, 
						source.getIntExtra(Intent.ACTION_GET_CONTENT, 0));
			}else if("short".equals(type)){
				target.putShort(Intent.ACTION_GET_CONTENT, 
						source.getShortExtra(Intent.ACTION_GET_CONTENT, (short)0));
			}else if("long".equals(type)){
				target.putLong(Intent.ACTION_GET_CONTENT, 
						source.getLongExtra(Intent.ACTION_GET_CONTENT, (long)0));
			}else if("float".equals(type)){
				target.putFloat(Intent.ACTION_GET_CONTENT, 
						source.getFloatExtra(Intent.ACTION_GET_CONTENT, (float)0));
			}else if("double".equals(type)){
				target.putDouble(Intent.ACTION_GET_CONTENT, 
						source.getDoubleExtra(Intent.ACTION_GET_CONTENT, (double)0));
			}else if("string".equals(type)){
				target.putString(Intent.ACTION_GET_CONTENT, 
						source.getStringExtra(Intent.ACTION_GET_CONTENT));
			}else if("intarray".equals(type)){
				target.putIntArray(Intent.ACTION_GET_CONTENT, 
						source.getIntArrayExtra(Intent.ACTION_GET_CONTENT));
			}else if("shortarray".equals(type)){
				target.putShortArray(Intent.ACTION_GET_CONTENT, 
						source.getShortArrayExtra(Intent.ACTION_GET_CONTENT));
			}else if("longarray".equals(type)){
				target.putLongArray(Intent.ACTION_GET_CONTENT, 
						source.getLongArrayExtra(Intent.ACTION_GET_CONTENT));
			}else if("floatarray".equals(type)){
				target.putFloatArray(Intent.ACTION_GET_CONTENT, 
						source.getFloatArrayExtra(Intent.ACTION_GET_CONTENT));
			}else if("doublearray".equals(type)){
				target.putDoubleArray(Intent.ACTION_GET_CONTENT, 
						source.getDoubleArrayExtra(Intent.ACTION_GET_CONTENT));
			}else if("stringarray".equals(type)){
				target.putStringArray(Intent.ACTION_GET_CONTENT, 
						source.getStringArrayExtra(Intent.ACTION_GET_CONTENT));
			}
		}
	}
	
	
	@Override 
    public void onSaveInstanceState(Bundle outState) 
    {
        super.onSaveInstanceState(outState);
        if(sources != null){
        	outState.putBundle("sources", sources);
        }
    }
	
	@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) 
    {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("sources")){
			adapter = new PackageAdapter(this, R.layout.row, new ArrayList<Bundle>(20));
			setListAdapter(adapter);
			setResults(savedInstanceState.getBundle("sources"));
        }
    }
}
