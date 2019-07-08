/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import vista.Pantalla_Devoluciones;

/**
 *
 * @author saube
 */
public class TikectDevoluciones {
    Pantalla_Devoluciones devoluciones;
     private DefaultTableModel modelo;
    
    public void  TikectDevoluciones(String folio ,String nombre, int piezas, double precio, double total){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        
        
        
        ServicioImp impServicio = new ServicioImp(); // se crea objeto 
        System.out.println(impServicio.getImpresoras()); // imprime todas las impresoras instaladas
         String auxs="";
       
        String impra = "MP-4200 TH"; // Nombre de la impresora

        // Se llama al metodo para imprimir una cadena
        auxs+= "DEVOLUCION\n\n";
        auxs+= "FARMACIAS GI\n";
        auxs+= "Altamirano #6-A\n";
        auxs+= "Iguala de la Independencia\n";
        auxs+= "Folio: " + folio + "\n";
        auxs+= "Fecha: " + dateFormat.format(date) + " Hora: " + hourFormat.format(date) + "\n";
        auxs+= "==========================================\n";
        auxs+= "Cant Descripcion       Precio    Importe\n";
        auxs+= "==========================================\n";

        
    
        
            if (nombre.length() > 17) { // si la descripcion_producto es mayor a 17 la corta
                nombre = nombre.substring(0, 17);
            }
            // Se formatea la cadena a imprimir con String.format para varios string
            auxs+= String.format("%-4s" + " " + "%-17s" + " " + "$%-8s" + " " + "$%-8s", piezas, nombre, precio, total);
            auxs+= "\n";
        
        
        auxs+= "\n==========================================\n";
        auxs+= "Devolucion realizada\nRegrese Pronto\n\n\n\n\n";// Varios saltos para no cortar antes
        impServicio.printCadena(impra, auxs);
        // Cortar el papel ....
        byte[] cutP = new byte[]{0x1d, 'V', 1}; // comado para cortar
        impServicio.printBytes(impra, cutP); // se imprime el bruto 
        
    }
    
}
