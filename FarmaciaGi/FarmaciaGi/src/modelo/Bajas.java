/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexwehrlein
 */
public class Bajas {
    private Connection con;
    private String codigo;
    private int piezas;
    private int existenxias;
    Conexion conn = new Conexion();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getPiezas() {
        return piezas;
    }

    public void setPiezas(int piezas) {
        this.piezas = piezas;
    }

    public int getExistenxias() {
        return existenxias;
    }

    public void setExistenxias(int existenxias) {
        this.existenxias = existenxias;
    }

    public Bajas(String codigo) {
        this.codigo = codigo;
    }

    public Bajas(String codigo, int piezas , int existenxias) {
        this.codigo = codigo;
        this.piezas = piezas;
        this.existenxias = existenxias;
    }

    public Bajas() {
    }
    
    public String Producto() {
        String sql = null , existencias = "" ;
        try {
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT cantidad FROM productos WHERE codigo=" + getCodigo() + "";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()) {
                existencias = rs.getString("cantidad");

            }
            stm.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return existencias;
    }
    
     public void actualizarExistencias() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "UPDATE productos SET cantidad = "+getExistenxias()+" WHERE codigo = "+getCodigo();
            stm.execute(sql);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }

    }
     
      public void insertarBajas() {
        String sql = null;

        try {
            con = conn.getConnection();
            Statement stm = (Statement) con.createStatement();
            sql = "INSERT INTO bajas (codigo,piezas) VALUES ( "+getCodigo()+" , "+getExistenxias()+")";
            stm.execute(sql);

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            conn.getClose();
        }

    }

}
