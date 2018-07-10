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

package tmendes.com.waterydroid.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import tmendes.com.waterydroid.R;
import tmendes.com.waterydroid.receivers.SnoozeReceiver;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager notificationManager;

    private static final String CHANNEL_ONE_ID = "tmendes.com.waterydroid.CHONE";
    private static final String CHANNEL_ONE_NAME = "Channel One";

    private Context ctx;

    public NotificationHelper(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        createChannels();
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            String notificationsNewMessageRingtone = prefs.getString("notifications_new_message_ringtone", "");
            Boolean vibrate = prefs.getBoolean("pref_title_vibrate", true);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();

            Log.i("Bla - notificationsNewMessageRingtone", notificationsNewMessageRingtone);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(vibrate);
            notificationChannel.setSound(Uri.parse(notificationsNewMessageRingtone), audioAttributes);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(notificationChannel);
        }
    }

    public Notification.Builder getNotification(String title,
                                                String body,
                                                Bitmap notifyPicture,
                                                PendingIntent pI) {
        PendingIntent piSnooze = PendingIntent.getBroadcast(ctx, 0, new Intent(ctx, SnoozeReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        boolean notificationsPersistent = prefs.getBoolean("notifications_persistent", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Action action = new Notification.Action.Builder(R.drawable.ic_sync_black_24dp, ctx.getResources().getString(R.string.snooze_message), piSnooze).build();
            return new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setColorized(true)
                    .setColor(Color.BLUE)
                    .setContentIntent(pI)
                    .setLargeIcon(notifyPicture)
                    .setOngoing(notificationsPersistent)
                    .addAction(action)
                    .setSmallIcon(R.drawable.ic_sync_black_24dp)
                    .setAutoCancel(true);
        } else {
            String notificationsNewMessageRingtone = prefs.getString("notifications_new_message_ringtone", "");
            //noinspection deprecation
            return new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setContentIntent(pI)
                    .setOngoing(notificationsPersistent)
                    .setSound(Uri.parse(notificationsNewMessageRingtone))
                    .addAction(R.drawable.ic_sync_black_24dp, ctx.getResources().getString(R.string.snooze_message), piSnooze)
                    .setLargeIcon(notifyPicture)
                    .setSmallIcon(R.drawable.ic_sync_black_24dp)
                    .setAutoCancel(true);
        }
    }

    public void notify(long id, Notification.Builder notification) {
        getManager().notify((int) id, notification.build());
    }

    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
}
