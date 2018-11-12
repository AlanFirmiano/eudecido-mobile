package com.company.ja.trabalhofinal.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Date;

public class Obra {

    private Double avaliacao;
    private Date dataFim;
    private Date dataInicio;
    private Date dataOrdem;
    private String descricao;
    private String latitude;
    private String longitude;
    private Double percentual;
    private String situacao;
    private Double valor;

    public Obra(){

    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataOrdem() {
        return dataOrdem;
    }

    public void setDataOrdem(Date dataOrdem) {
        this.dataOrdem = dataOrdem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    @Override
    public String toString() {
        return getDescricao();
    }
}
