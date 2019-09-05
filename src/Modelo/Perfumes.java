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
public class Perfumes {

    private Connection con;
    String codigoPerfume, laboratorio, precio, cantidad, marca, nombre;

    public Perfumes(String codigoPerfume, String nombre, String marca, String laboratorio, String precio, String cantidad) {
        this.codigoPerfume = codigoPerfume;
        this.laboratorio = laboratorio;
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

    public Perfumes(String codigoPerfume, String nombre, String marca) {
        this.codigoPerfume = codigoPerfume;
        this.nombre = nombre;
        this.marca = marca;

        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Perfumes() {
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

    public String getCodigoPerfume() {
        return codigoPerfume;
    }

    public void setCodigoPerfume(String codigoPerfume) {
        this.codigoPerfume = codigoPerfume;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
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

    public String getMarca() {
        return marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean registrarPerfume(DefaultTableModel modelo, String idProveedor) {
        try {
            String sql = "INSERT INTO COSMETICOS(idcosmeticos,nombre,laboratorio,precio,marca_idmarca) VALUES(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(getCodigoPerfume()));
            ps.setString(2, getNombre());
            ps.setString(3, getLaboratorio());
            ps.setFloat(4, Float.parseFloat(getPrecio()));
            ps.setInt(5, Integer.parseInt(getMarca()));
            ps.execute();
            con.commit();
            sql = "INSERT INTO PRODUCTO VALUES(Default," + getCodigoPerfume() + ",'Perfume'," + idProveedor + ")";
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

    public boolean modificarPerfume() {
        try {
            String sql = "UPDATE COSMETICOS SET nombre=?, laboratorio=?,precio=?, marca_idmarca=? WHERE idcosmeticos=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getLaboratorio());
            ps.setFloat(3, Float.parseFloat(getPrecio()));
            ps.setInt(4, Integer.parseInt(getMarca()));
            ps.setInt(5, Integer.parseInt(getCodigoPerfume()));
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

    public void obtenerPerfume(String codigo) {
        try {
            String sql = "SELECT cosmeticos.idcosmeticos,cosmeticos.nombre,cosmeticos.laboratorio,"
                    + "cosmeticos.precio,marca.nombre AS marca FROM cosmeticos JOIN marca ON "
                    + "cosmeticos.marca_idmarca=marca.idmarca WHERE idcosmeticos=" + codigo;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                setCodigoPerfume(String.valueOf(resultado.getInt("idcosmeticos")));
                setNombre(resultado.getString("nombre"));
                setLaboratorio(resultado.getString("laboratorio"));
                setPrecio(String.valueOf(resultado.getFloat("precio")));
                setMarca(resultado.getString("marca"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel cargarRegistroPerfumes(JTable tabla, String idSucursal) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.setDefaultRenderer(Object.class, new Render());
        JButton btnAlta = new JButton("Alta");
        btnAlta.setName("Alta");
//        ImageIcon im = new ImageIcon(getClass().getResource("/Imagenes/editar.png"));
//        btnEditar.setIcon(im);
        ArrayList<Perfumes> arrayPerfumes = new ArrayList<>();
        try {
            String sql = "SELECT cosmeticos.idcosmeticos,cosmeticos.nombre,marca.nombre AS "
                    + "marca,cosmeticos.laboratorio,cosmeticos.precio,detallesucursal.cantidadProductos "
                    + "FROM marca JOIN cosmeticos ON marca.idmarca=cosmeticos.marca_idmarca JOIN producto "
                    + "ON cosmeticos.idcosmeticos=producto.idForaneoProductos JOIN detallesucursal ON "
                    + "detallesucursal.producto_idproducto=producto.idproducto WHERE detallesucursal."
                    + "sucursal_idsucursal=" + idSucursal + " AND producto.clasificacion='Perfume'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayPerfumes.add(new Perfumes(String.valueOf(resultado.getInt("idcosmeticos")), resultado.getString("nombre"), resultado.getString("marca"), resultado.getString("laboratorio"), String.valueOf(resultado.getFloat("precio")), String.valueOf(resultado.getInt("cantidadProductos"))));
            }
            for (int i = 0; i < arrayPerfumes.size(); i++) {
                modelo.addRow(new Object[]{arrayPerfumes.get(i).getCodigoPerfume(), arrayPerfumes.get(i).getNombre(), arrayPerfumes.get(i).getMarca(), arrayPerfumes.get(i).getLaboratorio(), arrayPerfumes.get(i).getPrecio(), arrayPerfumes.get(i).getCantidad(), btnAlta});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public DefaultTableModel cargarRegistroPerfumesSolicitante(JTable tabla, String idSucursal) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.setDefaultRenderer(Object.class, new Render());
        JButton btnAdd = new JButton("Add");
        btnAdd.setName("Add");
        ArrayList<Perfumes> arrayPerfumes = new ArrayList<>();
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
            String sql = "SELECT cosmeticos.idcosmeticos,cosmeticos.nombre,marca.nombre AS marca "
                    + "FROM marca JOIN cosmeticos ON marca.idmarca=cosmeticos.marca_idmarca JOIN "
                    + "producto ON cosmeticos.idcosmeticos=producto.idForaneoProductos JOIN detallesucursal "
                    + "ON detallesucursal.producto_idproducto=producto.idproducto WHERE "
                    + "detallesucursal.sucursal_idsucursal=" + idSucursal + " AND producto.clasificacion='Perfume';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayPerfumes.add(new Perfumes(String.valueOf(resultado.getInt("idcosmeticos")), resultado.getString("nombre"), resultado.getString("marca")));
            }
            for (int i = 0; i < arrayPerfumes.size(); i++) {
                modelo.addRow(new Object[]{arrayPerfumes.get(i).getCodigoPerfume(), arrayPerfumes.get(i).getNombre(), arrayPerfumes.get(i).getMarca(), btnAdd});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
