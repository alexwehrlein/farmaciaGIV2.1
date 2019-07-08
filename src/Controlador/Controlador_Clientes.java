/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Clientes;
import Vista.Pantalla_Clientes;
import Vista.Pantalla_principal;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adriana
 */
public class Controlador_Clientes {
    Pantalla_Clientes pantalla_clientes;//objeto de pantalla clientes
    Pantalla_principal pantalla_principal;
  Clientes cliente;
 
    
    public Controlador_Clientes(Pantalla_principal pantalla_principal) {
        pantalla_clientes = new Pantalla_Clientes();
        pantalla_clientes.setVisible(true);
        pantalla_clientes.setResizable(true);
        //pp.setSize(981, 474);
        pantalla_clientes.setClosable(true);
        pantalla_clientes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pantalla_clientes.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_clientes.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_clientes);
        pantalla_clientes.rSTableMetroClientes.setModel(new Clientes().consultaClientes(pantalla_clientes.rSTableMetroClientes));//PARA CONSULTAR LA TABLA

         pantalla_clientes.btnAgregarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pantalla_clientes.dispose();
                pantalla_clientes.AgregarClientes.setVisible(true);
                pantalla_clientes.AgregarClientes.setResizable(false);
                pantalla_clientes.AgregarClientes.setBounds(449, 154, 503, 350);
                
            }
        });
         pantalla_clientes.btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new Controlador_Clientes(pantalla_principal);
            pantalla_clientes.AgregarClientes.setVisible(false);
               
            }
        });
         pantalla_clientes.rSTableMetroClientes.addMouseListener(new java.awt.event.MouseAdapter() {//Para al dos click en un dato en la
             //tabla mande llamar a la ventana
             @Override
            public void mouseClicked(MouseEvent dosclick) {
                int fila;
                if(dosclick.getClickCount()==2){
                pantalla_clientes.dispose();
                pantalla_clientes.EditarClientes.setVisible(true);
                pantalla_clientes.EditarClientes.setResizable(false);//ANCHO   LARGO
                pantalla_clientes.EditarClientes.setBounds(449, 154, 503, 560);
               
                fila = pantalla_clientes.rSTableMetroClientes.getSelectedRow();  //Sirve para saber que fila de la tabla se selecciono
                                int id = (int) pantalla_clientes.rSTableMetroClientes.getValueAt(fila, 0);
                                String nombre = pantalla_clientes.rSTableMetroClientes.getValueAt(fila, 1).toString();
                                String telefono = pantalla_clientes.rSTableMetroClientes.getValueAt(fila, 2).toString();
                                String email = pantalla_clientes.rSTableMetroClientes.getValueAt(fila, 3).toString();
                   String id2= String.valueOf(id);
                   //PARA MOSTRAR LOS DATOS EN EL FORMULARIO DE EDITAR 
                   pantalla_clientes.IdEditar.setText(id2);
                   pantalla_clientes.nombreCliente.setText(id2);           
                   pantalla_clientes.nombreCliente.setText(nombre);
                   pantalla_clientes.telefonoCliente.setText(telefono);
                   pantalla_clientes.emailCliente.setText(email);
                   
                   
                }
                //clicks para poder eliminar
                if(dosclick.getClickCount()==1){
                    int column = pantalla_clientes.rSTableMetroClientes.getColumnModel().getColumnIndexAtX(dosclick.getX());
                int row = dosclick.getY() / pantalla_clientes.rSTableMetroClientes.getRowHeight();
                int filaEliminar;
                if (row < pantalla_clientes.rSTableMetroClientes.getRowCount() && row >= 0 && column < pantalla_clientes.rSTableMetroClientes.getColumnCount() && column >= 0) {
                    Object value = pantalla_clientes.rSTableMetroClientes.getValueAt(row, column);
                    if (value instanceof JButton) {
                    ((JButton) value).doClick();
                        JButton boton = (JButton) value;
                        if (boton.getName().equals("btnEliminar")) {
                            int reply = JOptionPane.showConfirmDialog(null, "¿Eliminar Cliete?", "Modificar", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                filaEliminar= pantalla_clientes.rSTableMetroClientes.getSelectedRow();
                                 int id = (int) pantalla_clientes.rSTableMetroClientes.getValueAt(filaEliminar, 0);
                                 cliente= new Clientes(id);
                                 if (cliente.EliminarClientes()) {
                                      JOptionPane.showMessageDialog(null,"Dato eliminado corectamente");
                                      ActualizarTabla();//actualiza la tabla
                                     //esta la actualiza
            
            //es para hacer la consulta
                                }else{
                    JOptionPane.showMessageDialog(null,"Error","A ocurrido un error",JOptionPane.WARNING_MESSAGE);
                }
                    
                                
                            }
                            
                        }
                    
                }
                }
                }
            }
         });
         pantalla_clientes.btnCancelarEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new Controlador_Clientes(pantalla_principal);
            pantalla_clientes.EditarClientes.setVisible(false);
        
            }
            });
          pantalla_clientes.btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre=pantalla_clientes.NombreAgregar.getText();
                String telefono=pantalla_clientes.TelefonoAgregar.getText();
                String email=pantalla_clientes.EmailAgregar.getText();
                String estatus="Activo";
                cliente = new Clientes(0,nombre,telefono,email,estatus);
                if (cliente.registrarClientes()) {
                    JOptionPane.showMessageDialog(null,"Se registraron corectamente");
                    limpiarCamposInsertar();
                    ActualizarTabla();
                    pantalla_clientes.AgregarClientes.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(null,"Error","A ocurrido un error",JOptionPane.WARNING_MESSAGE);
                }
                    
                
           
        
            }
            });
          pantalla_clientes.btnAceptarEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //PARA MOSTRAR LOS DATOS EN EL FORMULARIO DE EDITAR 
                  String id=  pantalla_clientes.IdEditar.getText();
                  String nombre = pantalla_clientes.nombreCliente.getText();
                  String telefono= pantalla_clientes.telefonoCliente.getText();
                  String email =pantalla_clientes.emailCliente.getText();
                  int id2=Integer.valueOf(id);
            cliente= new Clientes(id2,nombre,telefono,email,"activo");
                if (cliente.EditarClientes()) {
                    JOptionPane.showMessageDialog(null,"Datos modificados  corectamente", "Modificación",JOptionPane.DEFAULT_OPTION);
                   limpiarCamposInsertar();
       
                    
                }else{
                    JOptionPane.showMessageDialog(null,"Error","A ocurrido un error",JOptionPane.WARNING_MESSAGE);
                }
        
            }
            });
          pantalla_clientes.rSMTextBusquedaClien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre =pantalla_clientes.rSMTextBusquedaClien.getText();
                //cliente=new Clientes(nombre);
                   eliminarTabla();
            pantalla_clientes.rSTableMetroClientes.setModel(new Clientes(nombre).busquedaClientes(pantalla_clientes.rSTableMetroClientes));//PARA CONSULTAR LA TABLA
            //    System.out.println(nombre); 
         
               
            }
            });
          
    }
     public void limpiarCamposEditar() {//limpiar formulario de editar
         pantalla_clientes.IdEditar.setText("");
        pantalla_clientes.nombreCliente.setText("");
        pantalla_clientes.emailCliente.setText("");
        pantalla_clientes.telefonoCliente.setText("");
    }
     public void limpiarCamposInsertar() {//limpiar formulario de agregar
         pantalla_clientes.NombreAgregar.setText("");
        pantalla_clientes.TelefonoAgregar.setText("");
        pantalla_clientes.EmailAgregar.setText("");
    
    }
     private void ActualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_clientes.rSTableMetroClientes.getModel();
        int filas = pantalla_clientes.rSTableMetroClientes.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
        pantalla_clientes.rSTableMetroClientes.setModel(new Clientes().consultaClientes(pantalla_clientes.rSTableMetroClientes));//PARA CONSULTAR LA TABLA
    }
     private void eliminarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_clientes.rSTableMetroClientes.getModel();
        int filas = pantalla_clientes.rSTableMetroClientes.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
       // pantalla_clientes.rSTableMetroClientes.setModel(new Clientes().busquedaClientes(pantalla_clientes.rSTableMetroClientes));//PARA CONSULTAR LA TABLA
    }
     

}
