package Controlador;

import Modelo.Producto;
import Modelo.Promociones;
import Vista.Pantalla_Promociones;
import Vista.Pantalla_principal;
import alert.AlertError;
import alert.AlertInformation;
import alert.AlertSuccess;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controlador_Promociones {

    Pantalla_Promociones pantalla_Promociones;
    Producto producto;
    Promociones promocion;
    AlertInformation alertInformation;
    AlertError alertError;
    AlertSuccess alertSuccess;

    public Controlador_Promociones(Pantalla_principal pantalla_principal, int idEm, int idSucursal) {
        pantalla_Promociones = new Pantalla_Promociones();
        pantalla_Promociones.setVisible(true);
        pantalla_Promociones.setResizable(true);
        pantalla_Promociones.setClosable(true);
        pantalla_Promociones.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pantalla_Promociones.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_Promociones.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_Promociones);

        pantalla_Promociones.txtCodigo.requestFocus();
        clear();
        radioButton(1);
        pantalla_Promociones.jTablePromociones.setModel(new Promociones().cargarRegistros(pantalla_Promociones.jTablePromociones, idSucursal, ""));

        pantalla_Promociones.jRadioButtonPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Promociones.tipo1.setVisible(true);
                pantalla_Promociones.tipo2.setVisible(false);
                pantalla_Promociones.tipo3.setVisible(false);
                clear();
                pantalla_Promociones.txtPrecio.requestFocus();
            }
        });
        pantalla_Promociones.jRadioButtonDescuento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Promociones.tipo1.setVisible(false);
                pantalla_Promociones.tipo2.setVisible(true);
                pantalla_Promociones.tipo3.setVisible(false);
                clear();
                pantalla_Promociones.txtDescuento.requestFocus();
            }
        });
        pantalla_Promociones.jRadioButtonMul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Promociones.tipo1.setVisible(false);
                pantalla_Promociones.tipo2.setVisible(false);
                pantalla_Promociones.tipo3.setVisible(true);
                clear();
                pantalla_Promociones.txtCantidad.requestFocus();
            }
        });

        pantalla_Promociones.txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String codigo = pantalla_Promociones.txtCodigo.getText();
                    Producto p = new Producto(codigo, idSucursal);
                    p.producto();
                    if (p.getExiste() == 1) {
                        alertError = new AlertError("El Producto no existe.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        return;
                    }
                    if (p.getPromocion() > 0) {
                        alertError = new AlertError("Ya hay una promocion \n para este producto \n primero borre la promocion.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        return;
                    }

                    pantalla_Promociones.txtProducto.setText(p.getMarca());
                    radioButton(0);

                }
            }
        });

        pantalla_Promociones.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = pantalla_Promociones.txtCodigo.getText();
                String marca = pantalla_Promociones.txtProducto.getText();
                String[] data = new String[5];

                if (pantalla_Promociones.jRadioButtonPrecio.isSelected()) {
                    String precio = pantalla_Promociones.txtPrecio.getText();
                    if (precio.length() == 0) {
                        alertError = new AlertError("Ingrese una cantidad.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Promociones.txtPrecio.requestFocus();
                        return;
                    }
                    if (!precio.matches("^\\d+\\.?\\d?\\d?")) {
                        alertError = new AlertError("Ingrese una cantidad valida.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Promociones.txtPrecio.requestFocus();
                        return;
                    }
                    data[0] = precio;
                    data[1] = "0";
                    data[2] = "0";
                    data[3] = "0";
                    data[4] = "1";
                    //promocion = new Promociones(codigo, idSucursal, Float.valueOf(precio), 0, 0, 0, 1);
                } else if (pantalla_Promociones.jRadioButtonDescuento.isSelected()) {
                    String descuetno = pantalla_Promociones.txtDescuento.getText();
                    if (descuetno.length() == 0) {
                        alertError = new AlertError("Ingrese una cantidad.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Promociones.txtDescuento.requestFocus();
                        return;
                    }
                    if (!descuetno.matches("^\\d+\\.?\\d?\\d?")) {
                        alertError = new AlertError("Ingrese una cantidad valida.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Promociones.txtDescuento.requestFocus();
                        return;
                    }
                    data[0] = "0";
                    data[1] = descuetno;
                    data[2] = "0";
                    data[3] = "0";
                    data[4] = "2";
                } else if (pantalla_Promociones.jRadioButtonMul.isSelected()) {
                    String cantidad = pantalla_Promociones.txtCantidad.getText();
                    String precio = pantalla_Promociones.txtCantidadPrecio.getText();
                    if (cantidad.length() == 0 && precio.length() == 0) {
                        alertError = new AlertError("Ingrese una cantidad.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        return;
                    }
                    if (!cantidad.matches("^\\d+\\.?\\d?\\d?")) {
                        alertError = new AlertError("Ingrese una cantidad valida.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Promociones.txtCantidad.requestFocus();
                        return;
                    }
                    if (!precio.matches("^\\d+\\.?\\d?\\d?")) {
                        alertError = new AlertError("Ingrese una cantidad valida.");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Promociones.txtCantidadPrecio.requestFocus();
                        return;
                    }
                    data[0] = "0";
                    data[1] = "0";
                    data[2] = cantidad;
                    data[3] = precio;
                    data[4] = "3";
                } else {
                    alertError = new AlertError("Seleccione un tipo de descuento.");
                    alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertError.show();
                }
                promocion = new Promociones(codigo, idSucursal, marca, Float.parseFloat(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3]), Float.valueOf(data[2]), Integer.parseInt(data[4]));
                boolean next = promocion.ingresarPromocion();
                if (next) {
                    alertSuccess = new AlertSuccess("Exito al guardar.");
                    alertSuccess.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertSuccess.show();
                    radioButton(1);
                    clear();
                    limpiar_jtables(pantalla_Promociones.jTablePromociones);
                    pantalla_Promociones.jTablePromociones.setModel(new Promociones().cargarRegistros(pantalla_Promociones.jTablePromociones, idSucursal, ""));
                    pantalla_Promociones.txtCodigo.setText("");
                    pantalla_Promociones.txtProducto.setText("");
                } else {
                    alertError = new AlertError("Error al guardar la promocion.");
                    alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertError.show();
                }
            }
        });

        pantalla_Promociones.jTablePromociones.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = pantalla_Promociones.jTablePromociones.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_Promociones.jTablePromociones.getRowHeight();
                if (row < pantalla_Promociones.jTablePromociones.getRowCount() && row >= 0 && colum < pantalla_Promociones.jTablePromociones.getColumnCount() && colum >= 0) {
                    Object v = pantalla_Promociones.jTablePromociones.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        if (b.getName().equals("btnEliminar")) {
                            if (JOptionPane.showConfirmDialog(null, "¿Desea Eliminarno?", "WARNING",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                String codigo = pantalla_Promociones.jTablePromociones.getValueAt(pantalla_Promociones.jTablePromociones.getSelectedRow(), 0).toString();
                                promocion = new Promociones(codigo, idSucursal);
                                promocion.eliminarPromocion();
                                limpiar_jtables(pantalla_Promociones.jTablePromociones);
                                pantalla_Promociones.jTablePromociones.setModel(new Promociones().cargarRegistros(pantalla_Promociones.jTablePromociones, idSucursal, ""));
                            }
                        }
                    }
                }

            }
        });
        
        pantalla_Promociones.txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String palabra = pantalla_Promociones.txtSearch.getText();
                promocion = new Promociones();
                limpiar_jtables(pantalla_Promociones.jTablePromociones);
                promocion.cargarRegistros(pantalla_Promociones.jTablePromociones,idSucursal, palabra);
            }
        }
        );

    }
    
     public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

    private void clear() {
        pantalla_Promociones.txtPrecio.setText("");
        pantalla_Promociones.txtDescuento.setText("");
        pantalla_Promociones.txtCantidad.setText("");
        pantalla_Promociones.txtCantidadPrecio.setText("");
    }

    private void radioButton(int tipo) {
        if (tipo == 1) {
            pantalla_Promociones.jRadioButtonDescuento.setEnabled(false);
            pantalla_Promociones.jRadioButtonPrecio.setEnabled(false);
            pantalla_Promociones.jRadioButtonMul.setEnabled(false);
            pantalla_Promociones.tipo1.setVisible(false);
            pantalla_Promociones.tipo2.setVisible(false);
            pantalla_Promociones.tipo3.setVisible(false);
            pantalla_Promociones.tipo_promociones.clearSelection();
            pantalla_Promociones.txtCodigo.requestFocus();
        } else {
            pantalla_Promociones.jRadioButtonDescuento.setEnabled(true);
            pantalla_Promociones.jRadioButtonPrecio.setEnabled(true);
            pantalla_Promociones.jRadioButtonMul.setEnabled(true);
        }

    }
}
