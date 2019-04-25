package sample.model.JDBCs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.model.FabricaConexao;
import sample.model.Fornecedor;
import sample.model.Funcionario;
import sample.model.Privilegio;
import sample.model.interfaces.FornecedorDAO;

import java.sql.*;

public class JDBCFornecedorDAO implements FornecedorDAO {

    ObservableList<Fornecedor> list = FXCollections.observableArrayList();

    private static JDBCFornecedorDAO instance;

    public static JDBCFornecedorDAO getInstance(){
        if(instance==null){
            instance = new JDBCFornecedorDAO();
        }
        return instance;
    }

    @Override
    public void create(Fornecedor f){
        try {
            String sql = "insert into fornecedor(cnpj, nome, telefone, endereco, email) values (?,?,?,?,?);";

            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.setInt(1,f.getCnpj());
            pstm.setString(2,f.getNome());
            pstm.setString(3,f.getTelefone());
            pstm.setString(4,f.getEndereco());
            pstm.setString(5,f.getEmail());

            if(search(f.getCnpj())!=null){
                alertar("Erro!", "Fornecedor Não Cadastrado", "CNPJ já Registrado no banco");
            }else {
                alertarOK("Cadastro realizado com sucesso","Fornecedor: "+f.getNome(),"Registrado no banco");
                pstm.execute();
                pstm.close();
                c.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public ObservableList<Fornecedor> listar(){
        list.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from fornecedor");
            while(rs.next()){
                Fornecedor fornecedor = montaFornecedor(rs);
                list.add(fornecedor);
            }

            rs.close();
            stm.close();
            c.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public Fornecedor search(int id){
        try {
            Connection c = FabricaConexao.getConnection();

            String sql = "select * from fornecedor where cnpj=?";
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            Fornecedor fornecedor = null;

            while (rs.next()) {
                fornecedor = montaFornecedor(rs);
            }
            rs.close();
            pstm.close();
            c.close();

            return fornecedor;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private Fornecedor montaFornecedor(ResultSet rs)throws SQLException {

        int cnpj = rs.getInt("cnpj");
        String nome = rs.getString("nome");
        String telefone = rs.getString("telefone");
        String endereco = rs.getString("endereco");
        String email = rs.getString("email");

        Fornecedor f = new Fornecedor(cnpj, nome,telefone,endereco,email);

        return f;
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
