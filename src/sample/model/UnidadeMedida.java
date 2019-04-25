package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class UnidadeMedida {

    private int idUniMedida;
    private SimpleStringProperty nome;

    public UnidadeMedida(int idUniMedida, String nome){
        this.idUniMedida = idUniMedida;
        this.nome = new SimpleStringProperty(nome);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public int getIdUniMedida() {
        return idUniMedida;
    }

    public void setIdUniMedida(int idUniMedida) {
        this.idUniMedida = idUniMedida;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
