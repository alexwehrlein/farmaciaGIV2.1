/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Abarrotes;
import Modelo.Alta;
import Modelo.Medicamento;
import Modelo.Perfumes;
import Modelo.Render;
import Modelo.Sucursal;
import Vista.Dialog_ModificarAbarrote;
import Vista.Dialog_SolicitarProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public class Controlador_DialogSolicitarProductos {

    private Dialog_SolicitarProductos dialog_SolicitarProductos;
    private DefaultTableModel modeloProducto;
    private String tipoProducto = "Medicamento";
    private ArrayList<String> arraySucursal;
    private int indexSucursal;
    private TableRowSorter filtro;

    public Controlador_DialogSolicitarProductos(JFrame parent, String nombreSucursal) {
        dialog_SolicitarProductos = new Dialog_SolicitarProductos(parent, true);
        dialog_SolicitarProductos.setLocationRelativeTo(null);
        dialog_SolicitarProductos.setResizable(true);
        filtro = new TableRowSorter(dialog_SolicitarProductos.jTableProductos.getModel());
        dialog_SolicitarProductos.jTableProductos.setRowSorter(filtro);
        modeloProducto = (DefaultTableModel) dialog_SolicitarProductos.jTableProductosSolicitados.getModel();
        dialog_SolicitarProductos.jTableProductos.setModel(new Medicamento().cargarRegistroMedicamentosSolicitante(dialog_SolicitarProductos.jTableProductos, "1"));
        cargarSucursal(nombreSucursal);

        dialog_SolicitarProductos.jTextFieldCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                filtro.setRowFilter(RowFilter.regexFilter("(?i)" + dialog_SolicitarProductos.jTextFieldCodigo.getText(), 0));
            }
        });

        dialog_SolicitarProductos.jComboBoxTipo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar_jtables(dialog_SolicitarProductos.jTableProductos);
                switch (dialog_SolicitarProductos.jComboBoxTipo.getSelectedIndex()) {
                    case 0:
                        tipoProducto = "Medicamento";
                        dialog_SolicitarProductos.jTableProductos.setModel(new Medicamento().cargarRegistroMedicamentosSolicitante(dialog_SolicitarProductos.jTableProductos, "1"));
                        break;
                    case 1:
                        dialog_SolicitarProductos.jTableProductos.setModel(new Perfumes().cargarRegistroPerfumesSolicitante(dialog_SolicitarProductos.jTableProductos, "1"));
                        tipoProducto = "Perfume";

                        break;
                    case 2:
                        dialog_SolicitarProductos.jTableProductos.setModel(new Abarrotes().cargarRegistroAbarrotesSolicitante(dialog_SolicitarProductos.jTableProductos, "1"));
                        tipoProducto = "Abarrote";
                        break;
                }

            }
        });

        dialog_SolicitarProductos.jComboBoxSucursalDestino.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (dialog_SolicitarProductos.jTableProductosSolicitados.getRowCount() > 0) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿DESEA CAMBIAR DE SUCURSAL? ", "CAMBIO DE SUCURSAL", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (opcion == 0) {
                        limpiar_jtables(dialog_SolicitarProductos.jTableProductosSolicitados);
                        indexSucursal = dialog_SolicitarProductos.jComboBoxSucursalDestino.getSelectedIndex();
                    } else {
                        dialog_SolicitarProductos.jComboBoxSucursalDestino.setSelectedIndex(indexSucursal);
                    }
                } else {
                    indexSucursal = dialog_SolicitarProductos.jComboBoxSucursalDestino.getSelectedIndex();
                }
            }
        }
        );

        dialog_SolicitarProductos.jTableProductos.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = dialog_SolicitarProductos.jTableProductos.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / dialog_SolicitarProductos.jTableProductos.getRowHeight();
                if (row < dialog_SolicitarProductos.jTableProductos.getRowCount() && row >= 0 && colum < dialog_SolicitarProductos.jTableProductos.getColumnCount() && colum >= 0) {
                    Object v = dialog_SolicitarProductos.jTableProductos.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        String codigo = dialog_SolicitarProductos.jTableProductos.getValueAt(dialog_SolicitarProductos.jTableProductos.getSelectedRow(), 0).toString();
                        String cantidad = new Alta().obtenerExistencia(codigo, tipoProducto, idSucursalPeticion());
                        if (cantidad == null) {
                            JOptionPane.showMessageDialog(dialog_SolicitarProductos, "LA SUCURSAL NO CUENTA CON ESTE PRODUCTO!!!");
                            return;
                        }

                        if (validarExistenciaProducto(codigo, Integer.parseInt(cantidad))) {
                            JOptionPane.showMessageDialog(dialog_SolicitarProductos, "EL PRODUCTO ES INSUFUCIENTE DE LA SUCURSAL A LA QUE SOLICITA !!!");
                            return;
                        }
                        agregarProducto(codigo);

                    }
                }

            }
        });

        dialog_SolicitarProductos.jTableProductosSolicitados.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e
                    ) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if((!dialog_SolicitarProductos.jTableProductosSolicitados.getValueAt(dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 2).toString().matches("^\\d+") )){
                               JOptionPane.showMessageDialog(dialog_SolicitarProductos, "SOLO NUMEROS ENTEROS !!!");
                                dialog_SolicitarProductos.jTableProductosSolicitados.setValueAt("1", dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 2);
                                return;
                            }
                            if((dialog_SolicitarProductos.jTableProductosSolicitados.getValueAt(dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 2).toString().equals("0"))){
                               JOptionPane.showMessageDialog(dialog_SolicitarProductos, "INGRESA UNA CANTIDAD MAYOR A 0 !!!");
                                dialog_SolicitarProductos.jTableProductosSolicitados.setValueAt("1", dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 2);
                                return;
                            }
                            
                            String codigo = dialog_SolicitarProductos.jTableProductosSolicitados.getValueAt(dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 0).toString();
                            String tipo = dialog_SolicitarProductos.jTableProductosSolicitados.getValueAt(dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 1).toString();
                            int cantidad = Integer.parseInt(dialog_SolicitarProductos.jTableProductosSolicitados.getValueAt(dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 2).toString());
                            int existenciaProductos = Integer.parseInt(new Alta().obtenerExistencia(codigo, tipo, idSucursalPeticion()));

                            if (cantidad > existenciaProductos) {
                                JOptionPane.showMessageDialog(dialog_SolicitarProductos, "EL PRODUCTO ES INSUFUCIENTE DE LA SUCURSAL A LA QUE SOLICITA !!!");
                                dialog_SolicitarProductos.jTableProductosSolicitados.setValueAt(existenciaProductos, dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow(), 2);
                                return;
                            }

                        }
                    }

                }
        );
        dialog_SolicitarProductos.jButtonSolicitar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialog_SolicitarProductos.jTableProductosSolicitados.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(dialog_SolicitarProductos, "AGREGUE EL/LOS PRODUCTO(S) QUE DESEA SOLICITAR");
                    return;
                }
                String clave = claveP();
                String sucursal2 = dialog_SolicitarProductos.jComboBoxSucursalDestino.getSelectedItem().toString();
                int opcion = JOptionPane.showConfirmDialog(null, "¿DESEA SOLICITAR ESTOS PRODUCTOS? ", "SOLICITAR PRODUCTOS", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opcion == 0) {
                    if (new Alta().registroPasoDeProductos(modeloProducto, clave, nombreSucursal, sucursal2)) {
                        JOptionPane.showMessageDialog(dialog_SolicitarProductos, "REGISTRO EXITOSO \n Clave: " + clave);
                        limpiar_jtables(dialog_SolicitarProductos.jTableProductosSolicitados);
                        dialog_SolicitarProductos.jComboBoxSucursalDestino.setSelectedIndex(0);
                        dialog_SolicitarProductos.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(dialog_SolicitarProductos, "ERROR AL REGISTRAR");
                    }

                }
            }
        });

        dialog_SolicitarProductos.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar_jtables(dialog_SolicitarProductos.jTableProductosSolicitados);
                dialog_SolicitarProductos.jComboBoxSucursalDestino.setSelectedIndex(0);
                dialog_SolicitarProductos.setVisible(false);
            }
        });

        dialog_SolicitarProductos.jTableProductosSolicitados.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = dialog_SolicitarProductos.jTableProductosSolicitados.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / dialog_SolicitarProductos.jTableProductosSolicitados.getRowHeight();
                if (row < dialog_SolicitarProductos.jTableProductosSolicitados.getRowCount() && row >= 0 && colum < dialog_SolicitarProductos.jTableProductosSolicitados.getColumnCount() && colum >= 0) {
                    Object v = dialog_SolicitarProductos.jTableProductosSolicitados.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        modeloProducto.removeRow(dialog_SolicitarProductos.jTableProductosSolicitados.getSelectedRow());
                    }
                }

            }
        });

        dialog_SolicitarProductos.setVisible(true);

    }

    public String claveP() {
        ArrayList<Integer> claves = new ArrayList<>();
        claves = new Alta().obtenerClaves();
        int numero = 0;
        Random numAleatorio = new Random();
        for (int i = 0; i < 6; i++) {
            numero += numAleatorio.nextInt(10000000);
        }
        if (numero < 0) {
            claveP();
        }
        if (claves != null) {
            for (int i = 0; i < claves.size(); i++) {
                if (claves.get(i) == numero) {
                    claveP();
                }
            }
        }

        return String.valueOf(numero);
    }

    public String idSucursalPeticion() {
        String idSucursal = "";
        if (dialog_SolicitarProductos.jComboBoxSucursalDestino.getItemCount() > 0) {
            for (int i = 0; i < arraySucursal.size(); i++) {
                if (arraySucursal.get(i).equals(dialog_SolicitarProductos.jComboBoxSucursalDestino.getSelectedItem().toString())) {
                    idSucursal = arraySucursal.get(i - 1);
                }
            }
        }
        return idSucursal;
    }

    public boolean validarExistenciaProducto(String codigo, int existenciaProductos) {
        boolean b = false;

        if (existenciaProductos == 0) {
            b = true;
        }
        if (productoAgregado(codigo)) {
            if (existenciaProductos == Integer.parseInt(modeloProducto.getValueAt(obtenerFila(codigo), 2).toString())) {
                b = true;
            }
        }

        return b;

    }

    private int obtenerFila(String codigo) {
        for (int i = 0; i < dialog_SolicitarProductos.jTableProductosSolicitados.getRowCount(); i++) {
            if (modeloProducto.getValueAt(i, 0).equals(codigo) && modeloProducto.getValueAt(i, 1).equals(tipoProducto)) {
                return i;
            }
        }
        return -1;
    }

    private boolean productoAgregado(String codigo) {
        boolean b = false;
        if (dialog_SolicitarProductos.jTableProductosSolicitados.getRowCount() > 0) {
            for (int i = 0; i < dialog_SolicitarProductos.jTableProductosSolicitados.getRowCount(); i++) {
                if (modeloProducto.getValueAt(i, 0).equals(codigo) && modeloProducto.getValueAt(i, 1).equals(tipoProducto)) {
                    b = true;
                }
            }
        }

        return b;

    }

    public void agregarProducto(String codigo) {
        dialog_SolicitarProductos.jTableProductosSolicitados.setDefaultRenderer(Object.class, new Render());
        JButton btnEliminar = new JButton("X");
        btnEliminar.setName("e");
//        ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
//        btnEliminar.setIcon(ie);
        if (productoAgregado(codigo)) {
            modeloProducto.setValueAt("" + (Integer.parseInt(modeloProducto.getValueAt(obtenerFila(codigo), 2).toString()) + 1), obtenerFila(codigo), 2);//Integer.parseInt(() + 1), 2)
        } else {
            modeloProducto.addRow(new Object[]{codigo, tipoProducto, "1", btnEliminar});
            dialog_SolicitarProductos.jTableProductosSolicitados.setModel(modeloProducto);
//            pantalla_RegistroPedido.tablaRegistroPedido.setModel(new modelo.Pedido().obtenerDatosProducto(codigo, pantalla_RegistroPedido.tablaRegistroPedido));
        }
//        pantalla_RegistroPedido.txtIdProducto.setText("");
    }

    public void cargarSucursal(String nombreSucursal) {
        arraySucursal = new Sucursal().obtenerSucursales();

        if (!arraySucursal.isEmpty()) {
            for (int i = 1; i < arraySucursal.size(); i += 2) {
                if (!arraySucursal.get(i).equals(nombreSucursal)) {
                    dialog_SolicitarProductos.jComboBoxSucursalDestino.addItem(arraySucursal.get(i));
                }
            }
        }
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

}
