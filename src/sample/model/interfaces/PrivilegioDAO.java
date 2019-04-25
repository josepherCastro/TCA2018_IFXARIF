package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.Privilegio;

import java.util.List;

public interface PrivilegioDAO {

    public ObservableList<Privilegio> listar() throws Exception;
    public Privilegio search(int id) throws Exception;
}
