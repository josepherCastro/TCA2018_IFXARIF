package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.Funcionario;
import sample.model.JDBCs.JDBCFuncionarioDAO;

import java.io.IOException;

public class ManterFuncionarioControl {
    @FXML
    private TableView tvFuncCadastrados;
    @FXML
    private TableColumn tcMatricula;
    @FXML
    private TableColumn tcNome;
    @FXML
    private TableColumn tcSetor;
    @FXML
    private TableColumn tcPrivilegio;
//    @FXML
//    private TableColumn tcSenha;
    @FXML
    private Parent cena;

    private ObservableList<Funcionario> list = FXCollections.observableArrayList();

    public void initialize(){
        list.clear();

        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
//        tcSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        tcSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        tcPrivilegio.setCellValueFactory(new PropertyValueFactory<>("fk_privilegio"));

        list = JDBCFuncionarioDAO.getInstance().listar();
        tvFuncCadastrados.setItems(list);

    }
    @FXML
    public void mCadastrarFunc(){ mudaJanela("../view/RegistrarFuncionario.fxml");}
    @FXML
    public void mAlterar(){ dialogo("Alterar","../view/AlterarFuncionario.fxml");}
    @FXML
    public void mExcluir(){}
    @FXML
    public void btLogoff(){
        mudaJanela("../view/Login.fxml");
    }
    public void mudaJanela(String endereco){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(endereco));

                try {
                    Parent layoutJanela = loader.load();

                    Stage stage=(Stage)cena.getScene().getWindow();

                    stage.setScene(new Scene(layoutJanela,520, 320));
                    stage.setResizable(false);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void dialogo(String nome, String endereco){

        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle(nome);

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(endereco));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);

            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        } catch (NullPointerException e ){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
