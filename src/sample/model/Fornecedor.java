package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class Fornecedor {
    private int cnpj;
    private SimpleStringProperty nome;
    private SimpleStringProperty telefone;
    private SimpleStringProperty endereco;
    private SimpleStringProperty email;

    public Fornecedor(int cnpj, String nome, String telefone, String endereco, String email) {
        this.cnpj = cnpj;
        this.nome = new SimpleStringProperty(nome);
        this.telefone = new SimpleStringProperty(telefone);
        this.endereco = new SimpleStringProperty(endereco);
        this.email = new SimpleStringProperty(email);
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
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


    public String getTelefone() {
        return telefone.get();
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public SimpleStringProperty telefoneProperty() {
        return telefone;
    }


    public String getEndereco() {
        return endereco.get();
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public SimpleStringProperty enderecoProperty() {
        return endereco;
    }


    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    @Override
    public String toString() {
        return getNome();
    }
    //@Override
    public String toString2(){
        return "\nCNPJ:\t"+getCnpj()+
                "\nNome:\t"+getNome()+
                "\nTelefone:\t"+getTelefone()+
                "\nE-mail:\t"+getEmail()+
                "\nEndereço:\t"+getEndereco()+".";
    }
}
