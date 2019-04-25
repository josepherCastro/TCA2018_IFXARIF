package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.Fornecedor;

public interface FornecedorDAO {
    public void create(Fornecedor f) throws Exception;
    public ObservableList<Fornecedor> listar()throws Exception;
}
