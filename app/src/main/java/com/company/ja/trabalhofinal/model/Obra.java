package com.company.ja.trabalhofinal.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Obra {

    public String key;
    public Double avaliacao;
    public String dataFim;
    public String dataInicio;
    public String dataOrdem;
    public String descricao;
    public Double latitude;
    public Double longitude;
    public Double percentual;
    public String situacao;
    public Double valor;
    public List<Comentario> comentarios;
    public List<Avaliacao> avaliacoes;

    public Obra(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataOrdem() {
        return dataOrdem;
    }

    public void setDataOrdem(String dataOrdem) {
        this.dataOrdem = dataOrdem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Double getPercentual() {
        return percentual;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("avaliacao", avaliacao);
        result.put("dataFim", dataFim);
        result.put("dataInicio", dataInicio);
        result.put("dataOrdem", dataOrdem);
        result.put("descricao", descricao);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("percentual", percentual);
        result.put("situacao", situacao);
        result.put("valor", valor);
        return result;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}
