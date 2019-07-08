/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author saube
 */
public class Usuarios {

    private int idLogin;
    private String usuario;
    private String passwork;
    private int idEmpleado;
    private String nombre;
    private Connection con;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Usuarios(int idLogin, String usuario, String passwork, String nombre) {
        this.idLogin = idLogin;
        this.usuario = usuario;
        this.passwork = passwork;
        this.nombre = nombre;
    }

    public Usuarios(int idLogin, String usuario, String passwork, int idEmpleado) {
        this.idLogin = idLogin;
        this.usuario = usuario;
        this.passwork = passwork;
        this.idEmpleado = idEmpleado;
    }

    public Usuarios(int idLogin) {
        this.idLogin = idLogin;
    }

    public Usuarios() {

    }

    public boolean registrarUsuario() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("INSERT INTO login VALUES(null,'" + getUsuario() + "','" + getPasswork() + "'," + getIdEmpleado() + ")");
            connection.close();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public boolean ModificarRegristros() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("UPDATE login SET usuario='" + getUsuario() + "', contrasena='" + getPasswork() + "',id_empleado=" + getIdEmpleado() + " WHERE id_login=" + getIdLogin());
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean eliminarUsuario() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("DELETE FROM login WHERE id_login=" + getIdLogin());
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;

        }
    }

    public ArrayList<Empleado> octenerEmpleados() {
        ArrayList<Empleado> listaEmpleados = new ArrayList<Empleado>();
        try {
            String sql = "SELECT nombre,id_empleado FROM empleado";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String nombre = rs.getString("nombre");
                int id = rs.getInt("id_empleado");

                Empleado empleado = new Empleado(id, nombre);
                listaEmpleados.add(empleado);
            }
            pst = null;
            pst.close();
            con.close();
        } catch (Exception e) {
        }
        return listaEmpleados;
    }

    public DefaultTableModel cargarRegistroEgreso(JTable jt) {

        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Usuarios> arrayEgresos = new ArrayList<>();
        try {

            String sql = "SELECT id_login , usuario, contrasena, empleado.nombre from empleado JOIN login ON empleado.id_empleado=login.id_empleado order by usuario";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Usuarios(resultado.getInt("id_login"), resultado.getString("usuario"), resultado.getString("contrasena"), resultado.getString("nombre")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{arrayEgresos.get(i).getIdLogin(), arrayEgresos.get(i).getUsuario(), arrayEgresos.get(i).getPasswork(),
                    arrayEgresos.get(i).getNombre()});
            }
            pst = null;
            pst.close();
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
