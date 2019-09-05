/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Medicamento;
import Modelo.Proveedor;
import Vista.Dialog_ModificarMedicamento;
import Vista.Dialog_NuevoMedicamento;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_ModificarMedicamento {

    private Dialog_ModificarMedicamento dialog_ModificarMedicamento;
    private String codigo;

    public Controlador_ModificarMedicamento(JFrame parent, String codigo, Controlador_productosPorSucursal controlador_productosPorSucursal) {
        this.codigo = codigo;
        dialog_ModificarMedicamento = new Dialog_ModificarMedicamento(parent, true);
        dialog_ModificarMedicamento.setLocationRelativeTo(null);
        dialog_ModificarMedicamento.setResizable(true);
        dialog_ModificarMedicamento.jLabelCodigo.setText(codigo);
        llenarDatosMedicamento();

        dialog_ModificarMedicamento.jButtonGuardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_ModificarMedicamento, "CAMPOS VACIOS");
                    return;
                }
                String marca = dialog_ModificarMedicamento.jTextFieldMarcaComercialEditar.getText();
                String sustancia = dialog_ModificarMedicamento.jTextFieldSustanciaEditar.getText();
                String laboratorio = dialog_ModificarMedicamento.jComboBoxLaboratorioEditar.getSelectedItem().toString();
                String precio = dialog_ModificarMedicamento.jTextFieldPrecioEditar.getText();
                String tipo;
                if (dialog_ModificarMedicamento.jRadioButtonPatenteEditar.isSelected()) {
                    tipo = "PATENTE";
                } else {
                    tipo = "GENÉRICO";
                }
                if (new Medicamento(codigo, marca, sustancia, laboratorio, tipo, precio, "0").modificarMedicamento()) {
                    JOptionPane.showMessageDialog(dialog_ModificarMedicamento, "EL MEDICAMENTO FUE MODIFICADO");
                    controlador_productosPorSucursal.cargarProductos();
                    dialog_ModificarMedicamento.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(dialog_ModificarMedicamento, "ERROR AL MODIFICAR");
                }
            }
        });

        dialog_ModificarMedicamento.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_ModificarMedicamento.setVisible(false);
            }
        });

        //----------------campo de texto solo numeros--------------------------
        dialog_ModificarMedicamento.jTextFieldPrecioEditar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_ModificarMedicamento.jTextFieldPrecioEditar.getText().matches("^\\d+\\.?\\d?\\d?") && dialog_ModificarMedicamento.jTextFieldPrecioEditar.getText().length() > 0) {
                    dialog_ModificarMedicamento.jTextFieldPrecioEditar.setText(dialog_ModificarMedicamento.jTextFieldPrecioEditar.getText().substring(0, dialog_ModificarMedicamento.jTextFieldPrecioEditar.getText().length() - 1));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

//          CLIC EN LOS JTEXTFIELD
        dialog_ModificarMedicamento.jTextFieldMarcaComercialEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarMedicamento.jTextFieldMarcaComercialEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarMedicamento.jTextFieldSustanciaEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarMedicamento.jTextFieldSustanciaEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarMedicamento.jTextFieldPrecioEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarMedicamento.jTextFieldPrecioEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarMedicamento.setVisible(true);
    }

    public void llenarDatosMedicamento() {
        Medicamento medicamento = new Medicamento();
        medicamento.obtenerMedicamento(codigo);
        dialog_ModificarMedicamento.jTextFieldMarcaComercialEditar.setText(medicamento.getMarcaComercial());
        dialog_ModificarMedicamento.jTextFieldSustanciaEditar.setText(medicamento.getSustancia());
        dialog_ModificarMedicamento.jComboBoxLaboratorioEditar.setSelectedItem(medicamento.getLaboratorio());
        dialog_ModificarMedicamento.jTextFieldPrecioEditar.setText(medicamento.getPrecio());
        if (medicamento.getTipo().equals("PATENTE")) {
            dialog_ModificarMedicamento.jRadioButtonPatenteEditar.setSelected(true);
        } else {
            dialog_ModificarMedicamento.jRadioButtonGenericoEditar.setSelected(true);
        }
    }

    public boolean camposVacios() {
        boolean b = false;
        if (dialog_ModificarMedicamento.jTextFieldMarcaComercialEditar.getText().toString().isEmpty()) {
            dialog_ModificarMedicamento.jTextFieldMarcaComercialEditar.setBackground(Color.RED);
            b = true;
        }
        if (dialog_ModificarMedicamento.jTextFieldSustanciaEditar.getText().toString().isEmpty()) {
            dialog_ModificarMedicamento.jTextFieldSustanciaEditar.setBackground(Color.RED);
            b = true;
        }

        if (dialog_ModificarMedicamento.jTextFieldPrecioEditar.getText().toString().isEmpty()) {
            dialog_ModificarMedicamento.jTextFieldPrecioEditar.setBackground(Color.RED);
            b = true;
        }
        return b;
    }

}
