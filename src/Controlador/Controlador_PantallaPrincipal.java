/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Pantalla_principal;
import java.awt.Color;
import java.awt.Dimension;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;


/**
 *
 * @author Jose Abada Nava
 */
public class Controlador_PantallaPrincipal {

    Pantalla_principal pantalla_Principal;
    
    public static boolean ventanaControl9 = false;

    public class Imagen extends javax.swing.JPanel {

        private String file;

        public Imagen(String file, Dimension dim) {
            this.setSize(dim); //se selecciona el tamaño del panel
            this.file = file;
        }
//Se crea un método cuyo parámetro debe ser un objeto Graphics

        public void paint(Graphics grafico) {
            Dimension height = getSize();
//Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(getClass().getResource(file));
//se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
            setOpaque(false);
            super.paintComponent(grafico);
        }
    }

    public Controlador_PantallaPrincipal() {
        Dimension dim;

        pantalla_Principal = new Pantalla_principal();
        dim = pantalla_Principal.getToolkit().getScreenSize();
        pantalla_Principal.setSize(dim);
        // pantalla_Principal.setResizable(false);
        //Imagen imagen = new Imagen("/Images/farmacia.png", dim);
        //pantalla_Principal.jDesktopPane.add(imagen);
        pantalla_Principal.setVisible(true);
        pantalla_Principal.setExtendedState(MAXIMIZED_BOTH);
        inicioP();
        
        pantalla_Principal.jMenuItemPersonal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Controlador_Empleados(pantalla_Principal);
            }
        });
        pantalla_Principal.jMenuItemClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            new Controlador_Clientes(pantalla_Principal);    
            }
        });

    }


    public void cerrar() {
        inicioP();
    }

    private void inicioP() {
        
    }


    public static void main(String[] args) {
        Controlador_PantallaPrincipal controlador_PantallaPrincipal = new Controlador_PantallaPrincipal();
    }
}
