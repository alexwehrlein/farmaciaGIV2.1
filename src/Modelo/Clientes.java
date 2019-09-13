/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adriana
 */
public class Clientes {
    private int iproveedor;
    private String nombre;
    private String telefono;
    private String email;
    private String estatus;
    private Connection con;
    Conexion conn=new Conexion();
    public Clientes(String nombre){//para mi busqueda de cliente por nombre
        this.nombre=nombre;
    }
    public Clientes(int iproveedor, String nombre, String telefono, String email, String estatus) {
        this.iproveedor = iproveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.estatus = estatus;
    }
    public Clientes(int iproveedor){
        this.iproveedor=iproveedor;
    }

    public int getIproveedor() {
        return iproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setIproveedor(int iproveedor) {
        this.iproveedor = iproveedor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public Clientes(){
        con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (Exception ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
   public boolean registrarClientes(){
       String sql = null;
       try {
           con= conn.getConnection();
           Statement stm=(Statement)con.createStatement();
           sql="INSERT INTO cliente VALUES(null,'" + getNombre() + "','" + getTelefono() + "','" + getEmail() + "','"+getEstatus()+"')";
           
           stm.execute(sql);
           con.setAutoCommit(true);
            con.close();
            return true;
           
       } catch (Exception e) {
          
       }
        return false;
   }
   public DefaultTableModel consultaClientes(JTable jt){
       jt.setDefaultRenderer(Object.class, new Render());
       JButton btnEliminar =new JButton ("Eliminar");
       btnEliminar.setName("btnEliminar");
       
       jt.setRowHeight(25);
       
       DefaultTableModel modelo= (DefaultTableModel) jt.getModel();
       ArrayList<Clientes> arrayConsulta = new ArrayList<>();
       try {
           String sql = "SELECT * FROM cliente order by nombre";//ordena por orden altabetico
            Connection con  =new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement)con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayConsulta.add(new Clientes(resultado.getInt("iproveedor"), resultado.getString("nombre"), resultado.getString("telefono"),resultado.getString("email"),resultado.getString("estatus")));
            }
            for (int i = 0; i < arrayConsulta.size(); i++) {
                modelo.addRow(new Object[]{ arrayConsulta.get(i).getIproveedor(), arrayConsulta.get(i).getNombre(), arrayConsulta.get(i).getTelefono(),
                 arrayConsulta.get(i).getEmail(),arrayConsulta.get(i).getEstatus(),btnEliminar});//se agrega el boton de eilimar a la tabla
            }
            
            con.close();
        } catch (SQLException ex) {
        }
       
       
       
       
        return modelo;
       
   }
   public boolean EditarClientes(){
       String sql=null;
       try {
           con=conn.getConnection();
           Statement stm=(Statement) con.createStatement();
           sql = "UPDATE cliente SET nombre ='" + getNombre() + "', telefono='"+getTelefono()+"', email='"+getEmail()+"', estatus='"+getEstatus()+"' WHERE  iproveedor = '" + getIproveedor() + "';";
            stm.execute(sql);
           con.setAutoCommit(true);
            con.close();
            return true;
       } catch (Exception e) {
           return false;
       }
       }
   public boolean EliminarClientes(){
       String sql=null;
       try {
          con=conn.getConnection();
           Statement stm=(Statement) con.createStatement();
           sql = "UPDATE cliente SET estatus='Inactivo' WHERE  iproveedor = '" + getIproveedor() + "';";
            stm.execute(sql);
           con.setAutoCommit(true);
            con.close();
            return true;
       } catch (Exception e) {
           return false;
       }
       }
   

           
            
    public DefaultTableModel busquedaClientes(JTable jt){
       // System.out.println(getNombre());
       jt.setDefaultRenderer(Object.class, new Render());
       JButton btnEliminar =new JButton ("Eliminar");
       btnEliminar.setName("btnEliminar");
       
       jt.setRowHeight(25);
        
       DefaultTableModel modelo= (DefaultTableModel) jt.getModel();
       ArrayList<Clientes> arrayConsulta = new ArrayList<>();
       try {
           String nombre= getNombre();
           String filtro= ""+nombre+"%";//para hacer busqueda aun que este en blanco y todo lo que coincia
           String sql = "SELECT * FROM cliente WHERE  nombre  LIKE "+'"'+ filtro+'"';
            Connection con  =new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement)con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayConsulta.add(new Clientes(resultado.getInt("iproveedor"), resultado.getString("nombre"), resultado.getString("telefono"),resultado.getString("email"),resultado.getString("estatus")));
            }
            for (int i = 0; i < arrayConsulta.size(); i++) {
                modelo.addRow(new Object[]{ arrayConsulta.get(i).getIproveedor(), arrayConsulta.get(i).getNombre(), arrayConsulta.get(i).getTelefono(),
                 arrayConsulta.get(i).getEmail(),btnEliminar});//se agrega el boton de eilimar a la tabla
            }
            
            con.close();
        } catch (SQLException ex) {
        }
       
       
       
       
        return modelo;
       
   }
            
    
}
