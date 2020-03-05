/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import alert.AlertInformation;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose Abada Nava
 */
public class Pantalla_principal extends javax.swing.JFrame {

    AlertInformation alertInformation;

    /**
     * Creates new form Pantalla_principal
     */
    public Pantalla_principal() {
        initComponents();
        cerrar();
    }

    public void cerrar() {
        try {
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    confirmarSalida();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmarSalida() {
        Icon icono = new ImageIcon(getClass().getResource("/Images/logo22.png"));
        int valor = JOptionPane.showConfirmDialog(this, "¿Esta seguro de cerrar la aplicacion?", "Advertencia", JOptionPane.YES_NO_OPTION);
        if (valor == JOptionPane.YES_OPTION) {
            alertInformation = new AlertInformation("Famacia Gi");
            alertInformation.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            alertInformation.show();
            //JOptionPane.showMessageDialog(null, "Famacia Gi", "Gracias", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        }
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Images/logo22.png"));

        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogLogin = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldUsuarioLogin = new javax.swing.JTextField();
        jButtonSalirLogin = new javax.swing.JButton();
        jButtonIngresarLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldPasswordLogin = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanelIma = new javax.swing.JPanel();
        ImageIcon Img = new ImageIcon(getClass().getResource("/Images/farmacia.png"));
        Image image = Img.getImage();
        jDesktopPane = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                g.drawImage(image , 0, 0, getWidth(), getHeight(), this);
            }
        };
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuAdmon = new javax.swing.JMenu();
        jMenuItemProducto = new javax.swing.JMenuItem();
        jMenuItemProductoxSucursal = new javax.swing.JMenuItem();
        jMenuItemEmpleados = new javax.swing.JMenuItem();
        jMenuItemClientes = new javax.swing.JMenuItem();
        jMenuItemProveedores = new javax.swing.JMenuItem();
        jMenuItemBajas = new javax.swing.JMenuItem();
        jMenuCajero = new javax.swing.JMenu();
        jMenuItemGastos = new javax.swing.JMenuItem();
        jMenuInicioSesion = new javax.swing.JMenu();
        jMenuItemIniciarSesion = new javax.swing.JMenuItem();

        jPanel1.setBackground(new java.awt.Color(0, 160, 223));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("Contraseña:");

        jTextFieldUsuarioLogin.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jTextFieldUsuarioLogin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldUsuarioLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUsuarioLoginActionPerformed(evt);
            }
        });

        jButtonSalirLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonSalirLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/if_Log Out_27856.png"))); // NOI18N
        jButtonSalirLogin.setText("Salir");
        jButtonSalirLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirLoginActionPerformed(evt);
            }
        });

        jButtonIngresarLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonIngresarLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Login_37128 (1).png"))); // NOI18N
        jButtonIngresarLogin.setText("Ingresar");

        jLabel3.setFont(new java.awt.Font("Bodoni MT", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("¡ Bienvenido !");

        jTextFieldPasswordLogin.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jTextFieldPasswordLogin.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/miranda.png"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/candado.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setText("Usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jButtonIngresarLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(jButtonSalirLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jLabel6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldUsuarioLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(154, 154, 154))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUsuarioLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(21, 21, 21)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIngresarLogin)
                    .addComponent(jButtonSalirLogin))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogLoginLayout = new javax.swing.GroupLayout(jDialogLogin.getContentPane());
        jDialogLogin.getContentPane().setLayout(jDialogLoginLayout);
        jDialogLoginLayout.setHorizontalGroup(
            jDialogLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogLoginLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialogLoginLayout.setVerticalGroup(
            jDialogLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        javax.swing.GroupLayout jDesktopPaneLayout = new javax.swing.GroupLayout(jDesktopPane);
        jDesktopPane.setLayout(jDesktopPaneLayout);
        jDesktopPaneLayout.setHorizontalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 842, Short.MAX_VALUE)
        );
        jDesktopPaneLayout.setVerticalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelImaLayout = new javax.swing.GroupLayout(jPanelIma);
        jPanelIma.setLayout(jPanelImaLayout);
        jPanelImaLayout.setHorizontalGroup(
            jPanelImaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane)
        );
        jPanelImaLayout.setVerticalGroup(
            jPanelImaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane)
        );

        jMenuBar1.setBackground(new java.awt.Color(0, 184, 233));

        jMenuAdmon.setBackground(new java.awt.Color(0, 0, 102));
        jMenuAdmon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/admin1.png"))); // NOI18N
        jMenuAdmon.setText("ADMINISTRADOR");
        jMenuAdmon.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jMenuItemProducto.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemProducto.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/productos.png"))); // NOI18N
        jMenuItemProducto.setText("Producto");
        jMenuItemProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProductoActionPerformed(evt);
            }
        });
        jMenuAdmon.add(jMenuItemProducto);

        jMenuItemProductoxSucursal.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemProductoxSucursal.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemProductoxSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/sucursal.png"))); // NOI18N
        jMenuItemProductoxSucursal.setText("Sucursal");
        jMenuItemProductoxSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProductoxSucursalActionPerformed(evt);
            }
        });
        jMenuAdmon.add(jMenuItemProductoxSucursal);

        jMenuItemEmpleados.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemEmpleados.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/empleados.png"))); // NOI18N
        jMenuItemEmpleados.setText("Empleados");
        jMenuItemEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEmpleadosActionPerformed(evt);
            }
        });
        jMenuAdmon.add(jMenuItemEmpleados);

        jMenuItemClientes.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemClientes.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/clientes.png"))); // NOI18N
        jMenuItemClientes.setText("Clientes");
        jMenuAdmon.add(jMenuItemClientes);

        jMenuItemProveedores.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemProveedores.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/proveedor.png"))); // NOI18N
        jMenuItemProveedores.setText("Proveedores");
        jMenuAdmon.add(jMenuItemProveedores);

        jMenuItemBajas.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemBajas.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemBajas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/bajas.png"))); // NOI18N
        jMenuItemBajas.setText("Bajas");
        jMenuAdmon.add(jMenuItemBajas);

        jMenuBar1.add(jMenuAdmon);

        jMenuCajero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cajero1.png"))); // NOI18N
        jMenuCajero.setText("CAJERO");
        jMenuCajero.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jMenuItemGastos.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemGastos.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemGastos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/menu/gatos.png"))); // NOI18N
        jMenuItemGastos.setText("Gastos");
        jMenuCajero.add(jMenuItemGastos);

        jMenuBar1.add(jMenuCajero);

        jMenuInicioSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loo4.png"))); // NOI18N
        jMenuInicioSesion.setText("LOGIN");
        jMenuInicioSesion.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuInicioSesion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jMenuInicioSesionItemStateChanged(evt);
            }
        });
        jMenuInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuInicioSesionActionPerformed(evt);
            }
        });

        jMenuItemIniciarSesion.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItemIniciarSesion.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jMenuItemIniciarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/login1.png"))); // NOI18N
        jMenuItemIniciarSesion.setText("Iniciar Sesion");
        jMenuInicioSesion.add(jMenuItemIniciarSesion);

        jMenuBar1.add(jMenuInicioSesion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelIma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelIma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuInicioSesionActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuInicioSesionActionPerformed

    private void jMenuInicioSesionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jMenuInicioSesionItemStateChanged

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuInicioSesionItemStateChanged

    private void jTextFieldUsuarioLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUsuarioLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUsuarioLoginActionPerformed

    private void jButtonSalirLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSalirLoginActionPerformed

    private void jMenuItemProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemProductoActionPerformed

    private void jMenuItemProductoxSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProductoxSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemProductoxSucursalActionPerformed

    private void jMenuItemEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemEmpleadosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButtonIngresarLogin;
    public javax.swing.JButton jButtonSalirLogin;
    public javax.swing.JDesktopPane jDesktopPane;
    public javax.swing.JDialog jDialogLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    public javax.swing.JMenu jMenuAdmon;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenu jMenuCajero;
    public javax.swing.JMenu jMenuInicioSesion;
    public javax.swing.JMenuItem jMenuItemBajas;
    public javax.swing.JMenuItem jMenuItemClientes;
    public javax.swing.JMenuItem jMenuItemEmpleados;
    public javax.swing.JMenuItem jMenuItemGastos;
    public javax.swing.JMenuItem jMenuItemIniciarSesion;
    public javax.swing.JMenuItem jMenuItemProducto;
    public javax.swing.JMenuItem jMenuItemProductoxSucursal;
    public javax.swing.JMenuItem jMenuItemProveedores;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanelIma;
    public javax.swing.JPasswordField jTextFieldPasswordLogin;
    public javax.swing.JTextField jTextFieldUsuarioLogin;
    // End of variables declaration//GEN-END:variables
}
