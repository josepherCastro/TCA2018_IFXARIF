package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.Funcionario;

public interface FuncionarioDAO {
    public void create(Funcionario func) throws Exception;
    public Funcionario verificaLogin(Funcionario func) throws Exception;
    public ObservableList<Funcionario> listar() throws Exception;
    public Funcionario search(int id) throws Exception;
    public void update(Funcionario func) throws Exception;
}
