package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.*;
import sample.model.JDBCs.JDBCFornecedorDAO;
import sample.model.JDBCs.JDBCFuncionarioDAO;
import sample.model.JDBCs.JDBCMaterialDAO;
import sample.model.JDBCs.JDBCRetiradaDAO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ManterMateriaisControl {
    private int flag=3;
    @FXML
    private TableView<Material> tvMateriaisCadastrados;
    @FXML
    private TableColumn<Material, Integer> tcIdMaterial;
    @FXML
    private TableColumn<Material, String> tcNome;
    @FXML
    private TableColumn<Material, Double> tcQuantidade;
    @FXML
    private TableColumn<Material, UnidadeMedida> tcUniMedida;
    @FXML
    private TableColumn<Material, Double> tcMinimo;
    @FXML
    private TableColumn<Material, Integer> tcdevolutivo;
    @FXML
    private TableColumn<Material, Fornecedor> tcFornecedor;

    @FXML
    private TableView<Material> tvMateriaisSelecionados;
    @FXML
    private TableColumn<Material, Integer> tcIdMaterialSelect;
    @FXML
    private TableColumn<Material, String> tcNomeSelect;
    @FXML
    private TableColumn<Material, Double> tcQuantidadeSelect;
    @FXML
    private TableColumn<Material, UnidadeMedida> tcUniMedidaSelect;
    @FXML
    private TableColumn<Material, Double> tcMinimoSelect;
    @FXML
    private TableColumn<Material, Integer> tcdevolutivoSelect;
    @FXML
    private TableColumn<Material, String> tcFornecedorSelect;
    @FXML
    private TextField tfMatricula;

    @FXML
    private Parent cena;

    private ObservableList<Material> list = FXCollections.observableArrayList();
    private ObservableList<Material> listCopy = FXCollections.observableArrayList();
    private ObservableList<Material> listSelect = FXCollections.observableArrayList();

    public void initialize(){

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcUniMedida.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcdevolutivo.setCellValueFactory(new PropertyValueFactory<>("devolutivo"));
        tcFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        if(flag==3){
            list.clear();
            list = JDBCMaterialDAO.getInstance().listar();
        }

        tvMateriaisCadastrados.setItems(list);
        tvMateriaisCadastrados.refresh();

        tcNomeSelect.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcUniMedidaSelect.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        tcQuantidadeSelect.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcdevolutivoSelect.setCellValueFactory(new PropertyValueFactory<>("devolutivo"));
        tcFornecedorSelect.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));

        tvMateriaisSelecionados.setItems(listSelect);
        tvMateriaisSelecionados.refresh();

        System.out.println(listSelect);
    }
    private void copyList(){
        for (Material m: list) {
            listCopy.add(m);
        }
    }

    @FXML
    public void btCadastrarFornecedor(){
        dialogo("Cadastro de Fornecedores", "../view/CadastrarFornecedor.fxml");
    }
    @FXML
    public void btDevolucao(){
        mudaJanela("../view/Devolucao.fxml");
    }
    @FXML
    public void btCadastrarMaterial(){
        flag=3;
        copyList();
        dialogo("Cadastro de Materiais", "../view/CadastrarMateriais.fxml");
        initialize();
    }

    public void click(MouseEvent mouseEvent) {

        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

            Material m = tvMateriaisCadastrados.getSelectionModel().getSelectedItem();

            Material material = new Material(m.getIdMaterial(),m.getNome(),m.getUnidadeMedida(),1,m.getQuantidadeMinima(),m.getFornecedor(),m.isDevolutivo());

            for (Material mt:listSelect) {
                if(mt.getIdMaterial()==m.getIdMaterial()){
                    System.out.println("na flag "+flag);
                    mt.setQuantidade(mt.getQuantidade()+1);
                    flag=0;
                    break;
                }else{
                    flag=1;
                    System.out.println("na flag "+flag);
                }
            }
            if (flag==3){
                flag=0;
                material.setQuantidade(1);
                listSelect.add(material);
            }
            if (flag==1){
                listSelect.add(material);
            }

            for (Material mt: list) {
                if(mt.getIdMaterial()==m.getIdMaterial()){
                    mt.setQuantidade(mt.getQuantidade()-1);
                }
                listCopy.add(mt);
            }
            initialize();
        }

        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){

            ContextMenu cm = new ContextMenu();
            MenuItem mi = new MenuItem();
            mi.setText("Opçao");
            cm.getItems().add(mi);

            cm.show(cena.getScene().getWindow(), mouseEvent.getScreenX(),mouseEvent.getScreenY());

        }
    }

    public void click2(MouseEvent mouseEvent) {

        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

            listSelect.remove(tvMateriaisSelecionados.getSelectionModel().getSelectedItem());
            initialize();

        }else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)){

            ContextMenu cm = new ContextMenu();
            MenuItem mi = new MenuItem();
            mi.setText("opção");
            cm.getItems().add(mi);
            cm.show(cena.getScene().getWindow(), mouseEvent.getScreenX(),mouseEvent.getScreenY());

        }
    }
    @FXML
    public void btRetira(){
        if(tfMatricula.getText().equals("")){
            alertar("Erro!","Matricula invalida", "Cheque se o funcionario encotara-se cadastrado no sitema.");
        }else{
            if(JDBCFuncionarioDAO.getInstance().search(Integer.parseInt(tfMatricula.getText()))!=null){
                Retirada r = new Retirada();
                r.setIdFuncionario(JDBCFuncionarioDAO.getInstance().search(Integer.parseInt(tfMatricula.getText())));
                System.out.println(r.getIdFuncionario().toString());
                //r.setData(LocalDateTime.now());
                r.setMateriais(listSelect);
                JDBCRetiradaDAO.getInstance().create(r);
                JDBCMaterialDAO.getInstance().update(list);
                for (Material m:list) {
                    if(m.getQuantidade()<=m.getQuantidadeMinima()){
                        Fornecedor f = JDBCFornecedorDAO.getInstance().search(m.getFornecedor().getCnpj());
                        alertarFalta("Atenção!","Quantidade de "+m.getNome()+" critico.","Contate o fornecedor\n"+f.toString2());
                    }
                }
            }else{
                alertar("Erro!","Matricula invalida", "Cheque se o funcionario encotara-se cadastrado no sitema.");
            }
        }

    }
    @FXML
    public void btRelatorio(){
        Relatorio relatorio= new Relatorio();
        relatorio.gerarRelatorio(LocalDate.now());
        System.out.println("foi");
    }
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
    public void alertar(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
    }
    public void alertarFalta(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
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
