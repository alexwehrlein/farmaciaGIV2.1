/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.table.TableColumn;

/**
 *
 * @author saube
 */
public class Empleados {
    

    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String turno;
    private String status;
    private int sucursalId;
    private String nombreSucursal;
    private Connection con;
    Conexion conn = new Conexion();

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Empleados() {
    }

    public Empleados(int id, String nombre, String telefono, String email, String turno, String status, int sucursalId) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.turno = turno;
        this.status = status;
        this.sucursalId = sucursalId;
    }

    public Empleados(int id, String nombre, String telefono, String email, String turno, String status, String nombreSucursal) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.turno = turno;
        this.status = status;
        this.nombreSucursal = nombreSucursal;
    }

    public Empleados(String nombre, String telefono, String email, String turno, String status, int sucursalId) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.turno = turno;
        this.status = status;
        this.sucursalId = sucursalId;
    }

    public boolean ingresarEmpleado() {
        String sql = null;
        try {
            con = conn.getConnection();
            sql = " Insert into empleado (nombre,telefono,email,turno,estatus,sucursal_idsucursal) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1, getNombre());
            stmt.setString(2, getTelefono());
            stmt.setString(3, getEmail());
            stmt.setString(4, getTurno());
            stmt.setString(5, getStatus());
            stmt.setInt(6, getSucursalId());
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }

    public boolean ModificarEmpleado() {
        String sql = null;
        try {
            con = conn.getConnection();
            sql = " UPDATE empleado SET nombre=?,telefono=?,email=?,turno=?,estatus=?,sucursal_idsucursal=? WHERE idempleado = ? ";
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1, getNombre());
            stmt.setString(2, getTelefono());
            stmt.setString(3, getEmail());
            stmt.setString(4, getTurno());
            stmt.setString(5, getStatus());
            stmt.setInt(6, getSucursalId());
            stmt.setInt(7, getId());
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "Error " + ex);
            return false;
        } finally {
            conn.getClose();
        }
        return true;
    }

    public DefaultTableModel cargarRegistros(JTable jt, String palabra) {
        String sql = null;
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
        //ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
        //btnModificar.setIcon(im);
        //ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
        //btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Empleados> arrayEmpleados = new ArrayList<>();
        try {
            if (palabra.isEmpty()) {
                sql = "SELECT empleado.* , sucursal.nombre AS nombreSucursal  FROM empleado INNER JOIN sucursal on empleado.sucursal_idsucursal = sucursal.idsucursal";
            } else {
                sql = "SELECT empleado.* , sucursal.nombre AS nombreSucursal  FROM empleado INNER JOIN sucursal on empleado.sucursal_idsucursal = sucursal.idsucursal  WHERE empleado.nombre LIKE '%"+palabra+"%'";
            }
            
            con = conn.getConnection();
            Statement pst = (Statement) con.createStatement();
            ResultSet resultado = pst.executeQuery(sql);
            while (resultado.next()) {
                arrayEmpleados.add(new Empleados(resultado.getInt("idempleado"), resultado.getString("nombre"), resultado.getString("telefono"), resultado.getString("email"), resultado.getString("turno"), resultado.getString("estatus"), resultado.getString("nombreSucursal")));
            }
            for (int i = 0; i < arrayEmpleados.size(); i++) {
                modelo.addRow(new Object[]{arrayEmpleados.get(i).getId(), arrayEmpleados.get(i).getNombre(), arrayEmpleados.get(i).getTelefono(),
                    arrayEmpleados.get(i).getEmail(), arrayEmpleados.get(i).getTurno(), arrayEmpleados.get(i).getStatus(), arrayEmpleados.get(i).getNombreSucursal(), btnModificar});
            }
            pst = null;

        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "Error " + ex);
        } finally {
            conn.getClose();
        }

        return modelo;
    }

    public ArrayList<Sucursal> octenerSucursales() {
        ArrayList<Sucursal> listaSucursal = new ArrayList<Sucursal>();
        try {
            String sql = "SELECT * FROM sucursal";
            con = conn.getConnection();
            Statement pst = (Statement) con.createStatement();
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("idSucursal");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");

                Sucursal sucursal = new Sucursal(id, nombre, direccion);
                listaSucursal.add(sucursal);
            }
            pst = null;
        } catch (Exception e) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, "Error " + e);
        } finally {
            conn.getClose();
        }
        return listaSucursal;
    }

}
