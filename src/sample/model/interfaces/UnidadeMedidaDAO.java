package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.UnidadeMedida;

public interface UnidadeMedidaDAO {
    public ObservableList<UnidadeMedida> listar();
    public void create(UnidadeMedida u);
}
