package com.company.ja.trabalhofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = new Intent();

        LinearLayout mapa = findViewById(R.id.linearMapa);
        LinearLayout obras = findViewById(R.id.linearObras);
        LinearLayout conta = findViewById(R.id.linearConta);
        LinearLayout melhor = findViewById(R.id.linearMelhores);
        LinearLayout pior = findViewById(R.id.linearPiores);
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
        Intent intent = new Intent(this,ObrasActivity.class);
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
}
