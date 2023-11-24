package Uno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;  // Agregado
import java.awt.Dimension;  // Agregado

public class UnoController {
    private Partida partida;
    private UnoView vista;  // Necesitarás una vista asociada al controlador

    public UnoController() {
        mostrarMenuInicio();
    }

    private void mostrarMenuInicio() {
        JFrame frame = new JFrame("UNO - Menú de Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel tituloLabel = new JLabel("UNO");
        tituloLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton iniciarJuegoButton = new JButton("Iniciar Juego");
        iniciarJuegoButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoConfiguracion();
                frame.dispose();
            }
        });

        JButton salirButton = new JButton("Salir del Juego");
        salirButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(tituloLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(iniciarJuegoButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(salirButton);
        panel.add(Box.createVerticalGlue());

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void mostrarDialogoConfiguracion() {
        JTextField cantidadJugadoresField = new JTextField();
        JTextField nombresJugadoresField = new JTextField();

        Object[] message = {
                "Cantidad de Jugadores:", cantidadJugadoresField,
                "Nombres de Jugadores (separados por comas):", nombresJugadoresField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Configuración del Juego", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String[] nombresJugadores = nombresJugadoresField.getText().split(",");
            if (nombresJugadores.length >= 2 && nombresJugadores.length <= 10) {
                iniciarJuego(nombresJugadores);
            } else {
                mostrarMensajeError("Error", "Debe haber entre 2 y 10 jugadores.");
                mostrarDialogoConfiguracion();  // Vuelve a mostrar el diálogo
            }
        } else {
            System.exit(0);  // Salir si el usuario cancela
        }
    }

    public void iniciarJuego(String[] nombresJugadores) {
        this.partida = new Partida(nombresJugadores);
        this.vista = new UnoView(partida, this);  // Crea tu vista pasándole la partida y el controlador
        this.vista.mostrarVentana();
        distribuirCartas();
    }

    private void distribuirCartas() {
        for (String jugador : partida.obtJugadores()) {
            JOptionPane.showMessageDialog(null, jugador + ", tus cartas son:\n" +
                    partida.obtManoJugador(jugador), "Cartas de " + jugador, JOptionPane.INFORMATION_MESSAGE);
            vista.actualizar();
        }
    }

    public void tomarCarta(String jugador) {
        try {
            partida.tomarCarta(jugador);
            vista.actualizar();
        } catch (InvalidPlayerTurnException e) {
            mostrarMensajeError("Error de turno", e.getMessage());
        }
    }

    public void jugarCarta(String jugador, Carta carta, Carta.Color colorDeclarado) {
        try {
            partida.tomarCartaJugador(jugador, carta, colorDeclarado);
            vista.actualizar();
        } catch (InvalidColorSubmissionException | InvalidValueSubmissionException | InvalidPlayerTurnException e) {
            mostrarMensajeError("Movimiento inválido", e.getMessage());
        }
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }

    public Partida getPartida() {
        return partida;
    }
}
