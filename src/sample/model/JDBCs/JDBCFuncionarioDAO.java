package sample.model.JDBCs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.model.FabricaConexao;
import sample.model.Funcionario;
import sample.model.Material;
import sample.model.Privilegio;
import sample.model.interfaces.FuncionarioDAO;

import java.sql.*;

public class JDBCFuncionarioDAO implements FuncionarioDAO {

    private ObservableList<Funcionario> list = FXCollections.observableArrayList();
    private static Funcionario funcio;

    private static JDBCFuncionarioDAO instance;

    public static JDBCFuncionarioDAO getInstance(){
        if(instance==null){
            instance = new JDBCFuncionarioDAO();
        }
        return instance;
    }

    @Override
    public void create(Funcionario func){
        try {
            String sql = "insert into funcionario(matricula,senha,nome,setor,fk_privilegio) values (?,?,?,?,?);";

            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.setInt(1,func.getMatricula());
            pstm.setString(2,func.getSenha());
            pstm.setString(3,func.getNome());
            pstm.setString(4,func.getSetor());
            pstm.setInt(5,func.getFk_privilegio().getIdPrivilegio());
            if(JDBCFuncionarioDAO.getInstance().search(func.getMatricula())!=null){
                alertar("Erro!", "Funcionario Não Cadastrado", "Matricula já Registrada no banco");
            }else {
                pstm.execute();
                alertarOK("Cadastro realizado com sucesso","Funcionario: "+func.getNome(),"Registrado no banco");
            }
            pstm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private Funcionario montaFuncionario(ResultSet rs)throws SQLException {

        int matricula = rs.getInt("matricula");
        String senha = rs.getString("senha");
        String nome = rs.getString("nome");
        String setor = rs.getString("setor");
        int fk_priv = rs.getInt("fk_privilegio");

        Funcionario func = new Funcionario();

        try {
            Privilegio fk_Privilegio = JDBCPrivilegioDAO.getInstance().search(fk_priv);
            func.setFk_privilegio(fk_Privilegio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        func.setMatricula(matricula);
        func.setNome(nome);
        func.setSenha(senha);
        func.setSetor(setor);

        return func;
    }
    @Override
    public Funcionario verificaLogin(Funcionario func) {
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from funcionario where matricula = '"+func.getMatricula()+
                    "' and senha ='"+func.getSenha()+"';");
            if(rs.next()){
                Funcionario funcionario = montaFuncionario(rs);
                rs.close();
                stm.close();
                c.close();
                funcio=JDBCFuncionarioDAO.getInstance().search(funcionario.getMatricula());
                return funcionario;
            }
            rs.close();
            stm.close();
            c.close();

            return  func;

        }catch (SQLException e){
            e.printStackTrace();
            return func;
        }
    }

    @Override
    public ObservableList<Funcionario> listar(){
        list.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("select * from funcionario");
            while(rs.next()){
                Funcionario funcionario = montaFuncionario(rs);
                list.add(funcionario);
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
    public Funcionario search(int id) {
        try {
            Connection c = FabricaConexao.getConnection();

            String sql = "select * from funcionario where matricula=?";
            PreparedStatement pstm = c.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            Funcionario funcionario = null;

            while (rs.next()) {
                funcionario = montaFuncionario(rs);
            }
            rs.close();
            pstm.close();
            c.close();

            return funcionario;
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return  null;
    }

    @Override
    public void update(Funcionario func){
        try {
            String sql = "UPDATE `funcionario` SET (`nome`, 'senha', 'setor', 'fk_privilegio') values (?,?,?,?,?) WHERE 'funcionario'.'matricula' = ?";
//        UPDATE `funcionario` SET `nome` = 'teste0001 alterado', `senha` = '1234', `fk_privilegio` = '3' WHERE `funcionario`.`matricula` = 20180009
            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.setString(1,func.getNome());
            pstm.setString(2,func.getSenha());
            pstm.setString(3,func.getSetor());
           // pstm.setInt(4,func.getFk_privilegio().getIdPrivilegio());
            pstm.setInt(5,func.getMatricula());

            pstm.execute();
            pstm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void alertar(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
    }
    private void alertarOK(String titulo,String cabecalho,String texto){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(texto);
        alert.showAndWait();
    }

    public static Funcionario getFuncio() {
        return funcio;
    }

    public static void setFuncio(Funcionario funcio) {
        JDBCFuncionarioDAO.funcio = funcio;
    }
}
