package Controlador;

import Modelo.*;
import Vista.*;

import Vista.Pantalla_Gastos;
import Vista.Pantalla_principal;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controlador_Gastos implements ActionListener{
    //objeto de pantalla
     Pantalla_Gastos vistaGastos ;    // vista   /*---------------- PANGASTOS -----------------*/
    GastosCon modeloGastos = new GastosCon();              // modelo
    Pantalla_principal pantalla_principal;
    String fechadesde="",fechahasta="", fechahoy="";            
      
     /*----------------------->pangastos vistaGastos */
    public Controlador_Gastos(Pantalla_Gastos vistaGastos, GastosCon modeloGastos){ // construct con recibir paramteros
        this.modeloGastos = modeloGastos;  
        this.vistaGastos = vistaGastos;//inicializar modelo y vista con los paramteros k se reciben 
        vistaGastos.btnRegistrarGasto.addActionListener(this); // add el botn registrar 
       vistaGastos.btnListar.addActionListener(this);
       vistaGastos.btnImprimirTikect.addActionListener(this);
    }

     Controlador_Gastos(Pantalla_principal pantalla_principal) { // need los paramters de pantalla principal para ppner mi ventana
     
       vistaGastos = new Pantalla_Gastos();   //Pantalla_Gastos();
        vistaGastos.setVisible(true);
        vistaGastos.setResizable(true);
        //pp.setSize(981, 474);
        vistaGastos.setClosable(true);  // se le asigna una x para cerrar
       vistaGastos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //  jaja 
        Dimension FrameSize = vistaGastos.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();  // la posicion de donde se enciuentra y s eposiciona
        vistaGastos.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        //vistaGastos.setLocationRelativeTo(null);
      // pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(vistaGastos);
  // vista.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // <<-- UN CIERRE LA VISTA DEL REPORTE CUANDO SE PRESIONE LA X de cerrar
       
    } 
   
   
    
       /* ********************** LLENDO DE LA TABLA DE GASTOS  ******************************** */
    
     public void LlenarTabla(JTable tablaD){ // recibe como parametro 
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);  // add modelo ala tabla 
        
        modeloT.addColumn("idegreso");    // add al modelo las 5 columnas con los nombrs
        modeloT.addColumn("tipo");
        modeloT.addColumn("fecha");
        modeloT.addColumn("total");
        modeloT.addColumn("empleado_idempleado");
        
        Object[] columna = new Object[5];  //crear un obj con el nombre de colunna
        
        int numResgistros = modeloGastos.listGastos().size(); // crear una varibal de tipo int k almacena con el numero de regitrsos k se recupera de la db
        
        for (int i = 0; i < numResgistros; i++ ) { // de cero a uno antes del total de numero de resgitros
            columna[0] = modeloGastos.listGastos().get(i).getIdegreso();
            columna[1] = modeloGastos.listGastos().get(i).getTipo();
            columna[2] = modeloGastos.listGastos().get(i).getFecha(); //  llenado de las columnas de la tbla
            columna[3] = modeloGastos.listGastos().get(i).getTotal();
            columna[4] = modeloGastos.listGastos().get(i).getEmpleado_idempleado();
              modeloT.addRow(columna); // add una fila alas colimnas
        }        
    }
     
     public void limpiar(){     /*====  VACIAR CAMPOS */
            vistaGastos.txtdescripcion.setText(null);
            vistaGastos.txtmonto.setText(null);
            vistaGastos.txtempleado.setText(null);
            vistaGastos.jDateChooserFecha.setDate(null);
         }                      
                                                 
    public void actionPerformed(ActionEvent e){
      //if(vistaGastos.btnRegistrarGasto == e.getSource()){ 
        if(e.getSource() == vistaGastos.btnRegistrarGasto){ // si el evento es generado por el botn registrar
            
           // String idegreso = vistaGastos.txtidegreso.getText();    // recuperando los valores ingresdo x el usuario
            String tipo = vistaGastos.txtdescripcion.getText();
            String total = vistaGastos.txtmonto.getText();
           SimpleDateFormat formatoFecha = new SimpleDateFormat("yyy-MM-dd");  // formato de la fecha e instanciando y darle formato de la fecha 
           String fecha = formatoFecha.format(vistaGastos.jDateChooserFecha.getDate()); // recuperar la fecha desde la vista            
           
           String empleado_idempleado = vistaGastos.txtempleado.getText();
            
            // declarar una variable de tipo string dar valor k se recupera con el metodo --> ""insertar persona""
            String resRegistro = modeloGastos.insertGastos(tipo, total, fecha, empleado_idempleado);  //
            
            if(resRegistro!=null){ // validando la insercion
                 JOptionPane.showMessageDialog(null, resRegistro);  // REGISTRO EXITOSO
                 limpiar();   //  LLAMADO DEL METODO LIMPIAR
                LlenarTabla(vistaGastos.jTableGastos); // DESDE AKI LLAMO EL LLENADO DE LA TABLA, PERO LA LLAMO DESDE LA VISTA, CON LA VARIABLE/OBJETO DE LA VISTA Y EL NOMBRE DE LA TABLA
            } else{
                JOptionPane.showMessageDialog(null, "NO RESGITRADO");
            }
            
        }               /*======  LISTAR LOS GATOS CONSULTA SIMPLE  ======= */
        if(e.getSource() == vistaGastos.btnListar){ // si el evento es generado x el boton listar 
          // JOptionPane.showMessageDialog(null, " BTN LISTAR....");
            LlenarTabla(vistaGastos.jTableGastos); // se llena la tabla y se manda como parametro la tabla de la vista JdateGastos
        }  
        
        
        /*------------------------ BOTON DE BUSCAR FECHAS UNA SOLA FECHA ---------------------------*/
       if(e.getSource() == vistaGastos.btnBuscaFecha){ 
           
          String fechahoy=vistaGastos.jDateXUnaFecha.toString();
          
            LlenarTablaBusquedaFecha(vistaGastos.jTableGastosFechaActual, llenarfechadehoy()); // se llena la tabla y se manda como parametro la tabla de la vista JdateGastos
        }
        /*-------------------------------------------------------------*/        
        
        
        /*------------------------ BOTON DE BUSCAR POR DOS FECHAS  ---------------------------*/
       if(e.getSource() == vistaGastos.btnBuscaXFechas){ 
           
            String fecha1=vistaGastos.jDateFecha1.toString();
            String fecha2=vistaGastos.jDateFecha2.toString();            
                          JOptionPane.showMessageDialog(null,llenarfechadesde());
                            JOptionPane.showMessageDialog(null,llenarfechahasta());                          
            LlenarTablaBusquedaRangoFechas(vistaGastos.jTableGastosxFechas, llenarfechadesde(), llenarfechahasta()); // se llena la tabla y se manda como parametro la tabla de la vista JdateGastos
        }
        /*-------------------------------------------------------------*/       
        
    }
       
      public String llenarfechadesde(){ // Ordena la fecha del componente Jcalendar  que esta de la sig. manera:  dia /mes / aÃ±o
        
       int año= vistaGastos.jDateFecha1.getCalendar().get(Calendar.YEAR);
       int mes= vistaGastos.jDateFecha1.getCalendar().get(Calendar.MONTH)+1;
       int dia= vistaGastos.jDateFecha1.getCalendar().get(Calendar.DAY_OF_MONTH);
       fechadesde= año+"-"+mes+"-"+dia;
        return fechadesde;
    }
    public String llenarfechahasta(){ // Ordena la fecha del componente Jcalendar  que esta de la sig. manera:  dia /mes / aÃ±o
        
       int año= vistaGastos.jDateFecha2.getCalendar().get(Calendar.YEAR);
       int mes= vistaGastos.jDateFecha2.getCalendar().get(Calendar.MONTH)+1;
       int dia= vistaGastos.jDateFecha2.getCalendar().get(Calendar.DAY_OF_MONTH);
       fechahasta= año+"-"+mes+"-"+dia;
        return fechahasta;
    }
    
     public String llenarfechadehoy(){ 
        
       int año= vistaGastos.jDateXUnaFecha.getCalendar().get(Calendar.YEAR);
       int mes= vistaGastos.jDateXUnaFecha.getCalendar().get(Calendar.MONTH)+1;
       int dia= vistaGastos.jDateXUnaFecha.getCalendar().get(Calendar.DAY_OF_MONTH);
       fechahoy= año+"-"+mes+"-"+dia;
        return fechahoy;
    }
        
        
      /*  ======   HACIENDO UNA CONSULTA DE LOS GASTOS A BUSCAR CON -- ((UNA)) -- FECHA DETERINADA =======A*/          
          public void LlenarTablaBusquedaFecha(JTable tablaD, String jDateXUnaFecha){ // recibe como parametro 
         Object[] columna = new Object[5];  //crear un obj con el nombre de colunna
            Connection con  =new Conexion().getConnection(); // CONEXION DB 
              DefaultTableModel modeloT = new DefaultTableModel();
                  tablaD.setModel(modeloT);  // add modelo ala tabla 
        
        modeloT.addColumn("Idegreso");    // add al modelo las 5 columnas con los nombrs TABLA
        modeloT.addColumn("Descripcion");        
        modeloT.addColumn("Total");
        modeloT.addColumn("Fecha");
       modeloT.addColumn("Turno");               
       
        try {
         String sSQL = "SELECT *FROM egreso\n" + "WHERE fecha = '"+llenarfechadehoy()+"'";
         //   "SELECT *FROM egreso\n" + "WHERE fecha = '2019-07-20'";            
        PreparedStatement ps = con.prepareStatement(sSQL);       
        try (ResultSet rs = ps.executeQuery(sSQL)) {
            while (rs.next()) {
                columna[0] = rs.getString("idegreso");  /* === LA DB == */
                columna[1] = rs.getString("tipo");
                columna[2] = rs.getString("total");
                columna[3] = rs.getString("fecha");
                columna[4] = rs.getString("empleado_idempleado");                
                modeloT.addRow(columna);
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.PLAIN_MESSAGE);    
    }
}
                    
                                                                          
          /*  ======   HACIENDO UNA CONSULTA DE LOS GASTOS A BUSCAR CON -- ((DOS)) -- FECHA DETERINADA =======A*/          
          public void LlenarTablaBusquedaRangoFechas(JTable tablaD, String jDateFecha1, String jDateFecha2){ // recibe como parametro 
         Object[] columna = new Object[5];  //crear un obj con el nombre de colunna
            Connection con  =new Conexion().getConnection(); // CONEXION DB 
              DefaultTableModel modeloT = new DefaultTableModel();
                  tablaD.setModel(modeloT);  // add modelo ala tabla 
        
        modeloT.addColumn("Idegreso");    // add al modelo las 5 columnas con los nombrs TABLA
        modeloT.addColumn("Descripcion");        
        modeloT.addColumn("Total");
        modeloT.addColumn("Fecha");
       modeloT.addColumn("Turno");               
       
        try {
         String sSQL = "SELECT *FROM egreso\n" + "WHERE fecha BETWEEN '"+llenarfechadesde()+"' AND '"+llenarfechahasta()+"'";
        
        PreparedStatement ps = con.prepareStatement(sSQL);       
        try (ResultSet rs = ps.executeQuery(sSQL)) {
            while (rs.next()) {
                columna[0] = rs.getString("idegreso");  /* === LA DB == */
                columna[1] = rs.getString("tipo");
                columna[2] = rs.getString("total");
                columna[3] = rs.getString("fecha");
                columna[4] = rs.getString("empleado_idempleado");                
                modeloT.addRow(columna);
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e, "Advertencia", JOptionPane.PLAIN_MESSAGE);    
    }
}
         
                          
  
         
       
}
        
