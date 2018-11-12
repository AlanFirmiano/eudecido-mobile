package com.company.ja.trabalhofinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
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
        Intent intent = new Intent(this,ObrasActivity.class);
        startActivity(intent);
    }

    public void top10P(){
        Intent intent = new Intent(this,ObrasActivity.class);
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
