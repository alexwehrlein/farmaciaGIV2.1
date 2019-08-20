
package Modelo;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import Vista.Pantalla_Gastos;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
    
    /*========      LISTAR  GASTOS    ===========*/
     public ArrayList<Gastos> listGastos() {
          ArrayList  listaGastos = new ArrayList();
          Gastos gastos; // obj de tipo persona 
          try {              
              Connection con  =new Conexion().getConnection();
              
             String sql = "SELECT `idegreso`, `tipo`, `total`, `fecha`, turno FROM `egreso` \n" +
"  INNER JOIN empleado\n" +
"WHERE egreso.`empleado_idempleado` = empleado.idempleado";            
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();     // valo devuelto se almaena en resultado                                      
           
            while (resultado.next()) {
                
                gastos = new Gastos();
               gastos.setIdegreso(resultado.getInt(1));
                gastos.setTipo(resultado.getString(2));                
                gastos.setTotal(resultado.getString(3));
                gastos.setFecha(resultado.getString(4));
                gastos.setEmpleado_idempleado(resultado.getString(5));
                listaGastos.add(gastos);                
            }     
            pst.close();                                              
          } catch (Exception e) {
          }
          return listaGastos;
      }                                
     
      /* ********************** LLENDO DE LA TABLA DE GASTOS  ******************************** */
    
     public DefaultTableModel LlenarTabla(JTable tablaD){ // recibe como parametro 
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);  // add modelo ala tabla 
        
        modeloT.addColumn("Idegreso");    // add al modelo las 5 columnas con los nombrs
        modeloT.addColumn("Descripcion");
        modeloT.addColumn("Fecha");
        modeloT.addColumn("Total");
        modeloT.addColumn("Turno");
        
        Object[] columna = new Object[5];  //crear un obj con el nombre de colunna
        
        int numResgistros = listGastos().size(); // crear una varibal de tipo int k almacena con el numero de regitrsos k se recupera de la db
        
        for (int i = 0; i < numResgistros; i++ ) { // de cero a uno antes del total de numero de resgitros
            columna[0] = listGastos().get(i).getIdegreso();
            columna[1] = listGastos().get(i).getTipo();
            columna[2] = listGastos().get(i).getFecha(); //  llenado de las columnas de la tbla
            columna[3] = listGastos().get(i).getTotal();
            columna[4] = listGastos().get(i).getEmpleado_idempleado();
              modeloT.addRow(columna); // add una fila alas colimnas
        }        
        
        return modeloT;
    }                                                                                                                             
         
}

