package Controlador;

import Modelo.Conexion;
import Modelo.Gastos;
import Modelo.GastosCon;
import Vista.Pantalla_Gastos;
import Vista.Pantalla_principal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ticket.TikectGasto;
public class Controlador_Gastos {
   
    
    Pantalla_Gastos vistaGastos ;    // vista   /*---------------- PANGASTOS -----------------*/
    GastosCon modeloGastos = new GastosCon();              // modelo 
      String fechadesde="",fechahasta="", fechahoy=""; 
      Gastos gastos;
      String empleado_idempleado;  // declara del turno
      TikectGasto tikectGastos;
    Calendar fecha_actual = new GregorianCalendar();
     public Controlador_Gastos(Pantalla_principal pantalla_principal, String turnoE) {
         this.empleado_idempleado = turnoE;  // se almacena turno en varaiable TurnoE
        vistaGastos = new Pantalla_Gastos();                                
        vistaGastos.setVisible(true);
        
         vistaGastos.setClosable(true);  // se le asigna una x para cerrar
         vistaGastos.setResizable(true);
         Dimension FrameSize = vistaGastos.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();  // la posicion de donde se enciuentra y s eposiciona
        vistaGastos.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
         pantalla_principal.jDesktopPane.add(vistaGastos);
        //vistaGastos.setLocationRelativeTo(null);
           
  
              // e aki el botn de registro
        vistaGastos.btnRegistrarGasto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (vistaGastos.txtdescripcion.getText().isEmpty() || vistaGastos.txtmonto.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No dejar campos en blanco");

                } else {
                    boolean pass = validarFormulario(vistaGastos.txtmonto.getText());
                    boolean pass2 = validarFormulariotexto(vistaGastos.txtdescripcion.getText());

                    if (pass && pass2) {
                         String tipo = vistaGastos.txtdescripcion.getText();
                        String total = vistaGastos.txtmonto.getText();
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyy-MM-dd");  // formato de la fecha e instanciando y darle formato de la fecha 
                      String fecha = formatoFecha.format(vistaGastos.jDateChooserFecha.getDate());  
                  //   Calendar fechahoy = vistaGastos.jDateChooserFecha.setCalendar(fecha_actual);
   
                        gastos = new Gastos(tipo, total, empleado_idempleado, fecha);

                        if (gastos.Gastosinsert()) {
                            JOptionPane.showMessageDialog(null,"idempleado" + empleado_idempleado);
                            JOptionPane.showMessageDialog(null, "Gastos Registrados con Exito");
                            limpiar();
                            JOptionPane.showMessageDialog(null, "Generando Ticket de Gastos");
                     vistaGastos.jTableGastos.setModel(new GastosCon().LlenarTabla(vistaGastos.jTableGastos));
                            tikectGastos = new TikectGasto();
                            tikectGastos.TikectGasto(tipo, total);

                        } else {
                            JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });  
//   TERMINA BOTON DE REGIRTOS



vistaGastos.txtdescripcion.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    vistaGastos.txtdescripcion.requestFocus();
                }
            }
        });






                                                                                                       
                
            //  ME HACE LA BUSKEDA AL PONER UNA FECJA EN EL CHOOSERDATE
        vistaGastos.jDateXUnaFecha.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String fechahoy=vistaGastos.jDateXUnaFecha.toString();
          
            LlenarTablaBusquedaFecha(vistaGastos.jTableGastosFechaActual, llenarfechadehoy()); 
            }
        });
        
        
        // BOTON PARA GENERAR TICKTEC CON LA BUSKEDA DE FECHAS 
        vistaGastos.btnImprimirticket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int fila;
                try {
                    fila = vistaGastos.jTableGastosFechaActual.getSelectedRow();
                    if (fila == -1) {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String tipo = (String) vistaGastos.jTableGastosFechaActual.getValueAt(fila, 1);
                        String total = (String) vistaGastos.jTableGastosFechaActual.getValueAt(fila, 2);
                        JOptionPane.showMessageDialog(null, "Ticket Generado Exitosamente");
                        tikectGastos = new TikectGasto();
                        tikectGastos.TikectGasto(tipo, total);
                    }
                } catch (Exception ex) {
                }
             
            }
        });
        // BOTON PARA GENERAR TICKTEC CON LA BUSKEDA DE FECHAS                          
       

     // boton listar gastos en tablaa
     vistaGastos.btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaGastos.jTableGastos.setModel(new GastosCon().LlenarTabla(vistaGastos.jTableGastos));
/* declaro VISTA.TABLA EN LA VISTA.OBTENGO EL MODELO(GASTOCON es el modelo lo llamo.llamo el metod del modelo LLENARTABLA
                y vuelvo a llamar la vista y la tabla) */                            
                    }                                                   
        });  
  
    } 
    
    
    public String llenarfechadehoy(){ 
        
       int año= vistaGastos.jDateXUnaFecha.getCalendar().get(Calendar.YEAR);
       int mes= vistaGastos.jDateXUnaFecha.getCalendar().get(Calendar.MONTH)+1;
       int dia= vistaGastos.jDateXUnaFecha.getCalendar().get(Calendar.DAY_OF_MONTH);
       fechahoy= año+"-"+mes+"-"+dia;
        return fechahoy;
    } 
    
    
    public void limpiar(){     /*====  VACIAR CAMPOS */
            vistaGastos.txtdescripcion.setText(null);
            vistaGastos.txtmonto.setText(null);
           // vistaGastos.jDateChooserFecha.setDate(null);
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
                           
          
           public boolean validarFormulario(String gastos) {
        boolean next = false;
        Pattern patGastos = Pattern.compile("^[0-9]+([.])?([0-9]+)?$");
        Matcher matGastos = patGastos.matcher(gastos);

        if (matGastos.matches()) {
            next = true;

        } else {
            JOptionPane.showMessageDialog(null, "Solo Numeros");
            vistaGastos.txtmonto.setBackground(Color.red);
        }

        return next;
    }

          
           
           
             public boolean validarFormulariotexto(String gastos) {
        boolean next = false;
        Pattern patGastos = Pattern.compile("^([a-zA-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{1,24}[\\s]*)+$");// ^([a-zA-ZÁÉÍÓÚ]{1}[a-zñáéíóú]{1,24}[\\s]*)+$
        Matcher matGastos = patGastos.matcher(gastos);

        if (matGastos.matches()) {
            next = true;

        } else {
            JOptionPane.showMessageDialog(null, "Solo letras");
            vistaGastos.txtdescripcion.setBackground(Color.red);
        }

        return next;
    }
          
          
          
}  
