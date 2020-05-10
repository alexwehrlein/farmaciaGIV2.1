package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static String bd = "farmacia";
    private static String login = "root";
    private static String password = "admin";
 //private static String password = "1234";
 //   private static String url = "jdbc:mysql://192.168.1.11:3306/" + bd;
    private static String url ="jdbc:mysql://localhost/"+bd+"?useSSL=false";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
               // System.out.println("Base de datos  " + bd + " conectado");
                //
//               JOptionPane.showMessageDialog(null, "Base de datos  " + bd + " conectado");
            }
        } catch (SQLException e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null,e.getMessage());
        } catch (ClassNotFoundException e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return conn;
    }
}
