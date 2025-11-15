package org.example;

import org.example.controlador.ControladorTriqui;
import org.example.modelo.JuegoTriqui;
import org.example.vista.VistaTriqui;

import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia el juego de Triqui
 * Demuestra el patrón MVC (Modelo-Vista-Controlador)
 */
public class Main {
    public static void main(String[] args) {
        // Usar SwingUtilities para ejecutar la GUI en el hilo apropiado
        // Se acuerdan lo que les explique de los Hilos? este es el hilo de la GUI, por lo cual no bloquea el hilo principal de java :) 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear las tres capas del MVC
                JuegoTriqui modelo = new JuegoTriqui();        // Modelo: datos del juego
                VistaTriqui vista = new VistaTriqui();         // Vista: interfaz gráfica
                ControladorTriqui controlador = new ControladorTriqui(modelo, vista); // Controlador: lógica
                
                // Iniciar la aplicación
                controlador.iniciar();
            }
        });
        // while(true){    
        //     // Hacemos un bucle infinito para mantener el hilo principal vivo
        //     System.out.println("Hilo principal vivo");
        // }
    }
}