/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alta;
import Vista.Dialog_AltaProductos;
import Vista.Pantalla_Producto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_DialogAltaProductos {

    private Dialog_AltaProductos dialog_AltaProductos;

    public Controlador_DialogAltaProductos(JFrame parent, Controlador_Producto controlador_Producto, String codigo, String tipo, String idSucursal) {
        dialog_AltaProductos = new Dialog_AltaProductos(parent, true);
        dialog_AltaProductos.setLocationRelativeTo(null);
        dialog_AltaProductos.setResizable(true);
        dialog_AltaProductos.jLabelCodigoProductoAlta.setText(codigo);

        dialog_AltaProductos.jTextFieldCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_AltaProductos.jTextFieldCantidad.getText().matches("^\\d+") && dialog_AltaProductos.jTextFieldCantidad.getText().length() > 0) {
                    dialog_AltaProductos.jTextFieldCantidad.setText(dialog_AltaProductos.jTextFieldCantidad.getText().substring(0, dialog_AltaProductos.jTextFieldCantidad.getText().length() - 1));
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (dialog_AltaProductos.jTextFieldCantidad.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(dialog_AltaProductos, "CAMPO VACIO");
                        return;
                    }
//                JOptionPane.showMessageDialog(dialog_AltaProductos, codigo +" - "+ tipo+" - "+ idSucursal+" - "+dialog_AltaProductos.jTextFieldCantidad.getText());
                    if (new Alta().altaProductos(codigo, tipo, idSucursal, dialog_AltaProductos.jTextFieldCantidad.getText().toString())) {
                        JOptionPane.showMessageDialog(dialog_AltaProductos, "EL PRODUCTO SE DIO DE ALTA");
                        controlador_Producto.cargarTablaMedicamentos();
                        controlador_Producto.cargarTablaAbarrotes();
                        controlador_Producto.cargarTablaPerfumes();
                        dialog_AltaProductos.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(dialog_AltaProductos, "ERROR AL DAR DE ALTA");
                    }

                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

        dialog_AltaProductos.jButtonGuardarAlta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_AltaProductos.jTextFieldCantidad.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog_AltaProductos, "CAMPO VACIO");
                    return;
                }
//                JOptionPane.showMessageDialog(dialog_AltaProductos, codigo +" - "+ tipo+" - "+ idSucursal+" - "+dialog_AltaProductos.jTextFieldCantidad.getText());
                if (new Alta().altaProductos(codigo, tipo, idSucursal, dialog_AltaProductos.jTextFieldCantidad.getText().toString())) {
                    JOptionPane.showMessageDialog(dialog_AltaProductos, "EL PRODUCTO SE DIO DE ALTA");
                    controlador_Producto.cargarTablaMedicamentos();
                    controlador_Producto.cargarTablaAbarrotes();
                    controlador_Producto.cargarTablaPerfumes();
                    dialog_AltaProductos.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(dialog_AltaProductos, "ERROR AL DAR DE ALTA");
                }
            }
        });

        dialog_AltaProductos.setVisible(true);

    }
}
