package sample.model;


import javafx.beans.property.SimpleStringProperty;

public class Funcionario {
    private int matricula;
    private SimpleStringProperty nome;
    private SimpleStringProperty setor;
    private SimpleStringProperty senha;
    private Privilegio fk_privilegio;

    public Funcionario(){
        nome = new SimpleStringProperty();
        setor = new SimpleStringProperty();
        senha = new SimpleStringProperty();
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
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


    public String getSetor() {
        return setor.get();
    }

    public void setSetor(String setor) {
        this.setor.set(setor);
    }

    public SimpleStringProperty setorProperty() {
        return setor;
    }


    public String getSenha() {
        return senha.get();
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public SimpleStringProperty senhaProperty() {
        return senha;
    }


    public Privilegio getFk_privilegio() {
        return fk_privilegio;
    }

    public void setFk_privilegio(Privilegio fk_privilegio) {
        this.fk_privilegio = fk_privilegio;
    }

//    @Override
//    public String toString() {
//        return "Funcionario{" +
//                "matricula=" + matricula +
//                ", nome=" + nome +
//                ", setor=" + setor +
//                ", senha=" + senha +
//                ", fk_privilegio=" + fk_privilegio +
//                '}';
//    }

    @Override
    public String toString() {
        return nome.get();
    }
}
