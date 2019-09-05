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

/**
 *
 * @author Jose Abada Nava
 */
public class Proveedor {

    private Connection con;

    public Proveedor() {
        con = Conexion.getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Medicamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> obtenerNombreProveedor() {
        ArrayList<String> arrayProveedor = new ArrayList<>();
        try {
            String sql = "SELECT idproveedor,nombre FROM PROVEEDOR WHERE estatus='ACTIVO';";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                arrayProveedor.add(String.valueOf(resultado.getInt("idproveedor")));
                arrayProveedor.add(resultado.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrayProveedor;
    }

}
