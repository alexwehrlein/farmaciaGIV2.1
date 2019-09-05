/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Marca;
import Modelo.Medicamento;
import Modelo.Perfumes;
import Modelo.Proveedor;
import Modelo.Sucursal;
import Vista.Dialog_NuevoMedicamento;
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
public class Controlador_NuevoPerfume {

    private Dialog_NuevoPerfume dialog_NuevoPerfume;
    private DefaultTableModel modeloSucursal;
    private ArrayList<String> arrayProveedor = new ArrayList<>();
    private ArrayList<String> arrayMarca = new ArrayList<>();
    private String idProveedor, idMarca;

    public Controlador_NuevoPerfume(JFrame parent, Controlador_productosPorSucursal controlador_productosPorSucursal) {
        dialog_NuevoPerfume = new Dialog_NuevoPerfume(parent, true);
        dialog_NuevoPerfume.setLocationRelativeTo(null);
        dialog_NuevoPerfume.setResizable(true);
        modeloSucursal = (DefaultTableModel) dialog_NuevoPerfume.jTableSucursalP.getModel();
        cargarTablaSucursal();
        cargarProveedor();
        cargarMarcas();

        dialog_NuevoPerfume.jComboBoxProveedor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrayProveedor.size(); i++) {
                    if (arrayProveedor.get(i).equals(dialog_NuevoPerfume.jComboBoxProveedor.getSelectedItem().toString())) {
                        idProveedor = arrayProveedor.get(i - 1);
                    }
                }

            }
        });

        dialog_NuevoPerfume.jComboBoxMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_NuevoPerfume.jComboBoxMarca.getItemCount() > 0) {
                    for (int i = 0; i < arrayMarca.size(); i++) {
                        if (arrayMarca.get(i).equals(dialog_NuevoPerfume.jComboBoxMarca.getSelectedItem().toString())) {
                            idMarca = arrayMarca.get(i - 1);
                        }
                    }

                }
            }
        });

        dialog_NuevoPerfume.jButtonAgregarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dialog_NuevoPerfume.jDialogMarca.setTitle("Registrar Marca");
                dialog_NuevoPerfume.jDialogMarca.setBounds(495, 224, 380, 200);
                dialog_NuevoPerfume.jDialogMarca.setResizable(false);
                dialog_NuevoPerfume.jDialogMarca.setVisible(true);

            }
        });
        dialog_NuevoPerfume.jButtonGuardarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_NuevoPerfume.jTextFieldNombreMarca.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog_NuevoPerfume, "CAMPO VACIO");
                    return;
                }
                if (new Marca().registrarMarca(dialog_NuevoPerfume.jTextFieldNombreMarca.getText())) {
                    JOptionPane.showMessageDialog(dialog_NuevoPerfume, "REGISTRO EXITOSO");
                    dialog_NuevoPerfume.jTextFieldNombreMarca.setText("");
                    cargarMarcas();
                    dialog_NuevoPerfume.jComboBoxMarca.setSelectedItem(dialog_NuevoPerfume.jTextFieldNombreMarca.getText().toString());
                    dialog_NuevoPerfume.jDialogMarca.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(dialog_NuevoPerfume, "ERROR AL REGISTRAR");
                    dialog_NuevoPerfume.jTextFieldNombreMarca.setText("");
                    dialog_NuevoPerfume.jDialogMarca.setVisible(false);
                }

            }
        });
        dialog_NuevoPerfume.jButtonCancelarMarca.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_NuevoPerfume.jTextFieldNombreMarca.setText("");
                dialog_NuevoPerfume.jDialogMarca.setVisible(false);

            }
        });
        dialog_NuevoPerfume.jButtonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_NuevoPerfume, "CAMPOS VACIOS");
                    return;
                }
                String codigo = dialog_NuevoPerfume.jTextFieldCodigoPerfume.getText();
                String nombre = dialog_NuevoPerfume.jTextFieldNombre.getText();
                String laboratorio = dialog_NuevoPerfume.jTextFieldLaboratorio.getText();
                String precio = dialog_NuevoPerfume.jTextFieldPrecio.getText();
                if (new Perfumes(codigo, nombre, idMarca, laboratorio, precio, "0").registrarPerfume(modeloSucursal, idProveedor)) {
                    JOptionPane.showMessageDialog(dialog_NuevoPerfume, "REGISTRO EXITOSO");
                    controlador_productosPorSucursal.cargarProductos();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(dialog_NuevoPerfume, "ERROR AL REGISTRAR");
                }
            }
        });
        
          dialog_NuevoPerfume.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                dialog_NuevoPerfume.jTextFieldCodigoPerfume.setBackground(Color.white);
                dialog_NuevoPerfume.jTextFieldNombre.setBackground(Color.white);
                dialog_NuevoPerfume.jTextFieldLaboratorio.setBackground(Color.white);
                dialog_NuevoPerfume.jTextFieldPrecio.setBackground(Color.white);
            }
        });

        dialog_NuevoPerfume.jTableSucursalP.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = dialog_NuevoPerfume.jTableSucursalP.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / dialog_NuevoPerfume.jTableSucursalP.getRowHeight();
                if (row < dialog_NuevoPerfume.jTableSucursalP.getRowCount() && row >= 0 && colum < dialog_NuevoPerfume.jTableSucursalP.getColumnCount() && colum >= 0) {
                    Object v = dialog_NuevoPerfume.jTableSucursalP.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        modeloSucursal.removeRow(dialog_NuevoPerfume.jTableSucursalP.getSelectedRow());
                    }
                }

            }
        });

        //----------------campo de texto solo numeros--------------------------
        dialog_NuevoPerfume.jTextFieldCodigoPerfume.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_NuevoPerfume.jTextFieldCodigoPerfume.getText().matches("^\\d+") && dialog_NuevoPerfume.jTextFieldCodigoPerfume.getText().length() > 0) {
                    dialog_NuevoPerfume.jTextFieldCodigoPerfume.setText(dialog_NuevoPerfume.jTextFieldCodigoPerfume.getText().substring(0, dialog_NuevoPerfume.jTextFieldCodigoPerfume.getText().length() - 1));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

        dialog_NuevoPerfume.jTextFieldPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_NuevoPerfume.jTextFieldPrecio.getText().matches("^\\d+\\.?\\d?\\d?") && dialog_NuevoPerfume.jTextFieldPrecio.getText().length() > 0) {
                    dialog_NuevoPerfume.jTextFieldPrecio.setText(dialog_NuevoPerfume.jTextFieldPrecio.getText().substring(0, dialog_NuevoPerfume.jTextFieldPrecio.getText().length() - 1));
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
        dialog_NuevoPerfume.jTextFieldCodigoPerfume.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoPerfume.jTextFieldCodigoPerfume.setBackground(Color.white);

            }
        });
        dialog_NuevoPerfume.jTextFieldNombre.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoPerfume.jTextFieldNombre.setBackground(Color.white);

            }
        });

        dialog_NuevoPerfume.jTextFieldLaboratorio.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoPerfume.jTextFieldLaboratorio.setBackground(Color.white);

            }
        });
        dialog_NuevoPerfume.jTextFieldPrecio.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_NuevoPerfume.jTextFieldPrecio.setBackground(Color.white);

            }
        });
        //-----------------
        
         dialog_NuevoPerfume.jLabelActualizarSucursal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarTablaSucursal();
            }
        });

        dialog_NuevoPerfume.setVisible(true);

    }

    public void cargarProveedor() {
        arrayProveedor = new Proveedor().obtenerNombreProveedor();
        idProveedor = arrayProveedor.get(0);
        for (int i = 1; i < arrayProveedor.size(); i += 2) {
            dialog_NuevoPerfume.jComboBoxProveedor.addItem(arrayProveedor.get(i));
        }

    }

    public void cargarMarcas() {
        dialog_NuevoPerfume.jComboBoxMarca.removeAllItems();
        arrayMarca = new Marca().obtenerNombreMarca();
        if (!arrayMarca.isEmpty()) {
            idMarca = arrayMarca.get(0);
            for (int i = 1; i < arrayMarca.size(); i += 2) {
                dialog_NuevoPerfume.jComboBoxMarca.addItem(arrayMarca.get(i));
            }
        }
    }

    public void cargarTablaSucursal() {
        limpiar_jtables(dialog_NuevoPerfume.jTableSucursalP);
        dialog_NuevoPerfume.jTableSucursalP.setModel(new Sucursal().cargarSucursales(dialog_NuevoPerfume.jTableSucursalP));
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }
    
        public void limpiarCampos() {
        dialog_NuevoPerfume.jTextFieldCodigoPerfume.setText("");
        dialog_NuevoPerfume.jTextFieldNombre.setText("");
        dialog_NuevoPerfume.jComboBoxMarca.setSelectedIndex(0);
        dialog_NuevoPerfume.jComboBoxProveedor.setSelectedIndex(0);
        dialog_NuevoPerfume.jTextFieldLaboratorio.setText("");
        dialog_NuevoPerfume.jTextFieldPrecio.setText("");
        cargarProveedor();
        cargarTablaSucursal();
    }

    public boolean camposVacios() {
        boolean b = false;
        if (dialog_NuevoPerfume.jTextFieldCodigoPerfume.getText().toString().isEmpty()) {
            dialog_NuevoPerfume.jTextFieldCodigoPerfume.setBackground(Color.RED);
            b = true;
        }
        if (dialog_NuevoPerfume.jTextFieldNombre.getText().toString().isEmpty()) {
            dialog_NuevoPerfume.jTextFieldNombre.setBackground(Color.RED);
            b = true;
        }
        if (dialog_NuevoPerfume.jTextFieldLaboratorio.getText().toString().isEmpty()) {
            dialog_NuevoPerfume.jTextFieldLaboratorio.setBackground(Color.RED);
            b = true;
        }

        if (dialog_NuevoPerfume.jTextFieldPrecio.getText().toString().isEmpty()) {
            dialog_NuevoPerfume.jTextFieldPrecio.setBackground(Color.RED);
            b = true;
        }
        return b;
    }

}
