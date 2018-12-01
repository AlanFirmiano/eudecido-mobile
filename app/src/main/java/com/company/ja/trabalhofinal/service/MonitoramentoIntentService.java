package com.company.ja.trabalhofinal.service;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.company.ja.trabalhofinal.ObrasActivity;
import com.company.ja.trabalhofinal.R;
import com.company.ja.trabalhofinal.model.Obra;
import com.company.ja.trabalhofinal.viewmodel.ObraViewModel;
import com.facebook.FacebookSdk;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapquest.android.commoncore.log.L;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MonitoramentoIntentService extends IntentService {

    private DatabaseReference mDatabase;
    private DatabaseReference dbRef;
    private List<Obra> obras;
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    public MonitoramentoIntentService() {
        super("MonitoramentoIntentService");
        if(obras == null)
            obras = new ArrayList<>();
        loadObras();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("AQUI", "AQUI");

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location == null)
            return;
        longitude = location.getLongitude();
        latitude = location.getLatitude();

        if(latitude!=null && longitude!=null)
            checkLocation(""+latitude, ""+longitude);


    }

    private void checkLocation(String latitude, String logintude){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("AQUI", "AQUI 2 "+latitude+" "+logintude+" "+obras.size());
        for(Obra ob: obras){
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://www.mapquestapi.com/directions/v2/route?key=TK7UVh3Y32XF1nA1MGrakp19hQn7VGvk&from=" + latitude + "," + logintude +
                            "&to="+ob.latitude+","+ob.longitude+
                            "&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false",
                    null,
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                Double distancia = response.getJSONObject("route").getDouble("distance");

                                if (distancia >= 5) {
                                    NotificationManager notificationManager =
                                            (NotificationManager) getApplication().getSystemService(Service.NOTIFICATION_SERVICE);
                                    Notification notification = new NotificationCompat.Builder(getApplication(),"notification_id")
                                            .setSmallIcon(R.drawable.ic_icon)
                                            .setContentTitle("OBRAS PUBLICAS")
                                            .setContentText("Obras proximas, caso queira fiscalizar!")
                                            .setDefaults(NotificationCompat.DEFAULT_SOUND)
                                            .build();
                                    notificationManager.notify(0, notification);
                                }
                            } catch (Exception e) {

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
        }
    }


    private void loadObras() {
        L.d("ObraViewModel", "load obras");
        if(mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbRef = FirebaseDatabase.getInstance().getReference("obras");

        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Obra ob = dataSnapshot.getValue(Obra.class);
                obras.add(ob);
                //Teste
                //checkLocation(latitude+"",longitude+"");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addChildEventListener(childListener);

    }
}
