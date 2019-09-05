/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Abarrotes;
import Modelo.Marca;
import Modelo.Perfumes;
import Vista.Dialog_ModificarAbarrote;
import Vista.Dialog_ModificarMedicamento;
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
public class Controlador_ModificarAbarrote {

    private Dialog_ModificarAbarrote dialog_ModificarAbarrote;
    private String codigo, idMarca;
    private ArrayList<String> arrayMarca = new ArrayList<>();

    public Controlador_ModificarAbarrote(JFrame parent, String codigo, Controlador_productosPorSucursal controlador_productosPorSucursal) {
        this.codigo = codigo;
        dialog_ModificarAbarrote = new Dialog_ModificarAbarrote(parent, true);
        dialog_ModificarAbarrote.setLocationRelativeTo(null);
        dialog_ModificarAbarrote.setResizable(true);
        dialog_ModificarAbarrote.jLabelCodigo.setText(codigo);
        cargarMarcas();
        llenarDatosAbarrote();

        dialog_ModificarAbarrote.jComboBoxMarcaEditar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < arrayMarca.size(); i++) {
                    if (arrayMarca.get(i).equals(dialog_ModificarAbarrote.jComboBoxMarcaEditar.getSelectedItem().toString())) {
                        idMarca = arrayMarca.get(i - 1);
                    }
                }

            }
        });

        dialog_ModificarAbarrote.jButtonGuardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposVacios()) {
                    JOptionPane.showMessageDialog(dialog_ModificarAbarrote, "CAMPOS VACIOS");
                    return;
                }
                String nombre = dialog_ModificarAbarrote.jTextFieldNombreEditar.getText();
                String precio = dialog_ModificarAbarrote.jTextFieldPrecioEditar.getText();
                if (new Abarrotes(codigo, nombre, idMarca, precio, "0").modificarAbarrote()) {
                    JOptionPane.showMessageDialog(dialog_ModificarAbarrote, "EL ABARROTE FUE MODIFICADO");
                    controlador_productosPorSucursal.cargarProductos();
                    dialog_ModificarAbarrote.setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(dialog_ModificarAbarrote, "ERROR AL MODIFICAR");
                }
            }
        });
        
           dialog_ModificarAbarrote.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog_ModificarAbarrote.setVisible(false);
            }
        });

        //----------------campo de texto solo numeros--------------------------
        dialog_ModificarAbarrote.jTextFieldPrecioEditar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dialog_ModificarAbarrote.jTextFieldPrecioEditar.getText().matches("^\\d+\\.?\\d?\\d?") && dialog_ModificarAbarrote.jTextFieldPrecioEditar.getText().length() > 0) {
                    dialog_ModificarAbarrote.jTextFieldPrecioEditar.setText(dialog_ModificarAbarrote.jTextFieldPrecioEditar.getText().substring(0, dialog_ModificarAbarrote.jTextFieldPrecioEditar.getText().length() - 1));
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
        dialog_ModificarAbarrote.jTextFieldNombreEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarAbarrote.jTextFieldNombreEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarAbarrote.jTextFieldPrecioEditar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent Mouse_evt) {
                dialog_ModificarAbarrote.jTextFieldPrecioEditar.setBackground(Color.white);

            }
        });

        dialog_ModificarAbarrote.setVisible(true);
    }

    public void cargarMarcas() {
        arrayMarca = new Marca().obtenerNombreMarca();
        for (int i = 1; i < arrayMarca.size(); i += 2) {
            dialog_ModificarAbarrote.jComboBoxMarcaEditar.addItem(arrayMarca.get(i));
        }

    }

    public void llenarDatosAbarrote() {
        Abarrotes abarrotes = new Abarrotes();
        abarrotes.obtenerAbarrote(codigo);
        dialog_ModificarAbarrote.jTextFieldNombreEditar.setText(abarrotes.getNombre());
        dialog_ModificarAbarrote.jComboBoxMarcaEditar.setSelectedItem(abarrotes.getMarca());
        dialog_ModificarAbarrote.jTextFieldPrecioEditar.setText(abarrotes.getPrecio());
        obtenerIdMarca(abarrotes.getMarca());
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

        if (dialog_ModificarAbarrote.jTextFieldNombreEditar.getText().toString().isEmpty()) {
            dialog_ModificarAbarrote.jTextFieldNombreEditar.setBackground(Color.RED);
            b = true;
        }

        if (dialog_ModificarAbarrote.jTextFieldPrecioEditar.getText().toString().isEmpty()) {
            dialog_ModificarAbarrote.jTextFieldPrecioEditar.setBackground(Color.RED);
            b = true;
        }
        return b;
    }

}
