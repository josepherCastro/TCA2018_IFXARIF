package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.Funcionario;
import sample.model.JDBCs.JDBCFuncionarioDAO;
import sample.model.JDBCs.JDBCPrivilegioDAO;


import java.io.IOException;

public class LoginControl {
    @FXML
    private BorderPane cena;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfSenha;
    @FXML
    public Funcionario func = new Funcionario();


    @FXML
    public void btLogar() throws Exception {

        int matricula = Integer.parseInt(tfMatricula.getText());
        String senha = this.tfSenha.getText();

//        Funcionario func = new Funcionario();
        func.setMatricula(matricula);
        func.setSenha(senha);

        func = JDBCFuncionarioDAO.getInstance().verificaLogin(func);

        if(func.getNome()!=null){
            if(func.getFk_privilegio().getIdPrivilegio()==1){
                System.out.println("ADM_2018");
                mudaJanela("../view/ManterFuncionario.fxml");
            }else if(func.getFk_privilegio().getIdPrivilegio()==2){
                System.out.println("Almoxarife_2018");
                mudaJanela("../view/ManterMateriais.fxml");
            }else{
                alertar("ERRO!", "Acesso Negado", "Funcionario "+
                        JDBCPrivilegioDAO.getInstance().search(func.getFk_privilegio().getIdPrivilegio()).getDescricao()
                        +" não Autorizado");
            }
        }else {
            alertar("ERRO!", "Falha de Autenticação","Matricula ou Senha Invalido(s)");
        }
    }

    public void alertar(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
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

    public Funcionario getFunc() {
        return func;
    }

    public void setFunc(Funcionario func) {
        this.func = func;
    }
}
