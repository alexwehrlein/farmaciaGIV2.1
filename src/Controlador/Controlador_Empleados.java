/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Pantalla_Personal;
import Vista.Pantalla_principal;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author saube
 */
public class Controlador_Empleados {

    Pantalla_Personal pp;//objeto de pantalla personal
    Pantalla_principal pantalla_principal;

    public Controlador_Empleados(Pantalla_principal pantalla_principal) {
        pp = new Pantalla_Personal();
        pp.setVisible(true);
        pp.setResizable(true);
        //pp.setSize(981, 474);
        pp.setClosable(true);
        pp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension FrameSize = pp.getSize();
        Dimension desktopSize = pantalla_principal.jDesktopPane.getSize();
        pp.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        pantalla_principal.jDesktopPane.removeAll();
        pantalla_principal.jDesktopPane.add(pp);

    }

}
