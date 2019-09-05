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
import Vista.Dialog_ProductosSolicitadosPorSucursal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_ProductosSolicitados {

    private Dialog_ProductosSolicitadosPorSucursal productosSolicitadosPorSucursal;
    private String clave;
    private DefaultTableModel modeloProducto;

    public Controlador_ProductosSolicitados(JFrame parent, String clave,String idSucursalLocal,String idSucursalSolicita) {
        this.clave = clave;
        productosSolicitadosPorSucursal = new Dialog_ProductosSolicitadosPorSucursal(parent, true);
        productosSolicitadosPorSucursal.setLocationRelativeTo(null);
        productosSolicitadosPorSucursal.setResizable(true);
        modeloProducto = (DefaultTableModel) productosSolicitadosPorSucursal.jTableProductosSolicitados.getModel();
        cargarTablaProductos();

        productosSolicitadosPorSucursal.jTableProductosSolicitados.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = productosSolicitadosPorSucursal.jTableProductosSolicitados.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / productosSolicitadosPorSucursal.jTableProductosSolicitados.getRowHeight();
                if (row < productosSolicitadosPorSucursal.jTableProductosSolicitados.getRowCount() && row >= 0 && colum < productosSolicitadosPorSucursal.jTableProductosSolicitados.getColumnCount() && colum >= 0) {
                    Object v = productosSolicitadosPorSucursal.jTableProductosSolicitados.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        if (b.getName().equals("D")) {
                            String codigo = productosSolicitadosPorSucursal.jTableProductosSolicitados.getValueAt(productosSolicitadosPorSucursal.jTableProductosSolicitados.getSelectedRow(), 0).toString();
                            String tipo = productosSolicitadosPorSucursal.jTableProductosSolicitados.getValueAt(productosSolicitadosPorSucursal.jTableProductosSolicitados.getSelectedRow(), 1).toString();
                            
                            switch (tipo) {
                                case "Medicamento":
                                    datosMedicamento(codigo);
                                    break;
                                case "Abarrote":
                                    datosAbarrote(codigo);
                                    break;
                                case "Perfume":
                                    datosPerfume(codigo);
                                    break;
                            }
                            productosSolicitadosPorSucursal.jDialogDetalles.setTitle("Detalles del Producto");
                            productosSolicitadosPorSucursal.jDialogDetalles.setBounds(540, 224, 290, 300);
                            productosSolicitadosPorSucursal.jDialogDetalles.setResizable(false);
                            productosSolicitadosPorSucursal.jDialogDetalles.setVisible(true);
                        } else {
                            int opcion = JOptionPane.showConfirmDialog(null, "¿DESEA ELIMINAR EL PRODUCTO SOLICITADO? ", "ELIMINAR PRODUCTO SOLICITADO", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (opcion == 0) {
                                modeloProducto.removeRow(productosSolicitadosPorSucursal.jTableProductosSolicitados.getSelectedRow());
                            }
                        }

                    }
                }

            }
        });
        
        productosSolicitadosPorSucursal.jButtonAceptar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                if(productosSolicitadosPorSucursal.jTableProductosSolicitados.getRowCount()==0){
//                    JOptionPane.showMessageDialog(productosSolicitadosPorSucursal, "NO EXISTEN PRODUCTOS ");
//                    return;
//                }
                if(new Alta().bajaProductos(clave,modeloProducto,idSucursalLocal,idSucursalSolicita)){
                    JOptionPane.showMessageDialog(productosSolicitadosPorSucursal, "LA SOLICITUD DE PRODUCTOS FUE ACEPTADA");
                     productosSolicitadosPorSucursal.setVisible(false);
                }else{
                    
                }
               
            }
        });
        
        productosSolicitadosPorSucursal.jButtonCancelar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              productosSolicitadosPorSucursal.setVisible(false);
            }
        });

        productosSolicitadosPorSucursal.setVisible(true);
    }

    public void datosMedicamento(String codigo) {
        Medicamento medicamento = new Medicamento();
        medicamento.obtenerMedicamento(codigo);
        productosSolicitadosPorSucursal.jLabelTipo.setText("Medicamento");
        productosSolicitadosPorSucursal.jLabel1.setText("Codigo: "+medicamento.getCodigoMedicamento());
        productosSolicitadosPorSucursal.jLabel2.setText("Sustancia: "+medicamento.getSustancia());
        productosSolicitadosPorSucursal.jLabel3.setText("Laboratorio: "+medicamento.getLaboratorio());
        productosSolicitadosPorSucursal.jLabel4.setText("Tipo: "+medicamento.getTipo());
    }

    public void datosAbarrote(String codigo) {
        Abarrotes abarrotes = new Abarrotes();
        abarrotes.obtenerAbarrote(codigo);
        productosSolicitadosPorSucursal.jLabelTipo.setText("Abarrote");
        productosSolicitadosPorSucursal.jLabel1.setText("Codigo: "+abarrotes.getCodigoAbarrote());
        productosSolicitadosPorSucursal.jLabel2.setText("Nombre: "+abarrotes.getNombre());
        productosSolicitadosPorSucursal.jLabel3.setText("Marca: "+abarrotes.getMarca());
         productosSolicitadosPorSucursal.jLabel4.setText("");
    }

    public void datosPerfume(String codigo) {
        Perfumes perfumes = new Perfumes();
        perfumes.obtenerPerfume(codigo);
        productosSolicitadosPorSucursal.jLabelTipo.setText("Perfume");
        productosSolicitadosPorSucursal.jLabel1.setText("Codigo: "+perfumes.getCodigoPerfume());
        productosSolicitadosPorSucursal.jLabel2.setText("Nombre: "+perfumes.getNombre());
        productosSolicitadosPorSucursal.jLabel3.setText("Marca: "+perfumes.getMarca());
        productosSolicitadosPorSucursal.jLabel4.setText("Laboratorio: "+perfumes.getLaboratorio());
    }

    public void cargarTablaProductos() {
        limpiar_jtables(productosSolicitadosPorSucursal.jTableProductosSolicitados);
        productosSolicitadosPorSucursal.jTableProductosSolicitados.setModel(new Alta().obtenerProductosSolicitadosPorSucursal(productosSolicitadosPorSucursal.jTableProductosSolicitados, clave));
    }

    public void limpiar_jtables(JTable jt) {
        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        int filas = jt.getRowCount();
        for (int i = 0; i < filas; i++) {
            modelo.removeRow(0);
        }
    }

}
