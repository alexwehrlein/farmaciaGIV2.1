/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Jose Abada Nava
 */
public class prueba {

    public prueba() {

    } //Cierre del constructor

    public String claveP() {
        ArrayList<Integer> claves = new ArrayList<>();
        //obtener
        int numero = 0;
        Random numAleatorio = new Random();
        for (int i = 0; i < 6; i++) {
            numero += numAleatorio.nextInt(10000000);
        }
        if (numero < 0) {
            claveP();
        }
        if (claves != null) {
            for (int i = 0; i < claves.size(); i++) {
                if (claves.get(i) == numero) {
                    claveP();
                }
            }
        }

        return String.valueOf(numero);
    }

    public static void main(String[] args) {
        prueba p = new prueba();
        System.out.println(p.claveP());

    }

} //Cierre de la clase
