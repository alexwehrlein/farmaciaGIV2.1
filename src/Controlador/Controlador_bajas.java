package Controlador;

import Modelo.BajaMedicamento;
import Modelo.Medicamento;
import Modelo.RowsRenderer;
import Vista.Pantalla_Bajas;
import Vista.Pantalla_principal;
import alert.*;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import rojeru_san.RSPanelsSlider;

public class Controlador_bajas {

    Pantalla_Bajas pantalla_Bajas;
    BajaMedicamento bajaMedicamento;
    AlertSuccess alertSuccess;
    AlertError alertError;
    String nombreMedicamneto = null;
    private DefaultTableModel modelo;

    public Controlador_bajas(Pantalla_principal pantalla_principal, int idEmpleado, int idSucursal) {
        pantalla_Bajas = new Pantalla_Bajas();
        pantalla_Bajas.setVisible(true);
        pantalla_Bajas.setResizable(false);
        //pp.setSize(981, 474);
        pantalla_Bajas.setClosable(true);
        pantalla_Bajas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pantalla_Bajas.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pantalla_Bajas.setLocation((desktopSize.width - FrameSize.width) / 2, (desktopSize.height - FrameSize.height) / 2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pantalla_Bajas);
        pantalla_Bajas.txtCodigo.requestFocus();

        //btnBajas
        pantalla_Bajas.btnBajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pantalla_Bajas.btnBajas.isSelected()) {
                    pantalla_Bajas.btnBajas.setSelected(true);
                    pantalla_Bajas.btnAdd.setSelected(false);

                    pantalla_Bajas.rSPanelsSlider1.setPanelSlider(15, pantalla_Bajas.bajas, RSPanelsSlider.DIRECT.LEFT);
                }
            }
        });

        //btnListar
        pantalla_Bajas.btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pantalla_Bajas.btnAdd.isSelected()) {
                    pantalla_Bajas.btnAdd.setSelected(true);
                    pantalla_Bajas.btnBajas.setSelected(false);

                    pantalla_Bajas.rSPanelsSlider1.setPanelSlider(15, pantalla_Bajas.lista, RSPanelsSlider.DIRECT.LEFT);
                }
            }
        });

        //buscar el producto por el codigo 
        pantalla_Bajas.txtCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Bajas.txtCodigo.getText().isEmpty()) {
                        alertError = new AlertError("Ingresar un codigo");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Bajas.txtCodigo.requestFocus();
                        return;
                    }
                    BajaMedicamento producto = new BajaMedicamento();
                    producto.obtenerMedicamento(pantalla_Bajas.txtCodigo.getText(), idSucursal);
                    if (producto.getNombre() == null) {
                        alertError = new AlertError("No se encontro el producto");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Bajas.txtCodigo.requestFocus();
                        return;
                    }
                    pantalla_Bajas.txtExistencias.setText(String.valueOf(producto.getCantidad()));
                    nombreMedicamneto = producto.getNombre();
                    pantalla_Bajas.txtBajas.requestFocus();
                }
            }
        });

        //agregar producto a la tabla
        pantalla_Bajas.txtBajas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (pantalla_Bajas.txtBajas.getText().isEmpty()) {
                        alertError = new AlertError("Ingresar una cantidad");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Bajas.txtBajas.requestFocus();
                        return;
                    }

                    if (!isNumeric(pantalla_Bajas.txtBajas.getText())) {
                        alertError = new AlertError("Ingresar una cantidad");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Bajas.txtBajas.requestFocus();
                        return;
                    }

                    int cantidad = Integer.parseInt(pantalla_Bajas.txtBajas.getText());
                    int existencias = Integer.parseInt(pantalla_Bajas.txtExistencias.getText());
                    String codigo = pantalla_Bajas.txtCodigo.getText();

                    if (cantidad < 0) {
                        alertError = new AlertError("Ingresar una cantidad mayor a 0");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Bajas.txtBajas.requestFocus();
                        return;
                    }

                    if (cantidad > existencias) {
                        alertError = new AlertError("<html><body> Ingresar una cantidad mayor <br> o igual a las existencias</body></html>");
                        alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                        alertError.show();
                        pantalla_Bajas.txtBajas.requestFocus();
                        return;
                    }

                    DefaultTableModel model = (DefaultTableModel) pantalla_Bajas.jtableDatos.getModel();
                    if (model.getRowCount() > 0) {
                        for (int i = 0; model.getRowCount() < 10; i++) {
                            if (model.getValueAt(i, 0).toString().equals(codigo)) {
                                alertError = new AlertError("Ya se agrego ese medicamento");
                                alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                                alertError.show();
                                pantalla_Bajas.txtBajas.requestFocus();
                                return;
                            }
                        }
                    }

                    model.addRow(new Object[]{codigo, nombreMedicamneto, cantidad});
                    nombreMedicamneto = null;
                    pantalla_Bajas.txtBajas.setText("");
                    pantalla_Bajas.txtExistencias.setText("");
                    pantalla_Bajas.txtCodigo.setText("");
                    pantalla_Bajas.txtCodigo.requestFocus();
                }
            }
        });

        //guardar los productos de la tabla
        pantalla_Bajas.btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo = (DefaultTableModel) pantalla_Bajas.jtableDatos.getModel();
                bajaMedicamento = new BajaMedicamento();
                Boolean next = bajaMedicamento.GuadarListaBajas(modelo, idSucursal, idSucursal);
                if (next) {
                    Clear_Table();
                    pantalla_Bajas.txtCodigo.requestFocus();
                    alertSuccess = new AlertSuccess("<html><body>Los datos se guardaron con exito</body></html>");
                    alertSuccess.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertSuccess.show();

                } else {
                    alertError = new AlertError("<html><body>Error al guardar los datos</body></html>");
                    alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertError.show();

                }
            }
        });

        //boton para buscar el listado de medicamento para dar de baja
        pantalla_Bajas.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fecha_ini = pantalla_Bajas.fecha_ini.getDatoFecha();
                Date fecha_fin = pantalla_Bajas.fecha_fin.getDatoFecha();
                if (fecha_ini != null && fecha_fin != null) {
                    SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                    Clear_Table();
                    pantalla_Bajas.jTableDatos.setModel(new BajaMedicamento().cargarRegistros(pantalla_Bajas.jTableDatos, idSucursal, Formato.format(fecha_ini), Formato.format(fecha_fin)));
                    TableColumn columna = pantalla_Bajas.jTableDatos.getColumnModel().getColumn(4);
                    RowsRenderer rowsRenderer = new RowsRenderer(4);
                    columna.setCellRenderer(rowsRenderer);
                }
            }
        });

        //Dar de baja los productos seleccionados de la lista
        pantalla_Bajas.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fecha_ini = pantalla_Bajas.fecha_ini.getDatoFecha();
                Date fecha_fin = pantalla_Bajas.fecha_fin.getDatoFecha();
                SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
                modelo = (DefaultTableModel) pantalla_Bajas.jTableDatos.getModel();
                bajaMedicamento = new BajaMedicamento();
                boolean next = bajaMedicamento.bajaMedicamento(modelo, idSucursal);
                if (next) {
                    Clear_Table();
                    pantalla_Bajas.jTableDatos.setModel(new BajaMedicamento().cargarRegistros(pantalla_Bajas.jTableDatos, idSucursal, Formato.format(fecha_ini), Formato.format(fecha_fin)));
                    TableColumn columna = pantalla_Bajas.jTableDatos.getColumnModel().getColumn(4);
                    RowsRenderer rowsRenderer = new RowsRenderer(4);
                    columna.setCellRenderer(rowsRenderer);
                    alertSuccess = new AlertSuccess("<html><body>Los productos se dieron de baja</body></html>");
                    alertSuccess.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertSuccess.show();
                } else {
                    alertError = new AlertError("<html><body>Ocurrio un error.</body></html>");
                    alertError.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                    alertError.show();
                }
            }
        });

    }

    private void Clear_Table() {
        DefaultTableModel modelo1 = (DefaultTableModel) pantalla_Bajas.jtableDatos.getModel();
        DefaultTableModel modelo2 = (DefaultTableModel) pantalla_Bajas.jTableDatos.getModel();
        int filas = pantalla_Bajas.jtableDatos.getRowCount();
        int filas2 = pantalla_Bajas.jTableDatos.getRowCount();
        for (int i = 0; filas > i; i++) {
            modelo1.removeRow(0);
        }
        for (int i = 0; filas2 > i; i++) {
            modelo2.removeRow(0);
        }
    }

    private static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

}
