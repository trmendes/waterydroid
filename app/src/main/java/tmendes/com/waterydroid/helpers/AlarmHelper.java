/*
 * Copyright (C) 2015-2016 The Food Restriction Project Team
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

package tmendes.com.waterydroid.helpers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import tmendes.com.waterydroid.receivers.BootReceiver;
import tmendes.com.waterydroid.receivers.NotifierReceiver;

import static android.app.AlarmManager.INTERVAL_DAY;

public class AlarmHelper {

    private AlarmManager alarmManager;

    private final static String ACTION_BD_NOTIFICATION = "tmendes.com.waterydroid.NOTIFICATION";

    public void setAlarm(Context context, long notificationFrenquency) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, NotifierReceiver.class);
        alarmIntent.setAction(ACTION_BD_NOTIFICATION);

        PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() + notificationFrenquency,
                notificationFrenquency,
                pendingAlarmIntent);

        /* Restart if rebooted */
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        context.getPackageManager().setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, NotifierReceiver.class);
        alarmIntent.setAction(ACTION_BD_NOTIFICATION);

        PendingIntent pendingAlarmIntent = PendingIntent.getBroadcast(context,
                0,
                alarmIntent,
                0);
        alarmManager.cancel(pendingAlarmIntent);

        /* Alarm won't start again if device is rebooted */
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}