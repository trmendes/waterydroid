/*
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
