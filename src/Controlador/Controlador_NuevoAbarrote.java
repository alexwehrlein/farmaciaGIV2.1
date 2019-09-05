/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Abarrotes;
import Modelo.Marca;
import Modelo.Perfumes;
import Modelo.Proveedor;
import Modelo.Sucursal;
import Vista.Dialog_NuevoAbarrote;
import Vista.Dialog_NuevoPerfume;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_NuevoAbarrote {

    private Dialog_NuevoAbarrote dialog_NuevoAbarrote;
    private DefaultTableModel modeloSucursal;
    private ArrayList<String> arrayProveedor = new ArrayList<>();
    private ArrayList<String> arrayMarca = new ArrayList<>();
    private String idProveedor, idMarca;

    public Controlador_NuevoAbarrote(JFrame parent, Controlador_productosPorSucursal controlador_productosPorSucursal) {
        dialog_NuevoAbarrote = new Dialog_NuevoAbarrote(parent, true);
        dialog_NuevoAbarrote.setLocationRelativeTo(null);
        dialog_NuevoAbarrote.setResizable(true);
        modeloSucursal = (DefaultTableModel) dialog_NuevoAbarrote.jTableSucursalP.getModel();
        cargarTablaSucursal();
        cargarProveedor();
        cargarMarcas();

        dialog_NuevoAbarrote.jComboBoxProveedor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrayProveedor.size(); i++) {
                    if (arrayProveedor.get(i).equals(dialog_NuevoAbarrote.jComboBoxProveedor.getSelectedItem().toString())) {
                        idProveedor = arrayProveedor.get(i - 1);
                    }
                }

            }
        });

        dialog_NuevoAbarrote.jComboBoxMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_NuevoAbarrote.jComboBoxMarca.getItemCount() > 0) {
                    for (int i = 0; i < arrayMarca.size(); i++) {
                        if (arrayMarca.get(i).equals(dialog_NuevoAbarrote.jComboBoxMarca.getSelectedItem().toString())) {
                            idMarca = arrayMarca.get(i - 1);
                        }
                    }

                }
            }
        });

        dialog_NuevoAbarrote.jButtonAgregarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dialog_NuevoAbarrote.jDialogMarca.setTitle("Registrar Marca");
                dialog_NuevoAbarrote.jDialogMarca.setBounds(495, 224, 380, 200);
                dialog_NuevoAbarrote.jDialogMarca.setResizable(false);
                dialog_NuevoAbarrote.jDialogMarca.setVisible(true);

            }
        });
        dialog_NuevoAbarrote.jButtonGuardarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_NuevoAbarrote.jTextFieldNombreMarca.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog_NuevoAbarrote, "CAMPO VACIO");
                    return;
                }
                if (new Marca().registrarMarca(dialog_NuevoAbarrote.jTextFieldNombreMarca.getText())) {
                    JOptionPane.showMessageDialog(dialog_NuevoAbarrote, "REGISTRO EXITOSO");
                    dialog_NuevoAbarrote.jTextFieldNombreMarca.setText("");
                    cargarMarcas();
                    dialog_NuevoAbarrote.jComboBoxMarca.setSelectedItem(dialog_NuevoAbarrote.jTextFieldNombreMarca.getText().toString());
                    dialog_NuevoAbarrote.jDialogMarca.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(dialog_NuevoAbarrote, "ERROR AL REGISTRAR");
                    dialog_NuevoAbarrote.jTextFieldNombreMarca.setText("");
                    dialog_NuevoAbarrote.jDialogMarca.setVisible(false);
                }

            }
        });
        dialog_NuevoAbarrote.jButtonCancelarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_NuevoAbarrote.jTextFieldNombreMarca.setText("");
                dialog_NuevoAbarrote.jDialogMarca.setVisible(false);

            }
        });

        dialog_NuevoAbarrote.jButtonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_NuevoAbarrote, "CAMPOS VACIOS");
                    return;
                }
                String codigo = dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.getText();
                String nombre = dialog_NuevoAbarrote.jTextFieldNombre.getText();
                String precio = dialog_NuevoAbarrote.jTextFieldPrecio.getText();
                if (new Abarrotes(codigo, nombre, idMarca, precio, "0").registrarAbarrote(modeloSucursal, idProveedor)) {
                    JOptionPane.showMessageDialog(dialog_NuevoAbarrote, "REGISTRO EXITOSO");
                    controlador_productosPorSucursal.cargarProductos();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(dialog_NuevoAbarrote, "ERROR AL REGISTRAR");
                }
            }
        });

        dialog_NuevoAbarrote.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.setBackground(Color.white);
                dialog_NuevoAbarrote.jTextFieldNombre.setBackground(Color.white);
                dialog_NuevoAbarrote.jTextFieldPrecio.setBackground(Color.white);
            }
        });

        dialog_NuevoAbarrote.jTableSucursalP.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = dialog_NuevoAbarrote.jTableSucursalP.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / dialog_NuevoAbarrote.jTableSucursalP.getRowHeight();
                if (row < dialog_NuevoAbarrote.jTableSucursalP.getRowCount() && row >= 0 && colum < dialog_NuevoAbarrote.jTableSucursalP.getColumnCount() && colum >= 0) {
                    Object v = dialog_NuevoAbarrote.jTableSucursalP.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        modeloSucursal.removeRow(dialog_NuevoAbarrote.jTableSucursalP.getSelectedRow());
                    }
                }

            }
        });

        //----------------campo de texto solo numeros--------------------------
        dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.getText().matches("^\\d+") && dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.getText().length() > 0) {
                    dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.setText(dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.getText().substring(0, dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.getText().length() - 1));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

        dialog_NuevoAbarrote.jTextFieldPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_NuevoAbarrote.jTextFieldPrecio.getText().matches("^\\d+\\.?\\d?\\d?") && dialog_NuevoAbarrote.jTextFieldPrecio.getText().length() > 0) {
                    dialog_NuevoAbarrote.jTextFieldPrecio.setText(dialog_NuevoAbarrote.jTextFieldPrecio.getText().substring(0, dialog_NuevoAbarrote.jTextFieldPrecio.getText().length() - 1));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

//           CLIC EN LOS JTEXTFIELD
        dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.setBackground(Color.white);

            }
        });

        dialog_NuevoAbarrote.jTextFieldNombre.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoAbarrote.jTextFieldNombre.setBackground(Color.white);

            }
        });

        dialog_NuevoAbarrote.jTextFieldPrecio.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoAbarrote.jTextFieldPrecio.setBackground(Color.white);

            }
        });

        //-------------
        dialog_NuevoAbarrote.jLabelActualizarSucursal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarTablaSucursal();
            }
        });

        dialog_NuevoAbarrote.setVisible(true);

    }

    public void cargarProveedor() {
        arrayProveedor = new Proveedor().obtenerNombreProveedor();
        idProveedor = arrayProveedor.get(0);
        for (int i = 1; i < arrayProveedor.size(); i += 2) {
            dialog_NuevoAbarrote.jComboBoxProveedor.addItem(arrayProveedor.get(i));
        }

    }

    public void cargarMarcas() {
        dialog_NuevoAbarrote.jComboBoxMarca.removeAllItems();
        arrayMarca = new Marca().obtenerNombreMarca();
        if (!arrayMarca.isEmpty()) {
            idMarca = arrayMarca.get(0);
            for (int i = 1; i < arrayMarca.size(); i += 2) {
                dialog_NuevoAbarrote.jComboBoxMarca.addItem(arrayMarca.get(i));
            }
        }
    }

    public void cargarTablaSucursal() {
        limpiar_jtables(dialog_NuevoAbarrote.jTableSucursalP);
        dialog_NuevoAbarrote.jTableSucursalP.setModel(new Sucursal().cargarSucursales(dialog_NuevoAbarrote.jTableSucursalP));
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

    public void limpiarCampos() {
        dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.setText("");
        dialog_NuevoAbarrote.jTextFieldNombre.setText("");
        dialog_NuevoAbarrote.jComboBoxMarca.setSelectedIndex(0);
        dialog_NuevoAbarrote.jComboBoxProveedor.setSelectedIndex(0);
        dialog_NuevoAbarrote.jTextFieldPrecio.setText("");
        cargarProveedor();
        cargarTablaSucursal();
    }

    public boolean camposVacios() {
        boolean b = false;
        if (dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.getText().toString().isEmpty()) {
            dialog_NuevoAbarrote.jTextFieldCodigoAbarrote.setBackground(Color.RED);
            b = true;
        }
        if (dialog_NuevoAbarrote.jTextFieldNombre.getText().toString().isEmpty()) {
            dialog_NuevoAbarrote.jTextFieldNombre.setBackground(Color.RED);
            b = true;
        }

        if (dialog_NuevoAbarrote.jTextFieldPrecio.getText().toString().isEmpty()) {
            dialog_NuevoAbarrote.jTextFieldPrecio.setBackground(Color.RED);
            b = true;
        }
        return b;
    }
}
