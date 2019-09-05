/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Marca;
import Modelo.Medicamento;
import Modelo.Perfumes;
import Vista.Dialog_ModificarAbarrote;
import Vista.Dialog_ModificarPerfume;
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

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_ModificarPerfume {

    private Dialog_ModificarPerfume dialog_ModificarPerfume;
    private String codigo, idMarca;
    private ArrayList<String> arrayMarca = new ArrayList<>();

    public Controlador_ModificarPerfume(JFrame parent, String codigo, Controlador_productosPorSucursal controlador_productosPorSucursal) {
        this.codigo = codigo;
        dialog_ModificarPerfume = new Dialog_ModificarPerfume(parent, true);
        dialog_ModificarPerfume.setLocationRelativeTo(null);
        dialog_ModificarPerfume.setResizable(true);
        dialog_ModificarPerfume.jLabelCodigo.setText(codigo);
        cargarMarcas();
        llenarDatosPerfume();

        dialog_ModificarPerfume.jComboBoxMarcaEditar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrayMarca.size(); i++) {
                    if (arrayMarca.get(i).equals(dialog_ModificarPerfume.jComboBoxMarcaEditar.getSelectedItem().toString())) {
                        idMarca = arrayMarca.get(i - 1);
                    }
                }

            }
        });

        dialog_ModificarPerfume.jButtonGuardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                 if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_ModificarPerfume, "CAMPOS VACIOS");
                    return;
                }
                String nombre = dialog_ModificarPerfume.jTextFieldNombreEditar.getText();
                String laboratorio = dialog_ModificarPerfume.jTextFieldLaboratorioEditar.getText();
                String precio = dialog_ModificarPerfume.jTextFieldPrecioEditar.getText();
                if (new Perfumes(codigo, nombre, idMarca, laboratorio, precio, "0").modificarPerfume()) {
                    JOptionPane.showMessageDialog(dialog_ModificarPerfume, "EL PERFUME FUE MODIFICADO");
                    controlador_productosPorSucursal.cargarProductos();
                    dialog_ModificarPerfume.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(dialog_ModificarPerfume, "ERROR AL MODIFICAR");
                }
            }
        });
        
           dialog_ModificarPerfume.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_ModificarPerfume.setVisible(false);
            }
        });
        
        //----------------campo de texto solo numeros--------------------------

        dialog_ModificarPerfume.jTextFieldPrecioEditar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_ModificarPerfume.jTextFieldPrecioEditar.getText().matches("^\\d+\\.?\\d?\\d?") && dialog_ModificarPerfume.jTextFieldPrecioEditar.getText().length() > 0) {
                    dialog_ModificarPerfume.jTextFieldPrecioEditar.setText(dialog_ModificarPerfume.jTextFieldPrecioEditar.getText().substring(0, dialog_ModificarPerfume.jTextFieldPrecioEditar.getText().length() - 1));
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
        dialog_ModificarPerfume.jTextFieldNombreEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarPerfume.jTextFieldNombreEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarPerfume.jTextFieldLaboratorioEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarPerfume.jTextFieldLaboratorioEditar.setBackground(Color.white);

            }
        });
        dialog_ModificarPerfume.jTextFieldPrecioEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarPerfume.jTextFieldPrecioEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarPerfume.setVisible(true);
    }

    public void cargarMarcas() {
        arrayMarca = new Marca().obtenerNombreMarca();
        for (int i = 1; i < arrayMarca.size(); i += 2) {
            dialog_ModificarPerfume.jComboBoxMarcaEditar.addItem(arrayMarca.get(i));
        }

    }

    public void llenarDatosPerfume() {
        Perfumes perfume = new Perfumes();
        perfume.obtenerPerfume(codigo);
        dialog_ModificarPerfume.jTextFieldNombreEditar.setText(perfume.getNombre());
        dialog_ModificarPerfume.jComboBoxMarcaEditar.setSelectedItem(perfume.getMarca());
        dialog_ModificarPerfume.jTextFieldLaboratorioEditar.setText(perfume.getLaboratorio());
        dialog_ModificarPerfume.jTextFieldPrecioEditar.setText(perfume.getPrecio());
        obtenerIdMarca(perfume.getMarca());
    }
    

    public void obtenerIdMarca(String marca) {
        for (int i = 0; i < arrayMarca.size(); i++) {
            if (arrayMarca.get(i).equals(marca)) {
                idMarca = arrayMarca.get(i - 1);
            }
        }
    }
    
    
    public boolean camposVacios() {
        boolean b = false;
        if (dialog_ModificarPerfume.jTextFieldNombreEditar.getText().toString().isEmpty()) {
            dialog_ModificarPerfume.jTextFieldNombreEditar.setBackground(Color.RED);
            b = true;
        }
        if (dialog_ModificarPerfume.jTextFieldLaboratorioEditar.getText().toString().isEmpty()) {
            dialog_ModificarPerfume.jTextFieldLaboratorioEditar.setBackground(Color.RED);
            b = true;
        }

        if (dialog_ModificarPerfume.jTextFieldPrecioEditar.getText().toString().isEmpty()) {
            dialog_ModificarPerfume.jTextFieldPrecioEditar.setBackground(Color.RED);
            b = true;
        }
        return b;
    }
}
