package com.company.ja.trabalhofinal;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.company.ja.trabalhofinal.model.Usuario;
import com.company.ja.trabalhofinal.service.AlarmeReceiver;
import com.company.ja.trabalhofinal.service.ComentariosIntentService;
import com.company.ja.trabalhofinal.viewmodel.ObraViewModel;
import com.company.ja.trabalhofinal.viewmodel.UsuarioViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 1;

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    private MapboxMap map;
    MapView mMapView;
    private MapboxMap mMapboxMap;
    private final LatLng LOCALIZATION_QUIXADA = new LatLng(-4.9699206,-39.0169499);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());

        setContentView(R.layout.activity_menu);
        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.invalidate();
        Intent intent = new Intent();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCALIZATION_QUIXADA, 12));
            }
        });

        CardView mapa = findViewById(R.id.cardMap);
        CardView obras = findViewById(R.id.cardList);
        CardView melhor = findViewById(R.id.cardTopM);
        CardView pior = findViewById(R.id.cardTopP);

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapa();
            }
        });

        obras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obras();
            }
        });

        melhor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top10M();
            }
        });

        pior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top10P();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();

        loginFB();
    }

    @Override
    public void onStart() {
        super.onStart();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(), "Bem vindo", Toast.LENGTH_LONG).show();
                        getUserFB(loginResult.getAccessToken().getToken(), loginResult.getAccessToken().getUserId());
                        checkPermission();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Tente novamente", Toast.LENGTH_LONG).show();
                        loginFB();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        loginFB();
                    }
                });
    }

    public void getUserFB(String token, String userId){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+userId+"?fields=id,picture.type(large),name",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject userJson = response.getJSONObject();
                        try {

                            if(UsuarioViewModel.logado == null)
                                UsuarioViewModel.logado = new Usuario();
                            UsuarioViewModel.logado.id = userId;
                            UsuarioViewModel.logado.nome = userJson.getString("name");
                            UsuarioViewModel.logado.fotoUrl = userJson.getJSONObject("picture").getJSONObject("data").getString("url");
                            //Iniciar services
                            startAlarm();
                            startMonitoramentodeObras();
                        }catch (Exception e){}
                    }
                }
        ).executeAsync();
    }

    public void loginFB(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }



    public void mapa(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void obras(){
        Intent intent = new Intent(this,ObrasActivity.class);
        startActivity(intent);
    }

    public void top10M(){
        Intent intent = new Intent(this,TopMelhoresActivity.class);
        startActivity(intent);
    }

    public void top10P(){
        Intent intent = new Intent(this,TopPioresActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }

    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }

    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permissão")
                        .setMessage("Solicito a permissão para acessar o GPS")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MenuActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }


    public void startAlarm(){
        Intent intent = new Intent(getApplicationContext(), AlarmeReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmeReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                (AlarmManager.INTERVAL_HALF_DAY/2), pIntent);
    }

    void startMonitoramentodeObras(){
        Intent i = new Intent(getApplication(), ComentariosIntentService.class);
        getApplication().startService(i);
    }

}
