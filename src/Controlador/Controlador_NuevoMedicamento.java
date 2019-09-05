/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alta;
import Vista.Dialog_NuevoMedicamento;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Modelo.Medicamento;
import Modelo.Perfumes;
import Modelo.Proveedor;
import Modelo.Sucursal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_NuevoMedicamento {

    private Dialog_NuevoMedicamento dialog_NuevoMedicamento;
    private DefaultTableModel modeloSucursal;
    private ArrayList<String> arrayProveedor = new ArrayList<>();
    private String idProveedor;

    public Controlador_NuevoMedicamento(JFrame parent, Controlador_productosPorSucursal controlador_productosPorSucursa) {
        dialog_NuevoMedicamento = new Dialog_NuevoMedicamento(parent, true);
        dialog_NuevoMedicamento.setLocationRelativeTo(null);
        dialog_NuevoMedicamento.setResizable(true);
        modeloSucursal = (DefaultTableModel) dialog_NuevoMedicamento.jTableSucursalM.getModel();
        cargarTablaSucursal();
        cargarProveedor();

        dialog_NuevoMedicamento.jComboBoxProveedor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrayProveedor.size(); i++) {
                    if (arrayProveedor.get(i).equals(dialog_NuevoMedicamento.jComboBoxProveedor.getSelectedItem().toString())) {
                        idProveedor = arrayProveedor.get(i - 1);
                    }
                }

            }
        });

        dialog_NuevoMedicamento.jButtonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_NuevoMedicamento, "CAMPOS VACIOS");
                    return;
                }

                String codigo = dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.getText();
                String marca = dialog_NuevoMedicamento.jTextFieldMarcaComercial.getText();
                String sustancia = dialog_NuevoMedicamento.jTextFieldSustancia.getText();
                String laboratorio = dialog_NuevoMedicamento.jComboBoxLaboratorio.getSelectedItem().toString();
                String tipo;
                if (dialog_NuevoMedicamento.jRadioButtonPatente.isSelected()) {
                    tipo = "PATENTE";
                } else {
                    tipo = "GENÉRICO";
                }
                String precio = dialog_NuevoMedicamento.jTextFieldPrecio.getText();
//                JOptionPane.showMessageDialog(dialog_NuevoMedicamento, codigo+" "+ marca+" "+ sustancia+" "+ laboratorio+" "+ tipo+" "+ precio+" "+ cantidad);

                if (new Medicamento(codigo, marca, sustancia, laboratorio, tipo, precio, "0").registrarMedicamento(modeloSucursal, idProveedor)) {
                    JOptionPane.showMessageDialog(dialog_NuevoMedicamento, "REGISTRO EXITOSO");
                    controlador_productosPorSucursa.cargarProductos();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(dialog_NuevoMedicamento, "ERROR AL REGISTRAR");
                }
            }
        });

        dialog_NuevoMedicamento.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.setBackground(Color.white);
                dialog_NuevoMedicamento.jTextFieldMarcaComercial.setBackground(Color.white);
                dialog_NuevoMedicamento.jTextFieldSustancia.setBackground(Color.white);
                dialog_NuevoMedicamento.jTextFieldPrecio.setBackground(Color.white);
            }
        });

        dialog_NuevoMedicamento.jTableSucursalM.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = dialog_NuevoMedicamento.jTableSucursalM.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / dialog_NuevoMedicamento.jTableSucursalM.getRowHeight();
                if (row < dialog_NuevoMedicamento.jTableSucursalM.getRowCount() && row >= 0 && colum < dialog_NuevoMedicamento.jTableSucursalM.getColumnCount() && colum >= 0) {
                    Object v = dialog_NuevoMedicamento.jTableSucursalM.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        modeloSucursal.removeRow(dialog_NuevoMedicamento.jTableSucursalM.getSelectedRow());
                    }
                }

            }
        });

        //----------------campo de texto solo numeros--------------------------
        dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.getText().matches("^\\d+") && dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.getText().length() > 0) {
                    dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.setText(dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.getText().substring(0, dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.getText().length() - 1));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

        dialog_NuevoMedicamento.jTextFieldPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_NuevoMedicamento.jTextFieldPrecio.getText().matches("^\\d+\\.?\\d?\\d?") && dialog_NuevoMedicamento.jTextFieldPrecio.getText().length() > 0) {
                    dialog_NuevoMedicamento.jTextFieldPrecio.setText(dialog_NuevoMedicamento.jTextFieldPrecio.getText().substring(0, dialog_NuevoMedicamento.jTextFieldPrecio.getText().length() - 1));
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
        dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.setBackground(Color.white);

            }
        });

        dialog_NuevoMedicamento.jTextFieldMarcaComercial.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoMedicamento.jTextFieldMarcaComercial.setBackground(Color.white);

            }
        });

        dialog_NuevoMedicamento.jTextFieldSustancia.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoMedicamento.jTextFieldSustancia.setBackground(Color.white);

            }
        });

        dialog_NuevoMedicamento.jTextFieldPrecio.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoMedicamento.jTextFieldPrecio.setBackground(Color.white);

            }
        });

        //-------------------------------
        dialog_NuevoMedicamento.jLabelActualizarSucursal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarTablaSucursal();
            }
        });

        dialog_NuevoMedicamento.setVisible(true);
    }

    public void cargarProveedor() {
        arrayProveedor = new Proveedor().obtenerNombreProveedor();
        idProveedor = arrayProveedor.get(0);
        for (int i = 1; i < arrayProveedor.size(); i += 2) {
            dialog_NuevoMedicamento.jComboBoxProveedor.addItem(arrayProveedor.get(i));
        }

    }

    public void cargarTablaSucursal() {
        limpiar_jtables(dialog_NuevoMedicamento.jTableSucursalM);
        dialog_NuevoMedicamento.jTableSucursalM.setModel(new Sucursal().cargarSucursales(dialog_NuevoMedicamento.jTableSucursalM));
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

    public void limpiarCampos() {
        dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.setText("");
        dialog_NuevoMedicamento.jTextFieldMarcaComercial.setText("");
        dialog_NuevoMedicamento.jTextFieldSustancia.setText("");
        dialog_NuevoMedicamento.jComboBoxLaboratorio.setSelectedIndex(0);
        dialog_NuevoMedicamento.jComboBoxProveedor.setSelectedIndex(0);
        dialog_NuevoMedicamento.jRadioButtonPatente.setSelected(true);
        dialog_NuevoMedicamento.jTextFieldPrecio.setText("");
        cargarProveedor();
        cargarTablaSucursal();
    }

    public boolean camposVacios() {
        boolean b = false;
        if (dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.getText().toString().isEmpty()) {
            dialog_NuevoMedicamento.jTextFieldCodigoMedicamento.setBackground(Color.RED);
            b = true;
        }
        if (dialog_NuevoMedicamento.jTextFieldMarcaComercial.getText().toString().isEmpty()) {
            dialog_NuevoMedicamento.jTextFieldMarcaComercial.setBackground(Color.RED);
            b = true;
        }
        if (dialog_NuevoMedicamento.jTextFieldSustancia.getText().toString().isEmpty()) {
            dialog_NuevoMedicamento.jTextFieldSustancia.setBackground(Color.RED);
            b = true;
        }

        if (dialog_NuevoMedicamento.jTextFieldPrecio.getText().toString().isEmpty()) {
            dialog_NuevoMedicamento.jTextFieldPrecio.setBackground(Color.RED);
            b = true;
        }
        return b;
    }

}
