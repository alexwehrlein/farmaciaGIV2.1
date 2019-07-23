
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author cachorra
 */
public class GastosCon {
    Conexion conexion; // variable tipo conexion
    
    public GastosCon(){ // constructor de this class
        conexion = new Conexion();//instanciar la variblle tipo conexion
    }
    
     /*  ======   INSERCION DE LOS GATOS  ========= */
      public String insertGastos(String tipo, String total, String fecha, String empleado_idempleado){  //pasando parametros
        String respuestaRegistro = null;                     //
        try {
           // Connection accesoDB =  conexion.getConnection();                      
            Connection connection =new Conexion().getConnection();            
            PreparedStatement ps = null;            
           
           String sql = "INSERT INTO egreso ( tipo, total, fecha, empleado_idempleado)  VALUES (?,?,?,?)";
           ps = (PreparedStatement) connection.prepareStatement(sql);   //
               // ps.setString(1, idegreso);
                ps.setString(1, tipo);
                ps.setString(2, total);
                ps.setString(3, fecha);
                ps.setString(4, empleado_idempleado);
               
                int numFAfectadas = ps.executeUpdate();
                
                if(numFAfectadas>0){
                    respuestaRegistro = "Gastos Registrados";
                }                                                           
        } catch (Exception e) {
        }
        return respuestaRegistro;
    }
    
    
    
     public ArrayList<Gastos> listGastos() {
          ArrayList  listaGastos = new ArrayList();
          Gastos gastos; // obj de tipo persona 
          try {
              
              Connection con  =new Conexion().getConnection();
              
              String sql = "SELECT * FROM persona";            
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();     // valo devuelto se almaena en resultado                                      
           
            while (resultado.next()) {
                
                gastos = new Gastos();
                gastos.setIdegreso(resultado.getString(1));
                gastos.setTipo(resultado.getString(2));
                gastos.setFecha(resultado.getString(3));
                gastos.setTotal(resultado.getString(4));
                gastos.setEmpleado_idempleado(resultado.getString(5));
                listaGastos.add(gastos);
            }                       
                            
          } catch (Exception e) {
          }
          return listaGastos;
      }                               
    
    
    
}
