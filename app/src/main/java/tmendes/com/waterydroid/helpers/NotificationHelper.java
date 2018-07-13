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
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;

import tmendes.com.waterydroid.R;

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
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(notificationChannel);
        }
    }

    public Notification.Builder getNotification(String title,
                                                String body) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String notificationsNewMessageRingtone = prefs.getString("notifications_new_message_ringtone", "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            Notification.Builder notification = new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setColorized(true)
                    .setColor(Color.BLUE)
                    .setShowWhen(true)
                    .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.drawable.ic_stat_local_drink)
                    .setAutoCancel(true);

            if (notificationsNewMessageRingtone.length() > 0) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                notification.setSound(Uri.parse(notificationsNewMessageRingtone), audioAttributes);
            }
            return notification;
        } else {
            //noinspection deprecation
            Notification.Builder notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.drawable.ic_stat_local_drink)
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                notification.setShowWhen(true);
            }

            if (notificationsNewMessageRingtone.length() > 0) {
                //noinspection deprecation
                notification.setSound(Uri.parse(notificationsNewMessageRingtone));
            }
            return notification;
        }
    }

    private boolean shallNotify() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        boolean notificationsNewMessage = prefs.getBoolean("notifications_new_message", true);
        boolean doNotDisturb = false;

        long startTimestamp = prefs.getLong("pref_notification_start", 0);
        long stopTimestamp = prefs.getLong("pref_notification_stop", 0);

        if (startTimestamp > 0 && stopTimestamp > 0) {
            Date now = Calendar.getInstance().getTime();
            Date start = new Date(startTimestamp);
            Date stop = new Date(stopTimestamp);

            doNotDisturb = !(now.after(start) && now.before(stop));
        }

        return notificationsNewMessage && doNotDisturb;
    }
    public void notify(long id, Notification.Builder notification) {
        if (shallNotify()) {
            getManager().notify((int) id, notification.build());
        }
    }

    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
}
