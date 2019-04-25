package sample.model.JDBCs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;
import sample.model.interfaces.UnidadeMedidaDAO;

import java.sql.*;

public class JDBCUnidadeMedidaDAO implements UnidadeMedidaDAO {

    ObservableList<UnidadeMedida> list = FXCollections.observableArrayList();

    private static JDBCUnidadeMedidaDAO instance;

    public static JDBCUnidadeMedidaDAO getInstance(){
        if(instance==null){
            instance = new JDBCUnidadeMedidaDAO();
        }
        return instance;
    }

    @Override
    public void create(UnidadeMedida unidadeMedida){
        try {
            String sql = "insert into unimedida(id, nome) values (?,?);";

            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.setString(1,"NULL");
            pstm.setString(2,unidadeMedida.getNome());

            pstm.execute();
            pstm.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public ObservableList<UnidadeMedida> listar() {
        list.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from unimedida");
            while(rs.next()){
                UnidadeMedida unidadeMedida = montaUniMedida(rs);
                list.add(unidadeMedida);
            }

            rs.close();
            stm.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    private UnidadeMedida montaUniMedida(ResultSet rs)throws SQLException {

        int idUniMedida = rs.getInt("idUniMedida");
        String nome = rs.getString("nome");

        UnidadeMedida unidadeMedida = new UnidadeMedida(idUniMedida, nome);

        return unidadeMedida;
    }
    public UnidadeMedida search(int id) throws Exception {

        Connection c = FabricaConexao.getConnection();

        String sql = "select * from unimedida where idUniMedida=?";
        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);

        ResultSet rs = pstm.executeQuery();
        UnidadeMedida unidadeMedida = null;

        while(rs.next()){
            unidadeMedida = montaUniMedida(rs);
        }
        rs.close();
        pstm.close();
        c.close();

        return unidadeMedida;
    }
}
