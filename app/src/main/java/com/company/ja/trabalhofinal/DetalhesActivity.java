package com.company.ja.trabalhofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.annotations.Marker;

import java.util.ArrayList;
import java.util.List;

public class DetalhesActivity extends AppCompatActivity {
    ListView listView;
    private List<String> comments = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    int myPosition = -1;
    String comentario;
    Intent intent;
    TextView nome;
    TextView valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        listView = (ListView) findViewById(R.id.listDados);
        intent = getIntent();
        nome = (TextView) findViewById(R.id.textView);
        valor = (TextView) findViewById(R.id.textView2);
        valor.setText(intent.getStringExtra("valor"));
        nome.setText(intent.getStringExtra("nome"));
        carregar();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                comentario = (String)parent.getItemAtPosition(position);
                myPosition = position;
                view.setSelected(true);
            }

        });
    }

    public void carregar(){
        comments.add("Show");
        comments.add("Gostei muito do projeto");
        comments.add("Show");
        comments.add("Gostei muito do projeto");
        comments.add("Show");
        comments.add("Gostei muito do projeto");
        comments.add("Show");
        comments.add("Gostei muito do projeto");
        comments.add("Show");
        comments.add("Gostei muito do projeto");
        arrayAdapter = new ArrayAdapter<String>(DetalhesActivity.this,android.R.layout.simple_list_item_1, comments);
        listView.setAdapter(arrayAdapter);
    }
}
