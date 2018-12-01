package com.company.ja.trabalhofinal.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BOOTReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent j) {
        Intent i = new Intent(context, ComentariosIntentService.class);
        context.startService(i);

        Intent intent = new Intent(context, AlarmeReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, AlarmeReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                (AlarmManager.INTERVAL_HALF_DAY/2), pIntent);
    }
}
