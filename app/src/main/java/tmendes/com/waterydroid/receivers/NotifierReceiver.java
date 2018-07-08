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

package tmendes.com.waterydroid.receivers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import tmendes.com.waterydroid.R;
import tmendes.com.waterydroid.helpers.AlarmHelper;
import tmendes.com.waterydroid.helpers.NotificationHelper;

public class NotifierReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //long notificationFrequency = prefs.getLong("notification_frequency", 120);

        boolean notificationsNewMessage = prefs.getBoolean("notifications_new_message", true);

        if (notificationsNewMessage) {
            String title = context.getResources().getString(R.string.app_name);
            String messageToShow = prefs.getString("message_to_show", context.getResources().getString(R.string.pref_notification_message_value));
            /* Notify */
            NotificationHelper nHelper = new NotificationHelper(context);
            @SuppressLint("ResourceType") Notification.Builder nBuilder = nHelper
                    .getNotification(title, messageToShow, null, null);
            nHelper.notify(System.currentTimeMillis(), nBuilder);
        }
    }
}
