/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alta;
import Modelo.Perfumes;
import Modelo.Sucursal;
import Vista.Pantalla_Producto;
import Vista.Pantalla_ProductosPorSucursal;
import Vista.Pantalla_principal;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_productosPorSucursal {

    Pantalla_ProductosPorSucursal pantalla_ProductosPorSucursal;
    private TableRowSorter trsFiltro;
    private String nombreSucursal;

    public Controlador_productosPorSucursal(Pantalla_principal pantalla_principal, String nombreSucursal,String idSucursalLocal) {
        this.nombreSucursal = nombreSucursal;
        pantalla_ProductosPorSucursal = new Pantalla_ProductosPorSucursal();
        pantalla_ProductosPorSucursal.setVisible(true);
        pantalla_ProductosPorSucursal.setResizable(true);
        pantalla_ProductosPorSucursal.setSize(1180, 540);
        pantalla_ProductosPorSucursal.setClosable(true);
        pantalla_ProductosPorSucursal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_ProductosPorSucursal.setLocation((desktopSize.width - 1180) / 2, (desktopSize.height - 580) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_ProductosPorSucursal);
        trsFiltro = new TableRowSorter(pantalla_ProductosPorSucursal.jTableProductos.getModel());
        pantalla_ProductosPorSucursal.jTableProductos.setRowSorter(trsFiltro);

        cargarProductos();
        cargarProductosSolicitados();

        pantalla_ProductosPorSucursal.jTextFieldBuscarProducto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (pantalla_ProductosPorSucursal.jRadioButtonCodigo.isSelected()) {
                    trsFiltro.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_ProductosPorSucursal.jTextFieldBuscarProducto.getText(), 0));
                } else {
                    trsFiltro.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_ProductosPorSucursal.jTextFieldBuscarProducto.getText(), 1));
                }
            }
        });

        pantalla_ProductosPorSucursal.jComboBoxNuevoProducto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (pantalla_ProductosPorSucursal.jComboBoxNuevoProducto.getSelectedIndex()) {
                    case 1:
                        new Controlador_NuevoMedicamento(pantalla_principal, Controlador_productosPorSucursal.this);
                        break;
                    case 2:
                        new Controlador_NuevoPerfume(pantalla_principal, Controlador_productosPorSucursal.this);
                        break;
                    case 3:
                        new Controlador_NuevoAbarrote(pantalla_principal, Controlador_productosPorSucursal.this);
                        break;
                }

            }
        });
        pantalla_ProductosPorSucursal.jTableProductos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {

                String codigo = pantalla_ProductosPorSucursal.jTableProductos.getValueAt(pantalla_ProductosPorSucursal.jTableProductos.getSelectedRow(), 0).toString();
                String tipo = pantalla_ProductosPorSucursal.jTableProductos.getValueAt(pantalla_ProductosPorSucursal.jTableProductos.getSelectedRow(), 1).toString();
                cargarProductosPorSucursal(codigo, tipo);
                int colum = pantalla_ProductosPorSucursal.jTableProductos.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_ProductosPorSucursal.jTableProductos.getRowHeight();
                if (row < pantalla_ProductosPorSucursal.jTableProductos.getRowCount() && row >= 0 && colum < pantalla_ProductosPorSucursal.jTableProductos.getColumnCount() && colum >= 0) {
                    Object v = pantalla_ProductosPorSucursal.jTableProductos.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        if (b.getName().equals("Editar")) {
                            switch (tipo) {
                                case "Medicamento":
                                    new Controlador_ModificarMedicamento(pantalla_principal, codigo, Controlador_productosPorSucursal.this);
                                    break;
                                case "Perfume":
                                    new Controlador_ModificarPerfume(pantalla_principal, codigo, Controlador_productosPorSucursal.this);
                                    break;
                                case "Abarrote":
                                    new Controlador_ModificarAbarrote(pantalla_principal, codigo, Controlador_productosPorSucursal.this);
                                    break;
                            }
                        }
                    }
                }

            }
        });
        pantalla_ProductosPorSucursal.jButtonSolicitar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_DialogSolicitarProductos(pantalla_principal, nombreSucursal);
            }
        });

        pantalla_ProductosPorSucursal.jLabelActualizar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarProductosSolicitados();
            }
        });

        pantalla_ProductosPorSucursal.jTableProductosSolicitados.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                String fecha = pantalla_ProductosPorSucursal.jTableProductosSolicitados.getValueAt(pantalla_ProductosPorSucursal.jTableProductosSolicitados.getSelectedRow(), 0).toString();
                String hora = pantalla_ProductosPorSucursal.jTableProductosSolicitados.getValueAt(pantalla_ProductosPorSucursal.jTableProductosSolicitados.getSelectedRow(), 1).toString();
                 String sucursal = pantalla_ProductosPorSucursal.jTableProductosSolicitados.getValueAt(pantalla_ProductosPorSucursal.jTableProductosSolicitados.getSelectedRow(), 2).toString();
                new Controlador_DialogSolicitarClave(pantalla_principal, fecha, hora,sucursal,idSucursalLocal);
            }
        });

    }

    public void cargarProductosPorSucursal(String codigo, String tipo) {
        limpiar_jtables(pantalla_ProductosPorSucursal.jTableProductosSucursal);
        pantalla_ProductosPorSucursal.jTableProductosSucursal.setModel(new Sucursal().productosPorSucursal(pantalla_ProductosPorSucursal.jTableProductosSucursal, codigo, tipo));
    }

    public void cargarProductos() {
        limpiar_jtables(pantalla_ProductosPorSucursal.jTableProductos);
        pantalla_ProductosPorSucursal.jTableProductos.setModel(new Sucursal().obtenerTodosProductos(pantalla_ProductosPorSucursal.jTableProductos));
    }

    public void cargarProductosSolicitados() {
        limpiar_jtables(pantalla_ProductosPorSucursal.jTableProductosSolicitados);
        pantalla_ProductosPorSucursal.jTableProductosSolicitados.setModel(new Alta().obtenerProductosSolicitados(pantalla_ProductosPorSucursal.jTableProductosSolicitados, nombreSucursal));
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }
}
