
package Modelo;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import Vista.Pantalla_Gastos;
import java.util.GregorianCalendar;

public class Gastos {
    int idegreso;
     String tipo;
     String total;
     String fecha;
     Calendar fechahoy;
     String empleado_idempleado;
     Pantalla_Gastos vistaGastos;
     private Connection con;
    Conexion conn = new Conexion(); 
    Calendar fecha_actual = new GregorianCalendar();
    
    public Calendar getFechahoy() {
        return fechahoy;
    }

    public void setFechahoy(Calendar fechahoy) {
        this.fechahoy = fechahoy;
    }
    
     public Gastos(){
   //  idegreso = 0;
     tipo = "";
     total = "";
     fecha = "";
     empleado_idempleado = "";
    }

   public Gastos(String descripcion, String total, String turno, String fecha) {
        this.tipo = descripcion; // tipo lo almaceno en descirpcion
        this.total = total;      
        this.empleado_idempleado = turno;  // empleado_idempleado lo almaceno en turno asi se usa en controlador
        this.fecha = fecha;
    }
   
   public Calendar Gastos(String descripcion, String total, String turno, Calendar fechahoy) {
        this.tipo = descripcion; // tipo lo almaceno en descirpcion
        this.total = total;      
        this.empleado_idempleado = turno; 
        
        this.fecha_actual = fechahoy;
        return null;
    }
    
    public int getIdegreso() {
        return idegreso;
    }

    public void setIdegreso(int idegreso) {
        this.idegreso = idegreso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmpleado_idempleado() {
        return empleado_idempleado;
    }

    public void setEmpleado_idempleado(String empleado_idempleado) {
        this.empleado_idempleado = empleado_idempleado;
    }                                        
    
    public boolean Gastosinsert() {
        String sql = null;
        try {
           Connection con  =new Conexion().getConnection();
            sql = "INSERT INTO egreso (tipo,fecha, total, empleado_idempleado)  VALUES (?,?,?,?)";
            com.mysql.jdbc.PreparedStatement stmt = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1, getTipo());
             stmt.setString(2, getFecha());
            stmt.setString(3, getTotal());
            stmt.setString(4, getEmpleado_idempleado());
                      
            stmt.executeUpdate();

            stmt.close();
             
        } catch (SQLException ex) {
            System.err.print(ex);
            return false;
        } 
       return true;
    }                         
}

