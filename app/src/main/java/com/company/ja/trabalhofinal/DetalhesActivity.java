package com.company.ja.trabalhofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.company.ja.trabalhofinal.model.Comentario;
import com.mapbox.mapboxsdk.annotations.Marker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetalhesActivity extends AppCompatActivity {
    ListView listView;
    private List<Comentario> comments = new ArrayList<>();
    private ArrayAdapter<Comentario> arrayAdapter;
    int myPosition = -1;
    String comentario;
    Intent intent;
    //DADOS
    TextView descricao;
    TextView valor;
    TextView dataOrdem;
    TextView dataInicioFim;
    TextView situacao;
    TextView percentual;
    TextView avaliacao;
    ProgressBar progress;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        listView = (ListView) findViewById(R.id.listDados);
        intent = getIntent();
        //GET
        descricao = (TextView) findViewById(R.id.textView);
        valor = (TextView) findViewById(R.id.textView2);
        dataOrdem = (TextView) findViewById(R.id.ordem);
        dataInicioFim = (TextView) findViewById(R.id.inicioFim);
        situacao = (TextView) findViewById(R.id.situacao);
        percentual = (TextView) findViewById(R.id.percentual);
        avaliacao = (TextView) findViewById(R.id.avaliacao);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        //SET
        if(intent.getStringExtra("valor") != null) {
            valor.setText(""+intent.getStringExtra("valor"));
        }else{
            valor.setText("Valor não Informado!");
        }
        descricao.setText(intent.getStringExtra("nome"));
        dataOrdem.setText(intent.getStringExtra("ordem"));
        dataInicioFim.setText(intent.getStringExtra("inicio") + "-" + intent.getStringExtra("fim"));
        situacao.setText(intent.getStringExtra("situacao"));
        percentual.setText(intent.getStringExtra("percentual")+"%");
        avaliacao.setText(intent.getStringExtra("avaliacao"));
//        progress.setProgress(Integer.parseInt(percentual.getText().toString()));
//        rating.setRating(Float.parseFloat(avaliacao.getText().toString()));

        Integer prog = (int) Double.parseDouble(intent.getStringExtra("percentual"));

        progress.setProgress(prog);
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
        Comentario c = new Comentario();
        Comentario c2 = new Comentario();
        Comentario c3 = new Comentario();
        Comentario c4 = new Comentario();
        Comentario c5 = new Comentario();
        c.setComentario("Show");
        comments.add(c);
        c2.setComentario("Gostei");
        comments.add(c2);
        c3.setComentario("Muito bom");
        comments.add(c3);
        c4.setComentario("Show");
        comments.add(c4);
        c5.setComentario("Não Gostei");
        comments.add(c5);
        arrayAdapter = new ArrayAdapter<Comentario>(DetalhesActivity.this,android.R.layout.simple_list_item_1, comments);
        listView.setAdapter(arrayAdapter);
    }
}
