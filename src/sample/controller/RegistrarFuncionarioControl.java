package sample.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.Funcionario;
import sample.model.JDBCs.JDBCFuncionarioDAO;
import sample.model.JDBCs.JDBCPrivilegioDAO;
import sample.model.Privilegio;

import java.io.IOException;

public class RegistrarFuncionarioControl {

    @FXML
    private ImageView idImg;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfSenha;
    @FXML
    private TextField tfSetor;
    @FXML
    private ComboBox cbPrivilegio;
    @FXML
    private BorderPane cena;

    public  void initialize(){
        cbPrivilegio.setItems(JDBCPrivilegioDAO.getInstance().listar());

        String url = "@/../Imagens/user.png";
        Image img = new Image(url);
        idImg.setImage(img);
    }
    @FXML
    public void salvar(){

        int matricula = Integer.parseInt(tfMatricula.getText());
        String nome = tfNome.getText();
        String senha = tfSenha.getText();
        String setor = tfSetor.getText();

        Privilegio idPrivilegio = (Privilegio) cbPrivilegio.getSelectionModel().getSelectedItem();

        Funcionario funcionario = new Funcionario();

        funcionario.setMatricula(matricula);
        funcionario.setNome(nome);
        funcionario.setSenha(senha);
        funcionario.setSetor(setor);
        funcionario.setFk_privilegio(idPrivilegio);

        JDBCFuncionarioDAO.getInstance().create(funcionario);

    }
    public void voltar(){
        mudaJanela("../view/ManterFuncionario.fxml");
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
    public void alertar(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
    }
}
