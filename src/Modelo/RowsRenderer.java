package Modelo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RowsRenderer extends DefaultTableCellRenderer {

    private int columna;

    public RowsRenderer(int Colpatron) {
        this.columna = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setBackground(Color.white);
        table.setForeground(Color.black);
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        if (table.getValueAt(row, columna).equals("X")) {
            this.setForeground(Color.RED);
            this.setFont(new java.awt.Font("Tahoma", 0, 20));
            this.setHorizontalAlignment( JLabel.CENTER );
        }
        return this;
    }
}
