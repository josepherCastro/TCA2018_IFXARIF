package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class FabricaConexao {
    private static Connection[] pool;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter dateFomater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

/*    private static String CONNECTION_STR = "jdbc:mysql:"+
            "//infoprojetos.com.br:3132/tads17_josepher" +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private static String USERNAME = "tads17_josepher";
    private static String PASSWORD = "91527938";
            */

    private static String CONNECTION_STR = "jdbc:mysql:"+
            "//localhost:3306/tads17_josepher?useTimezone=true&serverTimezone=UTC&useSSL=false";

    private static int MAX_CONNECTIONS=5;

    static {
        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException{

        for(int i=0;i<pool.length;i++){
            if((pool[i]==null) || (pool[i].isClosed())){

                pool[i] = DriverManager
                        .getConnection(CONNECTION_STR,"root","");

                return pool[i];
            }
        }

        throw new SQLException("Muitas conexões abertas!!!");

    }

}
