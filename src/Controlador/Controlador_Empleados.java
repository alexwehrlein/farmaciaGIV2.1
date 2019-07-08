/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleados;
import Modelo.Sucursal;
import Vista.Pantalla_Personal;
import Vista.Pantalla_principal;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author saube
 */
public class Controlador_Empleados {

    Pantalla_Personal pp;//objeto de pantalla personal
    Pantalla_principal pantalla_principal;
    Empleados empleados;
    int idEmpleado = 0;

    public Controlador_Empleados(Pantalla_principal pantalla_principal) {
        pp = new Pantalla_Personal();
        pp.setVisible(true);
        pp.setResizable(true);
        //pp.setSize(981, 474);
        pp.setClosable(true);
        pp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pp.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pp.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pp);
        pp.tablaEmpleados.setModel(new Empleados().cargarRegistros(pp.tablaEmpleados,""));

        pp.btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.btnAction.setText("Guardar");
                pp.jDialogEmpleado.setBounds(249, 154, 770, 480);
                pp.jDialogEmpleado.setResizable(false);
                pp.jDialogEmpleado.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                pp.jLabelStatus.setText("Nuevo Empleado");
                pp.comboStatus.setVisible(false);
                pp.jLabelSta.setVisible(false);
                pp.jDialogEmpleado.show(); 
                
            }
        });

        pp.tablaEmpleados.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int column = pp.tablaEmpleados.getColumnModel().getColumnIndexAtX(me.getX());
                int row = me.getY() / pp.tablaEmpleados.getRowHeight();
                int filaseleccionada;
                if (row < pp.tablaEmpleados.getRowCount() && row >= 0 && column < pp.tablaEmpleados.getColumnCount() && column >= 0) {
                    Object value = pp.tablaEmpleados.getValueAt(row, column);
                    if (value instanceof JButton) {
                        ((JButton) value).doClick();
                        JButton boton = (JButton) value;

                        if (boton.getName().equals("btnModificar")) {
                                pp.btnAction.setText("Modificar");
                                filaseleccionada = pp.tablaEmpleados.getSelectedRow();
                                int id = (int) pp.tablaEmpleados.getValueAt(filaseleccionada, 0);
                                String nombre = (String) pp.tablaEmpleados.getValueAt(filaseleccionada, 1);
                                String telefono = (String) pp.tablaEmpleados.getValueAt(filaseleccionada, 2);
                                String email = (String) pp.tablaEmpleados.getValueAt(filaseleccionada, 3);
                                String turno = (String) pp.tablaEmpleados.getValueAt(filaseleccionada, 4);
                                String status = (String) pp.tablaEmpleados.getValueAt(filaseleccionada, 5);
                                String sucursal = (String) pp.tablaEmpleados.getValueAt(filaseleccionada, 6);
                                idEmpleado = id;
                                setSelectedValue(pp.comboSucursal , sucursal);
                                pp.txtNombre.setText(nombre);
                                pp.txtTelefono.setText(telefono);
                                pp.txtEmail.setText(email);
                                pp.comboTurno.setSelectedItem(turno);
                                pp.comboStatus.setSelectedItem(status);
                                pp.comboStatus.setVisible(true);
                                pp.jLabelSta.setVisible(true);
                                pp.jDialogEmpleado.setBounds(249, 154, 770, 480);
                                pp.jDialogEmpleado.setResizable(false);
                                pp.jDialogEmpleado.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                pp.jLabelStatus.setText("Modificar Empleado");
                                pp.jDialogEmpleado.show();
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {

            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }
        });

        pp.btnAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pp.txtNombre.getText().isEmpty() && pp.txtEmail.getText().isEmpty() && pp.txtTelefono.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No dejar campos en blanco", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!pp.txtTelefono.getText().matches("[0-9]{10}")) {
                    JOptionPane.showMessageDialog(null, "Numero de telefono incorrecto.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pp.txtTelefono.requestFocus();
                    return;
                }

                if (!pp.txtEmail.getText().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.([a-zA-Z]{2,4})+$")) {
                    JOptionPane.showMessageDialog(null, "Correo electronico incorrecto.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pp.txtTelefono.requestFocus();
                    return;
                }
                if (!pp.txtNombre.getText().matches("^([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\']+[\\s])+([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])+[\\s]?([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])?$")) {
                    JOptionPane.showMessageDialog(null, "Ingrese nombre y apellido.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    pp.txtTelefono.requestFocus();
                    return;
                }

                String nombre = pp.txtNombre.getText();
                String telefono = pp.txtTelefono.getText();
                String email = pp.txtEmail.getText();
                String turno = pp.comboTurno.getSelectedItem().toString();
                String status = pp.comboStatus.getSelectedItem().toString();;
                Sucursal sucursal = (Sucursal) pp.comboSucursal.getSelectedItem();

                if (idEmpleado == 0) {// si es 0 indica que se regristrara un nuevo usuario
                    empleados = new Empleados(nombre, telefono, email, turno, "SI", sucursal.getIdSucursal());
                    boolean next = empleados.ingresarEmpleado();
                    if (next) {
                        Clear_Table();
                        limpiar_campos();
                        pp.tablaEmpleados.setModel(new Empleados().cargarRegistros(pp.tablaEmpleados , ""));
                        JOptionPane.showMessageDialog(null, "Empleado guardo.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {// se modificaran los datos del usuario
                    empleados = new Empleados(idEmpleado, nombre, telefono, email , turno, status, sucursal.getIdSucursal());
                    boolean next = empleados.ModificarEmpleado();
                    if (next) {
                        Clear_Table();
                        limpiar_campos();
                        pp.tablaEmpleados.setModel(new Empleados().cargarRegistros(pp.tablaEmpleados , ""));
                        JOptionPane.showMessageDialog(null, "Empleado Modificado.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        pp.jDialogEmpleado.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        pp.search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String palabra = pp.search.getText();
                empleados = new Empleados();
                Clear_Table();
                empleados.cargarRegistros(pp.tablaEmpleados, palabra);
            }
        }
        );

    }
    
    private void setSelectedValue(JComboBox comboBox, String value){
        Sucursal sucursal;
        for (int i = 0; i < comboBox.getItemCount(); i++)
        {
            sucursal = (Sucursal)comboBox.getItemAt(i);
            if (sucursal.getNombre().equalsIgnoreCase(value)){
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void limpiar_campos(){
        pp.txtEmail.setText("");
        pp.txtNombre.setText("");
        pp.txtTelefono.setText("");
    }
    
    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pp.tablaEmpleados.getModel();
        int filas = pp.tablaEmpleados.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
}
