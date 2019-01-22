/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.PreparedStatement;
import java.awt.Color;
import java.sql.Connection;
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
import javax.swing.table.TableColumn;
import vista.Pantalla_Productos;

/**
 *
 * @author saube
 */
public class Productos {

    private long codigo;
    private String marcaComercial;
    private String sustancias;
    private double precio;
    private String tipoMedicamento;
    private String laboratorio;
    private int proveedor;
    private String nombreProveedor;
    private int cantidad;

    Conexion conexion;
    Pantalla_Productos pantalla_Productos;

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Productos(long codigo, String marcaComercial, String sustancias, double precio, String tipoMedicamento, String laboratorio, String nombreProveedor, int cantidad) {
        this.codigo = codigo;
        this.marcaComercial = marcaComercial;
        this.sustancias = sustancias;
        this.precio = precio;
        this.tipoMedicamento = tipoMedicamento;
        this.laboratorio = laboratorio;
        this.nombreProveedor = nombreProveedor;
        this.cantidad = cantidad;
    }

    public Productos(long codigo, String marcaComercial, String sustancias, double precio, String tipoMedicamento, String laboratorio, int proveedor, int cantidad) {
        this.codigo = codigo;
        this.marcaComercial = marcaComercial;
        this.sustancias = sustancias;
        this.precio = precio;
        this.tipoMedicamento = tipoMedicamento;
        this.laboratorio = laboratorio;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getMarcaComercial() {
        return marcaComercial;
    }

    public void setMarcaComercial(String marcaComercial) {
        this.marcaComercial = marcaComercial;
    }

    public String getSustancias() {
        return sustancias;
    }

    public void setSustancias(String sustancias) {
        this.sustancias = sustancias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(String tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Productos() {

    }

    public Productos(long codigo) {
        this.codigo = codigo;
    }

    public Productos(long codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public Productos(long codigo, double precio, String nombre) {
        this.codigo = codigo;
        this.precio = precio;
        this.marcaComercial = nombre;
    }

    public Productos(String sustancias) {
        this.sustancias = sustancias;
    }

    public double PrrcioProducto() {
        double precio = 0;
        int num;

        try {
            String sql = "SELECT precio FROM productos WHERE codigo = " + codigo;
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                precio = resultado.getDouble("precio");

            }

            pst.close();
            pst = null;
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return precio;
    }

    public boolean ModificarRegristros() {
        try {
            Connection con = new Conexion().getConnection();
            com.mysql.jdbc.Statement stm = (com.mysql.jdbc.Statement) con.createStatement();
            stm.execute("UPDATE productos SET marca_comercial='" + getMarcaComercial() + "' , precio=" + getPrecio() + " WHERE codigo=" + getCodigo());
            con.setAutoCommit(true);
            con.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean verificarCodigo() {
        String sql = null, resultadoSql;
        boolean veCo = false;
        try {
            Connection con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();

            sql = "SELECT marca_comercial FROM productos WHERE codigo=" + getCodigo();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                resultadoSql = resultado.getString("marca_comercial");

                if (resultadoSql != null) {
                    veCo = true;
                } else {
                    veCo = false;
                }

            }
            stm.close();
            stm = null;
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);

        }
        return veCo;

    }

    public ArrayList<Productos> Sustancia(String sustancia) {
        ArrayList<Productos> arrayRegistros = new ArrayList<>();

        try {
            String sql = "SELECT * FROM productos WHERE sustancia like  '%" + sustancia + "%'";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                Productos r = new Productos();
                r.setSustancias(resultado.getString("sustancia"));
                arrayRegistros.add(r);
            }
            pst.close();
            pst = null;
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayRegistros;
    }

    public ArrayList<Proveedor> octenerProveedores() {
        ArrayList<Proveedor> listaProveedor = new ArrayList<Proveedor>();
        try {
            String sql = "SELECT * FROM proveedor";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_proveedor");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");

                Proveedor proveedor = new Proveedor(id, nombre, telefono, correo);
                listaProveedor.add(proveedor);
            }
            pst.close();
            pst = null;
            con.close();
        } catch (Exception e) {
        }
        return listaProveedor;
    }

    public boolean registrarProducto() {
        try {
            Connection connection = new Conexion().getConnection();
            Statement stm = (Statement) connection.createStatement();
            stm.execute("INSERT INTO productos VALUES(" + getCodigo() + ",'" + getMarcaComercial() + "','" + getSustancias() + "'," + getPrecio() + ",'"
                    + getTipoMedicamento() + "','" + getLaboratorio() + "'," + getProveedor() + "," + getCantidad() + ")");
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public boolean Modificarexistencias() {
        try {
            Connection connection = new Conexion().getConnection();
            com.mysql.jdbc.Statement stm = (com.mysql.jdbc.Statement) connection.createStatement();
            stm.execute("UPDATE productos SET cantidad='" + getCantidad() + "' WHERE codigo=" + getCodigo());
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean eliminarMedicamento() {
        try {
            Connection connection = new Conexion().getConnection();
            com.mysql.jdbc.Statement stm = (com.mysql.jdbc.Statement) connection.createStatement();
            stm.execute("DELETE FROM productos WHERE codigo=" + getCodigo());
            connection.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;

        }
    }

    public DefaultTableModel cargarRegistroEgreso(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        //JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(1);
        //String op[] = {"Luz", "Agua", "Gas", "Producto"};
        //tipo = new JComboBox(op);
        // col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        btnModificar.setIcon(im);
        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Productos> arrayProductos = new ArrayList<>();
        try {

            String sql = "SELECT codigo,marca_comercial,sustancia,precio,tipo_medicamento,laboratorio,"
                    + "proveedor.nombre,cantidad FROM productos JOIN proveedor "
                    + "on productos.proveedor=proveedor.id_proveedor order by marca_comercial";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayProductos.add(new Productos(resultado.getLong("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getDouble("precio"),
                        resultado.getString("tipo_medicamento"), resultado.getString("laboratorio"), resultado.getString("nombre"), resultado.getInt("cantidad")));
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).getCodigo(), arrayProductos.get(i).getMarcaComercial(),
                    arrayProductos.get(i).getSustancias(), arrayProductos.get(i).getPrecio(), arrayProductos.get(i).getTipoMedicamento(), arrayProductos.get(i).getLaboratorio(),
                    arrayProductos.get(i).getCantidad(), btnModificar});
            }
            pst.close();
            pst = null;
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public DefaultTableModel BuscarRegistroEgreso(JTable jt, long codigo) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        //JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(1);
        //String op[] = {"Luz", "Agua", "Gas", "Producto"};
        //tipo = new JComboBox(op);
        // col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        btnModificar.setIcon(im);
        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Productos> arrayProductos = new ArrayList<>();
        try {

            String sql = "SELECT codigo,marca_comercial,sustancia,precio,tipo_medicamento,laboratorio,"
                    + "proveedor.nombre,cantidad FROM productos JOIN proveedor "
                    + "on productos.proveedor=proveedor.id_proveedor  WHERE codigo=" + codigo;
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayProductos.add(new Productos(resultado.getLong("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getDouble("precio"),
                        resultado.getString("tipo_medicamento"), resultado.getString("laboratorio"), resultado.getString("nombre"), resultado.getInt("cantidad")));
            }
            int num = arrayProductos.size();

            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).getCodigo(), arrayProductos.get(i).getMarcaComercial(),
                    arrayProductos.get(i).getSustancias(), arrayProductos.get(i).getPrecio(), arrayProductos.get(i).getTipoMedicamento(), arrayProductos.get(i).getLaboratorio(),
                    arrayProductos.get(i).getCantidad(), btnModificar});
            }
            System.out.println(num);
            if (num < 1) {
                System.out.println("entro");
                pantalla_Productos = new Pantalla_Productos();
                pantalla_Productos.codigo.setBackground(Color.RED);
            } else {

            }
            pst.close();
            pst = null;
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

    public DefaultTableModel Buscar2RegistroEgreso(JTable jt, String codigo) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        //JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(1);
        //String op[] = {"Luz", "Agua", "Gas", "Producto"};
        //tipo = new JComboBox(op);
        // col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        btnModificar.setIcon(im);
        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Productos> arrayProductos = new ArrayList<>();
        try {

            String sql = "SELECT codigo,marca_comercial,sustancia,precio,tipo_medicamento,laboratorio,"
                    + "proveedor.nombre,cantidad FROM productos JOIN proveedor "
                    + "on productos.proveedor=proveedor.id_proveedor  WHERE marca_comercial like '%" + codigo + "%'";
            Connection con = new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayProductos.add(new Productos(resultado.getLong("codigo"), resultado.getString("marca_comercial"), resultado.getString("sustancia"), resultado.getDouble("precio"),
                        resultado.getString("tipo_medicamento"), resultado.getString("laboratorio"), resultado.getString("nombre"), resultado.getInt("cantidad")));
            }
            for (int i = 0; i < arrayProductos.size(); i++) {
                modelo.addRow(new Object[]{arrayProductos.get(i).getCodigo(), arrayProductos.get(i).getMarcaComercial(),
                    arrayProductos.get(i).getSustancias(), arrayProductos.get(i).getPrecio(), arrayProductos.get(i).getTipoMedicamento(), arrayProductos.get(i).getLaboratorio(),
                    arrayProductos.get(i).getCantidad(), btnModificar});
            }
            pst.close();
            pst = null;
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }

}
