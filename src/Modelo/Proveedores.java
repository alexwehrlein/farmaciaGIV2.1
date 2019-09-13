/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jesus
 */
public class Proveedores {

    private int iproveedor;
    private String nombre;
    private String telefono;
    private String ciudad;
    private String estado;
    private String email;
    private String estatus;
    private Connection con;
    Conexion conn = new Conexion();

    public Proveedores(int idproveedor, String nombre, String telefono, String ciudad, String estado, String email, String estatus) {
        this.iproveedor = idproveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.estado = estado;
        this.email = email;
        this.estatus = estatus;
    }
    public Proveedores(int idproveedor){
        this.iproveedor = idproveedor;
        
    }

    public int getIdproveedor() {
        return iproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public String getEmail() {
        return email;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setIdproveedor(int idproveedor) {
        this.iproveedor = idproveedor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Proveedores() {
        con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean agregarProveedor() {
        String sql = null;
        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO proveedor VALUES(null,'" + getNombre() + "','" + getTelefono() + "','" + getCiudad() + "','" + getEstado() + "' , '" + getEmail() + "' , '" + getEstatus() + "')";
            stm.execute(sql);
            con.setAutoCommit(true);
            con.close();

            return true;
        } catch (Exception e) {
            return false;
        }

    }
     public boolean EditarProveedor() {
        //String sql = null;
        try {
            //con = conn.getConnection();
            //Statement stm = (Statement) con.createStatement();
           // stm = "UPDATE proveedor SET nombre ='" + getNombre() + "', telefono='"+getTelefono()+"', ciudad='"+
             //     getCiudad()+"', estado='"+getEstado()+"', email='"+getEmail()+"', estatus='"+getEstatus()+
               //   "' WHERE  iproveedor = '" + getIdproveedor() ;
            
            //on.close();
            
            Connection connection =new Conexion().getConnection();
            com.mysql.jdbc.Statement stm= (com.mysql.jdbc.Statement) connection.createStatement();
            stm.execute("UPDATE proveedor SET nombre='"+getNombre()+"', telefono='"+getTelefono()+"',ciudad='"+getCiudad()+
                    "',estado='"+getEstado()+"',email='"+getEmail()+"',estatus='"+getEstatus()+"' WHERE iproveedor="+getIdproveedor());
            connection.close();
            return true;
        } catch (Exception e) {

            return false;
        }

    }
      public boolean eliminarProvedor(){
          String sql = null;
        try {
           Connection connection =new Conexion().getConnection();
            com.mysql.jdbc.Statement stm= (com.mysql.jdbc.Statement) connection.createStatement();
            stm.execute("UPDATE proveedor SET estatus='Inactivo' WHERE iproveedor="+getIdproveedor());
            connection.close();
            return  true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return  false;
            
            
        }
    }

    public DefaultTableModel ConsultaProveedores(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnEliminar = new JButton("Eliminar");
        //TableColumn col = jt.getColumnModel().getColumn(1);
        btnEliminar.setName("btnEliminar");

        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Proveedores> arrayConsulta = new ArrayList<>();
        try {

            String sql = "SELECT * FROM proveedor order by iproveedor";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayConsulta.add(new Proveedores(resultado.getInt("iproveedor"), resultado.getString("nombre"), resultado.getString("telefono"), resultado.getString("ciudad"), resultado.getString("estado"), resultado.getString("email"), resultado.getString("estatus")));
            }
            for (int i = 0; i < arrayConsulta.size(); i++) {
                modelo.addRow(new Object[]{arrayConsulta.get(i).getIdproveedor(), arrayConsulta.get(i).getNombre(), arrayConsulta.get(i).getTelefono(),
                    arrayConsulta.get(i).getCiudad(), arrayConsulta.get(i).getEstado(), arrayConsulta.get(i).getEmail(),arrayConsulta.get(i).getEstatus(), btnEliminar});
            }

            con.close();
        } catch (SQLException ex) {
        }

        return modelo;
    }

}
