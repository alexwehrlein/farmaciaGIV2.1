package Controlador;

import Modelo.Empleados;
import Vista.Pantalla_principal;
import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import Modelo.Sucursal;

public class Controlador_PantallaPrincipal {

    Pantalla_principal pantalla_Principal;
    String empleado_idempleado, nombre, turno, rol;
    public static boolean ventanaControl9 = false;
    ArrayList<String> array;
    private String idSucursal, nombreSucursal;

    public class Imagen extends javax.swing.JPanel {

        private String file;

        public Imagen(String file, Dimension dim) {
            this.setSize(dim); //se selecciona el tamaño del panel
            this.file = file;
        }
//Se crea un método cuyo parámetro debe ser un objeto Graphics

        public void paint(Graphics grafico) {
            Dimension height = getSize();
//Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(getClass().getResource(file));
//se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
            setOpaque(false);
            super.paintComponent(grafico);
        }
    }

    public Controlador_PantallaPrincipal() {

        Dimension dim;

        pantalla_Principal = new Pantalla_principal();
        dim = pantalla_Principal.getToolkit().getScreenSize();
        pantalla_Principal.setSize(dim);
        // pantalla_Principal.setResizable(false);
        //Imagen imagen = new Imagen("/Images/farmacia.png", dim);
        //pantalla_Principal.jDesktopPane.add(imagen);
        pantalla_Principal.setVisible(true);
        pantalla_Principal.setExtendedState(MAXIMIZED_BOTH);

//---------------------------------------parte de sucursal---------------------------------------------------
        array = new Sucursal().obtenerIdSucursal(System.getProperty("user.name"));
//        array = new Sucursal().obtenerIdSucursal("usuario2");
//        array = new Sucursal().obtenerIdSucursal("usuario3");
//        array = new Sucursal().obtenerIdSucursal("usuario4");
        if (array.isEmpty()) {
            new Controlador_DialogSucursal(pantalla_Principal);
        } else {
            idSucursal = array.get(0);
            nombreSucursal = array.get(1);
//            JOptionPane.showMessageDialog(pantalla_Principal,"ID: "+idSucursal+ " Sucursal: " + nombreSucursal);
        }
//---------------------------------------------------------------------------------------------------------------
        inicioP();

        //abre ventana de login  
        pantalla_Principal.jMenuItemIniciarSesion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (pantalla_Principal.jMenuItemIniciarSesion.getText().equals("Iniciar Sesion")) {
                    pantalla_Principal.jDialogLogin.setTitle("Farmacia GI");
                    pantalla_Principal.jDialogLogin.setBounds(249, 154, 598, 344);
                    pantalla_Principal.jDialogLogin.setResizable(false);
                    pantalla_Principal.jDialogLogin.setVisible(true);
                    pantalla_Principal.jTextFieldUsuarioLogin.requestFocus();

                } else {
                    int m = JOptionPane.showConfirmDialog(pantalla_Principal, "Desea cerrar sesion ", "Confirmar salida", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (m == 0) {
                        inicioP();
                        pantalla_Principal.jMenuItemIniciarSesion.setText("Iniciar Sesion");
                        //  pantalla_Principal.panel.removeAll();
                        //  pantalla_Principal.panel.repaint();
                    } else {

                    }
                    //     JOptionPane.showMessageDialog(null, "Administrador a cerrado sesion");
                    // new Controlador_Mensaje(pantalla_Principal, "Administrador a cerrado sesion");
                }
            }
        });

//ingresa al login Aceptar ---- SI ESTAN INCORECTAS TE MARCA DE DONDE ESTA EL ERROR SI EN EL USAURIO O CONTRASEÑA CAMBIA DE COLORRES
        pantalla_Principal.jTextFieldPasswordLogin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (!camposVacios()) {
                        pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.white);
                        pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.white);
                        String[] arr = new Empleados().obtenerContraUsuario(pantalla_Principal.jTextFieldUsuarioLogin.getText());
                        if (!arr[0].equals("")) {
                            if (arr[0].equals(pantalla_Principal.jTextFieldPasswordLogin.getText())) {
                                pantalla_Principal.jTextFieldUsuarioLogin.setText("");
                                pantalla_Principal.jTextFieldPasswordLogin.setText("");
                                empleado_idempleado = arr[1];
                                nombre = arr[3];
                                rol = arr[2];
                                turno = arr[4];
                                if (arr[2].equals("Administrador")) {
                                    activarAdministrador();
                                    pantalla_Principal.jDialogLogin.setVisible(false);
                                    JOptionPane.showMessageDialog(null, "Bienvenido: " + arr[3]);

                                } else {
                                    activarCajero();
                                    pantalla_Principal.jDialogLogin.setVisible(false);
                                    JOptionPane.showMessageDialog(null, "Bienvenido: " + arr[3]);

                                }
                            } else {
                                pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.red);
                                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                            }
                        } else {
                            pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.red);
                            JOptionPane.showMessageDialog(null, "Usuario Incorrecto");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Campos Vacios");
                    }
                }

            }
        });

        // BOTIN INGRESAR LA PANTALLA PRINCPAL
        pantalla_Principal.jButtonIngresarLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!camposVacios()) {
                    pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.white);
                    pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.white);
                    String[] arr = new Empleados().obtenerContraUsuario(pantalla_Principal.jTextFieldUsuarioLogin.getText());
                    if (!arr[0].equals("")) {
                        if (arr[0].equals(pantalla_Principal.jTextFieldPasswordLogin.getText())) {
                            pantalla_Principal.jTextFieldUsuarioLogin.setText("");
                            pantalla_Principal.jTextFieldPasswordLogin.setText("");
                            empleado_idempleado = arr[1];
                            nombre = arr[3];
                            turno = arr[4];
                            if (arr[2].equals("Administrador")) {
                                activarAdministrador();
                                pantalla_Principal.jDialogLogin.setVisible(false);
                                JOptionPane.showMessageDialog(null, "Bienvenido: " + arr[3]);
                                JOptionPane.showMessageDialog(null, "ADMIN Idempleado: " + arr[1] + " \n ADMIN puesto: " + arr[2] + " \n ADMIN turno: " + arr[4]);
                            } else {
                                activarCajero();
                                pantalla_Principal.jDialogLogin.setVisible(false);
                                JOptionPane.showMessageDialog(null, "Bienvenido: " + arr[3]);
                                JOptionPane.showMessageDialog(null, "EMPLEADO Idempleado: " + arr[1] + " \n EMPLEADO puesto: " + arr[2] + " \n EMPLEADO turno: " + arr[4]);
                            }
                        } else {
                            pantalla_Principal.jTextFieldPasswordLogin.setBackground(Color.red);
                            JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                        }
                    } else {
                        pantalla_Principal.jTextFieldUsuarioLogin.setBackground(Color.red);
                        JOptionPane.showMessageDialog(null, "Usuario Incorrecto");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Campos Vacios");
                }

            }
        });

        pantalla_Principal.jButtonSalirLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pantalla_Principal.jDialogLogin.setVisible(false);
            }
        });

        pantalla_Principal.jMenuItemProducto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Producto(pantalla_Principal, idSucursal);
            }
        });
        
        pantalla_Principal.jMenuItemProductoxSucursal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_productosPorSucursal(pantalla_Principal, nombreSucursal ,idSucursal);
            }
        });
        
        pantalla_Principal.jMenuItemGastos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Gastos(pantalla_Principal, empleado_idempleado);
            }
        });
        
        pantalla_Principal.jMenuItemEmpleados.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Empleados(pantalla_Principal, Integer.parseInt(empleado_idempleado) , Integer.parseInt(idSucursal));
            }
        });
        
        pantalla_Principal.jMenuItemProveedores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Proveedores(pantalla_Principal, Integer.parseInt(empleado_idempleado) , Integer.parseInt(idSucursal));
            }
        });
        
        pantalla_Principal.jMenuItemClientes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Clientes(pantalla_Principal, Integer.parseInt(empleado_idempleado) , Integer.parseInt(idSucursal));
            }
        });

    }

    public boolean camposVacios() {
        return pantalla_Principal.jTextFieldUsuarioLogin.getText().isEmpty() || pantalla_Principal.jTextFieldPasswordLogin.getText().isEmpty();
    }

    public void cerrar() {
        inicioP();
    }

    private void activarAdministrador() {
        pantalla_Principal.jMenuItemProveedores.setEnabled(true);
        pantalla_Principal.jMenuItemClientes.setEnabled(true);
        pantalla_Principal.jMenuItemEmpleados.setEnabled(true);
        pantalla_Principal.jMenuItemProducto.setEnabled(true);
        pantalla_Principal.jMenuItemProductoxSucursal.setEnabled(true);
        pantalla_Principal.jMenuItemIniciarSesion.setText("Cerrar Sesion");
    }

    private void activarCajero() {
        pantalla_Principal.jMenuItemGastos.setEnabled(true);
        pantalla_Principal.jMenuItemIniciarSesion.setText("Cerrar Sesion");

    }

    private void inicioP() {
        pantalla_Principal.jMenuItemProveedores.setEnabled(false);
        pantalla_Principal.jMenuItemClientes.setEnabled(false);
        pantalla_Principal.jMenuItemEmpleados.setEnabled(false);
        pantalla_Principal.jMenuItemGastos.setEnabled(false);
        pantalla_Principal.jMenuItemProducto.setEnabled(false);
        pantalla_Principal.jMenuItemProductoxSucursal.setEnabled(false);
    }

    public static void main(String[] args) {
        Controlador_PantallaPrincipal controlador_PantallaPrincipal = new Controlador_PantallaPrincipal();
    }
}
