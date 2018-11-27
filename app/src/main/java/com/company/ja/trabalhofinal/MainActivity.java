package com.company.ja.trabalhofinal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.company.ja.trabalhofinal.model.Obra;
import com.company.ja.trabalhofinal.viewmodel.ObraViewModel;
import com.mapbox.mapboxsdk.annotations.BaseMarkerOptions;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.*;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapquest.android.commoncore.log.L;
import com.mapquest.mapping.*;
import com.mapquest.mapping.maps.MapView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private MapboxMap map;
    MapView mMapView;
    private MapboxMap mMapboxMap;
    private final LatLng LOCALIZATION_QUIXADA = new LatLng(-4.9699206,-39.0169499);
    FloatingActionButton info;
    Marker markerSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        info = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        mMapView.onCreate(savedInstanceState);
//        mMapView.setNightMode();
        Collection<MarkerOptions> listMarkerOptions = new ArrayList<>();

        ObraViewModel model = ViewModelProviders.of(this).get(ObraViewModel.class);
        model.getObras().observe(this, obras -> {
            L.d("ObraViewModel", "dados recuperados main");
            listMarkerOptions.clear();
            for(Obra ob : obras){
                L.d("ObraViewModel", "dados recuperados main "+obras.size());
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(new LatLng(ob.latitude,ob.longitude));
                markerOption.title(ob.descricao);
                markerOption.snippet("R$ "+ob.valor);
                listMarkerOptions.add(markerOption);
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(MapboxMap mapboxMap) {

                    mapboxMap.addMarkers((List<? extends BaseMarkerOptions>) listMarkerOptions);

                    mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCALIZATION_QUIXADA, 12));
                    mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            infoButton(true);
                            markerSelect = marker;
                            return false;
                        }

                    });
                    mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng point) {
                            infoButton(false);
                            markerSelect = null;
                        }
                    });

                }

            });
        });


        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mapboxMap.addMarkers((List<? extends BaseMarkerOptions>) listMarkerOptions);

                mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCALIZATION_QUIXADA, 12));
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        infoButton(true);
                        markerSelect = marker;
                        return false;
                    }

                });
                mapboxMap.setOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng point) {
                        infoButton(false);
                        markerSelect = null;
                    }
                });

            }

        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalhesObra();
            }
        });
    }

    public void infoButton(boolean visible){
        if(visible)
            findViewById(R.id.floatingActionButton2).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.floatingActionButton2).setVisibility(View.INVISIBLE);
    }

    public void detalhesObra(){
        Intent intent = new Intent(this, DetalhesActivity.class);
        intent.putExtra("nome", markerSelect.getTitle());
        intent.putExtra("valor", markerSelect.getSnippet());
        intent.putExtra("ordem", "05/09/2018");
        intent.putExtra("inicio", "10/11/2018");
        intent.putExtra("fim", "20/04/2019");
        intent.putExtra("situacao", "EM PROGRESSO");
        intent.putExtra("percentual", "87");
        intent.putExtra("avaliacao", "4.4");
        startActivity(intent);
    }

//    private void addMarker(MapboxMap mapboxMap) {
////        locations.add(new LatLng(-4.9364574, -39.1657405));
//
//        markerOptions.position(new LatLng(-4.9607298,-39.0357613));
//        markerOptions.title("OBRA 2");
//        markerOptions.snippet("R$ 320.320,00");
//        mapboxMap.addMarker(markerOptions);
//
//        markerOptions.position(new LatLng(-4.947916,-39.0237485));
//        markerOptions.title("OBRA 1");
//        markerOptions.snippet("R$ 1.255.320,00");
//        mapboxMap.addMarker(markerOptions);
//
//        markerOptions.position(new LatLng(-4.9577298,-39.0057613));
//        markerOptions.title("OBRA 2");
//        markerOptions.snippet("R$ 320.320,00");
//        mapboxMap.addMarker(markerOptions);
//
//        }

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
