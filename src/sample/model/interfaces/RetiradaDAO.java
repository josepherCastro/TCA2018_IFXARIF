package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.Retirada;

public interface RetiradaDAO {
    public void create(Retirada r) throws Exception;
    public ObservableList<Retirada> listar() throws Exception;
}
