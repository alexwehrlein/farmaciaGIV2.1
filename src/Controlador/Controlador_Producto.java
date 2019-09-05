/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Pantalla_Producto;
import Vista.Pantalla_principal;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Modelo.Abarrotes;
import Modelo.Medicamento;
import Modelo.Perfumes;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_Producto {

    Pantalla_Producto pantalla_Producto;
    private String idSucursal;
    private TableRowSorter filtroMedicamento, filtroAbarrote, filtroPerfume;

    public Controlador_Producto(Pantalla_principal pantalla_principal, String idSucursal) {
        this.idSucursal = idSucursal;
        pantalla_Producto = new Pantalla_Producto();
        pantalla_Producto.setVisible(true);
        pantalla_Producto.setResizable(true);
        pantalla_Producto.setSize(1250, 600);
        pantalla_Producto.setClosable(true);
        pantalla_Producto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_Producto.setLocation((desktopSize.width - 1250) / 2, (desktopSize.height - 630) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_Producto);

        filtroMedicamento = new TableRowSorter(pantalla_Producto.jTableMedicamentos.getModel());
        filtroAbarrote = new TableRowSorter(pantalla_Producto.jTableAbarrotes.getModel());
        filtroPerfume = new TableRowSorter(pantalla_Producto.jTablePerfume.getModel());
        pantalla_Producto.jTableMedicamentos.setRowSorter(filtroMedicamento);
        pantalla_Producto.jTableAbarrotes.setRowSorter(filtroAbarrote);
        pantalla_Producto.jTablePerfume.setRowSorter(filtroPerfume);

        cargarTablaMedicamentos();
        cargarTablaAbarrotes();
        cargarTablaPerfumes();

        //buscarProducto Medicamento
        pantalla_Producto.jTextFieldBuscarMedicamentos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                filtroMedicamento.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_Producto.jTextFieldBuscarMedicamentos.getText(), 0));
            }
        });
        //buscarProducto Abarrote
        pantalla_Producto.jTextFieldBuscarAbarrotes.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                filtroAbarrote.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_Producto.jTextFieldBuscarAbarrotes.getText(), 0));
            }
        });

        //buscarProducto Perfume
        pantalla_Producto.jTextFieldBuscarPerfume.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                filtroPerfume.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_Producto.jTextFieldBuscarPerfume.getText(), 0));
            }
        });

        //medicamentos
        pantalla_Producto.jTableMedicamentos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = pantalla_Producto.jTableMedicamentos.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_Producto.jTableMedicamentos.getRowHeight();
                if (row < pantalla_Producto.jTableMedicamentos.getRowCount() && row >= 0 && colum < pantalla_Producto.jTableMedicamentos.getColumnCount() && colum >= 0) {
                    Object v = pantalla_Producto.jTableMedicamentos.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        if (b.getName().equals("Editar")) {
//                              String codigoMedicamento = pantalla_Producto.jTableMedicamentos.getValueAt(pantalla_Producto.jTableMedicamentos.getSelectedRow(), 0).toString();
                            JOptionPane.showMessageDialog(pantalla_Producto, "EDITAR");
                        } else {
                            String codigo = pantalla_Producto.jTableMedicamentos.getValueAt(pantalla_Producto.jTableMedicamentos.getSelectedRow(), 0).toString();
                            new Controlador_DialogAltaProductos(pantalla_principal, Controlador_Producto.this, codigo, "Medicamento", idSucursal);
                        }

                    }
                }

            }
        });

        //abarrotes
        pantalla_Producto.jTableAbarrotes.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = pantalla_Producto.jTableAbarrotes.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_Producto.jTableAbarrotes.getRowHeight();
                if (row < pantalla_Producto.jTableAbarrotes.getRowCount() && row >= 0 && colum < pantalla_Producto.jTableAbarrotes.getColumnCount() && colum >= 0) {
                    Object v = pantalla_Producto.jTableAbarrotes.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        if (b.getName().equals("Editar")) {
//                              String codigoMedicamento = pantalla_Producto.jTableMedicamentos.getValueAt(pantalla_Producto.jTableMedicamentos.getSelectedRow(), 0).toString();
                            JOptionPane.showMessageDialog(pantalla_Producto, "EDITAR");
                        } else {
                            String codigo = pantalla_Producto.jTableAbarrotes.getValueAt(pantalla_Producto.jTableAbarrotes.getSelectedRow(), 0).toString();
                            new Controlador_DialogAltaProductos(pantalla_principal, Controlador_Producto.this, codigo, "Abarrote", idSucursal);
                        }

                    }
                }

            }
        });

        //perfumes
        pantalla_Producto.jTablePerfume.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = pantalla_Producto.jTablePerfume.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_Producto.jTablePerfume.getRowHeight();
                if (row < pantalla_Producto.jTablePerfume.getRowCount() && row >= 0 && colum < pantalla_Producto.jTablePerfume.getColumnCount() && colum >= 0) {
                    Object v = pantalla_Producto.jTablePerfume.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        if (b.getName().equals("Editar")) {
//                              String codigoMedicamento = pantalla_Producto.jTableMedicamentos.getValueAt(pantalla_Producto.jTableMedicamentos.getSelectedRow(), 0).toString();
                            JOptionPane.showMessageDialog(pantalla_Producto, "EDITAR");
                        } else {
                            String codigo = pantalla_Producto.jTablePerfume.getValueAt(pantalla_Producto.jTablePerfume.getSelectedRow(), 0).toString();
                            new Controlador_DialogAltaProductos(pantalla_principal, Controlador_Producto.this, codigo, "Perfume", idSucursal);
                        }

                    }
                }

            }
        });

    }

    public void cargarTablaMedicamentos() {
        limpiar_jtables(pantalla_Producto.jTableMedicamentos);
        pantalla_Producto.jTableMedicamentos.setModel(new Medicamento().cargarRegistroMedicamentos(pantalla_Producto.jTableMedicamentos, idSucursal));
    }

    public void cargarTablaAbarrotes() {
        limpiar_jtables(pantalla_Producto.jTableAbarrotes);
        pantalla_Producto.jTableAbarrotes.setModel(new Abarrotes().cargarRegistroAbarrotes(pantalla_Producto.jTableAbarrotes, idSucursal));
    }

    public void cargarTablaPerfumes() {
        limpiar_jtables(pantalla_Producto.jTablePerfume);
        pantalla_Producto.jTablePerfume.setModel(new Perfumes().cargarRegistroPerfumes(pantalla_Producto.jTablePerfume, idSucursal));
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

}
