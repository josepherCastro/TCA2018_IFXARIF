package sample.model.JDBCs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.model.*;
import sample.model.interfaces.MaterialDAO;

import java.sql.*;

public class JDBCMaterialDAO implements MaterialDAO {
    private static JDBCMaterialDAO instance;
    private ObservableList<Material> list = FXCollections.observableArrayList();

    public static JDBCMaterialDAO getInstance(){
        if(instance==null){
            instance = new JDBCMaterialDAO();
        }
        return instance;
    }
    @Override
    public void create(Material material){
        try {
            String sql = "insert into material(idMaterial,nome,quantidade,quantMinima,devolutivo,fk_fornecedor,fk_uniMedida) values (?,?,?,?,?,?,?);";

            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.setInt(1,material.getIdMaterial());
            pstm.setString(2,material.getNome());
            pstm.setDouble(3,material.getQuantidade());
            pstm.setDouble(4,material.getQuantidadeMinima());
            pstm.setBoolean(5,material.isDevolutivo());
            pstm.setInt(6,material.getFornecedor().getCnpj());
            pstm.setInt(7,material.getUnidadeMedida().getIdUniMedida());

            if(searchAux(material.getNome())!=null){
                alertar("Erro!", "Material Não Cadastrado", "Material já Registrado no banco");
            }else {
                pstm.execute();
                alertarOK("Cadastro realizado com sucesso","Material: "+material.getNome(),"Registrado no banco");
                pstm.close();
                c.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public ObservableList<Material> listar(){
        list.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from material");
            while(rs.next()){
                Material material = montaMaterial(rs);
                list.add(material);
            }

            rs.close();
            stm.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(ObservableList<Material> list) {
        for (Material material :list) {
            try {
                String sql = "UPDATE `material` SET `quantidade`= ? WHERE idMaterial = ?";
                Connection c = FabricaConexao.getConnection();
                PreparedStatement pstm = c.prepareStatement(sql);

                pstm.setDouble(1,material.getQuantidade());
                pstm.setInt(2,material.getIdMaterial());

                pstm.execute();
                pstm.close();
                c.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    private Material montaMaterial(ResultSet rs)throws SQLException {

        int id = rs.getInt("idMaterial");
        String nome = rs.getString("nome");
        Double quantTotal = rs.getDouble("quantidade");
        Double quantMinima = rs.getDouble("quantMinima");
        boolean devolutivo = rs.getBoolean("devolutivo");
        int fk_fornecedor = rs.getInt("fk_fornecedor");
        int fk_uniMedida = rs.getInt("fk_uniMedida");

        Fornecedor fornecedor = null;
        UnidadeMedida unidadeMedida = null;
        try {
            fornecedor = JDBCFornecedorDAO.getInstance().search(fk_fornecedor);
            unidadeMedida = JDBCUnidadeMedidaDAO.getInstance().search(fk_uniMedida);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Material material = new Material(id,nome,unidadeMedida,quantTotal,quantMinima,fornecedor,devolutivo);

        return material;
    }
    public Material search(int id) throws Exception {

        Connection c = FabricaConexao.getConnection();

        String sql = "select * from material where idMaterial";
        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);

        ResultSet rs = pstm.executeQuery();
        Material material = null;

        while(rs.next()){
            material = montaMaterial(rs);
        }
        rs.close();
        pstm.close();
        c.close();

        return material;
    }
    public Material searchAux(String nome){
        try {
            Connection c = FabricaConexao.getConnection();

            String sql = "select * from material where nome";
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setString(1, nome);

            ResultSet rs = pstm.executeQuery();
            Material material = null;

            while (rs.next()) {
                material = montaMaterial(rs);
            }
            rs.close();
            pstm.close();
            c.close();

            return material;
        }catch (Exception e){
                e.printStackTrace();
        }
            return null;
    }
    public void alertar(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
    }
    public void alertarOK(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
    }

}
