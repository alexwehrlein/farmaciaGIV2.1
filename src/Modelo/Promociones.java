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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Promociones {

    private Connection con;
    Conexion conn = new Conexion();
    private String idProducto;
    private int idSucursal;
    private float promocion;
    private int descuento;
    private int cantidad;
    private int tipo;
    private float precio;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public float getPromocion() {
        return promocion;
    }

    public void setPromocion(float promocion) {
        this.promocion = promocion;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Promociones() {
    }

    public Promociones(String idProducto, int idSucursal, String nombre, float promocion, int descuento, int cantidad, float precio, int tipo) {
        this.idProducto = idProducto;
        this.idSucursal = idSucursal;
        this.promocion = promocion;
        this.descuento = descuento;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Promociones(String idProducto, int idSucursal) {
        this.idProducto = idProducto;
        this.idSucursal = idSucursal;
    }

    public void eliminarPromocion() {
        String sql = null;
        try {
            con = conn.getConnection();
            con.setAutoCommit(false);
            sql = "UPDATE detallesucursal SET promocion=? WHERE producto_idproducto = ? AND sucursal_idsucursal = ? ";
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setInt(1, 0);
            stmt.setString(2, getIdProducto());
            stmt.setInt(3, getIdSucursal());
            stmt.executeUpdate();

            sql = "DELETE FROM promociones WHERE idProducto = ? AND idSucursal = ? ";
            stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1, getIdProducto());
            stmt.setInt(2, getIdSucursal());
            stmt.executeUpdate();

            con.commit();
            stmt.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
                Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, "Error " + ex);
            } catch (SQLException ex1) {
                Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, "" + ex1);
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, "" + ex);
            }
        }
    }

    public boolean ingresarPromocion() {
        String sql = null;
        try {
            con = conn.getConnection();
            con.setAutoCommit(false);
            sql = " Insert promociones (idProducto,idSucursal,promocion,descuento,cantidad,precio,tipo) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1, getIdProducto());
            stmt.setInt(2, getIdSucursal());
            stmt.setFloat(3, getPromocion());
            stmt.setInt(4, getDescuento());
            stmt.setInt(5, getCantidad());
            stmt.setFloat(6, getPrecio());
            stmt.setInt(7, getTipo());
            stmt.executeUpdate();

            sql = "UPDATE detallesucursal SET promocion=? WHERE producto_idproducto = ? AND sucursal_idsucursal = ? ";
            stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setInt(1, getTipo());
            stmt.setString(2, getIdProducto());
            stmt.setInt(3, getIdSucursal());
            stmt.executeUpdate();
            con.commit();
            stmt.close();

        } catch (SQLException ex) {
            try {
                con.rollback();
                Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, "Error " + ex);
                return false;
            } catch (SQLException ex1) {
                Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, "" + ex1);
            }
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Promociones.class.getName()).log(Level.SEVERE, "" + ex);
            }
        }
        return true;
    }

    public DefaultTableModel cargarRegistros(JTable jt, int idSucursal, String palabra) {
        String sql = null;
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        //ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        //btnModificar.setIcon(im);
        ImageIcon ie = new ImageIcon(getClass().getResource("/Images/menu/eli.png"));
        btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Promociones> arrayPromociones = new ArrayList<>();
        try {

            sql = "SELECT IFNULL(m.marcaComercial, IFNULL(co.nombre , a.nombre)) AS nombre , "
                    + "IFNULL(m.precio, IFNULL(co.precio , a.precio)) AS precio , "
                    + "IFNULL(m.tipo,'') AS tipo , "
                    + "d.* ,  p.* , p.tipo AS tipoPromo , p.precio AS precioPromo , p.promocion AS promo FROM detallesucursal d "
                    + "LEFT JOIN medicamentos m ON (m.idmedicamentos = d.producto_idproducto) "
                    + "LEFT JOIN cosmeticos co ON (co.idcosmeticos = d.producto_idproducto) "
                    + "LEFT JOIN abarrotes a ON (a.idabarrotes = d.producto_idproducto) "
                    + "LEFT JOIN promociones p ON (p.idProducto = d.producto_idproducto) ";
            if (palabra.length() > 0) {
                 sql += "WHERE d.promocion <> 0 AND IFNULL(m.marcaComercial, IFNULL(co.nombre , a.nombre)) LIKE '"+palabra+"%' OR d.producto_idproducto LIKE '"+palabra+"%'  ORDER BY nombre ASC ";
            } else {
                sql += "WHERE d.promocion <> 0 And d.sucursal_idsucursal = " + idSucursal + " ORDER BY nombre ASC ";
            }

            con = conn.getConnection();
            Statement pst = (Statement) con.createStatement();
            ResultSet resultado = pst.executeQuery(sql);
            while (resultado.next()) {
                arrayPromociones.add(new Promociones(resultado.getString("producto_idproducto"), resultado.getInt("sucursal_idsucursal"), resultado.getString("nombre"), resultado.getFloat("promo"), resultado.getInt("descuento"), resultado.getInt("cantidad"), resultado.getFloat("precioPromo"), resultado.getInt("tipoPromo")));
            }
            for (int i = 0; i < arrayPromociones.size(); i++) {
                System.out.println(arrayPromociones.get(i));
                String promocion = "";
                if (arrayPromociones.get(i).getTipo() == 1) {
                    promocion = "Precio $ " + arrayPromociones.get(i).getPromocion();
                } else if (arrayPromociones.get(i).getTipo() == 2) {
                    promocion = "Descuento del % " + arrayPromociones.get(i).getDescuento();
                } else if (arrayPromociones.get(i).getTipo() == 3) {
                    promocion = "Promcion del " + arrayPromociones.get(i).getCantidad() + " X " + arrayPromociones.get(i).getPrecio();
                }
                modelo.addRow(new Object[]{arrayPromociones.get(i).getIdProducto(), arrayPromociones.get(i).getNombre(), promocion, btnEliminar});
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
