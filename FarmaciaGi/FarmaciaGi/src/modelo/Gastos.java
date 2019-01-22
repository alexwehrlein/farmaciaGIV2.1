/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author saube
 */
public class Gastos {
    private String descripcion;
    private double total;
    private String turno;
    private String fecha;
    Conexion conexion ;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Gastos(String descripcion, double total, String turno) {
        this.descripcion = descripcion;
        this.total = total;
        this.turno = turno;
    }

    public Gastos(String descripcion, double total, String turno, String fecha) {
        this.descripcion = descripcion;
        this.total = total;
        this.turno = turno;
        this.fecha = fecha;
    }
    
    
    
    
    public boolean registrarGastos(){
        try {
            Connection connection =new Conexion().getConnection();
           Statement stm= (Statement) connection.createStatement();
           stm.execute("INSERT INTO gastos VALUES(null,'"+getDescripcion()+"',CURDATE(),"+getTotal()+" , '"+getTurno()+"')");
          connection.close();
       return  true;
        } catch (Exception e) {
            
            return false;
        }
              
        
    }
    
    public  Gastos(){
        
    }
    
     public DefaultTableModel cargarRegistroEgreso(JTable jt) {
        jt.setDefaultRenderer(Object.class, new Render());
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        //JComboBox tipo;
        TableColumn col = jt.getColumnModel().getColumn(1);
        //String op[] = {"Luz", "Agua", "Gas", "Producto"};
        //tipo = new JComboBox(op);
       // col.setCellEditor(new DefaultCellEditor(tipo));
        btnModificar.setName("btnModificar");
        btnEliminar.setName("btnEliminar");
       ImageIcon im = new ImageIcon(getClass().getResource("/imagenes/mo.png"));
       btnModificar.setIcon(im);
       ImageIcon ie = new ImageIcon(getClass().getResource("/imagenes/eli.png"));
       btnEliminar.setIcon(ie);
        jt.setRowHeight(25);

        DefaultTableModel modelo = (DefaultTableModel) jt.getModel();
        ArrayList<Gastos> arrayEgresos = new ArrayList<>();
        try {
            
            String sql = "SELECT * FROM gastos WHERE fecha = CURDATE()  order by descripcion";
            Connection con  =new Conexion().getConnection();
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayEgresos.add(new Gastos(resultado.getString("descripcion"), resultado.getDouble("total"), resultado.getString("turno"), resultado.getString("fecha")));
            }
            for (int i = 0; i < arrayEgresos.size(); i++) {
                modelo.addRow(new Object[]{ arrayEgresos.get(i).getDescripcion(), arrayEgresos.get(i).getTotal(), arrayEgresos.get(i).getFecha(),
                    arrayEgresos.get(i).getTurno()});
            }
            con.close();
        } catch (SQLException ex) {
            //Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modelo;
    }
    
    
    
    
}
