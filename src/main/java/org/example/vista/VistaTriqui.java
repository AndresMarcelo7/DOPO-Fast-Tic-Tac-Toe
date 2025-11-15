package org.example.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * VISTA: Interfaz gráfica del juego de Triqui
 * Usa JFrame para la ventana, JButton para las casillas y JMenu para las opciones
 */
public class VistaTriqui extends JFrame {
    private JButton[][] botones;
    private JLabel etiquetaTurno;
    private JMenuBar menuBar;
    private JMenu menuJuego;
    private JMenuItem itemNuevoJuego;
    private JMenuItem itemGuardar;
    private JMenuItem itemCargar;
    private JMenuItem itemSalir;
    
    /**
     * Constructor: Crea la interfaz gráfica
     */
    public VistaTriqui() {
        configurarVentana();
        crearMenu();
        crearTablero();
        crearPanelInfo();
    }
    
    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("Juego de Triqui - MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
    }
    
    /**
     * Crea el menú con opciones de juego
     */
    private void crearMenu() {
        menuBar = new JMenuBar();
        menuJuego = new JMenu("Juego");
        
        itemNuevoJuego = new JMenuItem("Nuevo Juego");
        itemGuardar = new JMenuItem("Guardar Partida");
        itemCargar = new JMenuItem("Cargar Partida");
        itemSalir = new JMenuItem("Salir");
        
        menuJuego.add(itemNuevoJuego);
        menuJuego.addSeparator();
        menuJuego.add(itemGuardar);
        menuJuego.add(itemCargar);
        menuJuego.addSeparator();
        menuJuego.add(itemSalir);
        
        menuBar.add(menuJuego);
        //Metodo de JFrame para agregar un menuBar a la ventana
        setJMenuBar(menuBar);
    }
    
    /**
     * Crea el tablero de 3x3 con botones
     */
    private void crearTablero() {
        JPanel panelTablero = new JPanel();
        // Se acuerdan de los GridLayouts???
        panelTablero.setLayout(new GridLayout(3, 3, 5, 5));
        panelTablero.setBackground(Color.BLACK);
        panelTablero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        botones = new JButton[3][3];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton(" ");
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.WHITE);
                botones[i][j].setPreferredSize(new Dimension(100, 100));
                panelTablero.add(botones[i][j]);
            }
        }
        
        //Metodo de JFrame para agregar un JPanel a la ventana
        add(panelTablero, BorderLayout.CENTER);
    }
    
    /**
     * Crea el panel con información del turno
     */
    private void crearPanelInfo() {
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(new Color(240, 240, 240));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        etiquetaTurno = new JLabel("Turno: X");
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 18));
        panelInfo.add(etiquetaTurno);
        
        add(panelInfo, BorderLayout.SOUTH);
    }
    
    /**
     * Actualiza el texto de un botón del tablero
     */
    public void actualizarBoton(int fila, int columna, char simbolo) {
        String texto = (simbolo == ' ') ? " " : String.valueOf(simbolo);
        botones[fila][columna].setText(texto);
        
        // Cambiar color según el símbolo
        if (simbolo == 'X') {
            botones[fila][columna].setForeground(new Color(231, 76, 60)); // Rojo
        } else if (simbolo == 'O') {
            botones[fila][columna].setForeground(new Color(52, 152, 219)); // Azul
        }
    }
    
    /**
     * Actualiza la etiqueta del turno
     */
    public void actualizarTurno(char turno) {
        etiquetaTurno.setText("Turno: " + turno);
    }
    
    /**
     * Reinicia visualmente el tablero
     */
    public void reiniciarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText(" ");
                botones[i][j].setEnabled(true);
            }
        }
    }
    
    /**
     * Deshabilita todos los botones (cuando termina el juego)
     */
    public void deshabilitarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }
    
    /**
     * Muestra un mensaje al usuario
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Triqui", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de error
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Pregunta al usuario dónde guardar la partida
     */
    public String solicitarRutaGuardar() {
        return JOptionPane.showInputDialog(this, 
            "Ingrese el nombre del archivo:", 
            "partida.dat");
    }
    
    /**
     * Pregunta al usuario de dónde cargar la partida
     */
    public String solicitarRutaCargar() {
        return JOptionPane.showInputDialog(this, 
            "Ingrese el nombre del archivo:", 
            "partida.dat");
    }
    
    /**
     * Muestra la ventana
     */
    public void mostrar() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Métodos para asignar listeners desde el controlador
    public void agregarListenerBoton(int fila, int columna, ActionListener listener) {
        botones[fila][columna].addActionListener(listener);
    }
    
    public void agregarListenerNuevoJuego(ActionListener listener) {
        itemNuevoJuego.addActionListener(listener);
    }
    
    public void agregarListenerGuardar(ActionListener listener) {
        itemGuardar.addActionListener(listener);
    }
    
    public void agregarListenerCargar(ActionListener listener) {
        itemCargar.addActionListener(listener);
    }
    
    public void agregarListenerSalir(ActionListener listener) {
        itemSalir.addActionListener(listener);
    }
}
