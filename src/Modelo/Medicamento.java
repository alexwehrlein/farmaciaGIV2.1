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
import javax.swing.ImageIcon;
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
public class Medicamento {

    private Connection con;
    String codigoMedicamento, marcaComercial, sustancia, laboratorio, tipo, precio, cantidad;

    public Medicamento(String codigoMedicamento, String marcaComercial, String sustancia, String laboratorio, String tipo, String precio, String cantidad) {
        this.codigoMedicamento = codigoMedicamento;
        this.marcaComercial = marcaComercial;
        this.sustancia = sustancia;
        this.laboratorio = laboratorio;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;

        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Medicamento(String codigoMedicamento, String sustancia, String tipo) {
        this.codigoMedicamento = codigoMedicamento;
        this.sustancia = sustancia;
        this.tipo = tipo;
        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Medicamento() {
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

    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    public String getMarcaComercial() {
        return marcaComercial;
    }

    public void setMarcaComercial(String marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public String getSustancia() {
        return sustancia;
    }

    public void setSustancia(String sustancia) {
        this.sustancia = sustancia;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public boolean registrarMedicamento(DefaultTableModel modelo, String idProveedor) {
        try {
            String sql = "INSERT INTO MEDICAMENTOS(idmedicamentos,marcaComercial,sustancia,laboratorio,tipo,precio) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(getCodigoMedicamento()));
            ps.setString(2, getMarcaComercial());
            ps.setString(3, getSustancia());
            ps.setString(4, getLaboratorio());
            ps.setString(5, getTipo());
            ps.setFloat(6, Float.parseFloat(getPrecio()));
            ps.execute();
            con.commit();
            sql = "INSERT INTO PRODUCTO VALUES(Default," + getCodigoMedicamento() + ",'Medicamento'," + idProveedor + ")";
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

    public boolean modificarMedicamento() {
        try {
            String sql = "UPDATE MEDICAMENTOS SET marcaComercial=?, sustancia=?,laboratorio=?, tipo=?, precio=? WHERE idMedicamentos=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, getMarcaComercial());
            ps.setString(2, getSustancia());
            ps.setString(3, getLaboratorio());
            ps.setString(4, getTipo());
            ps.setFloat(5, Float.parseFloat(getPrecio()));
            ps.setInt(6, Integer.parseInt(getCodigoMedicamento()));
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

    public void obtenerMedicamento(String codigo) {
        try {
            String sql = "SELECT * FROM medicamentos WHERE idmedicamentos=" + codigo;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                setCodigoMedicamento(String.valueOf(resultado.getInt("idmedicamentos")));
                setMarcaComercial(resultado.getString("marcaComercial"));
                setSustancia(resultado.getString("sustancia"));
                setLaboratorio(resultado.getString("laboratorio"));
                setTipo(resultado.getString("tipo"));
                setPrecio(String.valueOf(resultado.getFloat("precio")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel cargarRegistroMedicamentos(JTable tabla, String idSucursal) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.setDefaultRenderer(Object.class, new Render());
        JButton btnAlta = new JButton("Alta");
        btnAlta.setName("Alta");
//        ImageIcon im = new ImageIcon(getClass().getResource("/Imagenes/editar.png"));
//        btnEditar.setIcon(im);
        ArrayList<Medicamento> arrayMedicamento = new ArrayList<>();
        try {
            String sql = "SELECT medicamentos.idmedicamentos,medicamentos.marcacomercial,"
                    + "medicamentos.sustancia,medicamentos.laboratorio,medicamentos.tipo,"
                    + "medicamentos.precio,detallesucursal.cantidadProductos FROM medicamentos "
                    + "JOIN producto ON medicamentos.idmedicamentos=producto.idForaneoProductos "
                    + "JOIN detallesucursal ON detallesucursal.producto_idproducto=producto.idproducto "
                    + "WHERE detallesucursal.sucursal_idsucursal=" + idSucursal + " AND producto.clasificacion='Medicamento';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayMedicamento.add(new Medicamento(String.valueOf(resultado.getInt("idmedicamentos")), resultado.getString("marcaComercial"), resultado.getString("sustancia"), resultado.getString("laboratorio"), resultado.getString("tipo"), String.valueOf(resultado.getFloat("precio")), String.valueOf(resultado.getInt("cantidadProductos"))));
            }
            for (int i = 0; i < arrayMedicamento.size(); i++) {
                modelo.addRow(new Object[]{arrayMedicamento.get(i).getCodigoMedicamento(), arrayMedicamento.get(i).getMarcaComercial(), arrayMedicamento.get(i).getSustancia(), arrayMedicamento.get(i).getLaboratorio(), arrayMedicamento.get(i).getTipo(), arrayMedicamento.get(i).getPrecio(), arrayMedicamento.get(i).getCantidad(), btnAlta});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public DefaultTableModel cargarRegistroMedicamentosSolicitante(JTable tabla, String idSucursal) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.setDefaultRenderer(Object.class, new Render());
        JButton btnAdd = new JButton("Add");
        btnAdd.setName("Add");
        ArrayList<Medicamento> arrayMedicamento = new ArrayList<>();
        JTableHeader head = tabla.getTableHeader();
        TableColumnModel tcm = head.getColumnModel();
        TableColumn tabCM = tcm.getColumn(0);
        TableColumn tabCM2 = tcm.getColumn(1);
        TableColumn tabCM3 = tcm.getColumn(2);
        TableColumn tabCM4 = tcm.getColumn(3);
        tabCM.setHeaderValue("Codigo");
        tabCM2.setHeaderValue("Sustancia");
        tabCM3.setHeaderValue("Tipo de Medicamento");
        tabCM4.setHeaderValue("Agregar");
        
        tabla.repaint();

        try {
            String sql = "SELECT medicamentos.idmedicamentos,medicamentos.sustancia,medicamentos.tipo "
                    + "FROM medicamentos JOIN producto ON medicamentos.idmedicamentos=producto.idForaneoProductos "
                    + "JOIN detallesucursal ON detallesucursal.producto_idproducto=producto.idproducto "
                    + "WHERE detallesucursal.sucursal_idsucursal=" + idSucursal + " AND producto.clasificacion='Medicamento';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayMedicamento.add(new Medicamento(String.valueOf(resultado.getInt("idmedicamentos")), resultado.getString("sustancia"), resultado.getString("tipo")));
            }
            for (int i = 0; i < arrayMedicamento.size(); i++) {
                modelo.addRow(new Object[]{arrayMedicamento.get(i).getCodigoMedicamento(), arrayMedicamento.get(i).getSustancia(), arrayMedicamento.get(i).getTipo(), btnAdd});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
