/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Empleado;
import modelo.Proveedor;
import modelo.Usuarios;
import vista.Pantalla_Proveedor;
import vista.Pantalla_Usuarios;

/**
 *
 * @author saube
 */
public class Controlador_PantallaUsuarios {

    Pantalla_Usuarios pantalla_Usuarios;
    Usuarios usuarios;

    public Controlador_PantallaUsuarios() {
        pantalla_Usuarios = new Pantalla_Usuarios();
        pantalla_Usuarios.setVisible(true);
        pantalla_Usuarios.setLocationRelativeTo(null);
        pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));

        pantalla_Usuarios.jButtonRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Usuarios.jTextFieldUsuario.getText().isEmpty() || pantalla_Usuarios.jTextFieldPassword.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No deje campos vacios");
                } else {
                    String usuario = pantalla_Usuarios.jTextFieldUsuario.getText();
                    String passwork = pantalla_Usuarios.jTextFieldPassword.getText();
                    Empleado empleado = (Empleado) pantalla_Usuarios.jComboBoxEmpleado.getSelectedItem();

                    usuarios = new Usuarios(0, usuario, passwork, empleado.getIdEmpleado());

                    if (usuarios.registrarUsuario()) {
                        JOptionPane.showMessageDialog(null, "Datos ingresados Correctamente");
                        limpiarCampos();
                        Clear_Table();
                        pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));

                    } else {
                        JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }

        });

        pantalla_Usuarios.jButtonEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Usuarios.jTableUsuarios.getSelectedRow() >= 0) {
                    int m = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL USUARIO ", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (m == 0) {

                        int filaSeleccionada = pantalla_Usuarios.jTableUsuarios.getSelectedRow();
                        int idusuario = Integer.parseInt(pantalla_Usuarios.jTableUsuarios.getValueAt(filaSeleccionada, 0).toString());

                        usuarios = new Usuarios(idusuario);

                        if (usuarios.eliminarUsuario()) {
                            JOptionPane.showMessageDialog(null, "Usuario Eliminado");
                            Clear_Table();
                            limpiarCampos();
                            pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));
                        } else {
                            JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un Usuario", "Usuarios", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        pantalla_Usuarios.jButtonEditarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int m = JOptionPane.showConfirmDialog(null, "DESEA MODIFICAR EL USUARIO ", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (m == 0) {
                    int filaSeleccionada = pantalla_Usuarios.jTableUsuarios.getSelectedRow();
                    int idusuario = Integer.parseInt(pantalla_Usuarios.jTableUsuarios.getValueAt(filaSeleccionada, 0).toString());
                    String usuario = pantalla_Usuarios.jTextFieldUsuario.getText();
                    String passwork = pantalla_Usuarios.jTextFieldPassword.getText();
                    Empleado empleado = (Empleado) pantalla_Usuarios.jComboBoxEmpleado.getSelectedItem();

                    usuarios = new Usuarios(idusuario, usuario, passwork, empleado.getIdEmpleado());

                    if (usuarios.ModificarRegristros()) {
                        JOptionPane.showMessageDialog(null, "Usuario Modificado");
                        Clear_Table();
                        limpiarCampos();
                        pantalla_Usuarios.jTableUsuarios.setModel(new Usuarios().cargarRegistroEgreso(pantalla_Usuarios.jTableUsuarios));

                    } else {
                        JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

    }

    private void limpiarCampos() {
        pantalla_Usuarios.jTextFieldPassword.setText("");
        pantalla_Usuarios.jTextFieldUsuario.setText("");
    }

    private void Clear_Table() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Usuarios.jTableUsuarios.getModel();
        int filas = pantalla_Usuarios.jTableUsuarios.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
    }
}
