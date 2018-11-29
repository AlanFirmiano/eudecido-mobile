package com.company.ja.trabalhofinal.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Usuario {
    public String key;
    public String id;
    public String nome;
    public String fotoUrl;

    public Usuario(){

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
