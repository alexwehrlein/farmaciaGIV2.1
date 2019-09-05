/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alta;
import Modelo.Sucursal;
import Vista.Dialog_AltaProductos;
import Vista.Dialog_SolicitarClavePaso;
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
public class Controlador_DialogSolicitarClave {

    Dialog_SolicitarClavePaso dialog_SolicitarClavePaso;

    public Controlador_DialogSolicitarClave(JFrame parent, String fecha, String hora, String sucursalQueSolicita, String idSucursalLocal) {
        dialog_SolicitarClavePaso = new Dialog_SolicitarClavePaso(parent, true);
        dialog_SolicitarClavePaso.setLocationRelativeTo(null);
        dialog_SolicitarClavePaso.setResizable(true);
        dialog_SolicitarClavePaso.jLabelFecha.setText(fecha);
        dialog_SolicitarClavePaso.jLabelHora.setText(hora);
        dialog_SolicitarClavePaso.jButtonAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (new Alta().validarExistenciaDeLaClave(dialog_SolicitarClavePaso.jTextFieldClave.getText(), fecha,hora, sucursalQueSolicita)) {
                    dialog_SolicitarClavePaso.setVisible(false);
                    new Controlador_ProductosSolicitados(parent, dialog_SolicitarClavePaso.jTextFieldClave.getText(), idSucursalLocal, new Sucursal().idSucursal(sucursalQueSolicita));
                    
                } else {
                    JOptionPane.showMessageDialog(dialog_SolicitarClavePaso, "CLAVE INCORRECTA");
                }
            }
        });

        dialog_SolicitarClavePaso.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           dialog_SolicitarClavePaso.jTextFieldClave.setText("");
           dialog_SolicitarClavePaso.setVisible(false);
            }
        });

        dialog_SolicitarClavePaso.jTextFieldClave.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_SolicitarClavePaso.jTextFieldClave.getText().matches("^\\d+") && dialog_SolicitarClavePaso.jTextFieldClave.getText().length() > 0) {
                    dialog_SolicitarClavePaso.jTextFieldClave.setText(dialog_SolicitarClavePaso.jTextFieldClave.getText().substring(0, dialog_SolicitarClavePaso.jTextFieldClave.getText().length() - 1));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

        dialog_SolicitarClavePaso.setVisible(true);
    }

}
