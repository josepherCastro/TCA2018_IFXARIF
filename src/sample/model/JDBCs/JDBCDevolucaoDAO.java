package sample.model.JDBCs;

import sample.model.Devolucao;
import sample.model.interfaces.DevolucaoDAO;

public class JDBCDevolucaoDAO implements DevolucaoDAO {
    private static JDBCDevolucaoDAO instance;

    public static JDBCDevolucaoDAO getInstance(){
        if(instance==null){
            instance = new JDBCDevolucaoDAO();
        }
        return instance;
    }

    @Override
    public void create(Devolucao d) throws Exception {

    }
}
