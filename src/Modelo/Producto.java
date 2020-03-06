package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producto {
    
    private String codigo;
    private int id;
    private int idSucursal;
    private String marca;
    private String tipo;
    private int cantidad;
    private int promocion;
    private float precio;
    private int existe;
    private Connection con;
    Conexion conn = new Conexion();

    public int getExiste() {
        return existe;
    }

    public void setExiste(int existe) {
        this.existe = existe;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPromocion() {
        return promocion;
    }

    public void setPromocion(int promocion) {
        this.promocion = promocion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Producto(String codigo, String marca, String tipo, int cantidad, int promocion, float precio) {
        this.codigo = codigo;
        this.marca = marca;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.promocion = promocion;
        this.precio = precio;
    }

    public Producto(String codigo, int idSucursal) {
        this.codigo = codigo;
        this.idSucursal = idSucursal;
    }

    @Override
    public String toString() {
        return "Producto{" + "codigo=" + codigo + ", id=" + id + ", idSucursal=" + idSucursal + ", marca=" + marca + ", tipo=" + tipo + ", cantidad=" + cantidad + ", promocion=" + promocion + ", precio=" + precio + ", existe=" + existe + '}';
    }

    
    public void producto() {

        try {
            String sql = "SELECT IFNULL(m.marcaComercial, IFNULL(co.nombre , a.nombre)) AS nombre , " +
                        "IFNULL(m.precio, IFNULL(co.precio , a.precio)) AS precio , " +
                        "IFNULL(m.tipo,'') AS tipo , " +
                        "d.* FROM detallesucursal d " +
                        "LEFT JOIN medicamentos m ON (m.idmedicamentos = d.producto_idproducto) " +
                        "LEFT JOIN cosmeticos co ON (co.idcosmeticos = d.producto_idproducto) " +
                        "LEFT JOIN abarrotes a ON (a.idabarrotes = d.producto_idproducto) " +
                        "WHERE d.producto_idproducto = "+getCodigo()+" AND d.sucursal_idsucursal = "+getIdSucursal()+"";
            
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            if (resultado.next()) {
                setCodigo(resultado.getString("producto_idproducto"));
                setMarca(resultado.getString("nombre"));
                setTipo(resultado.getString("tipo"));
                setPrecio(resultado.getFloat("precio"));
                setCantidad(resultado.getInt("cantidadProductos"));
                setPromocion(resultado.getInt("promocion"));
            }else{
                setExiste(1);
            }
            stm.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, " Error ", ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, "Error " + ex);
            }
        }
    }
    
}
