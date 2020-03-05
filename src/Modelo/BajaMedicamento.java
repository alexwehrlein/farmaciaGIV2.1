package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BajaMedicamento {

    private int id;
    private String nombre;
    private String codigo;
    private int cantidad;
    private String fecha_r;
    private String fecha_b;
    private Boolean baja;
    private Connection con;

    Conexion conn = new Conexion();

    public BajaMedicamento() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha_r() {
        return fecha_r;
    }

    public void setFecha_r(String fecha_r) {
        this.fecha_r = fecha_r;
    }

    public String getFecha_b() {
        return fecha_b;
    }

    public void setFecha_b(String fecha_b) {
        this.fecha_b = fecha_b;
    }

    public Boolean getBaja() {
        return baja;
    }

    public void setBaja(Boolean baja) {
        this.baja = baja;
    }

    public BajaMedicamento(int id , String codigo, String nombre, int cantidad, Boolean baja) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.baja = baja;
    }

    public void obtenerMedicamento(String codigo, int idSucursal) {
        con = conn.getConnection();
        try {
            String sql = "SELECT * FROM detallesucursal INNER JOIN medicamentos on medicamentos.idmedicamentos = detallesucursal.sucursal_idsucursal WHERE detallesucursal.sucursal_idsucursal = " + idSucursal + " AND detallesucursal.producto_idproducto = " + codigo + "";

            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                setCodigo(String.valueOf(resultado.getInt("producto_idproducto")));
                setNombre(resultado.getString("marcaComercial"));
                setCantidad(resultado.getInt("cantidadProductos"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.WARNING, "" + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BajaMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean bajaMedicamento(DefaultTableModel modelo, int idSucursal) {
        con = conn.getConnection();
        int existencias = 0, existenciaFinal = 0 , idproducto = 0;
        String sqlSelect = null, sqlUpdate = null;
        boolean next = true;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            con.setAutoCommit(false);
            for (int i = 0; i < modelo.getRowCount(); i++) {
                boolean status = (boolean) modelo.getValueAt(i, 5);
                String baja = modelo.getValueAt(i, 4).toString();
                idproducto = (int) modelo.getValueAt(i, 0);
                
                if (baja.equals("") && status == true) {
                    sqlSelect = "SELECT * FROM detallesucursal INNER JOIN medicamentos on medicamentos.idmedicamentos = detallesucursal.sucursal_idsucursal WHERE detallesucursal.sucursal_idsucursal = " + idSucursal + " AND detallesucursal.producto_idproducto = " + modelo.getValueAt(i, 1).toString() + "";

                    PreparedStatement pst = con.prepareStatement(sqlSelect);
                    ResultSet resultado = pst.executeQuery();
                    while (resultado.next()) {
                        existencias = resultado.getInt("cantidadProductos");
                    }
                    existenciaFinal = existencias - Integer.parseInt(modelo.getValueAt(i, 3).toString());
                    sqlUpdate = "UPDATE detallesucursal SET cantidadProductos = ? WHERE producto_idproducto = ? AND sucursal_idsucursal = ? ";
                    com.mysql.jdbc.PreparedStatement stmt = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sqlUpdate);
                    stmt.setInt(1, existenciaFinal);
                    stmt.setLong(2, Long.parseLong(modelo.getValueAt(i, 1).toString()));
                    stmt.setInt(3, idSucursal);
                    stmt.executeUpdate();
                    stmt.close();
                    
                    sqlUpdate = "UPDATE baja_medicamento SET baja = ? , fecha_baja = ? WHERE id = ? ";
                    com.mysql.jdbc.PreparedStatement stmt2 = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sqlUpdate);
                    stmt2.setInt(1, 1);
                    stmt2.setDate(2, java.sql.Date.valueOf(dateFormat.format(date)));
                    stmt2.setInt(3, idproducto);
                    stmt2.executeUpdate();
                    stmt2.close();
                }
            }
            con.commit();
        } catch (SQLException ex) {
            try {
                next = false;
                con.rollback();
                Logger.getLogger(Medicamento.class.getName()).log(Level.WARNING, "" + ex);
            } catch (SQLException ex1) {
                next = false;
                Logger.getLogger(BajaMedicamento.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BajaMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return next;
    }

    public Boolean GuadarListaBajas(DefaultTableModel modelo, int idSucursal, int idEmpleado) {
        String sql = null;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {

            for (int i = 0; i < modelo.getRowCount(); i++) {
                con = conn.getConnection();
                sql = " Insert baja_medicamento (codigo,cantidad,idEmpleado,idsucursal,fecha_registro) VALUES (?,?,?,?,?)";
                com.mysql.jdbc.PreparedStatement stmt = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sql);
                stmt.setString(1, modelo.getValueAt(i, 0).toString());
                stmt.setString(2, modelo.getValueAt(i, 2).toString());
                stmt.setInt(3, idEmpleado);
                stmt.setInt(4, idSucursal);
                stmt.setDate(5, java.sql.Date.valueOf(dateFormat.format(date)));
                stmt.executeUpdate();

                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "" + ex);
            }
        }
        return true;

    }

    public DefaultTableModel cargarRegistros(JTable jt, int idSucursal, String fecha_ini, String fecha_fin) {
        String sql = null;
        jt.setDefaultRenderer(Object.class, new Render());

        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<BajaMedicamento> arraybaja = new ArrayList<>();
        try {

            sql = "SELECT * FROM baja_medicamento INNER JOIN medicamentos on medicamentos.idmedicamentos = baja_medicamento.codigo WHERE baja_medicamento.idsucursal = " + idSucursal + " AND fecha_registro BETWEEN '" + fecha_ini + "' AND '" + fecha_fin + "'";

            con = conn.getConnection();
            Statement pst = (Statement) con.createStatement();
            ResultSet resultado = pst.executeQuery(sql);
            while (resultado.next()) {
                boolean statusBaja = (resultado.getInt("baja") == 0) ? false : true;
                arraybaja.add(new BajaMedicamento(resultado.getInt("id") , resultado.getString("codigo"), resultado.getString("marcaComercial"), resultado.getInt("cantidad"), statusBaja));
            }
            for (int i = 0; i < arraybaja.size(); i++) {
                modelo.addRow(new Object[]{arraybaja.get(i).getId(), arraybaja.get(i).getCodigo(), arraybaja.get(i).getNombre(), arraybaja.get(i).getCantidad(), (arraybaja.get(i).getBaja() == true) ? "X" : "", false});
            }
            pst = null;

        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "Error " + ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return modelo;
    }

}
