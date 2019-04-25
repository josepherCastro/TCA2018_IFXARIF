package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.model.Fornecedor;
import sample.model.JDBCs.JDBCFornecedorDAO;
import sun.net.TelnetInputStream;

import java.awt.*;
import java.io.IOException;

public class CadastrarFornecedorControl {
    @FXML
    private TextField tfCNPJ;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfEndereco;
    @FXML
    private TextField tfEmail;
    @FXML
    private VBox cena;

    @FXML
    public void btSalvar(){
        int cnpj = Integer.parseInt(tfCNPJ.getText());

        Fornecedor f = new Fornecedor(cnpj, tfNome.getText(), tfTelefone.getText(), tfEndereco.getText(), tfEmail.getText());

        JDBCFornecedorDAO.getInstance().create(f);
    }
    public Fornecedor processResult() throws Exception {

        //Fornecedor d= new Fornecedor();
        Fornecedor f = new Fornecedor(Integer.parseInt(tfCNPJ.getText()),
                tfNome.getText(), tfTelefone.getText(),
                tfEndereco.getText(), tfEmail.getText());
        JDBCFornecedorDAO.getInstance().create(f);


        return f;
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
}
