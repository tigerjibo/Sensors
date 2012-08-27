package pl.etakt.mobile.sensors.data;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class TalkService extends IntentService {

	private Thread tRecord;

	public TalkService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Context context = getApplicationContext();
		tRecord = new Thread(new recordAudio());
		tRecord.start();

		while (tRecord.isAlive()) {
			if (getIsDone()) {
				if (aRecorder.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
					socketConnection(context, host, port);
				}
			}
		}
	}

	class recordAudio implements Runnable {

		private AudioRecord aRecorder;

		public void run() {
			try {
				OutputStream osFile = new FileOutputStream(file);
				BufferedOutputStream bosFile = new BufferedOutputStream(osFile);
				DataOutputStream dosFile = new DataOutputStream(bosFile);

				aRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
						sampleRate, channelInMode, encodingMode, bufferSize);

				data = new short[bufferSize];

				aRecorder.setPositionNotificationPeriod(sampleRate);
				aRecorder
						.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {
							int count = 1;

							@Override
							public void onPeriodicNotification(
									AudioRecord recorder) {
								Log.e(WiFiDirect.TAG, "Period notf: " + count++);

								if (getRecording() == false) {
									aRecorder.stop();
									aRecorder.release();
									setIsDone(true);
									Log.d(WiFiDirect.TAG,
											"Recorder stopped and released prematurely");
								}
							}

							@Override
							public void onMarkerReached(AudioRecord recorder) {
								// TODO Auto-generated method stub

							}
						});

				aRecorder.startRecording();
				Log.d(WiFiDirect.TAG, "start Recording");
				aRecorder.read(data, 0, bufferSize);
				for (int i = 0; i < data.length; i++) {
					dosFile.writeShort(data[i]);
				}

				if (aRecorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
					aRecorder.stop();
					aRecorder.release();
					setIsDone(true);
					Log.d(WiFiDirect.TAG, "Recorder stopped and released");
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
