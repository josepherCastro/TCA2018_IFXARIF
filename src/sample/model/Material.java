package sample.model;

import javafx.beans.property.SimpleStringProperty;
import sample.model.JDBCs.JDBCMaterialDAO;

public class Material {
    private int idMaterial;
    private SimpleStringProperty nome;
    private double quantidade;
    private double quantidadeMinima;
    private boolean devolutivo;
    private UnidadeMedida fk_unidadeMedida;
    private Fornecedor fk_fornecedor;

    public Material(int idMaterial, String nome, UnidadeMedida unidadeMedida, double quantidade, double quantidadeMinima, Fornecedor fornecedor, boolean devolutivo) {
        this.idMaterial = idMaterial;
        this.nome = new SimpleStringProperty(nome);
        this.fk_unidadeMedida = unidadeMedida;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.fk_fornecedor = fornecedor;
        this.devolutivo = devolutivo;
    }

    public Material(String nome, UnidadeMedida unidadeMedida, double quantidade, double quantidadeMinima, Fornecedor fornecedor, boolean devolutivo) {
        this.nome = new SimpleStringProperty(nome);
        this.fk_unidadeMedida = unidadeMedida;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.fk_fornecedor = fornecedor;
        this.devolutivo = devolutivo;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
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

    public SimpleStringProperty nomeFornecedorProperty() {
        return new SimpleStringProperty(fk_fornecedor.getNome());
    }


    public UnidadeMedida getUnidadeMedida() {
        return fk_unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.fk_unidadeMedida = unidadeMedida;
    }


    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }


    public double getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(double quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }


    public Fornecedor getFornecedor() {
        return fk_fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fk_fornecedor = fornecedor;
    }


    public boolean isDevolutivo() {
        return devolutivo;
    }

    public void setDevolutivo(boolean devolutivo) {
        this.devolutivo = devolutivo;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
