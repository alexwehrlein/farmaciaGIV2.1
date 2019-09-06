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
 * @author Jose Abada Nava
 */
public class Sucursal {

    private Connection con;

    public Sucursal() {
        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean registrarSucursal(String nombre, String direccion) {
        try {
            String sql = "INSERT INTO sucursal(nombre,direccion) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.execute();
            con.commit();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + e);
            try {
                con.rollback();
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return false;
    }

    public boolean registrarDetalleSucursal(DefaultTableModel modelo) {
        try {

            String sql = "";
            for (int i = 0; i < modelo.getRowCount(); i++) {
                sql = "INSERT INTO detalleSucursal VALUES (" + modelo.getValueAt(i, 0).toString() + "," + obtenerIdProducto() + ",0)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.execute();
                con.commit();
            }
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
                return false;
            } catch (SQLException ex) {
                Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return false;
    }

    public String obtenerIdProducto() {
        String idProducto = "";
        try {
            Statement sta = con.createStatement();

            String sql = "SELECT MAX(idproducto) AS id FROM producto;";
            ResultSet resultado = sta.executeQuery(sql);
            if (resultado.next()) {
                idProducto = String.valueOf(resultado.getInt("id"));
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                 Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return idProducto;

    }

    public boolean registrarMaquina(String idSucursal, String usuario) {
        try {
            String sql = "INSERT INTO maquina(sucursal_idsucursal,usuario) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(idSucursal));
            ps.setString(2, usuario);
            ps.execute();
            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
                return false;
            } catch (SQLException ex) {
                 Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return false;
    }

    public ArrayList<String> obtenerIdSucursal(String usuario) {
        ArrayList<String> array = new ArrayList<>();
        String sql = "select sucursal.idsucursal,sucursal.nombre from sucursal JOIN maquina "
                + "ON sucursal.idsucursal=maquina.sucursal_idsucursal Where maquina.usuario='" + usuario + "'";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                array.add(String.valueOf(resultado.getInt("idsucursal")));
                array.add(resultado.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
        }
        return array;
    }

    public ArrayList<String> obtenerSucursales() {
        ArrayList<String> arraySucursales = new ArrayList<>();
        try {
            String sql = "SELECT idsucursal,nombre from sucursal";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arraySucursales.add(String.valueOf(resultado.getInt("idsucursal")));
                arraySucursales.add(resultado.getString("nombre"));
            }
        } catch (SQLException ex) {
             Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
        }

        return arraySucursales;
    }

    public DefaultTableModel cargarSucursales(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnEliminar = new JButton("X");
        btnEliminar.setName("Eliminar");
        String sql = "SELECT idsucursal,nombre FROM sucursal";
        ArrayList<ArrayList<String>> arraySucursal = new ArrayList<ArrayList<String>>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                ArrayList<String> array2 = new ArrayList<>();
                array2.add(String.valueOf(resultado.getInt("idsucursal")));
                array2.add(resultado.getString("nombre"));
                arraySucursal.add(array2);
            }
            for (int i = 0; i < arraySucursal.size(); i++) {
                modelo.addRow(new Object[]{arraySucursal.get(i).get(0), arraySucursal.get(i).get(1), btnEliminar});
            }
        } catch (SQLException ex) {
             Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
        }

        return modelo;
    }

    public DefaultTableModel obtenerTodosProductos(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnEditar = new JButton("Editar");
        btnEditar.setName("Editar");
        String sql = " SELECT idForaneoProductos AS codigo,clasificacion FROM producto";
        ArrayList<ArrayList<String>> arrayProductos = new ArrayList<ArrayList<String>>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                ArrayList<String> array2 = new ArrayList<>();
                array2.add(String.valueOf(resultado.getInt("codigo")));
                array2.add(resultado.getString("clasificacion"));
                array2.add(existencia(new Alta().idProducto(String.valueOf(resultado.getInt("codigo")), resultado.getString("clasificacion"))));
                arrayProductos.add(array2);
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).get(0), arrayProductos.get(i).get(1), arrayProductos.get(i).get(2), btnEditar});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
        }

        return modelo;
    }

    public String existencia(String idProducto) {
        try {
            Statement sta = con.createStatement();
            String sql = "SELECT SUM(cantidadProductos) AS existencia FROM detallesucursal WHERE producto_idproducto=" + idProducto;
            ResultSet resultado = sta.executeQuery(sql);
            if (resultado.next()) {
                idProducto = String.valueOf(resultado.getInt("existencia"));
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return idProducto;

    }

    public DefaultTableModel productosPorSucursal(JTable jt, String Codigo, String tipo) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        jt.setDefaultRenderer(Object.class, new Render());
        String sql = "SELECT sucursal.nombre,detalleSucursal.cantidadProductos AS existencia FROM sucursal "
                + "JOIN detallesucursal ON sucursal.idsucursal=detallesucursal.sucursal_idsucursal WHERE "
                + "detallesucursal.producto_idproducto=" + new Alta().idProducto(Codigo, tipo);
        ArrayList<ArrayList<String>> arrayProductos = new ArrayList<ArrayList<String>>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                ArrayList<String> array2 = new ArrayList<>();
                array2.add(resultado.getString("nombre"));
                array2.add(String.valueOf(resultado.getInt("existencia")));
                arrayProductos.add(array2);
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).get(0), arrayProductos.get(i).get(1)});
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
        }

        return modelo;
    }

    public String idSucursal(String nombre) {
        String id = "";
        try {
            Statement sta = con.createStatement();
            String sql = "SELECT idsucursal FROM sucursal WHERE nombre='"+nombre+"'";
            ResultSet resultado = sta.executeQuery(sql);
            if (resultado.next()) {
                id = String.valueOf(resultado.getInt("idsucursal"));
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
        return id;

    }
}
