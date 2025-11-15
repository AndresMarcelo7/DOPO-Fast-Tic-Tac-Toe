package org.example.controlador;

import org.example.modelo.JuegoTriqui;
import org.example.vista.VistaTriqui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * CONTROLADOR: Coordina el Modelo y la Vista
 * Maneja la lógica del juego y las interacciones del usuario
 */
public class ControladorTriqui {
    private JuegoTriqui modelo;
    private VistaTriqui vista;
    
    /**
     * Constructor: Inicializa el controlador con modelo y vista
     */
    public ControladorTriqui(JuegoTriqui modelo, VistaTriqui vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        inicializarListeners();
        actualizarVista();
    }
    
    /**
     * Configura todos los listeners de la vista
     */
    private void inicializarListeners() {
        // Listeners para los botones del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int fila = i;
                final int columna = j;
                // Se acuerdan de las clases anónimas? aqui estamos creando una clase anónima que implementa la interface ActionListener 
                // y sobreescribiendo el método actionPerformed

                // Porque usamos una clase anónima? porque no queremos crear una clase separada solo para este listener
                // y porque no queremos crear una instancia de la clase
                // y ademas, no queremos que el listener sea accedido desde fuera de la clase
                vista.agregarListenerBoton(i, j, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manejarClickCasilla(fila, columna);
                    }
                });
            }
        }
        
        // Listeners para el menú
        vista.agregarListenerNuevoJuego(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoJuego();
            }
        });
        
        vista.agregarListenerGuardar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPartida();
            }
        });
        
        vista.agregarListenerCargar(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPartida();
            }
        });
        
        vista.agregarListenerSalir(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    /**
     * Maneja el click en una casilla del tablero
     */
    private void manejarClickCasilla(int fila, int columna) {
        // Intentar realizar el movimiento
        boolean movimientoValido = modelo.realizarMovimiento(fila, columna);
        
        if (movimientoValido) {
            actualizarCasilla(fila, columna);
            actualizarTurno();
            
            // Verificar si el juego terminó
            if (modelo.isJuegoTerminado()) {
                vista.deshabilitarTablero();
                mostrarResultado();
            }
        }
    }
    
    /**
     * Inicia un nuevo juego
     */
    private void nuevoJuego() {
        modelo.reiniciar();
        vista.reiniciarTablero();
        actualizarVista();
        vista.mostrarMensaje("¡Nuevo juego iniciado! Empieza X");
    }
    
    /**
     * Guarda la partida actual usando ObjectOutputStream
     */
    private void guardarPartida() {
        String nombreArchivo = vista.solicitarRutaGuardar();
        
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            return; // Usuario canceló
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(nombreArchivo))) {
            
            // Serializar el objeto modelo
            oos.writeObject(modelo);
            vista.mostrarMensaje("Partida guardada exitosamente en: " + nombreArchivo);
            
        } catch (IOException e) {
            vista.mostrarError("Error al guardar la partida: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Carga una partida guardada usando ObjectInputStream
     */
    private void cargarPartida() {
        String nombreArchivo = vista.solicitarRutaCargar();
        
        if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) {
            return; // Usuario canceló
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(nombreArchivo))) {
            
            // Deserializar el objeto modelo
            modelo = (JuegoTriqui) ois.readObject();
            actualizarVista();
            vista.mostrarMensaje("Partida cargada exitosamente desde: " + nombreArchivo);
            
            // Si el juego estaba terminado, deshabilitar el tablero
            if (modelo.isJuegoTerminado()) {
                vista.deshabilitarTablero();
            }
            
        } catch (FileNotFoundException e) {
            vista.mostrarError("Archivo no encontrado: " + nombreArchivo);
        } catch (IOException e) {
            vista.mostrarError("Error al cargar la partida: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            vista.mostrarError("Error al deserializar la partida: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Actualiza TODA la vista según el estado del modelo
     * Este método se usa principalmente al CARGAR una partida guardada
     * porque necesitamos sincronizar toda la vista con el modelo
     */
    private void actualizarVista() {
        // Actualizar todos los botones del tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char simbolo = modelo.getCasilla(i, j);
                vista.actualizarBoton(i, j, simbolo);
            }
        }
        
        // Actualizar el turno
        vista.actualizarTurno(modelo.getTurnoActual());
    }
    
    /**
     * OPTIMIZACIÓN: Actualiza solo UNA casilla específica
     * Este método se usa en movimientos normales para evitar recorrer toda la matriz
     */
    private void actualizarCasilla(int fila, int columna) {
        char simbolo = modelo.getCasilla(fila, columna);
        vista.actualizarBoton(fila, columna, simbolo);
    }
    
    /**
     * Actualiza solo la etiqueta del turno
     */
    private void actualizarTurno() {
        vista.actualizarTurno(modelo.getTurnoActual());
    }
    
    /**
     * Muestra el resultado del juego
     */
    private void mostrarResultado() {
        char ganador = modelo.getGanador();
        
        if (ganador == 'E') {
            vista.mostrarMensaje("¡Empate! El tablero está lleno.");
        } else {
            vista.mostrarMensaje("¡Ganó el jugador " + ganador + "!");
        }
    }
    
    /**
     * Inicia la aplicación
     */
    public void iniciar() {
        vista.mostrar();
    }
}
