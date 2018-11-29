package com.company.ja.trabalhofinal;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
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
        CardView conta = findViewById(R.id.cardConta);
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

        conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conta();
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
    }

    @Override
    public void onStart() {
        super.onStart();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(), "Logado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Tente novamente", Toast.LENGTH_LONG).show();
                        loginFB();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("FBERRO", exception.getMessage());
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        loginFB();
                    }
                });

        loginFB();
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

    public void conta(){
        Intent intent = new Intent(this,RegisterActivity.class);
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

}
