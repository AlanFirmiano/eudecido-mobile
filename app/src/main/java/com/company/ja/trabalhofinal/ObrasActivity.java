package com.company.ja.trabalhofinal;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.company.ja.trabalhofinal.model.Obra;

import java.util.ArrayList;
import java.util.List;

public class ObrasActivity extends Activity {
    ListView listView;
    private List<Obra> obras = new ArrayList<>();
    private ArrayAdapter<Obra> arrayAdapter;
    int myPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obras);
        listView = (ListView) findViewById(R.id.listObras);

        carregar();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myPosition = position;
                view.setSelected(true);
            }

        });
    }

    public void carregar(){
        Obra ob = new Obra();
        ob.setDescricao("OBRA1");
        obras.add(ob);
        Obra ob2 = new Obra();
        ob.setDescricao("OBRA2");
        obras.add(ob2);
        listView.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<Obra>(ObrasActivity.this,android.R.layout.simple_list_item_1, obras);
    }

}
