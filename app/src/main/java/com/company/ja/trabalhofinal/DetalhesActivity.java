package com.company.ja.trabalhofinal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.company.ja.trabalhofinal.listview.CustomList;
import com.company.ja.trabalhofinal.model.Avaliacao;
import com.company.ja.trabalhofinal.model.Comentario;
import com.company.ja.trabalhofinal.model.Obra;
import com.company.ja.trabalhofinal.model.Usuario;
import com.company.ja.trabalhofinal.viewmodel.ObraViewModel;
import com.company.ja.trabalhofinal.viewmodel.UsuarioViewModel;
import com.mapquest.android.commoncore.log.L;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    EditText editComent;
    FloatingActionButton comentar;
    ProgressBar progress;
    RatingBar myRatingBar;
    Obra obra;
    List<Obra> listObras;
    Integer[] imageId = {
            R.drawable.ic_person_black_24dp
    };
    String[] web = {
            "Java",
            "C++",
            "C#",
            "HTML",
            "CSS"
    } ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        listView = (ListView) findViewById(R.id.listDados);
        listObras = new ArrayList<>();
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
        editComent = (EditText) findViewById(R.id.editText4);
        myRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        comentar = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        //SET

        descricao.setText(intent.getStringExtra("nome"));

        dataOrdem.setText(formatDate(intent.getStringExtra("inicio")));
        dataInicioFim.setText(formatDate(intent.getStringExtra("inicio")) + "-" + formatDate(intent.getStringExtra("fim")));
        situacao.setText(intent.getStringExtra("situacao"));
        percentual.setText(intent.getStringExtra("percentual")+"%");
        avaliacao.setText(intent.getStringExtra("avaliacao"));
//        progress.setProgress(Integer.parseInt(percentual.getText().toString()));
//        rating.setRating(Float.parseFloat(avaliacao.getText().toString()));

        Integer prog = (int) Double.parseDouble(intent.getStringExtra("percentual"));

        progress.setProgress(prog);
        obra = new Obra();
        ObraViewModel model = ViewModelProviders.of(this).get(ObraViewModel.class);
        avaliacao.setText("0.0");
        model.getObra(intent.getStringExtra("key")).observe(this, obras -> {
            this.obra = obras;
            comments = obra.comentarios;
            funct();
            avaliacao.setText(""+this.obra.avaliacao);
            //carregar();
            carregarListView();
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                comentario = (String)parent.getItemAtPosition(position);
//                myPosition = position;
//                view.setSelected(true);
//            }
//
//        });
        comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(obra.comentarios==null){
                    obra.comentarios = new ArrayList<>();
                }
                Comentario com = new Comentario();
                com.setComentario(editComent.getText().toString());

                com.usuario = UsuarioViewModel.logado;

                obra.comentarios.add(com);
                editComent.setText("");
                model.updateObra(obra);
            }
        });
        myRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(obra!=null) {

                    try {
                        ratingBar.setRating(rating);
                        Avaliacao nova = new Avaliacao();
                        nova.setValor((double) rating);
                        nova.usuario = UsuarioViewModel.logado;
                        //obra.setAvaliacao((double) rating);
                        if(obra.avaliacoes==null){
                            obra.avaliacoes = new ArrayList<>();
                        }
                        obra.avaliacoes.add(nova);
                        L.e("ERRO -> "+obra.avaliacoes.size());
                        model.updateObra(obra);
                        Toast.makeText(getApplicationContext(), "AVALIAÇÃO -> " + rating, Toast.LENGTH_LONG).show();
                    }catch (Exception err){
                        Toast.makeText(getApplicationContext(), "Erro na avaliação!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Problema de conexão, Tente mais tarde!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

public void funct(){
    Locale l = new Locale("pt","BR");
    Locale.setDefault(l);
    NumberFormat nf = NumberFormat.getCurrencyInstance(l);
    if(obra.valor != null) {
        valor.setText(nf.format(obra.valor));
    }else{
        valor.setText("Valor não Informado!");
    }
}
    public void carregarListView(){
        CustomList listAdapter = new
                CustomList(DetalhesActivity.this, comments);
        listView=(ListView)findViewById(R.id.listDados);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(DetalhesActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String formatDate(String dateString){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(dateString);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
            return fmtOut.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "Data Indisponivel";
    }
}
