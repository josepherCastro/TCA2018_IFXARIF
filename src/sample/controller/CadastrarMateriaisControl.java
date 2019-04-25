package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.model.Fornecedor;
import sample.model.JDBCs.JDBCFornecedorDAO;
import sample.model.JDBCs.JDBCMaterialDAO;
import sample.model.JDBCs.JDBCUnidadeMedidaDAO;
import sample.model.Material;
import sample.model.UnidadeMedida;

import java.io.IOException;

public class CadastrarMateriaisControl {
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfQuantMinima;
    @FXML
    private TextField tfQuantTotal;
    @FXML
    private CheckBox chbDevolutivo;
    @FXML
    private ComboBox cbUniMedida;
    @FXML
    private ComboBox cbFornecedor;
    @FXML
    private VBox cena;

    private ObservableList<UnidadeMedida> litaUniMedida = FXCollections.observableArrayList();
    private ObservableList<Fornecedor> listaFornecedor = FXCollections.observableArrayList();

    public void initialize(){

        listaFornecedor.clear();
        litaUniMedida.clear();

        litaUniMedida = JDBCUnidadeMedidaDAO.getInstance().listar();
        listaFornecedor = JDBCFornecedorDAO.getInstance().listar();

        cbUniMedida.setItems(litaUniMedida);
        cbFornecedor.setItems(listaFornecedor);

    }

    @FXML
    public void btSalvar(){
        UnidadeMedida uniMedida = (UnidadeMedida) cbUniMedida.getSelectionModel().getSelectedItem();
        Fornecedor fornecedor = (Fornecedor) cbFornecedor.getSelectionModel().getSelectedItem();

        Material material = new Material(tfNome.getText(), uniMedida, Double.parseDouble(tfQuantTotal.getText()), Double.parseDouble(tfQuantMinima.getText()), fornecedor, chbDevolutivo.isSelected());

        JDBCMaterialDAO.getInstance().create(material);

        System.out.println(material);
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
