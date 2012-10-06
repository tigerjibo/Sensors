package pl.etakt.mobile.sensors.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;

public class AverageNoise {
	
	private Thread recordingThread;
	private int bufferSize = 800;
	private short[][] buffers = new short[256][bufferSize];
	private int[] averages = new int[256];
	private int lastBuffer = 0;
	
	private static String TAG = "AverageNoise";
	
	public AverageNoise (){
		
		Log.d(TAG, "AverageNoise object created");
	}
	
	protected AudioRecord recorder;
	private boolean recorderStarted;

	protected void startListenToMicrophone() {
	    if (!recorderStarted) {

	        recordingThread = new Thread() {

				@Override
	            public void run() {
	                int minBufferSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
	                        AudioFormat.ENCODING_PCM_16BIT);
	                recorder = new AudioRecord(AudioSource.MIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
	                        AudioFormat.ENCODING_PCM_16BIT, minBufferSize * 10);
	                recorder.setPositionNotificationPeriod(bufferSize);
	                recorder.setRecordPositionUpdateListener(new OnRecordPositionUpdateListener() {
	                    @Override
	                    public void onPeriodicNotification(AudioRecord recorder) {
	                        short[] buffer = buffers[++lastBuffer % buffers.length];
	                        recorder.read(buffer, 0, bufferSize);
	                        long sum = 0;
	                        for (int i = 0; i < bufferSize; ++i) {
	                            sum += Math.abs(buffer[i]);
	                        }
	                        averages[lastBuffer % buffers.length] = (int) (sum / bufferSize);
	                        lastBuffer = lastBuffer % buffers.length;
	                    }

	                    @Override
	                    public void onMarkerReached(AudioRecord recorder) {
	                    }
	                });
	                recorder.startRecording();
	                short[] buffer = buffers[lastBuffer % buffers.length];
	                recorder.read(buffer, 0, bufferSize);
	                while (true) {
	                    if (isInterrupted()) {
	                        recorder.stop();
	                        recorder.release();
	                        break;
	                    }
	                }
	            }
	        };
	        recordingThread.start();

	        recorderStarted = true;
	    }
	}

	private void stopListenToMicrophone() {
	    if (recorderStarted) {
	        if (recordingThread != null && recordingThread.isAlive() && !recordingThread.isInterrupted()) {
	            recordingThread.interrupt();
	        }
	        recorderStarted = false;
	    }
	}


}
