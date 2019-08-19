
package Modelo;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

                /*        THIS CLAS ES MY */
public class Empleado {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String turno;
    private String estatus;
    private String sucursal_idsucursal;
    
    
   private Connection con;
    Conexion conn = new Conexion();
    

    public int getIdempleado() {
        return id;
    }

    public void setIdempleado(int idempleado) {
        this.id = idempleado;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getSucursal_idsucursal() {
        return sucursal_idsucursal;
    }

    public void setSucursal_idsucursal(String sucursal_idsucursal) {
        this.sucursal_idsucursal = sucursal_idsucursal;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }        
    
     public Empleado(int id, String nombre, String telefono, String correo, String turno, String estado, String sucursal_idsucursal) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = correo;
        this.turno = turno;
        this.estatus = estado;
        this.sucursal_idsucursal = sucursal_idsucursal;
        
          con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }     
     
     public Empleado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
     
     
     public Empleado(){
         con = new Conexion().getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }             
     
     public String[] obtenerContraUsuario(String usuario){
        String []arr = {"","","","",""};
        try { //          select contrasena,login.empleado_idempleado,empleado.nombre,empleado.turno from login join empleado on login.empleado_idempleado=empleado.idempleado '" + usuario + "'";      
  // String sql = "select contrasena,login.id_empleado,empleado.puesto,empleado.nombre,empleado.turno from login join empleado on login.id_empleado=empleado.id_empleado  '" + usuario + "'";
     String sql = "select contrasena,login.empleado_idempleado,empleado.puesto,empleado.nombre,empleado.turno from login"
             + " inner join empleado on login.empleado_idempleado=empleado.idempleado where usuario=  '" + usuario + "'";                      
            java.sql.Statement sta = con.createStatement();
            ResultSet resultado = sta.executeQuery(sql);
            if(resultado.next()){
                arr[0]=resultado.getString("contrasena");
                arr[1]=String.valueOf(resultado.getInt("empleado_idempleado"));
                arr[2]=resultado.getString("puesto");
                arr[3]=resultado.getString("nombre");
                arr[4]=resultado.getString("turno");
         // "Select user, password From usuarios where user=? AND password=?";       
            }
            con.close();
        } catch (SQLException ex) {
        }
        return arr;
    }    
}
