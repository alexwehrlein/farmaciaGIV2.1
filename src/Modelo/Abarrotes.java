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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Abarrotes {

    private Connection con;
    String codigoAbarrote, nombre, precio, cantidad, marca;

    public Abarrotes(String codigoAbarrote, String nombre, String marca, String precio, String cantidad) {
        this.codigoAbarrote = codigoAbarrote;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.marca = marca;

        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Abarrotes(String codigoAbarrote, String nombre, String marca) {
        this.codigoAbarrote = codigoAbarrote;
        this.nombre = nombre;
        this.marca = marca;

        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Abarrotes() {
        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getCodigoAbarrote() {
        return codigoAbarrote;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCodigoAbarrote(String codigoAbarrote) {
        this.codigoAbarrote = codigoAbarrote;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public boolean registrarAbarrote(DefaultTableModel modelo, String idProveedor) {
        try {
            String sql = "INSERT INTO ABARROTES(idabarrotes,nombre,precio,marca_idmarca) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(getCodigoAbarrote()));
            ps.setString(2, getNombre());
            ps.setFloat(3, Float.parseFloat(getPrecio()));
            ps.setInt(4, Integer.parseInt(getMarca()));
            ps.execute();
            con.commit();
            sql = "INSERT INTO PRODUCTO VALUES(Default," + getCodigoAbarrote() + ",'Abarrote'," + idProveedor + ")";
            PreparedStatement ps2 = con.prepareStatement(sql);
            ps2.execute();
            con.commit();
            if (!new Sucursal().registrarDetalleSucursal(modelo)) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
                return false;
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return false;
    }

    public boolean modificarAbarrote() {
        try {
            String sql = "UPDATE ABARROTES SET nombre=?,precio=?,marca_idmarca=? WHERE idabarrotes=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setFloat(2, Float.parseFloat(getPrecio()));
            ps.setInt(3, Integer.parseInt(getMarca()));
            ps.setInt(4, Integer.parseInt(getCodigoAbarrote()));
            ps.execute();
            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
                return false;
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return false;
    }

    public void obtenerAbarrote(String codigo) {
        try {
            String sql = "SELECT abarrotes.idabarrotes,abarrotes.nombre,marca.nombre AS marca,abarrotes.precio "
                    + "FROM abarrotes JOIN marca ON abarrotes.marca_idmarca=marca.idmarca "
                    + "WHERE idabarrotes=" + codigo;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                setCodigoAbarrote(String.valueOf(resultado.getInt("idabarrotes")));
                setNombre(resultado.getString("nombre"));
                setPrecio(String.valueOf(resultado.getFloat("precio")));
                setMarca(resultado.getString("marca"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel cargarRegistroAbarrotes(JTable tabla, String idSucursal) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.setDefaultRenderer(Object.class, new Render());
        JButton btnAlta = new JButton("Alta");
        btnAlta.setName("Alta");
//        ImageIcon im = new ImageIcon(getClass().getResource("/Imagenes/editar.png"));
//        btnEditar.setIcon(im);
        ArrayList<Abarrotes> arrayAbarrotes = new ArrayList<>();
        try {
            String sql = "SELECT abarrotes.idabarrotes,abarrotes.nombre,marca.nombre AS marca,"
                    + "abarrotes.precio,detallesucursal.cantidadProductos FROM marca JOIN abarrotes "
                    + "ON marca.idmarca=abarrotes.marca_idmarca JOIN producto ON abarrotes.idabarrotes="
                    + "producto.idForaneoProductos JOIN detallesucursal ON detallesucursal.producto_idproducto="
                    + "producto.idproducto WHERE detallesucursal.sucursal_idsucursal=" + idSucursal + " AND "
                    + "producto.clasificacion='Abarrote';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayAbarrotes.add(new Abarrotes(String.valueOf(resultado.getInt("idabarrotes")), resultado.getString("nombre"), resultado.getString("marca"), String.valueOf(resultado.getFloat("precio")), String.valueOf(resultado.getInt("cantidadProductos"))));
            }
            for (int i = 0; i < arrayAbarrotes.size(); i++) {
                modelo.addRow(new Object[]{arrayAbarrotes.get(i).getCodigoAbarrote(), arrayAbarrotes.get(i).getNombre(), arrayAbarrotes.get(i).getMarca(), arrayAbarrotes.get(i).getPrecio(), arrayAbarrotes.get(i).getCantidad(), btnAlta});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public DefaultTableModel cargarRegistroAbarrotesSolicitante(JTable tabla, String idSucursal) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.setDefaultRenderer(Object.class, new Render());
        JButton btnAdd = new JButton("Add");
        btnAdd.setName("Add");
        ArrayList<Abarrotes> arrayAbarrotes = new ArrayList<>();
        JTableHeader head = tabla.getTableHeader();
        TableColumnModel tcm = head.getColumnModel();
        TableColumn tabCM = tcm.getColumn(0);
        TableColumn tabCM2 = tcm.getColumn(1);
        TableColumn tabCM3 = tcm.getColumn(2);
        TableColumn tabCM4 = tcm.getColumn(3);
        tabCM.setHeaderValue("Codigo");
        tabCM2.setHeaderValue("Nombre");
        tabCM3.setHeaderValue("Marca");
        tabCM4.setHeaderValue("Agregar");

        tabla.repaint();
        try {
            String sql = "SELECT abarrotes.idabarrotes,abarrotes.nombre,marca.nombre AS marca "
                    + "FROM marca JOIN abarrotes ON marca.idmarca=abarrotes.marca_idmarca JOIN "
                    + "producto ON abarrotes.idabarrotes=producto.idForaneoProductos JOIN detallesucursal "
                    + "ON detallesucursal.producto_idproducto=producto.idproducto WHERE "
                    + "detallesucursal.sucursal_idsucursal=" + idSucursal + " AND producto.clasificacion='Abarrote';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayAbarrotes.add(new Abarrotes(String.valueOf(resultado.getInt("idabarrotes")), resultado.getString("nombre"), resultado.getString("marca")));
            }
            for (int i = 0; i < arrayAbarrotes.size(); i++) {
                modelo.addRow(new Object[]{arrayAbarrotes.get(i).getCodigoAbarrote(), arrayAbarrotes.get(i).getNombre(), arrayAbarrotes.get(i).getMarca(), btnAdd});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
