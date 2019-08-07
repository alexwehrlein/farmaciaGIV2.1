package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Modelo.Gastos;
import java.sql.Statement;

public class GastosCon {
    
    Conexion conexion; // variable tipo conexion
    
    public GastosCon(){ // constructor de this class
        conexion = new Conexion();//instanciar la variblle tipo conexion
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
          } catch (Exception e) {
          }
          return listaGastos;
      }                           
     
     
      /* ********************** LLENDO DE LA TABLA DE GASTOS  ******************************** */
    
     public DefaultTableModel LlenarTabla(JTable tablaD){ // recibe como parametro 
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);  // add modelo ala tabla 
        
        modeloT.addColumn("idegreso");    // add al modelo las 5 columnas con los nombrs
        modeloT.addColumn("tipo");
        modeloT.addColumn("fecha");
        modeloT.addColumn("total");
        modeloT.addColumn("empleado_idempleado");
        
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
               
     
     /*================    LISTAR BUSQUEDA DE  GASTOS POR UNA SOLA FECHA DETERMINADA   ==================*/
     public ArrayList<Gastos> listBuscaXFecha() {
          ArrayList  listaGastos = new ArrayList();
          Gastos gastos; // obj de tipo persona 
          try {              
              Connection con  =new Conexion().getConnection();
              
             String sql = "SELECT *FROM egreso\n" + "WHERE fecha = '2019-07-20'";            
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
          } catch (Exception e) {
          }
          return listaGastos;
      }   
                         
     
          /*================    LISTAR BUSQUEDA DE  GASTOS POR --- (( DOS )) ---, RANGO DE FECHAS    ==================*/
     public ArrayList<Gastos> listBuscaXRangoFechas(String jDateFecha1, String jDateFecha2) {
          ArrayList  listaGastos = new ArrayList();
          Gastos gastos; // obj de tipo persona 
          try {
             
              Connection con  =new Conexion().getConnection();
              
             String sql = "SELECT *FROM egreso\n" + "WHERE fecha BETWEEN ? AND ?";    
          
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();     // valo devuelto se almaena en resultado                                      
               pst.setDate(1, java.sql.Date.valueOf(jDateFecha1));
              pst.setDate(2, java.sql.Date.valueOf(jDateFecha2));
                    
            while (resultado.next()) {
                
                gastos = new Gastos();
               gastos.setIdegreso(resultado.getInt(1));
                gastos.setTipo(resultado.getString(2));                
                gastos.setTotal(resultado.getString(3));
                gastos.setFecha(resultado.getString(4));
                gastos.setEmpleado_idempleado(resultado.getString(5));
                listaGastos.add(gastos);
            }                                                   
          } catch (Exception e) {
          }
          return listaGastos;
      }                                                                                      
    
}
