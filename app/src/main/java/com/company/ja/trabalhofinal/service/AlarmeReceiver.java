package com.company.ja.trabalhofinal.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmeReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MonitoramentoIntentService.class);
        context.startService(i);
    }
}
