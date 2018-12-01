package com.company.ja.trabalhofinal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.company.ja.trabalhofinal.listview.CustomList;
import com.company.ja.trabalhofinal.listview.ObrasList;
import com.company.ja.trabalhofinal.model.Obra;
import com.company.ja.trabalhofinal.viewmodel.ObraViewModel;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapquest.android.commoncore.log.L;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObrasActivity extends AppCompatActivity {
    ListView listView;
    private List<Obra> listObras = new ArrayList<>();
    private ArrayAdapter<Obra> arrayAdapter;
    int myPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);
        listView = (ListView) findViewById(R.id.listObras);

        Collection<MarkerOptions> listMarkerOptions = new ArrayList<>();

        ObraViewModel model = ViewModelProviders.of(this).get(ObraViewModel.class);
        model.getObras().observe(this, obras -> {
            this.listObras = obras;
            listMarkerOptions.clear();

            carregarListView();
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myPosition = position;
                view.setSelected(true);
                L.d("CLICK","CLICOU NA OBRA -> "+listObras.get(position));
                detalhesObra(myPosition);
            }

        });


    }

    public void detalhesObra(int position){
        Obra selectObra = listObras.get(position);
        Intent intent = new Intent(this, DetalhesActivity.class);
        intent.putExtra("key", selectObra.getKey());
        intent.putExtra("nome", selectObra.getDescricao());
        intent.putExtra("valor", selectObra.getValor());
        intent.putExtra("ordem", selectObra.getDataOrdem());
        intent.putExtra("inicio", selectObra.getDataInicio());
        intent.putExtra("fim", selectObra.getDataFim());
        intent.putExtra("situacao", selectObra.getSituacao().toUpperCase());
        intent.putExtra("percentual", ""+selectObra.getPercentual());
        intent.putExtra("avaliacao", ""+selectObra.getAvaliacao());
        startActivity(intent);
    }

    public void carregarListView(){
        ObrasList listAdapter = new
                ObrasList(ObrasActivity.this, listObras);
        listView.setAdapter(listAdapter);
    }

}
