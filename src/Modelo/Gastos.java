
package Modelo;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.SQLException;


public class Gastos {
     String idegreso;
     String tipo;
     String fecha;
     String total;
     String empleado_idempleado;
    
 public Gastos(){
     idegreso = "";
     tipo = "";
     fecha = "";
     total = "";
     empleado_idempleado = "";
    }

    public String getIdegreso() {
        return idegreso;
    }

    public void setIdegreso(String idegreso) {
        this.idegreso = idegreso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEmpleado_idempleado() {
        return empleado_idempleado;
    }

    public void setEmpleado_idempleado(String empleado_idempleado) {
        this.empleado_idempleado = empleado_idempleado;
    }

      
    
   }  // CIERRE clase -->>  Gastos