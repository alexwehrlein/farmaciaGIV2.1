/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

/**
 *
 * @author saube
 */
public class Pantalla_Bajas extends javax.swing.JInternalFrame {

    /**
     * Creates new form Pantalla_Bajas
     */
    public Pantalla_Bajas() {
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
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new rojeru_san.RSButton();
        btnBajas = new rojeru_san.RSButton();
        rSPanelsSlider1 = new rojeru_san.RSPanelsSlider();
        lista = new javax.swing.JPanel();
        txtBajas = new rojeru_san.RSMTextFull();
        txtCodigo = new rojeru_san.RSMTextFull();
        txtExistencias = new rojeru_san.RSMTextFull();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableDatos = new rojerusan.RSTableMetro();
        btnSave = new rojeru_san.RSButton();
        bajas = new javax.swing.JPanel();
        fecha_ini = new rojeru_san.componentes.RSDateChooser();
        fecha_fin = new rojeru_san.componentes.RSDateChooser();
        btnBuscar = new rojeru_san.RSButton();
        btnGuardar = new rojeru_san.RSButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDatos = new rojerusan.RSTableMetro();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Baja De Medicamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 20))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAdd.setBackground(new java.awt.Color(155, 217, 217));
        btnAdd.setText("Agregar");
        btnAdd.setColorText(new java.awt.Color(20, 36, 64));
        btnAdd.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        btnAdd.setName("lista"); // NOI18N
        btnAdd.setSelected(true);
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, -1, -1));

        btnBajas.setBackground(new java.awt.Color(155, 217, 217));
        btnBajas.setText("Bajas");
        btnBajas.setColorText(new java.awt.Color(20, 36, 64));
        btnBajas.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        btnBajas.setName("bajas"); // NOI18N
        jPanel2.add(btnBajas, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, -1));

        lista.setBackground(new java.awt.Color(255, 255, 255));
        lista.setName("lista"); // NOI18N
        lista.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBajas.setForeground(new java.awt.Color(255, 0, 51));
        txtBajas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBajas.setPlaceholder("Baja");
        lista.add(txtBajas, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 46, 130, -1));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setPlaceholder("Codigo");
        lista.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 46, -1, -1));

        txtExistencias.setEditable(false);
        txtExistencias.setBackground(new java.awt.Color(255, 255, 255));
        txtExistencias.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtExistencias.setPlaceholder("Existencias");
        lista.add(txtExistencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(405, 46, 130, -1));

        jtableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Bajas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtableDatos.setColorBackgoundHead(new java.awt.Color(0, 160, 223));
        jtableDatos.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        jtableDatos.setColorFilasForeground1(new java.awt.Color(0, 160, 223));
        jtableDatos.setColorFilasForeground2(new java.awt.Color(0, 160, 223));
        jtableDatos.setColorSelBackgound(new java.awt.Color(0, 160, 223));
        jtableDatos.setFuenteFilas(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtableDatos.setRowHeight(25);
        jtableDatos.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jtableDatos);
        if (jtableDatos.getColumnModel().getColumnCount() > 0) {
            jtableDatos.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        lista.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 710, 250));

        btnSave.setBackground(new java.awt.Color(155, 217, 217));
        btnSave.setText("Guardar");
        btnSave.setColorText(new java.awt.Color(20, 36, 64));
        btnSave.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        lista.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, 130, -1));

        rSPanelsSlider1.add(lista, "card3");

        bajas.setBackground(new java.awt.Color(255, 255, 255));
        bajas.setName("bajas"); // NOI18N

        fecha_ini.setFuente(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        fecha_fin.setFuente(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btnBuscar.setBackground(new java.awt.Color(155, 217, 217));
        btnBuscar.setText("Buscar");
        btnBuscar.setColorText(new java.awt.Color(20, 36, 64));
        btnBuscar.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N

        btnGuardar.setBackground(new java.awt.Color(155, 217, 217));
        btnGuardar.setText("Guardar");
        btnGuardar.setColorText(new java.awt.Color(20, 36, 64));
        btnGuardar.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Codigo", "Nombre", "Bajas", "Status", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDatos.setColorBackgoundHead(new java.awt.Color(0, 160, 223));
        jTableDatos.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        jTableDatos.setColorFilasForeground1(new java.awt.Color(0, 160, 223));
        jTableDatos.setColorFilasForeground2(new java.awt.Color(0, 160, 223));
        jTableDatos.setColorSelBackgound(new java.awt.Color(0, 160, 223));
        jTableDatos.setFuenteFilas(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTableDatos.setRowHeight(25);
        jTableDatos.setRowSelectionAllowed(false);
        jScrollPane3.setViewportView(jTableDatos);
        if (jTableDatos.getColumnModel().getColumnCount() > 0) {
            jTableDatos.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableDatos.getColumnModel().getColumn(3).setMaxWidth(150);
            jTableDatos.getColumnModel().getColumn(4).setMaxWidth(80);
            jTableDatos.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        javax.swing.GroupLayout bajasLayout = new javax.swing.GroupLayout(bajas);
        bajas.setLayout(bajasLayout);
        bajasLayout.setHorizontalGroup(
            bajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bajasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bajasLayout.createSequentialGroup()
                        .addComponent(fecha_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(fecha_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bajasLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bajasLayout.setVerticalGroup(
            bajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bajasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(bajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fecha_fin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(bajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bajasLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bajasLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        rSPanelsSlider1.add(bajas, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rSPanelsSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rSPanelsSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel bajas;
    public rojeru_san.RSButton btnAdd;
    public rojeru_san.RSButton btnBajas;
    public rojeru_san.RSButton btnBuscar;
    public rojeru_san.RSButton btnGuardar;
    public rojeru_san.RSButton btnSave;
    public rojeru_san.componentes.RSDateChooser fecha_fin;
    public rojeru_san.componentes.RSDateChooser fecha_ini;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public rojerusan.RSTableMetro jTableDatos;
    public rojerusan.RSTableMetro jtableDatos;
    public javax.swing.JPanel lista;
    public rojeru_san.RSPanelsSlider rSPanelsSlider1;
    public rojeru_san.RSMTextFull txtBajas;
    public rojeru_san.RSMTextFull txtCodigo;
    public rojeru_san.RSMTextFull txtExistencias;
    // End of variables declaration//GEN-END:variables
}
