/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jose Abada Nava
 */
public class Marca {

    private Connection con;

    public Marca() {
        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean registrarMarca(String nombre) {
        try {
            String sql = "INSERT INTO MARCA(nombre) VALUES(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.execute();
            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
                return false;
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return false;
    }

    public ArrayList<String> obtenerNombreMarca() {
        ArrayList<String> arrayMarca = new ArrayList<>();
        try {
            String sql = "SELECT * FROM MARCA;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayMarca.add(String.valueOf(resultado.getInt("idmarca")));
                arrayMarca.add(resultado.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrayMarca;
    }

}
