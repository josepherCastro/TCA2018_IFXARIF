package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sample.model.Fornecedor;
import sample.model.JDBCs.JDBCRetiradaDAO;
import sample.model.Material;
import sample.model.Retirada;
import sample.model.UnidadeMedida;

import java.time.LocalDateTime;

public class DevolucaoControl {
    @FXML
    private TableView<Retirada> tvRetirados;
    @FXML
    private TableColumn<Retirada, Integer> tcIdRetirada;
    @FXML
    private TableColumn<Retirada, String> tcFuncionario;
    @FXML
    private TableColumn<Retirada, Double> tcQuantidade;
    @FXML
    private TableColumn<Retirada, String> tcMaterial;
    @FXML
    private TableColumn<Retirada, LocalDateTime> tcDataHora;

    @FXML
    private BorderPane cena;


    private ObservableList<Retirada> list = FXCollections.observableArrayList();

    public void initialize(){
        list.clear();

        tcIdRetirada.setCellValueFactory(new PropertyValueFactory<>("idRetirada"));
        tcFuncionario.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
        tcMaterial.setCellValueFactory(new PropertyValueFactory<>("materiais"));
        tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcDataHora.setCellValueFactory(new PropertyValueFactory<>("data"));

        list = JDBCRetiradaDAO.getInstance().listar();

        tvRetirados.setItems(list);
        tvRetirados.refresh();


    }

    public void click(MouseEvent mouseEvent) {

        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
        }
    }
}
