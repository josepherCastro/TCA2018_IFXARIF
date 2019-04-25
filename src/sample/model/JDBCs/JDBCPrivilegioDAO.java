package sample.model.JDBCs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.FabricaConexao;
import sample.model.Funcionario;
import sample.model.Privilegio;
import sample.model.interfaces.PrivilegioDAO;

import java.sql.*;

public class JDBCPrivilegioDAO implements PrivilegioDAO {

    private ObservableList<Privilegio> lista = FXCollections.observableArrayList();
    private static JDBCPrivilegioDAO instance;

    public static JDBCPrivilegioDAO getInstance(){
        if(instance==null){
            instance = new JDBCPrivilegioDAO();
        }
        return instance;
    }


    @Override
    public ObservableList<Privilegio> listar(){
        lista.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from privilegio");
            while(rs.next()){
                Privilegio privilegio = montaPrivilegio(rs);
                lista.add(privilegio);
            }

            rs.close();
            stm.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    public Privilegio search(int id) throws Exception {

        Connection c = FabricaConexao.getConnection();

        String sql = "select * from privilegio where idPrivilegio=?";
        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);

        ResultSet rs = pstm.executeQuery();
        Privilegio privilegio = null;

        while(rs.next()){
            privilegio = montaPrivilegio(rs);
        }
        rs.close();
        pstm.close();
        c.close();

        return privilegio;
    }
    private Privilegio montaPrivilegio(ResultSet rs)throws SQLException {
        int id = rs.getInt("idPrivilegio");
        int tipo = rs.getInt("tipo");
        String descricao = rs.getString("descricao");

        Privilegio privilegio = new Privilegio(id, tipo, descricao);

        return privilegio;
    }

}
