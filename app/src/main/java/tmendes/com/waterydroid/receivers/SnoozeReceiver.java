package tmendes.com.waterydroid.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tmendes.com.waterydroid.helpers.AlarmHelper;

public class SnoozeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

/*		// Set AlarmManager to show again in 10min
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		int snoozeInterval = prefs.getInt("snooze_interval", 10);
		new AlarmHelper().setAlarm(context, snoozeInterval);

		// Delete the notification
		NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nManager.cancel(1);*/
	}
}
