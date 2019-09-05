/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Dialog_AltaProductos;
import Vista.Dialog_Sucursal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Modelo.Sucursal;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_DialogSucursal {

    private Dialog_Sucursal dialog_Sucursal;
    private ArrayList<String> arraySucursal;
    private String idSucursal;

    public Controlador_DialogSucursal(JFrame parent) {
        dialog_Sucursal = new Dialog_Sucursal(parent, true);
        dialog_Sucursal.setLocationRelativeTo(null);
        dialog_Sucursal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog_Sucursal.setResizable(true);
        dialog_Sucursal.setSize(410, 140);
        cargarSucursal();

        dialog_Sucursal.jComboBoxSucursales.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_Sucursal.jComboBoxSucursales.getItemCount() > 0) {
                    for (int i = 0; i < arraySucursal.size(); i++) {
                        if (arraySucursal.get(i).equals(dialog_Sucursal.jComboBoxSucursales.getSelectedItem().toString())) {
                            idSucursal = arraySucursal.get(i - 1);
                        }
                    }
                }

            }
        });

        dialog_Sucursal.jButtonAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (new Sucursal().registrarMaquina(idSucursal, System.getProperty("user.name"))) {
                    JOptionPane.showMessageDialog(dialog_Sucursal, "REGISTRO EXITOSO");
                    dialog_Sucursal.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(dialog_Sucursal, "ERROR AL REGISTRAR");
                }
            }
        });

        dialog_Sucursal.jButtonAddSucursal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_Sucursal.setSize(410, 420);
                dialog_Sucursal.jButtonAceptar.setEnabled(false);
                dialog_Sucursal.jComboBoxSucursales.setEnabled(false);
            }
        });

        dialog_Sucursal.jButtonRegistrarS.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_Sucursal, "CAMPOS VACIOS");
                    return;
                }
                if (new Sucursal().registrarSucursal(dialog_Sucursal.jTextFieldNombre.getText(), dialog_Sucursal.jTextAreaDireccion.getText())) {
                    JOptionPane.showMessageDialog(dialog_Sucursal, "SUCURSAL REGISTRADA");
                    cargarSucursal();
                    dialog_Sucursal.setSize(410, 140);
                    dialog_Sucursal.jButtonAceptar.setEnabled(true);
                    dialog_Sucursal.jComboBoxSucursales.setEnabled(true);
                    dialog_Sucursal.jTextFieldNombre.setText("");
                    dialog_Sucursal.jTextAreaDireccion.setText("");
                } else {
                    JOptionPane.showMessageDialog(dialog_Sucursal, "ERROR AL REGISTRAR");
                }
            }
        });

        dialog_Sucursal.jButtonCancelarS.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_Sucursal.setSize(410, 140);
                dialog_Sucursal.jButtonAceptar.setEnabled(true);
                dialog_Sucursal.jComboBoxSucursales.setEnabled(true);
                dialog_Sucursal.jTextFieldNombre.setText("");
                dialog_Sucursal.jTextAreaDireccion.setText("");
            }
        });

        //CLIC EN LOS JTEXTFIELD
        dialog_Sucursal.jTextFieldNombre.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_Sucursal.jTextFieldNombre.setBackground(Color.white);

            }
        });

        dialog_Sucursal.jTextAreaDireccion.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_Sucursal.jTextAreaDireccion.setBackground(Color.white);

            }
        });

        dialog_Sucursal.setVisible(true);
    }

    public void cargarSucursal() {
        dialog_Sucursal.jComboBoxSucursales.removeAllItems();
        arraySucursal = new Sucursal().obtenerSucursales();
        
        if (!arraySucursal.isEmpty()) {
            idSucursal = arraySucursal.get(0);
            for (int i = 1; i < arraySucursal.size(); i += 2) {
                dialog_Sucursal.jComboBoxSucursales.addItem(arraySucursal.get(i));
            }
        }
    }

    public boolean camposVacios() {
        boolean b = false;
        if (dialog_Sucursal.jTextFieldNombre.getText().toString().isEmpty()) {
            dialog_Sucursal.jTextFieldNombre.setBackground(Color.red);
            b = true;
        }
        if (dialog_Sucursal.jTextAreaDireccion.getText().toString().isEmpty()) {
            dialog_Sucursal.jTextAreaDireccion.setBackground(Color.red);
            b = true;
        }
        return b;
    }

}
