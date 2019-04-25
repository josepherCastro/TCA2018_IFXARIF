package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.Material;

public interface MaterialDAO {
    public void create(Material m) throws Exception;
    public ObservableList<Material> listar() throws Exception;
    public void update(ObservableList<Material> list) throws Exception;
}
