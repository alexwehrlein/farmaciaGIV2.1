package controlador;

import com.placeholder.PlaceHolder;
import com.sun.java.accessibility.util.AWTEventMonitor;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;
import modelo.Productos;
import modelo.Render;
import modelo.Ventas;
import tikect.TikectGasto;
import tikect.TikectInventario;
import tikect.TikectR;
import tikect.TikectVentas;
import tikect.TikectkArqueo;
import vista.Pantalla_Ventas;

/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_Pantalla_Ventas {

    Pantalla_Ventas pantalla_Ventas;
    Ventas ventas;
    Cliente cliente;
    TikectVentas tikectVentas;
    TikectR tikectR;
    TikectkArqueo arqueo;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloTabDescuento;
    private DefaultTableModel modeloPausarVenta;
    //  private float totalFinal = 0;
    private TableRowSorter trsFiltro;
    String idEmpleado, nombreEmpleado, cantidad, idCli = "1", turno, id, canProductos, rol;
    boolean netx = true;
    int folio;
    Productos productos;
    PlaceHolder placeHolder;
    String TotalVentaFinal;
    int pausaVenta = 1;
    float precioMayorista = 0;

    public Controlador_Pantalla_Ventas(String idEmpleado, String nombreEmpleado, String turnoEmpleado, String rol) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.turno = turnoEmpleado;
        this.rol = rol;
        pantalla_Ventas = new Pantalla_Ventas();
        pantalla_Ventas.setTitle("Punto de Venta");
        pantalla_Ventas.setVisible(true);
        pantalla_Ventas.setResizable(false);
        pantalla_Ventas.setLocationRelativeTo(null);
        placeHolder = new PlaceHolder(pantalla_Ventas.jTextFieldSustancia, "Busqueda por sustancias");
        // pantalla_Ventas.setSize(846, 645);
        // pantalla_Ventas.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        modelo = (DefaultTableModel) pantalla_Ventas.jTableProductosVenta.getModel();
        modeloTabDescuento = (DefaultTableModel) pantalla_Ventas.jTableDescuentoCliente.getModel();
        modeloPausarVenta = (DefaultTableModel) pantalla_Ventas.jTablePausaVenta.getModel();

        pantalla_Ventas.jTextFieldClienteVenta.setText("PUBLICO EN GENERAL");
        pantalla_Ventas.jTextFieldClienteVenta.setEditable(false);
        //pantalla_Ventas.jTextFieldTotalVenta.setEditable(false);
        //pantalla_Ventas.jTextFieldCambio.setEditable(false);
        pantalla_Ventas.jTableClientes.setModel(new Cliente().cargarTablaRegistroCliente(pantalla_Ventas.jTableClientes));
        trsFiltro = new TableRowSorter(pantalla_Ventas.jTableClientes.getModel());
        pantalla_Ventas.jTableClientes.setRowSorter(trsFiltro);
        pantalla_Ventas.jRadioButtonSelectNombre.setSelected(true);
        //pantalla_Ventas.jComboBoxAnti.setEnabled(false);
        pantalla_Ventas.jComboBoxGenerico.setEnabled(false);
        pantalla_Ventas.jComboBoxPatente.setEnabled(false);
        pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
        pantalla_Ventas.jLabelCantidadProductos.setText("0 ");
        pantalla_Ventas.jLabelNombreEmpleado.setText(nombreEmpleado);
        ventas = new Ventas();
        cliente = new Cliente();
        id = ventas.folio();
        folio = Integer.parseInt(id) + 1;
        pantalla_Ventas.jTextFieldFolio.setText(String.valueOf(folio));
        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
        tablaDes();
        pantalla_Ventas.btnMas.setMnemonic(KeyEvent.VK_PLUS);
        pantalla_Ventas.btnMas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila;
                try {
                    fila = pantalla_Ventas.jTableProductosVenta.getSelectedRow();
                    if (fila == -1) {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String codigo = (String) pantalla_Ventas.jTableProductosVenta.getValueAt(fila, 0);

                        canProductos = ventas.productoCero(codigo);
                        if (!canProductos.equals("0")) {

                            canProductos = ventas.productoCero(codigo);
                            int canProductosInt = Integer.parseInt(canProductos);

                            for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {

                                String art = pantalla_Ventas.jTableProductosVenta.getValueAt(i, 0).toString();
                                if (art.equals(codigo)) {
                                    int cantidadTabla = Integer.parseInt(pantalla_Ventas.jTableProductosVenta.getValueAt(i, 4).toString());
                                    if (cantidadTabla >= canProductosInt) {

                                        netx = false;
                                        break;
                                    } else {
                                        netx = true;
                                        break;
                                    }

                                } else {

                                    netx = true;
                                    break;
                                }

                            }

                            if (netx) {
                                agregarProducto(codigo, "1");//agrega producto a la tabla
                                agregarSubTotalporTipo();
                                if (!pantalla_Ventas.jTextFieldClienteVenta.getText().equals("PUBLICO EN GENERAL")) {
                                    for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                                        switch (modelo.getValueAt(i, 3).toString()) {
                                            case "PATENTE":
                                                pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                                break;
                                            case "GENÉRICO":
                                                pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                                break;

                                        }
                                    }

                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Ya no hay producto en existencia");
                                pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                                pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                                new Controlador_PantallaProductos(rol, turno);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "El producto esta agotado");
                            pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                            pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                            new Controlador_PantallaProductos(rol, turno);
                        }
                    }
                } catch (Exception ex) {
                }
            }
        });

        pantalla_Ventas.btnMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila;
                try {
                    fila = pantalla_Ventas.jTableProductosVenta.getSelectedRow();
                    if (fila == -1) {
                        JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String codigo = (String) pantalla_Ventas.jTableProductosVenta.getValueAt(fila, 0);
                        String piezas = (String) pantalla_Ventas.jTableProductosVenta.getValueAt(fila, 4);
                        if (Integer.parseInt(piezas) > 1) {
                            quitarProducto(codigo);
                            agregarSubTotalporTipo();
                        }

                    }
                } catch (Exception ex) {
                }
            }
        }
        );

        pantalla_Ventas.jComboBoxSustancia.addKeyListener(
                new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String sustancia = pantalla_Ventas.jComboBoxSustancia.getSelectedItem().toString();
                    String codigo = ventas.OctenerCodigo(sustancia);
                    canProductos = ventas.productoCero(codigo);
                    if (!canProductos.equals("0")) {

                        canProductos = ventas.productoCero(codigo);
                        int canProductosInt = Integer.parseInt(canProductos);

                        for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {

                            String art = pantalla_Ventas.jTableProductosVenta.getValueAt(i, 0).toString();
                            if (art.equals(codigo)) {
                                int cantidadTabla = Integer.parseInt(pantalla_Ventas.jTableProductosVenta.getValueAt(i, 4).toString());
                                if (cantidadTabla >= canProductosInt) {

                                    netx = false;
                                    break;
                                } else {
                                    netx = true;
                                    break;
                                }

                            } else {

                                netx = true;
                                break;
                            }

                        }

                        if (netx) {
                            agregarProducto2(codigo);//agrega producto a la tabla
                            agregarSubTotalporTipo();
                            if (!pantalla_Ventas.jTextFieldClienteVenta.getText().equals("PUBLICO EN GENERAL")) {
                                for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                                    switch (modelo.getValueAt(i, 3).toString()) {
                                        case "PATENTE":
                                            pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                            break;
                                        case "GENÉRICO":
                                            pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                            break;

                                    }
                                }

                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Ya no hay producto en existencia");
                            pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                            pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                            new Controlador_PantallaProductos(rol, turno);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "El producto esta agotado");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        new Controlador_PantallaProductos(rol, turno);
                    }
                }

            }

        }
        );

        pantalla_Ventas.jButtonVentaM.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                pantalla_Ventas.jDialogVentaM.setTitle("Farmacia GI");
                pantalla_Ventas.jDialogVentaM.setBounds(249, 154, 460, 240);
                pantalla_Ventas.jDialogVentaM.setResizable(false);
                pantalla_Ventas.jDialogVentaM.setVisible(true);
                pantalla_Ventas.jTextFieldCantidadM.setText("");
                pantalla_Ventas.jTextFieldCodigoM.setText("");
                pantalla_Ventas.jTextFieldCantidadM.requestFocus();
            }
        }
        );

        pantalla_Ventas.btnPrecioMayoreo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Ventas.jDialogPrecioMayoreo.setBounds(249, 154, 388, 199);
                pantalla_Ventas.jDialogPrecioMayoreo.setResizable(false);
                pantalla_Ventas.jDialogPrecioMayoreo.setVisible(true);
                pantalla_Ventas.jTextFieldCodigo.setText("");
                pantalla_Ventas.jTextFieldPrecio.setText("");
                pantalla_Ventas.jComboBoxDes.setSelectedItem("0");
                pantalla_Ventas.jTextFieldCodigo.requestFocus();
            }
        });

        pantalla_Ventas.jTextFieldCodigo.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String codigo = pantalla_Ventas.jTextFieldCodigo.getText();

                    if (codigo.isEmpty()) {
                        //JOptionPane.showMessageDialog(null, "CAMPO VACIO..", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        return;
                    }

                    if (!codigo.matches("^\\d+$")) {
                        JOptionPane.showMessageDialog(null, "CODIGO INCORRECTO", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        return;
                    }
                    if (!new Ventas().existeRegistroProducto(codigo)) {
                        JOptionPane.showMessageDialog(null, "EL PRODUCTO NO EXISTE", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        return;

                    }
                    
                    ventas = new Ventas(Long.parseLong(codigo));
                    precioMayorista = Float.parseFloat(ventas.precioProducto());
                    pantalla_Ventas.jTextFieldPrecio.setText(String.format(Locale.US, "%.2f", precioMayorista));
                }
            }
        });
        
        pantalla_Ventas.jComboBoxDes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = pantalla_Ventas.jTextFieldCodigo.getText();
                String precio = pantalla_Ventas.jTextFieldPrecio.getText();
                if (codigo.isEmpty() && precio.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "No deje campos en blancooo","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int porcentaje = Integer.parseInt(pantalla_Ventas.jComboBoxDes.getSelectedItem().toString());
                String total = sacarDesc(porcentaje, precioMayorista);//con descuento
                pantalla_Ventas.jTextFieldPrecio.setText(total);
                
            }
        });
        

        pantalla_Ventas.jTextFieldCodigoM.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e
            ) {
                String codigo = pantalla_Ventas.jTextFieldCodigoM.getText();
                int piezas = Integer.parseInt(pantalla_Ventas.jTextFieldCantidadM.getText());

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (codigo.isEmpty()) {
                        //JOptionPane.showMessageDialog(null, "CAMPO VACIO..", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        return;
                    }

                    if (!codigo.matches("^\\d+$")) {
                        JOptionPane.showMessageDialog(null, "CODIGO INCORRECTO", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        return;
                    }
                    if (!new Ventas().existeRegistroProducto(codigo)) {
                        JOptionPane.showMessageDialog(null, "EL PRODUCTO NO EXISTE", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        return;

                    }

                    canProductos = ventas.productoCero(codigo);
                    if (!canProductos.equals("0")) {
                        if (Integer.parseInt(canProductos) >= piezas) {

                            canProductos = ventas.productoCero(codigo);
                            int canProductosInt = Integer.parseInt(canProductos);//productos en existencia

                            for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {

                                String art = pantalla_Ventas.jTableProductosVenta.getValueAt(i, 0).toString();
                                if (art.equals(codigo)) {
                                    int cantidadTabla = Integer.parseInt(pantalla_Ventas.jTableProductosVenta.getValueAt(i, 4).toString());
                                    if (cantidadTabla > canProductosInt) {

                                        netx = false;
                                        break;
                                    } else {
                                        netx = true;
                                        break;
                                    }

                                } else {

                                    netx = true;
                                    break;
                                }

                            }

                            if (netx) {
                                agregarProducto(codigo, String.valueOf(piezas));//agrega producto a la tabla
                                agregarSubTotalporTipo();
                                if (!pantalla_Ventas.jTextFieldClienteVenta.getText().equals("PUBLICO EN GENERAL")) {
                                    for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                                        switch (modelo.getValueAt(i, 3).toString()) {
                                            case "PATENTE":
                                                pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                                break;
                                            case "GENÉRICO":
                                                pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                                break;

                                        }
                                    }

                                }
                                pantalla_Ventas.jTextFieldCantidadM.setText("");
                                pantalla_Ventas.jTextFieldCodigoM.setText("");
                                pantalla_Ventas.jDialogVentaM.setVisible(false);

                            } else {
                                JOptionPane.showMessageDialog(null, "Ya no hay producto en existencia");
                                pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                                pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                                new Controlador_PantallaProductos(rol, turno);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Solo quedan en existencia " + canProductos);
                            pantalla_Ventas.jTextFieldCodigoM.setText("");
                            pantalla_Ventas.jTextFieldCodigoM.requestFocus();
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "El producto esta agotado");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        new Controlador_PantallaProductos(rol, turno);
                    }
                }
            }
        }
        );

        pantalla_Ventas.jTextFieldFolioProductoVenta.addKeyListener(
                new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    String codigo = pantalla_Ventas.jTextFieldFolioProductoVenta.getText();

                    if (codigo.isEmpty()) {
                        //JOptionPane.showMessageDialog(null, "CAMPO VACIO..", "ERROR", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        return;
                    }

                    if (!codigo.matches("^\\d+$")) {
                        JOptionPane.showMessageDialog(null, "CODIGO INCORRECTO", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        return;
                    }
                    if (!new Ventas().existeRegistroProducto(codigo)) {
                        JOptionPane.showMessageDialog(null, "EL PRODUCTO NO EXISTE", "ERROR..", JOptionPane.ERROR_MESSAGE);
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        return;

                    }

                    canProductos = ventas.productoCero(codigo);
                    if (!canProductos.equals("0")) {

                        canProductos = ventas.productoCero(codigo);
                        int canProductosInt = Integer.parseInt(canProductos);

                        for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {

                            String art = pantalla_Ventas.jTableProductosVenta.getValueAt(i, 0).toString();
                            if (art.equals(codigo)) {
                                int cantidadTabla = Integer.parseInt(pantalla_Ventas.jTableProductosVenta.getValueAt(i, 4).toString());
                                if (cantidadTabla >= canProductosInt) {

                                    netx = false;
                                    break;
                                } else {
                                    netx = true;
                                    break;
                                }

                            } else {

                                netx = true;
                                break;
                            }

                        }

                        if (netx) {
                            agregarProducto(codigo, "1");//agrega producto a la tabla
                            agregarSubTotalporTipo();
                            if (!pantalla_Ventas.jTextFieldClienteVenta.getText().equals("PUBLICO EN GENERAL")) {
                                for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                                    switch (modelo.getValueAt(i, 3).toString()) {
                                        case "PATENTE":
                                            pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                            break;
                                        case "GENÉRICO":
                                            pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                            break;

                                    }
                                }

                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Ya no hay producto en existencia");
                            pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                            pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                            new Controlador_PantallaProductos(rol, turno);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "El producto esta agotado");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        new Controlador_PantallaProductos(rol, turno);
                    }

                    //pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                }
            }

        }
        );

        pantalla_Ventas.jTextFieldSustancia.addKeyListener(
                new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e
            ) {
                String sustancia = pantalla_Ventas.jTextFieldSustancia.getText();
                productos = new Productos();
                pantalla_Ventas.jComboBoxSustancia.removeAllItems();
                ArrayList<Productos> datos = productos.Sustancia(sustancia);
                for (Productos r : datos) {
                    pantalla_Ventas.jComboBoxSustancia.addItem(r.getSustancias());

                }

            }
        }
        );

        pantalla_Ventas.jButtonAgregar.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {

                if (pantalla_Ventas.jComboBoxSustancia.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(null, "NO HAY PRODUCTO QUE AGREGAR", "ERROR..", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String sustancia = pantalla_Ventas.jComboBoxSustancia.getSelectedItem().toString();
                String codigo = ventas.OctenerCodigo(sustancia);
                canProductos = ventas.productoCero(codigo);
                if (!canProductos.equals("0")) {

                    canProductos = ventas.productoCero(codigo);
                    int canProductosInt = Integer.parseInt(canProductos);

                    for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {

                        String art = pantalla_Ventas.jTableProductosVenta.getValueAt(i, 0).toString();
                        if (art.equals(codigo)) {
                            int cantidadTabla = Integer.parseInt(pantalla_Ventas.jTableProductosVenta.getValueAt(i, 4).toString());
                            if (cantidadTabla >= canProductosInt) {

                                netx = false;
                                break;
                            } else {
                                netx = true;
                                break;
                            }

                        } else {

                            netx = true;
                            break;
                        }

                    }

                    if (netx) {
                        agregarProducto2(codigo);//agrega producto a la tabla
                        agregarSubTotalporTipo();
                        if (!pantalla_Ventas.jTextFieldClienteVenta.getText().equals("PUBLICO EN GENERAL")) {
                            for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                                switch (modelo.getValueAt(i, 3).toString()) {
                                    case "PATENTE":
                                        pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                        break;
                                    case "GENÉRICO":
                                        pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                        break;

                                }
                            }

                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Ya no hay producto en existencia");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                        pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                        new Controlador_PantallaProductos(rol, turno);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El producto esta agotado");
                    pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                    pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                    new Controlador_PantallaProductos(rol, turno);
                }

            }
        }
        );

        pantalla_Ventas.jTextFieldFolioProductoVenta.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_F9) {

                }
            }

        }
        );

        pantalla_Ventas.jTableProductosVenta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F9) {
                    if (pantalla_Ventas.jTableProductosVenta.getSelectedRowCount() != 1) {
                        int[] rows = pantalla_Ventas.jTableProductosVenta.getSelectedRows();
                        for (int i = rows.length - 1; i >= 0; i--) {
                            modelo.removeRow(rows[i]);
                        }
                    } else {
                        modelo.removeRow(pantalla_Ventas.jTableProductosVenta.getSelectedRow());
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    agregarTotal();
                    agregarSubTotalporTipo();
                }
            }
        });

        pantalla_Ventas.btnCambioCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pantalla_Ventas.jButtonVenta.requestFocus();

                }
            }
        });

        pantalla_Ventas.jButtonVenta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    ventaRegistrar();
                }
            }
        });

        pantalla_Ventas.jTableProductosVenta.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {

                int colum = pantalla_Ventas.jTableProductosVenta.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_Ventas.jTableProductosVenta.getRowHeight();
                if (row < pantalla_Ventas.jTableProductosVenta.getRowCount() && row >= 0 && colum < pantalla_Ventas.jTableProductosVenta.getColumnCount() && colum >= 0) {
                    Object v = pantalla_Ventas.jTableProductosVenta.getValueAt(row, colum);
                    if (v instanceof JButton) {
                        ((JButton) v).doClick();
                        JButton b = (JButton) v;
                        int m = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR EL PRODUCTO ", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (m == 0) {
                            modelo.removeRow(pantalla_Ventas.jTableProductosVenta.getSelectedRow());
                            agregarTotal();
                            agregarSubTotalporTipo();
                            pago();
                            pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                            if (pantalla_Ventas.jTableProductosVenta.getRowCount() > 0) {
                                //String a = String.valueOf(pantalla_Ventas.jComboBoxAnti.getSelectedItem());
                                String p = String.valueOf(pantalla_Ventas.jComboBoxPatente.getSelectedItem());
                                String g = String.valueOf(pantalla_Ventas.jComboBoxGenerico.getSelectedItem());
                                for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                                    // pantalla_Ventas.jComboBoxAnti.setEnabled(false);
                                    pantalla_Ventas.jComboBoxPatente.setEnabled(false);
                                    pantalla_Ventas.jComboBoxGenerico.setEnabled(false);
                                    //pantalla_Ventas.jComboBoxAnti.setSelectedItem("0");
                                    pantalla_Ventas.jComboBoxPatente.setSelectedItem("0");
                                    pantalla_Ventas.jComboBoxGenerico.setSelectedItem("0");
                                    switch (modelo.getValueAt(i, 3).toString()) {
                                        case "PATENTE":
                                            pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                            pantalla_Ventas.jComboBoxPatente.setSelectedItem(p);
                                            break;
                                        case "GENÉRICO":
                                            pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                            pantalla_Ventas.jComboBoxGenerico.setSelectedItem(g);
                                            break;

                                    }
                                }
                            }
                        }
                    } else {
                        //   agregarTotal();
                    }
                }

            }

        });

        pantalla_Ventas.btnCambioCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                float t = obtenerT();
                double cambioVentaD;
                if (!pantalla_Ventas.btnCambioCliente.getText().matches("^\\d+\\.?\\d?\\d?") && pantalla_Ventas.btnCambioCliente.getText().length() > 0) {
                    pantalla_Ventas.btnCambioCliente.setText(pantalla_Ventas.btnCambioCliente.getText().substring(0, pantalla_Ventas.btnCambioCliente.getText().length() - 1));
                }
                if (pantalla_Ventas.btnCambioCliente.getText().length() > 0) {
                    float cantidadIngresada = Float.parseFloat(pantalla_Ventas.btnCambioCliente.getText());
                    if (t < cantidadIngresada) {
                        String cambioVenta = String.format(Locale.US, "%.2f", cantidadIngresada - t);

                        float decNumbert = Float.parseFloat(cambioVenta.substring(cambioVenta.indexOf('.')));

                        pantalla_Ventas.jTextFieldCambio.setText("" + cambioVenta);

                    } else {
                        pantalla_Ventas.jTextFieldCambio.setText("0.00");
                    }
                } else {
                    pantalla_Ventas.jTextFieldCambio.setText("");
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 128 || e.getKeyCode() == 129 || e.getKeyCode() == 130 || e.getKeyCode() == 135) {
                    e.consume();
                }
            }
        });

        pantalla_Ventas.jButtonClienteDescuento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modelo.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "AGREGUE PRODUCTOS", "ERROR..", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pantalla_Ventas.jDialogClienteVentas.setTitle("Clientes");
                pantalla_Ventas.jDialogClienteVentas.setBounds(449, 154, 500, 470);
                pantalla_Ventas.jDialogClienteVentas.setResizable(false);
                pantalla_Ventas.jDialogClienteVentas.setVisible(true);

            }
        });
        pantalla_Ventas.jButtonPausarVenta1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modelo.getRowCount() <= 0) {
                    JOptionPane.showMessageDialog(null, "AGREGUE PRODUCTOS", "ERROR..", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ventas.pausarVenta(modelo, pausaVenta)) {
                    pantalla_Ventas.jTablePausaVenta.setModel(modeloPausarVenta);
                    String[] datos = new String[3];
                    datos[0] = String.valueOf(pausaVenta);
                    datos[1] = modelo.getValueAt(0, 1).toString();
                    datos[2] = pantalla_Ventas.jLabelSubtotalVenta.getText();
                    modeloPausarVenta.addRow(datos);

                    pausaVenta = pausaVenta + 1;
                    pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                    pantalla_Ventas.jTextFieldClienteVenta.setText("PUBLICO EN GENERAL");
                    modelo.setRowCount(0);
                    pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
                    // pantalla_Ventas.jComboBoxAnti.setEnabled(false);
                    pantalla_Ventas.jComboBoxGenerico.setEnabled(false);
                    pantalla_Ventas.jComboBoxPatente.setEnabled(false);
                    //pantalla_Ventas.jComboBoxAnti.setSelectedItem("0");
                    pantalla_Ventas.jComboBoxGenerico.setSelectedItem("0");
                    pantalla_Ventas.jComboBoxPatente.setSelectedItem("0");
                    tablaDes();
                    pantalla_Ventas.jTextFieldTotalVenta.setText("");
                    pantalla_Ventas.btnCambioCliente.setText("");
                    pantalla_Ventas.jLabelCantidadProductos.setText("0");
                    pantalla_Ventas.jTextFieldCambio.setText("");
                    pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
                    //  totalFinal=0;
                    cantidad = "";
                    idCli = "1";
                    pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                    pantalla_Ventas.jTextFieldSustancia.setText("");
                    pantalla_Ventas.jComboBoxSustancia.removeAllItems();
                    placeHolder = new PlaceHolder(pantalla_Ventas.jTextFieldSustancia, "Busqueda por sustancias");

                }

            }
        });

        pantalla_Ventas.jTextFieldBuscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String cadena = pantalla_Ventas.jTextFieldBuscarCliente.getText();
                pantalla_Ventas.jTextFieldBuscarCliente.setText(cadena);
                pantalla_Ventas.repaint();
                //MODIFIQUE ESTA PARA QUE EL FILTO SE CASE INSENSITIVE
                if (pantalla_Ventas.jRadioButtonSelectId.isSelected()) {
                    trsFiltro.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_Ventas.jTextFieldBuscarCliente.getText(), 0));
                } else {
                    trsFiltro.setRowFilter(RowFilter.regexFilter("(?i)" + pantalla_Ventas.jTextFieldBuscarCliente.getText(), 1));
                }
            }

        });

        pantalla_Ventas.jButtonSeleccionarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = pantalla_Ventas.jTableClientes.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                idCli = pantalla_Ventas.jTableClientes.getValueAt(row, 0).toString();
                pantalla_Ventas.jTextFieldClienteVenta.setText(pantalla_Ventas.jTableClientes.getValueAt(row, 1).toString());

                for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                    switch (modelo.getValueAt(i, 3).toString()) {
                        case "PATENTE":
                            pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                            break;
                        case "GENÉRICO":
                            pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                            break;

                    }
                }

                // new Controlador_CU2_LevantarPedido(pantalla).agregarProducto();
                pantalla_Ventas.jDialogClienteVentas.dispose();

            }
        });

        pantalla_Ventas.jComboBoxGenerico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int porcentaje = Integer.parseInt(pantalla_Ventas.jComboBoxGenerico.getSelectedItem().toString());
                float totalTipoAnti = Float.parseFloat(modeloTabDescuento.getValueAt(1, 2).toString());//sin descuento
                modeloTabDescuento.setValueAt(sacarDesc(porcentaje, totalTipoAnti), 1, 3);//con descuento
                pantalla_Ventas.jTextFieldTotalVenta.setText(String.format(Locale.US, "%.2f", obtenerT()));
                pantalla_Ventas.jLabelSubtotalVenta.setText(String.format(Locale.US, "%.2f", obtenerT()));
            }
        });

        pantalla_Ventas.jComboBoxPatente.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int porcentaje = Integer.parseInt(pantalla_Ventas.jComboBoxPatente.getSelectedItem().toString());
                float totalTipoAnti = Float.parseFloat(modeloTabDescuento.getValueAt(0, 2).toString());
                modeloTabDescuento.setValueAt(sacarDesc(porcentaje, totalTipoAnti), 0, 3);
                //JOptionPane.showMessageDialog(null, sacarDesc(porcentaje, totalTipoAnti));
                pantalla_Ventas.jTextFieldTotalVenta.setText(String.format(Locale.US, "%.2f", obtenerT()));
                pantalla_Ventas.jLabelSubtotalVenta.setText(String.format(Locale.US, "%.2f", obtenerT()));
            }
        });

        pantalla_Ventas.jButtonRegistrarVentaa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                float total = obtenerT();
                pantalla_Ventas.jTextFieldTotalVenta.setText((String.format(Locale.US, "%.2f", total)));
                //pantalla_Ventas.btnCambioCliente.requestFocusInWindow();
                pantalla_Ventas.jDialogCobro.setTitle("Cobro");
                pantalla_Ventas.jDialogCobro.setBounds(249, 154, 626, 440);
                pantalla_Ventas.jDialogCobro.setResizable(false);
                pantalla_Ventas.jDialogCobro.setVisible(true);
                pantalla_Ventas.btnCambioCliente.requestFocus();

            }
        });

        pantalla_Ventas.jButtonCancelarVenta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int m = JOptionPane.showConfirmDialog(null, "DESEA CANCELAR LA VENTA ", "CONFIRMAR", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (m == 0) {
                    pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                    pantalla_Ventas.jTextFieldClienteVenta.setText("PUBLICO EN GENERAL");
                    modelo.setRowCount(0);
                    pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
                    // pantalla_Ventas.jComboBoxAnti.setEnabled(false);
                    pantalla_Ventas.jComboBoxGenerico.setEnabled(false);
                    pantalla_Ventas.jComboBoxPatente.setEnabled(false);
                    //pantalla_Ventas.jComboBoxAnti.setSelectedItem("0");
                    pantalla_Ventas.jComboBoxGenerico.setSelectedItem("0");
                    pantalla_Ventas.jComboBoxPatente.setSelectedItem("0");
                    tablaDes();
                    pantalla_Ventas.jTextFieldTotalVenta.setText("");
                    pantalla_Ventas.btnCambioCliente.setText("");
                    pantalla_Ventas.jLabelCantidadProductos.setText("0");
                    pantalla_Ventas.jTextFieldCambio.setText("");
                    pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
                    //  totalFinal=0;
                    cantidad = "";
                    idCli = "1";
                    pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                    pantalla_Ventas.jTextFieldSustancia.setText("");
                    pantalla_Ventas.jComboBoxSustancia.removeAllItems();
                    placeHolder = new PlaceHolder(pantalla_Ventas.jTextFieldSustancia, "Busqueda por sustancias");
                }
            }
        });

        pantalla_Ventas.jTablePausaVenta.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent Mouse_evt) {
                int colum = pantalla_Ventas.jTablePausaVenta.getColumnModel().getColumnIndexAtX(Mouse_evt.getX());
                int row = Mouse_evt.getY() / pantalla_Ventas.jTablePausaVenta.getRowHeight();
                if (row < pantalla_Ventas.jTablePausaVenta.getRowCount() && row >= 0 && colum < pantalla_Ventas.jTablePausaVenta.getColumnCount() && colum >= 0) {
                    int filaSeleccionada = pantalla_Ventas.jTablePausaVenta.getSelectedRow();
                    if (modelo.getRowCount() <= 0) {
                        String ID = modeloPausarVenta.getValueAt(filaSeleccionada, 0).toString();
                        ArrayList<Ventas> datos = ventas.ventaPausada(ID);
                        for (Ventas r : datos) {
                            for (int i = 0; i < r.getPiezas(); i++) {
                                ingresarVentaPausada(Long.toString(r.getCodigo()));
                            }

                        }
                    }
                }
            }

        });

        pantalla_Ventas.jButtonRTikect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Ventas.jDialogTikect.setTitle("");
                pantalla_Ventas.jDialogTikect.setBounds(249, 154, 490, 219);
                pantalla_Ventas.jDialogTikect.setResizable(false);
                pantalla_Ventas.jDialogTikect.setVisible(true);
                pantalla_Ventas.jDialogTikect.setModal(true);
                pantalla_Ventas.folioTikect.requestFocus();
            }
        });

        pantalla_Ventas.folioTikect.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pantalla_Ventas.folioTikect.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "INGRESE UN FOLIO", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!pantalla_Ventas.folioTikect.getText().matches("^[0-9]+$")) {
                        JOptionPane.showMessageDialog(null, "INGRESE UN FOLIO valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String folio = pantalla_Ventas.folioTikect.getText();

                    String[] arr = ventas.infoTikect(folio);
                    List<List<String>> productos = ventas.infoTikectProductos(folio);

                    tikectR = new TikectR();
                    tikectR.tikectR(arr, productos);
                }
            }
        });

        pantalla_Ventas.jButtonEliminarVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pausaVenta = 1;
                modeloPausarVenta.setRowCount(0);
                ventas.eliminarVentaPausada();
                pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
            }
        });

    }

    public void ingresarVentaPausada(String codigo) {

        canProductos = ventas.productoCero(codigo);
        if (!canProductos.equals("0")) {

            canProductos = ventas.productoCero(codigo);
            int canProductosInt = Integer.parseInt(canProductos);

            for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {

                String art = pantalla_Ventas.jTableProductosVenta.getValueAt(i, 0).toString();
                if (art.equals(codigo)) {
                    int cantidadTabla = Integer.parseInt(pantalla_Ventas.jTableProductosVenta.getValueAt(i, 4).toString());
                    if (cantidadTabla >= canProductosInt) {

                        netx = false;
                        break;
                    } else {
                        netx = true;
                        break;
                    }

                } else {

                    netx = true;
                    break;
                }

            }

            if (netx) {
                agregarProducto2(codigo);//agrega producto a la tabla
                agregarSubTotalporTipo();
                if (!pantalla_Ventas.jTextFieldClienteVenta.getText().equals("PUBLICO EN GENERAL")) {
                    for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
                        switch (modelo.getValueAt(i, 3).toString()) {
                            case "PATENTE":
                                pantalla_Ventas.jComboBoxPatente.setEnabled(true);
                                break;
                            case "GENÉRICO":
                                pantalla_Ventas.jComboBoxGenerico.setEnabled(true);
                                break;

                        }
                    }

                }

            } else {
                JOptionPane.showMessageDialog(null, "Ya no hay producto en existencia");
                pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
                pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
                new Controlador_PantallaProductos(rol, turno);
            }

        } else {
            JOptionPane.showMessageDialog(null, "El producto esta agotado");
            pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
            pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
            new Controlador_PantallaProductos(rol, turno);

        }

        //pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
    }

    public void pago() {
        float t = obtenerT();

        if (pantalla_Ventas.btnCambioCliente.getText().length() > 0) {
            if (pantalla_Ventas.jTextFieldCambio.getText().matches("^[0-9]+([.])?([0-9]+)?$")) {
                float cantidadIngresada = Float.parseFloat(pantalla_Ventas.btnCambioCliente.getText());
                if (t < cantidadIngresada) {
                    pantalla_Ventas.jTextFieldCambio.setText("$ " + (String.format(Locale.US, "%.2f", cantidadIngresada - t)));
                } else {
                    pantalla_Ventas.jTextFieldCambio.setText("$ ");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el cambio correctamente", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public float obtenerT() {
        return Float.parseFloat(modeloTabDescuento.getValueAt(0, 3).toString()) + Float.parseFloat(modeloTabDescuento.getValueAt(1, 3).toString()) + Float.parseFloat(modeloTabDescuento.getValueAt(2, 3).toString()) + Float.parseFloat(modeloTabDescuento.getValueAt(3, 3).toString()) + Float.parseFloat(modeloTabDescuento.getValueAt(4, 3).toString());
    }

    public void enviarDatosTikect() {
        int porcentajeGenerico = Integer.parseInt(pantalla_Ventas.jComboBoxGenerico.getSelectedItem().toString());
        int porcentajePatente = Integer.parseInt(pantalla_Ventas.jComboBoxPatente.getSelectedItem().toString());
        int filas = 0;
        String folio = pantalla_Ventas.jTextFieldFolio.getText();
        String empleada = pantalla_Ventas.jLabelNombreEmpleado.getText();
        String cliente = pantalla_Ventas.jTextFieldClienteVenta.getText();
        int piezas = Integer.parseInt(pantalla_Ventas.jLabelCantidadProductos.getText());
        String total = pantalla_Ventas.jTextFieldTotalVenta.getText();
        String pago = pantalla_Ventas.btnCambioCliente.getText();
        String cambio = pantalla_Ventas.jTextFieldCambio.getText();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            filas = filas + 1;
        }

        String[] prod = new String[filas]; //String[3];
        String[] prec = new String[filas];
        String[] cant = new String[filas];
        String[] impor = new String[filas];

        for (int i = 0; i < modelo.getRowCount(); i++) {
            float descuentoGenerico = (float) (Float.parseFloat(modelo.getValueAt(i, 6).toString()) * porcentajeGenerico / 100.0);
            float descuentoPatente = (float) (Float.parseFloat(modelo.getValueAt(i, 6).toString()) * porcentajeGenerico / 100.0);
            String tipo_m = modelo.getValueAt(i, 3).toString();
            prod[i] = modelo.getValueAt(i, 1).toString();
            prec[i] = modelo.getValueAt(i, 5).toString();
            cant[i] = modelo.getValueAt(i, 4).toString();
            if (tipo_m == "GENÉRICO") {
                float importe = Float.parseFloat(modelo.getValueAt(i, 6).toString()) - descuentoGenerico;
                impor[i] = String.format(Locale.US, "%.2f", importe);
            } else if (tipo_m == "PATENTE") {
                float importe = Float.parseFloat(modelo.getValueAt(i, 6).toString()) - descuentoPatente;
                impor[i] = String.format(Locale.US, "%.2f", importe);
            } else {
                impor[i] = modelo.getValueAt(i, 6).toString();
            }

        }
        for (int i = 0; i < impor.length; i++) {
            System.out.println(impor[i]);
        }
        tikectVentas = new TikectVentas();
        tikectVentas.tikectVentas(folio, empleada, cliente, piezas, total, pago, cambio, prod, prec, cant, impor);

    }

    public void agregarSubTotalporTipo() {
        float totalTipoConsulta = 0;
        float totalTipoPatente = 0;
        float totalTipoGenerico = 0;
        float totalTipoAbarrotes = 0;
        float totalTipoPerfumeria = 0;
        float subTotal = 0;
        int pzsConsulta = 0;
        int pzsPatente = 0;
        int pzsGenerico = 0;
        int pzsAbarrotes = 0;
        int pzsPerfumeria = 0;
        for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
            switch (modelo.getValueAt(i, 3).toString()) {
                case "PATENTE":
                    totalTipoPatente += Float.parseFloat(modelo.getValueAt(i, 6).toString());
                    pzsPatente += Integer.parseInt(modelo.getValueAt(i, 4).toString());
                    break;
                case "GENÉRICO":
                    totalTipoGenerico += Float.parseFloat(modelo.getValueAt(i, 6).toString());
                    pzsGenerico += Integer.parseInt(modelo.getValueAt(i, 4).toString());
                    break;
                case "CONSULTA":
                    totalTipoConsulta += Float.parseFloat(modelo.getValueAt(i, 6).toString());
                    pzsConsulta += Integer.parseInt(modelo.getValueAt(i, 4).toString());
                    break;
                case "ABARROTES":
                    totalTipoAbarrotes += Float.parseFloat(modelo.getValueAt(i, 6).toString());
                    pzsAbarrotes += Integer.parseInt(modelo.getValueAt(i, 4).toString());
                    break;
                case "PERFUMERIA":
                    totalTipoPerfumeria += Float.parseFloat(modelo.getValueAt(i, 6).toString());
                    pzsPerfumeria += Integer.parseInt(modelo.getValueAt(i, 4).toString());
                    break;

            }
        }
        subTotal = totalTipoConsulta + totalTipoGenerico + totalTipoPatente + totalTipoAbarrotes + totalTipoPerfumeria;

        pantalla_Ventas.jLabelSubtotalVenta.setText("$" + String.format(Locale.US, "%.2f", subTotal));
        cantidad = String.valueOf(pzsConsulta + pzsGenerico + pzsPatente + pzsAbarrotes + pzsPerfumeria);
        pantalla_Ventas.jLabelCantidadProductos.setText(String.valueOf(pzsConsulta + pzsGenerico + pzsPatente + pzsAbarrotes + pzsPerfumeria));
        //  totalFinal = subTotal;

        modeloTabDescuento.setValueAt("PATENTE", 0, 0);
        modeloTabDescuento.setValueAt("GENÉRICO", 1, 0);
        modeloTabDescuento.setValueAt("CONSULTA", 2, 0);
        modeloTabDescuento.setValueAt("ABARROTES", 3, 0);
        modeloTabDescuento.setValueAt("PREFUMERIA", 4, 0);

        modeloTabDescuento.setValueAt("" + pzsPatente, 0, 1);
        modeloTabDescuento.setValueAt("" + pzsGenerico, 1, 1);
        modeloTabDescuento.setValueAt("" + pzsConsulta, 2, 1);
        modeloTabDescuento.setValueAt("" + pzsAbarrotes, 3, 1);
        modeloTabDescuento.setValueAt("" + pzsPerfumeria, 4, 1);

        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoPatente), 0, 2);
        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoGenerico), 1, 2);
        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoConsulta), 2, 2);
        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoAbarrotes), 3, 2);
        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoPerfumeria), 4, 2);

        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoConsulta), 2, 3);
        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoAbarrotes), 3, 3);
        modeloTabDescuento.setValueAt("" + String.format(Locale.US, "%.2f", totalTipoPerfumeria), 4, 3);

        // modeloTabDescuento.setValueAt("" + sacarDesc(Integer.parseInt(pantalla_Ventas.jComboBoxAnti.getSelectedItem().toString()), totalTipoAntibiotico), 0, 3);
        modeloTabDescuento.setValueAt("" + sacarDesc(Integer.parseInt(pantalla_Ventas.jComboBoxPatente.getSelectedItem().toString()), totalTipoPatente), 0, 3);
        modeloTabDescuento.setValueAt("" + sacarDesc(Integer.parseInt(pantalla_Ventas.jComboBoxGenerico.getSelectedItem().toString()), totalTipoGenerico), 1, 3);
        //pantalla_Ventas.jTextFieldTotalVenta.setText(String.format("%.2f", obtenerT()));
        TotalVentaFinal = String.format(Locale.US, "%.2f", obtenerT());
        pago();
    }

    public String sacarDesc(int porcentaje, float total) {
        double to = total * porcentaje / 100.0;
        double Tf = total - to;
        return String.format(Locale.US, "%.2f", Tf);

    }

    public void agregarTotal() {
        int pzs = 0;
        float precio = 0;
        for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
            pzs = Integer.parseInt(modelo.getValueAt(i, 4).toString());
            precio = Float.parseFloat(modelo.getValueAt(i, 5).toString());
            modelo.setValueAt("" + String.format(Locale.US, "%.2f", pzs * precio), i, 6);
        }

    }

    public void tablaDes() {

        modeloTabDescuento.setValueAt("PATENTE", 0, 0);
        modeloTabDescuento.setValueAt("GENÉRICO", 1, 0);
        modeloTabDescuento.setValueAt("CONSULTA", 2, 0);
        modeloTabDescuento.setValueAt("ABARROTES", 3, 0);
        modeloTabDescuento.setValueAt("PERFUMERIA", 4, 0);

        modeloTabDescuento.setValueAt("0", 0, 1);
        modeloTabDescuento.setValueAt("0", 1, 1);
        modeloTabDescuento.setValueAt("0", 2, 1);
        modeloTabDescuento.setValueAt("0", 3, 1);
        modeloTabDescuento.setValueAt("0", 4, 1);

        modeloTabDescuento.setValueAt("0.0", 0, 2);
        modeloTabDescuento.setValueAt("0.0", 1, 2);
        modeloTabDescuento.setValueAt("0.0", 2, 2);
        modeloTabDescuento.setValueAt("0.0", 3, 2);
        modeloTabDescuento.setValueAt("0.0", 4, 2);

        modeloTabDescuento.setValueAt("0.0", 0, 3);
        modeloTabDescuento.setValueAt("0.0", 1, 3);
        modeloTabDescuento.setValueAt("0.0", 2, 3);
        modeloTabDescuento.setValueAt("0.0", 3, 3);
        modeloTabDescuento.setValueAt("0.0", 4, 3);
    }

    public void agregarProducto(String codigo, String piezas) {
        System.out.println(piezas);
        if (productoAgregado(codigo)) {
            modelo.setValueAt("" + (Integer.parseInt(modelo.getValueAt(obtenerFila(codigo), 4).toString()) + 1), obtenerFila(codigo), 4);//Integer.parseInt(() + 1), 2)
            agregarTotal();

        } else {
            pantalla_Ventas.jTableProductosVenta.setModel(new Ventas().obtenerDatosProducto(codigo, pantalla_Ventas.jTableProductosVenta, piezas));
        }
        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
    }

    public void quitarProducto(String codigo) {
        modelo.setValueAt("" + (Integer.parseInt(modelo.getValueAt(obtenerFila(codigo), 4).toString()) - 1), obtenerFila(codigo), 4);//Integer.parseInt(() + 1), 2)
        agregarTotal();
    }

    public void agregarProducto2(String codigo) {

        if (productoAgregado(codigo)) {
            modelo.setValueAt("" + (Integer.parseInt(modelo.getValueAt(obtenerFila(codigo), 4).toString()) + 1), obtenerFila(codigo), 4);//Integer.parseInt(() + 1), 2)
            agregarTotal();

        } else {
            pantalla_Ventas.jTableProductosVenta.setModel(new Ventas().obtenerDatosProducto(codigo, pantalla_Ventas.jTableProductosVenta, "1"));
        }
        pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
    }

    private boolean productoAgregado(String codigo) {
        for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    private int obtenerFila(String codigo) {
        for (int i = 0; i < pantalla_Ventas.jTableProductosVenta.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    private void ventaRegistrar() {
        if (modelo.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(null, "AGREGUE PRODUCTOS", "ERROR..", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pantalla_Ventas.btnCambioCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE EL PAGO", "ERROR..", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (obtenerT() > Float.parseFloat(pantalla_Ventas.btnCambioCliente.getText())) {
            JOptionPane.showMessageDialog(null, "EL PAGO ES INSUFICIENTE", "ERROR..", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //  JOptionPane.showMessageDialog(null, idEmpleado + "  " + idCli + "  " + cantidad + " " + String.valueOf(obtenerT()));
        String tipoVenta = modelo.getValueAt(0, 3).toString();
        int des_p = Integer.parseInt((String) pantalla_Ventas.jComboBoxPatente.getSelectedItem());
        int des_g = Integer.parseInt((String) pantalla_Ventas.jComboBoxGenerico.getSelectedItem());
        if (new Ventas().registrarVenta(idEmpleado, idCli, cantidad, String.valueOf(obtenerT()), modelo, turno, tipoVenta, des_p, des_g)) {

            enviarDatosTikect();
            JOptionPane.showMessageDialog(null, "La venta se registro con exito");
            pantalla_Ventas.jDialogCobro.dispose();
            pantalla_Ventas.jTextFieldFolioProductoVenta.setText("");
            pantalla_Ventas.jTextFieldClienteVenta.setText("PUBLICO EN GENERAL");
            modelo.setRowCount(0);
            pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
            //pantalla_Ventas.jComboBoxAnti.setEnabled(false);
            pantalla_Ventas.jComboBoxGenerico.setEnabled(false);
            pantalla_Ventas.jComboBoxPatente.setEnabled(false);
            //pantalla_Ventas.jComboBoxAnti.setSelectedItem("0");
            pantalla_Ventas.jComboBoxGenerico.setSelectedItem("0");
            pantalla_Ventas.jComboBoxPatente.setSelectedItem("0");
            // modeloTabDescuento.setRowCount(0);
            tablaDes();
            pantalla_Ventas.jLabelSubtotalVenta.setText("$0");
            pantalla_Ventas.jTextFieldTotalVenta.setText("");
            pantalla_Ventas.btnCambioCliente.setText("");
            pantalla_Ventas.jLabelCantidadProductos.setText("0");
            pantalla_Ventas.jTextFieldCambio.setText("");

            //   totalFinal=0;
            cantidad = "";
            idCli = "1";
            ventas = new Ventas();
            id = ventas.folio();
            folio = Integer.parseInt(id) + 1;
            pantalla_Ventas.jTextFieldFolio.setText(String.valueOf(folio));
            pantalla_Ventas.jTextFieldSustancia.setText("");
            pantalla_Ventas.jComboBoxSustancia.removeAllItems();
            pantalla_Ventas.jTextFieldFolioProductoVenta.requestFocus();
            placeHolder = new PlaceHolder(pantalla_Ventas.jTextFieldSustancia, "Busqueda por sustancias");
//            int arqueo = ventas.numArqueos();//saver cuantos arqueos
//            arqueo = arqueo +1;
//            int canArqueo = 3000 * arqueo;
//            ventas = new Ventas(turno);
//            String arr[] = ventas.arqueo();
//            float ventasT = Float.parseFloat(arr[0]);
//            float devoluciones = Float.parseFloat(arr[1]);
//            float gastos = Float.parseFloat(arr[1]);
//            float total = (ventasT) - (devoluciones - gastos);
//            if (total >= canArqueo) {
//                ventas = new  Ventas(turno,total);
//                boolean netx = ventas.insertAqueo();
//                this.arqueo = new TikectkArqueo();
//                this.arqueo.TikectkArqueo(total, arqueo);
//                
//            }
        } else {
            JOptionPane.showMessageDialog(null, "La venta no se registro");

        }
    }
}
