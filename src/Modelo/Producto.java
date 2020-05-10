package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    private float promocionP;
    private int promocionDescuento;
    private int promocionCantidad;
    private float promocionPrecio;
    private int PromocionTipo;
    private String search;
    
    private Connection con;
    Conexion conn = new Conexion();

    public float getPromocionP() {
        return promocionP;
    }

    public void setPromocionP(float promocionP) {
        this.promocionP = promocionP;
    }

    public int getPromocionDescuento() {
        return promocionDescuento;
    }

    public void setPromocionDescuento(int promocionDescuento) {
        this.promocionDescuento = promocionDescuento;
    }

    public int getPromocionCantidad() {
        return promocionCantidad;
    }

    public void setPromocionCantidad(int promocionCantidad) {
        this.promocionCantidad = promocionCantidad;
    }

    public float getPromocionPrecio() {
        return promocionPrecio;
    }

    public void setPromocionPrecio(float promocionPrecio) {
        this.promocionPrecio = promocionPrecio;
    }

    public int getPromocionTipo() {
        return PromocionTipo;
    }

    public void setPromocionTipo(int PromocionTipo) {
        this.PromocionTipo = PromocionTipo;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    

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
    
    public Producto(String codigo, String marca) {
        this.codigo = codigo;
        this.marca = marca;
    }

    public Producto(int idSucursal, String search) {
        this.idSucursal = idSucursal;
        this.search = search;
    }

    public Producto() {
    }

    @Override
    public String toString() {
        return marca;
    }
    
    public void producto() {

        try {
            String sql = "SELECT IFNULL(m.marcaComercial, IFNULL(co.nombre , a.nombre)) AS nombre , " +
                        "IFNULL(m.precio, IFNULL(co.precio , a.precio)) AS precio , " +
                        "IFNULL(m.tipo,'') AS tipo , " +
                        "d.* , p.*, p.tipo AS tipoPromo , p.precio AS precioPromo , p.promocion AS promo FROM detallesucursal d " +
                        "LEFT JOIN medicamentos m ON (m.idmedicamentos = d.producto_idproducto) " +
                        "LEFT JOIN cosmeticos co ON (co.idcosmeticos = d.producto_idproducto) " +
                        "LEFT JOIN abarrotes a ON (a.idabarrotes = d.producto_idproducto) " +
                        "LEFT JOIN promociones p ON (p.idProducto = d.producto_idproducto) "+
                        "WHERE d.sucursal_idsucursal = "+getIdSucursal()+" AND d.producto_idproducto = "+getCodigo()+" ";
            
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
                
                setPromocionP(resultado.getFloat("promo"));
                setPromocionDescuento(resultado.getInt("descuento"));
                setPromocionCantidad(resultado.getInt("cantidad"));
                setPromocionPrecio(resultado.getInt("precioPromo"));
                setPromocionTipo(resultado.getInt("tipoPromo"));
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
    
    public ArrayList<Producto> octenerProducto() {
        ArrayList<Producto> listaProductos = new ArrayList<Producto>();
        try {
            String sql = "SELECT IFNULL(m.marcaComercial, IFNULL(co.nombre , a.nombre)) AS nombre , " +
                        "IFNULL(m.precio, IFNULL(co.precio , a.precio)) AS precio , " +
                        "IFNULL(m.tipo,'') AS tipo , " +
                        "d.* , p.*, p.tipo AS tipoPromo , p.precio AS precioPromo , p.promocion AS promo FROM detallesucursal d " +
                        "LEFT JOIN medicamentos m ON (m.idmedicamentos = d.producto_idproducto) " +
                        "LEFT JOIN cosmeticos co ON (co.idcosmeticos = d.producto_idproducto) " +
                        "LEFT JOIN abarrotes a ON (a.idabarrotes = d.producto_idproducto) " +
                        "LEFT JOIN promociones p ON (p.idProducto = d.producto_idproducto) "+
                        "WHERE d.sucursal_idsucursal = "+getIdSucursal()+" AND IFNULL(m.marcaComercial, IFNULL(co.nombre , a.nombre)) LIKE '"+getSearch()+"%' ";
            
            con = new Conexion().getConnection();
            Statement stm = (Statement) con.createStatement();
            ResultSet resultado = stm.executeQuery(sql);
            while (resultado.next()) {
                String codigo = resultado.getString("producto_idproducto");
                String nombre = resultado.getString("nombre");
                Producto p = new Producto(codigo, nombre);
                listaProductos.add(p);
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
        return listaProductos;
    }
    
}
