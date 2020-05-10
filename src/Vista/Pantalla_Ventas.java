/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Producto;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author saube
 */
public class Pantalla_Ventas extends javax.swing.JInternalFrame {
    public DefaultComboBoxModel<Producto> modeloProductos;
    /**
     * Creates new form Pantalla_Ventas
     */
    public Pantalla_Ventas() {
        modeloProductos = new DefaultComboBoxModel<Producto>(); 
        initComponents();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVenta = new rojerusan.RSTableMetro();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePausaVenta = new javax.swing.JTable();
        rSPanelImage1 = new rojerusan.RSPanelImage();
        jLabelSubtotalVenta = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabelSubtotalVenta1 = new javax.swing.JLabel();
        rSButtonIconI1 = new rojerusan.RSButtonIconI();
        rSButtonIconI2 = new rojerusan.RSButtonIconI();
        rSButtonIconI3 = new rojerusan.RSButtonIconI();
        rSButtonIconI4 = new rojerusan.RSButtonIconI();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxProductos = new javax.swing.JComboBox<>();
        rSButtonIconI5 = new rojerusan.RSButtonIconI();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ventas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 36))); // NOI18N

        jTableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Marca Comercias", "Tipo Medicamento", "Piezas", "Precio", "Total", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVenta.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        jTableVenta.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        jTableVenta.setFuenteFilas(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTableVenta.setFuenteFilasSelect(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTableVenta.setFuenteHead(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jTableVenta.setRowHeight(25);
        jTableVenta.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jScrollPane1.setViewportView(jTableVenta);
        if (jTableVenta.getColumnModel().getColumnCount() > 0) {
            jTableVenta.getColumnModel().getColumn(3).setPreferredWidth(1);
            jTableVenta.getColumnModel().getColumn(4).setPreferredWidth(1);
            jTableVenta.getColumnModel().getColumn(5).setPreferredWidth(1);
            jTableVenta.getColumnModel().getColumn(6).setPreferredWidth(10);
            jTableVenta.getColumnModel().getColumn(7).setPreferredWidth(1);
        }

        jTablePausaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Marca Comercial", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTablePausaVenta);
        if (jTablePausaVenta.getColumnModel().getColumnCount() > 0) {
            jTablePausaVenta.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        rSPanelImage1.setImagen(new javax.swing.ImageIcon(getClass().getResource("/Images/LOGO GI.jpg"))); // NOI18N

        javax.swing.GroupLayout rSPanelImage1Layout = new javax.swing.GroupLayout(rSPanelImage1);
        rSPanelImage1.setLayout(rSPanelImage1Layout);
        rSPanelImage1Layout.setHorizontalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        rSPanelImage1Layout.setVerticalGroup(
            rSPanelImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 99, Short.MAX_VALUE)
        );

        jLabelSubtotalVenta.setBackground(new java.awt.Color(255, 0, 0));
        jLabelSubtotalVenta.setFont(new java.awt.Font("Times New Roman", 1, 80)); // NOI18N
        jLabelSubtotalVenta.setForeground(new java.awt.Color(255, 0, 0));
        jLabelSubtotalVenta.setText("0");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel23.setText("TOTAL");

        jLabelSubtotalVenta1.setBackground(new java.awt.Color(255, 0, 0));
        jLabelSubtotalVenta1.setFont(new java.awt.Font("Times New Roman", 1, 80)); // NOI18N
        jLabelSubtotalVenta1.setForeground(new java.awt.Color(255, 0, 0));
        jLabelSubtotalVenta1.setText("$");

        rSButtonIconI1.setBackground(new java.awt.Color(255, 255, 255));
        rSButtonIconI1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deleteVenta.png"))); // NOI18N
        rSButtonIconI1.setToolTipText("CANCELAR VENTA");
        rSButtonIconI1.setColorHover(new java.awt.Color(255, 255, 255));

        rSButtonIconI2.setBackground(new java.awt.Color(255, 255, 255));
        rSButtonIconI2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deleteVenta.png"))); // NOI18N
        rSButtonIconI2.setToolTipText("CANCELAR VENTA");
        rSButtonIconI2.setColorHover(new java.awt.Color(255, 255, 255));

        rSButtonIconI3.setBackground(new java.awt.Color(255, 255, 255));
        rSButtonIconI3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deleteVenta.png"))); // NOI18N
        rSButtonIconI3.setToolTipText("CANCELAR VENTA");
        rSButtonIconI3.setColorHover(new java.awt.Color(255, 255, 255));

        rSButtonIconI4.setBackground(new java.awt.Color(255, 255, 255));
        rSButtonIconI4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deleteVenta.png"))); // NOI18N
        rSButtonIconI4.setToolTipText("CANCELAR VENTA");
        rSButtonIconI4.setColorHover(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("CODIGO");

        txtCodigo.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtDescripcion.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("DESCRIPCION");

        jComboBoxProductos.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jComboBoxProductos.setModel(modeloProductos);

        rSButtonIconI5.setBackground(new java.awt.Color(255, 255, 255));
        rSButtonIconI5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/deleteVenta.png"))); // NOI18N
        rSButtonIconI5.setToolTipText("CANCELAR VENTA");
        rSButtonIconI5.setColorHover(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jComboBoxProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(rSPanelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(101, 101, 101))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabelSubtotalVenta1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelSubtotalVenta)
                                        .addGap(90, 90, 90))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(126, 126, 126)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(rSButtonIconI1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rSButtonIconI5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rSButtonIconI3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rSButtonIconI2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(155, 155, 155)
                                        .addComponent(rSButtonIconI4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rSPanelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rSButtonIconI1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rSButtonIconI2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rSButtonIconI3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rSButtonIconI5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rSButtonIconI4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSubtotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelSubtotalVenta1))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<Producto> jComboBoxProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabelSubtotalVenta;
    public javax.swing.JLabel jLabelSubtotalVenta1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTable jTablePausaVenta;
    public rojerusan.RSTableMetro jTableVenta;
    private rojerusan.RSButtonIconI rSButtonIconI1;
    private rojerusan.RSButtonIconI rSButtonIconI2;
    private rojerusan.RSButtonIconI rSButtonIconI3;
    private rojerusan.RSButtonIconI rSButtonIconI4;
    private rojerusan.RSButtonIconI rSButtonIconI5;
    private rojerusan.RSPanelImage rSPanelImage1;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables
}