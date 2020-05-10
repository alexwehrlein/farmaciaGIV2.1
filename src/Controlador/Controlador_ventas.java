package Controlador;

import Modelo.Producto;
import Modelo.Render;
import Vista.Pantalla_Ventas;
import Vista.Pantalla_principal;
import alert.AlertError;
import alert.AlertInformation;
import alert.AlertSuccess;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class Controlador_ventas {

    Producto producto;
    Pantalla_Ventas pantalla_Ventas;
    AlertInformation alertInformation;
    AlertError alertError;
    AlertSuccess alertSuccess;
    int idSursal;

    public Controlador_ventas(Pantalla_principal pantalla_principal, int idEm, int idSucursal, String turno) {
        this.idSursal = idSucursal;
        pantalla_Ventas = new Pantalla_Ventas();
        pantalla_Ventas.setVisible(true);
        pantalla_Ventas.setResizable(true);
        pantalla_Ventas.setClosable(true);
        pantalla_Ventas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pantalla_Ventas.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_Ventas.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_Ventas);
        pantalla_Ventas.txtCodigo.requestFocus();

        //buscar por desccripcion
        pantalla_Ventas.txtDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String descricion = pantalla_Ventas.txtDescripcion.getText();
                pantalla_Ventas.jComboBoxProductos.removeAllItems();
                ArrayList<Producto> listaProductos;
                producto = new Producto(idSucursal, descricion);
                listaProductos = producto.octenerProducto();
                listaProductos.forEach((pro) -> {
                    pantalla_Ventas.modeloProductos.addElement(pro);
                });
            }
        });

        pantalla_Ventas.txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String codigo = pantalla_Ventas.txtCodigo.getText();
                    producSearch(codigo);
                }
            }
        });

        pantalla_Ventas.jComboBoxProductos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Producto producto = (Producto) pantalla_Ventas.jComboBoxProductos.getSelectedItem();
                    producSearch(producto.getCodigo());
                }
            }
        });
    }

    private void producSearch(String codigo) {
        Producto product = new Producto(codigo, this.idSursal);
        product.producto();
        if (product.getExiste() == 1) {
            alertError = new AlertError("El Producto no existe.");
            alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            alertError.show();
            pantalla_Ventas.txtCodigo.requestFocus();
            return;
        }
        if (product.getCantidad() <= 0) {
            alertError = new AlertError("Ya no hay existencias para este Producto.");
            alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            alertError.show();
            pantalla_Ventas.txtCodigo.requestFocus();
            return;
        }

        float precio = promocion(product);
        agregarProducto(product, precio);
        ventaTotal(precio);
        pantalla_Ventas.txtCodigo.setText("");
        pantalla_Ventas.txtDescripcion.setText("");
        pantalla_Ventas.txtCodigo.requestFocus();
    }

    private float promocion(Producto produc) {
        float precio = 0;
        int[] oferta = new int[30];
        int cantidad = 1;
        switch (produc.getPromocionTipo()) {
            case 1:
                precio = produc.getPromocionP();
                break;

            case 2:
                precio = (produc.getPrecio() / 100) * produc.getPromocionDescuento();
                break;
            case 3:
                for (int i = 1; i < 30; i++) {
                    oferta[i] = produc.getPromocionCantidad() * i;
                }
                DefaultTableModel modelo = (DefaultTableModel) pantalla_Ventas.jTableVenta.getModel();
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String codigo = modelo.getValueAt(i, 0).toString();
                    if (codigo.equals(produc.getCodigo())) {
                        cantidad = Integer.parseInt(modelo.getValueAt(i, 3).toString()) + 1;
                        break;
                    }
                }
                for (int i = 0; i < oferta.length; i++) {
                    if (cantidad == oferta[i]) {
                        precio = produc.getPromocionPrecio() * i + 1;
                    }
                }
                if (precio <= 0) {
                    precio = produc.getPrecio();
                }
                break;
            default:
                precio = produc.getPrecio();
                break;
        }
        return precio;
    }

    private void agregarProducto(Producto produc, float precio) {
        boolean flang = false;
        int piezas = 0;
        int indice = 0;
        float total = 0;
        DefaultTableModel modelo = (DefaultTableModel) pantalla_Ventas.jTableVenta.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String codigo = modelo.getValueAt(i, 0).toString();
            if (codigo.equals(produc.getCodigo())) {
                flang = true;
                piezas = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                total = Float.parseFloat((modelo.getValueAt(i, 5).toString()));
                indice = i;
                break;
            }
        }
        if (flang) {
            modelo.setValueAt((piezas + 1), indice, 3);
            modelo.setValueAt((total + precio), indice, 5);
        } else {
            pantalla_Ventas.jTableVenta.setDefaultRenderer(Object.class, new Render());
            JButton btnEliminar = new JButton("");
            btnEliminar.setName("btnEliminar");
            ImageIcon ie = new ImageIcon(getClass().getResource("/Images/menu/eli.png"));
            btnEliminar.setIcon(ie);
            pantalla_Ventas.jTableVenta.setRowHeight(25);
            modelo.addRow(new Object[]{produc.getCodigo(), produc.getMarca(), produc.getTipo(), 1, produc.getPrecio(), precio, btnEliminar, (produc.getPromocionTipo() > 0) ? "S" : "N"});
        }
    }

    private void ventaTotal(float precio) {
        float total = Float.valueOf(pantalla_Ventas.jLabelSubtotalVenta.getText()) + precio;
        pantalla_Ventas.jLabelSubtotalVenta.setText(String.format("%.2f", (float) total));
    }

}
