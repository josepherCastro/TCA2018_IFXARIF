package sample.model;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Retirada {
    private int idRetirada;
    private Funcionario idFuncionario;
    private ObservableList<Material> materiais;
    private double quantidade;
    private LocalDateTime data;

    public int getIdRetirada() {
        return idRetirada;
    }

    public void setIdRetirada(int idRetirada) {
        this.idRetirada = idRetirada;
    }

    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public ObservableList<Material> getIdMateriais() {
        return materiais;
    }

    public void setIdMateriais(ObservableList<Material> materiais) {
        this.materiais = materiais;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public ObservableList<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(ObservableList<Material> materiais) {
        this.materiais = materiais;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Retirada{" +
                "idRetirada=" + idRetirada +
                ", idFuncionario=" + idFuncionario.getMatricula() +
                ", materiais=" + materiais +
                ", quantidade=" + quantidade +
                ", data=" + data +
                '}';
    }
}
