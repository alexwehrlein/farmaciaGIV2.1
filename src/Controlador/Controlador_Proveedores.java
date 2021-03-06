/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Proveedores;
import Vista.Pantalla_Proveedores;
import Vista.Pantalla_principal;
import java.awt.Dialog;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jesus
 */
public class Controlador_Proveedores {

    Pantalla_Proveedores pantalla_proveedores;//objeto de pantalla personal
    Pantalla_principal pantalla_principal;
    Proveedores proveedores;

    public Controlador_Proveedores(Pantalla_principal pantalla_principal) {
        pantalla_proveedores = new Pantalla_Proveedores();
        pantalla_proveedores.TableProveedores.setModel(new Proveedores().ConsultaProveedores(pantalla_proveedores.TableProveedores));

        pantalla_proveedores.setVisible(true);
        pantalla_proveedores.setResizable(true);
        //pp.setSize(981, 474);
        pantalla_proveedores.setClosable(true);
        pantalla_proveedores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pantalla_proveedores.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_proveedores.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_proveedores);

        pantalla_proveedores.btnAgregarProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //pantalla_proveedores.dispose();
                pantalla_proveedores.AgregarProveedor.setVisible(true);
                pantalla_proveedores.AgregarProveedor.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                pantalla_proveedores.AgregarProveedor.setResizable(false);
                pantalla_proveedores.AgregarProveedor.setBounds(390, 100, 350, 600);

            }
        });

        pantalla_proveedores.btnCancelarAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Proveedores(pantalla_principal);
                pantalla_proveedores.dispose();
                pantalla_proveedores.AgregarProveedor.setVisible(false);
            }
        });

        pantalla_proveedores.TableProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent dobleclik) {
                int fila;
                if (dobleclik.getClickCount() == 2) {
                    //pantalla_proveedores.dispose();
                    pantalla_proveedores.EditarProveedores.setVisible(true);
                    pantalla_proveedores.EditarProveedores.setResizable(false);
                    pantalla_proveedores.EditarProveedores.setBounds(400, 70, 350, 650);
                    fila = pantalla_proveedores.TableProveedores.getSelectedRow();
                    int id = (int) pantalla_proveedores.TableProveedores.getValueAt(fila, 0);
                    String nombre = pantalla_proveedores.TableProveedores.getValueAt(fila, 1).toString();
                    String telefono = pantalla_proveedores.TableProveedores.getValueAt(fila, 2).toString();
                    String ciudad = pantalla_proveedores.TableProveedores.getValueAt(fila, 3).toString();
                    String estado = pantalla_proveedores.TableProveedores.getValueAt(fila, 4).toString();
                    String email = pantalla_proveedores.TableProveedores.getValueAt(fila, 5).toString();
                    String id2 = String.valueOf(id);
                    pantalla_proveedores.idProveedor.setText(id2);
                    pantalla_proveedores.nombreProveedor.setText(nombre);
                    pantalla_proveedores.telefonoProveedor.setText(telefono);
                    pantalla_proveedores.ciudadProveedor.setText(ciudad);
                    pantalla_proveedores.estadoProveedor.setText(estado);
                    pantalla_proveedores.emailProveedor.setText(email);
                }
                ///para eliminar
                if (dobleclik.getClickCount() == 1) {

                    int column = pantalla_proveedores.TableProveedores.getColumnModel().getColumnIndexAtX(dobleclik.getX());
                    int row = dobleclik.getY() / pantalla_proveedores.TableProveedores.getRowHeight();
                    int filaEliminar;
                    if (row < pantalla_proveedores.TableProveedores.getRowCount() && row >= 0 && column < pantalla_proveedores.TableProveedores.getColumnCount() && column >= 0) {
                        Object value = pantalla_proveedores.TableProveedores.getValueAt(row, column);
                        if (value instanceof JButton) {
                            ((JButton) value).doClick();
                            JButton boton = (JButton) value;

                            if (boton.getName().equals("btnEliminar")) {
                                int reply = JOptionPane.showConfirmDialog(null, "¿Eliminar Proveedor?", "Modificar", JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.YES_OPTION) {
                                    filaEliminar = pantalla_proveedores.TableProveedores.getSelectedRow();
                                    int id = (int) pantalla_proveedores.TableProveedores.getValueAt(filaEliminar, 0);
                                    proveedores = new Proveedores(id);
                                    if (proveedores.eliminarProvedor()) {
                                        JOptionPane.showMessageDialog(null, "Dato Eliminado Correctamente");
                                        actualizarTabla();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                            }
                        }

                    }
                }
            }
        });
        pantalla_proveedores.btnCancelarEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Proveedores(pantalla_principal);
                pantalla_proveedores.dispose();
                pantalla_proveedores.EditarProveedores.setVisible(false);
            }
        });

        pantalla_proveedores.btnAcetarAgregarPro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = pantalla_proveedores.nombreProveedorAg.getText();
                String telefono = pantalla_proveedores.telefono.getText();
                String correo = pantalla_proveedores.ciudad.getText();
                String direccion = pantalla_proveedores.estado.getText();
                String puesto = pantalla_proveedores.email.getText();
                String estatus = "1";
                System.out.println(nombre + " " + telefono);
                proveedores = new Proveedores(0, nombre, telefono, correo, direccion, puesto, estatus);
                if (proveedores.agregarProveedor()) {
                    JOptionPane.showMessageDialog(null, "Datos ingresados Correctamente");
                    limpiarCamposAgregar();
                    pantalla_proveedores.AgregarProveedor.setVisible(false);
                     pantalla_proveedores.setVisible(true);
                     actualizarTabla();

                } else {
                    JOptionPane.showMessageDialog(null, "Error");

                }
            }

        });
        pantalla_proveedores.btnAceptarEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = pantalla_proveedores.idProveedor.getText();
                String nombre = pantalla_proveedores.nombreProveedor.getText();
                String telefono = pantalla_proveedores.telefonoProveedor.getText();
                String ciudad = pantalla_proveedores.ciudadProveedor.getText();
                String estado = pantalla_proveedores.estadoProveedor.getText();
                String email = pantalla_proveedores.emailProveedor.getText();
                int id2 = Integer.valueOf(id);

                proveedores = new Proveedores(id2, nombre, telefono, ciudad, estado, email, "activo");
                if (proveedores.EditarProveedor()) {
                    JOptionPane.showMessageDialog(null, "Datos Modificados Correctamente");
                    pantalla_proveedores.EditarProveedores.setVisible(false);
                     pantalla_proveedores.setVisible(true);
                     actualizarTabla();

                }

            }
        });

    }

    private void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) pantalla_proveedores.TableProveedores.getModel();
        int filas = pantalla_proveedores.TableProveedores.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo.removeRow(0);
        }
         pantalla_proveedores.TableProveedores.setModel(new Proveedores().ConsultaProveedores(pantalla_proveedores.TableProveedores));
    }

    public void limpiarCamposAgregar() {
        pantalla_proveedores.nombreProveedorAg.setText("");
        pantalla_proveedores.telefono.setText("");
        pantalla_proveedores.ciudad.setText("");
        pantalla_proveedores.estado.setText("");
        pantalla_proveedores.email.setText("");
    }

}
