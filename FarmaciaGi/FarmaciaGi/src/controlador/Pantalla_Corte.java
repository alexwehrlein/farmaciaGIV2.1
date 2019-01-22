/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import modelo.Corte;
import tikect.TikectCorte;
import tikect.TikectCorteConsulta;
import vista.Pantalla_CorteCaja;
import vista.Pantalla_Proveedor;
import vista.Pantalla_Ventas;
import vista.Pantalla_principal;

/**
 *
 * @author saube
 */
public class Pantalla_Corte {

    Pantalla_CorteCaja pantalla_Corte;
    Controlador_PantallaPrincipal controlador_PantallaPrincipal;
    Pantalla_Ventas pantalla_Ventas;
    Pantalla_principal pantalla_principal;

    TikectCorte tikectCorte;
    TikectCorteConsulta tcc;
    Corte corte;
    String ventaTotal = "0";
    String consultorioTotal = "0";
    String devolucionesTotal = "0";
    String gastosTotal = "0";
    String perfumeriaTotal = "0";
    String abarrotesTotal = "0";
    String id;
    int folio;
    String turnoF;
     ArrayList<String> nombresClientes = new ArrayList<String>();

    public Pantalla_Corte(String turno) {
        this.turnoF = turno;
        pantalla_Corte = new Pantalla_CorteCaja();
        pantalla_Corte.setVisible(true);
        pantalla_Corte.setLocationRelativeTo(null);

        pantalla_Corte.jButtonCorte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (pantalla_Corte.jComboBoxTurno.getSelectedItem().toString().equals(turnoF)) {

                    corte = new Corte(turnoF);
                    boolean next = corte.Corte();

                    if (next) {

                        String turno = pantalla_Corte.jComboBoxTurno.getSelectedItem().toString();
                        corte = new Corte(turno);

                        id = corte.folio();
                        folio = Integer.parseInt(id) + 1;
                        pantalla_Corte.jTextFieldFolio.setText(String.valueOf(folio));

                        ventaTotal = corte.ventaTotal();
                        consultorioTotal = corte.consultaTotal();
                        devolucionesTotal = corte.devolucionesTotal();
                        abarrotesTotal = corte.abarrotesTotal();
                        perfumeriaTotal = corte.perfumeriaTotal();
                        gastosTotal = corte.gastosTotal();
                        nombresClientes = corte.descuentos();
                        String arr [] = corte.totalesC();
                        
      
                        double vt = Double.parseDouble(ventaTotal);
                        double ct = Double.parseDouble(consultorioTotal);
                        double dt = Double.parseDouble(devolucionesTotal);
                        double at = Double.parseDouble(abarrotesTotal);
                        double pt = Double.parseDouble(perfumeriaTotal);
                        double gt = Double.parseDouble(gastosTotal);
                        
                        pantalla_Corte.jTextFieldTVenta.setText("$ " + String.format("%.2f", vt));
                        pantalla_Corte.jTextFieldTConsultorio.setText("$ "+String.format("%.2f", ct));
                        pantalla_Corte.jTextFieldTDevoluciones.setText("$ " + String.format("%.2f",dt));
                        pantalla_Corte.jTextFieldTAbarrotes.setText("$ " + String.format("%.2f", at));
                        pantalla_Corte.jTextFieldTPerfumeria.setText("$ " + String.format("%.2f", pt));
                        pantalla_Corte.jTextFieldTGastos.setText("$ " + String.format("%.2f", gt));

                        double t = vt + ct + at + pt;
                        double tt = t - dt - gt;
                        double tk =tt-ct;
                        
                        

                        pantalla_Corte.jTextFieldTEntregar.setText("$ " +String.format("%.2f", tt) );

                        corte = new Corte(turno, tt);
                        if (corte.registrarCortes()) {

                            JOptionPane.showMessageDialog(null, "El corte se a guardado");
                            tikectCorte = new TikectCorte();
                            tikectCorte.TikecCorte(ventaTotal, consultorioTotal, devolucionesTotal, gastosTotal,abarrotesTotal,perfumeriaTotal, tk, turno,nombresClientes,arr);
                            tcc = new TikectCorteConsulta();
                            tcc.Tikect(ct, turno);
                            JOptionPane.showMessageDialog(null, "Turno finalizado" , "Adios" , JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);

                        } else {
                            JOptionPane.showMessageDialog(null, "error", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El corte ya a sido realizado ", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "No puedes hacer el corte distinto a tu turno", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
       
    }

}
