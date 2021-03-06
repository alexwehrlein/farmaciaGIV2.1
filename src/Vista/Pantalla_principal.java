/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

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
                public void windowClosing(WindowEvent e){
                    confirmarSalida();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void confirmarSalida(){
        Icon icono = new ImageIcon(getClass().getResource("/Images/logo22.png"));
        int valor = JOptionPane.showConfirmDialog(this, "¿Esta seguro de cerrar la aplicacion?","Advertencia",JOptionPane.YES_NO_OPTION);
        if (valor == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,"Famacia Gi" , "Gracias", JOptionPane.INFORMATION_MESSAGE);
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
        jMenuItemPersonal = new javax.swing.JMenuItem();
        jMenuItemClientes = new javax.swing.JMenuItem();
        jMenuItemProveedores = new javax.swing.JMenuItem();
        jMenuCajero = new javax.swing.JMenu();
        jMenuItemGastos = new javax.swing.JMenuItem();
        jMenuInicioSesion = new javax.swing.JMenu();
        jMenuItemIniciarSesion = new javax.swing.JMenuItem();

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
            .addGap(0, 461, Short.MAX_VALUE)
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

        jMenuItemPersonal.setText("Personal");
        jMenuItemPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPersonalActionPerformed(evt);
            }
        });
        jMenuAdmon.add(jMenuItemPersonal);

        jMenuItemClientes.setText("Clientes");
        jMenuItemClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClientesActionPerformed(evt);
            }
        });
        jMenuAdmon.add(jMenuItemClientes);

        jMenuItemProveedores.setText("Proveedores");
        jMenuAdmon.add(jMenuItemProveedores);

        jMenuBar1.add(jMenuAdmon);

        jMenuCajero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cajero1.png"))); // NOI18N
        jMenuCajero.setText("CAJERO");

        jMenuItemGastos.setText("gastos");
        jMenuCajero.add(jMenuItemGastos);

        jMenuBar1.add(jMenuCajero);

        jMenuInicioSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/loo4.png"))); // NOI18N
        jMenuInicioSesion.setText("LOGIN");
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

    private void jMenuItemPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPersonalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemPersonalActionPerformed

    private void jMenuItemClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemClientesActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane jDesktopPane;
    public javax.swing.JMenu jMenuAdmon;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenu jMenuCajero;
    public javax.swing.JMenu jMenuInicioSesion;
    public javax.swing.JMenuItem jMenuItemClientes;
    public javax.swing.JMenuItem jMenuItemGastos;
    public javax.swing.JMenuItem jMenuItemIniciarSesion;
    public javax.swing.JMenuItem jMenuItemPersonal;
    public javax.swing.JMenuItem jMenuItemProveedores;
    public javax.swing.JPanel jPanelIma;
    // End of variables declaration//GEN-END:variables
}
