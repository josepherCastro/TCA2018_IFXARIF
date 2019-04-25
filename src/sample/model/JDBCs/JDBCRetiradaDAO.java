package sample.model.JDBCs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;
import sample.model.interfaces.RetiradaDAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JDBCRetiradaDAO implements RetiradaDAO {
    private static JDBCRetiradaDAO instance;

    public static JDBCRetiradaDAO getInstance(){
        if(instance==null){
            instance = new JDBCRetiradaDAO();
        }
        return instance;
    }
    private ObservableList<Retirada> list = FXCollections.observableArrayList();
    private ObservableList<Material> listAux = FXCollections.observableArrayList();

    @Override
    public void create(Retirada r) {
        System.out.println(r.toString());
        try {
            String sql = "insert into retiradamaterial(idRetirada,quantidade,dataHora,fk_funcionario,fk_material) values (?,?,?,?,?);";

            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.setInt(1,r.getIdRetirada());
            pstm.setString(3, String.valueOf(LocalDateTime.now()));

            pstm.setInt(4,r.getIdFuncionario().getMatricula());

            for (Material m: r.getMateriais()) {
                pstm.setDouble(2,m.getQuantidade());
                pstm.setInt(5,m.getIdMaterial());
                pstm.execute();
            }

            pstm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private Retirada montaRetirada(ResultSet rs)throws SQLException {
        int id = rs.getInt("idRetirada");
        Double quantidade = rs.getDouble("quantidade");
        String dataHora = rs.getString("dataHora");
        int fk_material = rs.getInt("fk_material");
        int fk_funcionario = rs.getInt("fk_funcionario");

        Material material = null;
        Funcionario funcionario = null;
        try {
            funcionario = JDBCFuncionarioDAO.getInstance().search(fk_funcionario);
            listAux.add(JDBCMaterialDAO.getInstance().search(fk_material));

            System.out.println(material);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Retirada item = new Retirada();
        item.setIdRetirada(id);
        item.setQuantidade(quantidade);
        item.setData(LocalDateTime.parse(dataHora, FabricaConexao.dateFomater));
        item.setIdFuncionario(funcionario);
        //item.setMateriais(listAux);

        return item;
    }

    @Override
    public ObservableList<Retirada> listar() {
        list.clear();
        listAux.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from retiradamaterial");
            while(rs.next()){
                Retirada retirada = montaRetirada(rs);
                //retirada
                list.add(retirada);
            }

            rs.close();
            stm.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
