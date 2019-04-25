package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class Privilegio {
    private int idPrivilegio;
    private int tipo;
    private SimpleStringProperty descricao;

    public Privilegio(int id, int tipo, String descricao) {
        this.idPrivilegio = id;
        this.tipo = tipo;
        this.descricao = new SimpleStringProperty(descricao);
    }

    public Privilegio(){}

    public int getIdPrivilegio() {
        return idPrivilegio;
    }

    public void setIdPrivilegio(int idPrivilegio) {
        this.idPrivilegio = idPrivilegio;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public SimpleStringProperty descriçãoProperty() {
        return descricao;
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}