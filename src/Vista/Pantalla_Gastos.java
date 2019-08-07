package Vista;
import Controlador.Controlador_Gastos;
import Controlador.Controlador_PantallaPrincipal;
import java.lang.Object;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cachorra
 */
public class Pantalla_Gastos extends javax.swing.JInternalFrame {

    /**
     * Creates new form Pantalla_Gastos
     */
    public Pantalla_Gastos() {
        initComponents();
      //  setLocationRelativeTo(null);setVisible(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableGastos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnRegistrarGasto = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        txtmonto = new javax.swing.JTextField();
        txtdescripcion = new javax.swing.JTextField();
        jDateChooserFecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableGastosFechaActual = new javax.swing.JTable();
        btnImprimirticket = new javax.swing.JButton();
        jDateXUnaFecha = new com.toedter.calendar.JDateChooser();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GASTOS DE FARMACIA GI", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 36))); // NOI18N
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));
        jPanel2.setLayout(null);

        jTableGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Idegreso", "Descripcion", "Total", "Fecha", "Turno"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableGastos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTableGastos);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 240, 650, 230);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Fecha:");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 150, 41, 29);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Descripción del Gasto:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 10, 167, 29);

        btnRegistrarGasto.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnRegistrarGasto.setText("Registrar Gastos");
        jPanel2.add(btnRegistrarGasto);
        btnRegistrarGasto.setBounds(430, 170, 210, 50);

        btnListar.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnListar.setText("Listar Gastos");
        jPanel2.add(btnListar);
        btnListar.setBounds(430, 110, 210, 50);

        txtmonto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtmonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtmonto);
        txtmonto.setBounds(10, 110, 210, 40);
        jPanel2.add(txtdescripcion);
        txtdescripcion.setBounds(10, 40, 480, 40);

        jDateChooserFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(jDateChooserFecha);
        jDateChooserFecha.setBounds(10, 180, 210, 40);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Monto del Gasto:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 80, 105, 29);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Busqueda de Gastos");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(870, 10, 250, 40);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Fecha:");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(770, 90, 90, 40);

        jTableGastosFechaActual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Idegreso", "Descripcion", "Total", "Fecha", "Turno"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableGastosFechaActual);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(680, 150, 600, 170);

        btnImprimirticket.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnImprimirticket.setText("Imprimir Tikect");
        jPanel2.add(btnImprimirticket);
        btnImprimirticket.setBounds(910, 360, 200, 50);

        jDateXUnaFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(jDateXUnaFecha);
        jDateXUnaFecha.setBounds(890, 90, 230, 40);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 50, 1290, 480);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1311, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnImprimirticket;
    public javax.swing.JButton btnListar;
    public javax.swing.JButton btnRegistrarGasto;
    public com.toedter.calendar.JDateChooser jDateChooserFecha;
    public com.toedter.calendar.JDateChooser jDateXUnaFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTableGastos;
    public javax.swing.JTable jTableGastosFechaActual;
    public javax.swing.JTextField txtdescripcion;
    public javax.swing.JTextField txtmonto;
    // End of variables declaration//GEN-END:variables
   

    
}
