package org.example.modelo;

import java.io.Serializable;

/**
 * MODELO: Representa el estado del juego de Triqui
 * Esta clase contiene toda la información del juego y es Serializable
 * para poder guardar y cargar partidas
 */
public class JuegoTriqui implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Tablero 3x3: ' ' = vacío, 'X' = jugador 1, 'O' = jugador 2
    private char[][] tablero;
    
    // Turno actual: 'X' o 'O'
    private char turnoActual;
    
    // Estado del juego: true si hay ganador o empate
    private boolean juegoTerminado;
    
    // Ganador: 'X', 'O', 'E' (empate), o ' ' (sin ganador)
    private char ganador;
    
    /**
     * Constructor: Inicializa un nuevo juego
     */
    public JuegoTriqui() {
        tablero = new char[3][3];
        reiniciar();
    }
    
    /**
     * Reinicia el juego a su estado inicial
     */
    public void reiniciar() {
        // Limpiar el tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' ';
            }
        }
        turnoActual = 'X';  // X siempre empieza
        juegoTerminado = false;
        ganador = ' ';
    }
    
    /**
     * Realiza un movimiento en el tablero
     */
    public boolean realizarMovimiento(int fila, int columna) {
        // Validar que la casilla esté vacía y el juego no haya terminado
        if (fila < 0 || fila > 2 || columna < 0 || columna > 2) {
            return false;
        }
        if (tablero[fila][columna] != ' ' || juegoTerminado) {
            return false;
        }
        
        // Colocar la ficha
        tablero[fila][columna] = turnoActual;
        
        // Verificar si hay ganador
        if (verificarGanador()) {
            juegoTerminado = true;
            ganador = turnoActual;
        } else if (tableroLleno()) {
            // Verificar empate
            juegoTerminado = true;
            ganador = 'E';
        } else {
            // Cambiar turno
            turnoActual = (turnoActual == 'X') ? 'O' : 'X';
        }
        
        return true;
    }
    
    /**
     * Verifica si el jugador actual ganó
     */
    private boolean verificarGanador() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == turnoActual && 
                tablero[i][1] == turnoActual && 
                tablero[i][2] == turnoActual) {
                return true;
            }
        }
        
        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j] == turnoActual && 
                tablero[1][j] == turnoActual && 
                tablero[2][j] == turnoActual) {
                return true;
            }
        }
        
        // Verificar diagonal principal
        if (tablero[0][0] == turnoActual && 
            tablero[1][1] == turnoActual && 
            tablero[2][2] == turnoActual) {
            return true;
        }
        
        // Verificar diagonal secundaria
        if (tablero[0][2] == turnoActual && 
            tablero[1][1] == turnoActual && 
            tablero[2][0] == turnoActual) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Verifica si el tablero está lleno
     */
    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Getters
    public char getCasilla(int fila, int columna) {
        return tablero[fila][columna];
    }
    
    public char getTurnoActual() {
        return turnoActual;
    }
    
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
    
    public char getGanador() {
        return ganador;
    }
}
