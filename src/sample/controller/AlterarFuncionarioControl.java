package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Funcionario;
import sample.model.JDBCs.JDBCFuncionarioDAO;
import sample.model.JDBCs.JDBCPrivilegioDAO;
import sample.model.Privilegio;

import java.awt.*;
import java.io.IOException;

public class AlterarFuncionarioControl {
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfBuscarMatricula;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfSenha;
    @FXML
    private TextField tfSetor;
    @FXML
    private ComboBox cbPrivilegio;
    @FXML
    private Text tPrivilegio;
    private Funcionario funcionario;
    public void initialize(){
        cbPrivilegio.setItems(JDBCPrivilegioDAO.getInstance().listar());
    }
    @FXML
    public void btBuscar(){
        funcionario = JDBCFuncionarioDAO.getInstance().search(Integer.parseInt(tfBuscarMatricula.getText()));
        if(funcionario!=null){
            tfNome.setText(funcionario.getNome());
            tfMatricula.setText(String.valueOf(funcionario.getMatricula()));
            tfSenha.setText(funcionario.getSenha());
            tfSetor.setText(funcionario.getSetor());
            tPrivilegio.setText(funcionario.getFk_privilegio().toString());
        }else{
            System.out.println("Funcionario nao encontrado..");
        }
    }
    @FXML
    public void salvar(){

        Funcionario f = new Funcionario();
        Privilegio p = (Privilegio) cbPrivilegio.getSelectionModel().getSelectedItem();
        f.setNome(tfNome.getText());
        f.setMatricula(Integer.parseInt(tfMatricula.getText()));
        f.setSenha(tfSenha.getText());
        f.setSetor(tfSetor.getText());
        if(p!=null) {
            f.setFk_privilegio(p);
        }else{
            f.setFk_privilegio(funcionario.getFk_privilegio());
        }
        System.out.println(f.toString());

        JDBCFuncionarioDAO.getInstance().update(f);
    }
}
