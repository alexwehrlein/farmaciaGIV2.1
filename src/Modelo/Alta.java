/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Alta {

    private Connection con;

    public Alta() {
        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean altaProductos(String codigo, String tipo, String idSucursal, String cantidad) {
        try {
            String sql = "UPDATE detallesucursal SET cantidadProductos=(cantidadProductos+" + cantidad + ") WHERE producto_idproducto=" + idProducto(codigo, tipo) + " AND sucursal_idsucursal=" + idSucursal + ";";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            con.commit();
            return true;
        } catch (SQLException ex) {
            // Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean bajaProductos(String clave,DefaultTableModel modelo,String idSucursalLocal, String idSucursalSolicita) {
        try {
            String sql;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                sql = "UPDATE detallesucursal SET cantidadProductos=(cantidadProductos-" + modelo.getValueAt(i, 2).toString() + ") WHERE producto_idproducto=" + idProducto(modelo.getValueAt(i, 0).toString(), modelo.getValueAt(i, 1).toString()) + " AND sucursal_idsucursal=" + idSucursalLocal + ";";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.execute();
                con.commit();
                
                sql = "UPDATE detallesucursal SET cantidadProductos=(cantidadProductos+" + modelo.getValueAt(i, 2).toString() + ") WHERE producto_idproducto=" + idProducto(modelo.getValueAt(i, 0).toString(), modelo.getValueAt(i, 1).toString()) + " AND sucursal_idsucursal=" + idSucursalSolicita + ";";
                PreparedStatement ps2 = con.prepareStatement(sql);
                ps2.execute();
                con.commit();
            }
              if (!modificarEstatusPasoDeProducto(clave)) {
                return false;
            }
            
            return true;
        } catch (SQLException ex) {
            // Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
        public boolean modificarEstatusPasoDeProducto(String clave) {
        try {
            String sql = "UPDATE pasoproducto SET estatus='Hecho' WHERE idpasoproducto="+clave;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            con.commit();
            return true;
        } catch (SQLException ex) {
            // Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String idProducto(String codigo, String tipo) {
        String idProducto = "";
        try {
            Statement sta = con.createStatement();
            String sql = "SELECT idproducto FROM producto WHERE idForaneoProductos=" + codigo + " AND clasificacion='" + tipo + "';";
            ResultSet resultado = sta.executeQuery(sql);
            if (resultado.next()) {
                idProducto = String.valueOf(resultado.getInt("idproducto"));
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return idProducto;

    }

    public String codigoProducto(String idproducto) {
        String codigo = "";
        try {
            Statement sta = con.createStatement();
            String sql = "SELECT idforaneoproductos AS codigo FROM  PRODUCTO WHERE idproducto=" + idproducto;
            ResultSet resultado = sta.executeQuery(sql);
            if (resultado.next()) {
                codigo = String.valueOf(resultado.getInt("codigo"));
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return codigo;

    }

    public String obtenerExistencia(String codigo, String tipo, String idSucursal) {
        String idProducto = null;
        try {
            Statement sta = con.createStatement();
            String sql = "SELECT cantidadProductos AS cantidad FROM detalleSucursal "
                    + "WHERE sucursal_idsucursal=" + idSucursal + " AND producto_idproducto="
                    + "(SELECT idproducto FROM producto WHERE idForaneoProductos=" + codigo + " AND "
                    + "clasificacion='" + tipo + "');";
            ResultSet resultado = sta.executeQuery(sql);
            if (resultado.next()) {
                idProducto = String.valueOf(resultado.getInt("cantidad"));
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return idProducto;

    }

    public boolean registroPasoDeProductos(DefaultTableModel modelo, String clavePaso, String sucursal1, String sucursal2) {
        try {
            String sql = "INSERT INTO pasoProducto VALUES(?,CURDATE(),CURTIME(),?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(clavePaso));
//            ps.setDate(2, Date.valueOf("CURDATE()"));
//            ps.setTime(3, Time.valueOf("CURTIME()"));
            ps.setString(2, sucursal1);
            ps.setString(3, sucursal2);
            ps.setString(4, "Pendiente");
            ps.execute();
            con.commit();
            if (!registrarDetallePasoProducto(modelo, clavePaso)) {
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

    public boolean registrarDetallePasoProducto(DefaultTableModel modelo, String clave) {
        try {

            String sql = "";
            for (int i = 0; i < modelo.getRowCount(); i++) {
                sql = "INSERT INTO detallePasoProducto VALUES (" + clave + "," + idProducto(modelo.getValueAt(i, 0).toString(), modelo.getValueAt(i, 1).toString()) + "," + modelo.getValueAt(i, 2).toString() + ")";
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
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return false;
    }

    public ArrayList<Integer> obtenerClaves() {
        ArrayList<Integer> arrayMarca = new ArrayList<>();
        try {
            String sql = "select idpasoProducto AS clave FROM pasoproducto WHERE estatus='Pendiente';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayMarca.add(resultado.getInt("clave"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrayMarca;
    }

    public DefaultTableModel obtenerProductosSolicitados(JTable jt, String sucursal2) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        String sql = "SELECT fecha,hora,sucursal1 FROM pasoproducto WHERE sucursal2='" + sucursal2 + "' AND estatus='Pendiente';";
        ArrayList<ArrayList<String>> arrayProductos = new ArrayList<ArrayList<String>>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                ArrayList<String> array2 = new ArrayList<>();
                array2.add(String.valueOf(resultado.getDate("fecha")));
                array2.add(String.valueOf(resultado.getTime("hora")));
                array2.add(resultado.getString("sucursal1"));
                arrayProductos.add(array2);
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).get(0), arrayProductos.get(i).get(1), arrayProductos.get(i).get(2)});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public boolean validarExistenciaDeLaClave(String clave, String fecha,String hora, String sucursalQueSolicita) {
        try {
            String sql = "SELECT * FROM pasoproducto WHERE idpasoproducto= " + clave + " AND fecha='" + fecha + "' AND sucursal1='" + sucursalQueSolicita + "' AND hora='"+hora+"';";
            Statement sta = con.createStatement();
            ResultSet resultado = sta.executeQuery(sql);
            return resultado.next();
        } catch (SQLException ex) {
            Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public DefaultTableModel obtenerProductosSolicitadosPorSucursal(JTable jt, String clave) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnSelect = new JButton("X");
        btnSelect.setName("S");
//        ImageIcon im = new ImageIcon(getClass().getResource("/Imagenes/editar.png"));
//        btnSelect.setIcon(im);

        JButton btnDetalles = new JButton("Detalles");
        btnDetalles.setName("D");
//        ImageIcon im2 = new ImageIcon(getClass().getResource("/Imagenes/editar.png"));
//        btnDetalles.setIcon(im2);

        String sql = "select detallepasoproducto.producto_idproducto AS codigo,producto.clasificacion AS "
                + "tipo,detallepasoproducto.cantidadproducto from detallepasoproducto JOIN producto ON "
                + "detallepasoproducto.producto_idproducto=producto.idproducto WHERE "
                + "detallepasoproducto.pasoproducto_idpasoproducto=" + clave;
        ArrayList<ArrayList<String>> arrayProductos = new ArrayList<ArrayList<String>>();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                ArrayList<String> array2 = new ArrayList<>();
                array2.add(codigoProducto(String.valueOf(resultado.getInt("codigo"))));
                array2.add(resultado.getString("tipo"));
                array2.add(String.valueOf(resultado.getInt("cantidadproducto")));
                arrayProductos.add(array2);
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).get(0), arrayProductos.get(i).get(1), arrayProductos.get(i).get(2), btnDetalles, btnSelect});
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
