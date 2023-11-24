package Uno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoMenu {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MostrarMenu();
        });
    }

    public static void MostrarMenu() {
        JFrame frame = new JFrame("Menú UNO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel tituloLabel = new JLabel("UNO");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton iniciarJuegoButton = new JButton("Iniciar Juego");
        JButton salirButton = new JButton("Salir");

        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidadJugadores = obtenerCantidadJugadores();
                if (cantidadJugadores > 0) {
                    String[] jugadores = obtenerNombresJugadores(cantidadJugadores);
                    if (jugadores != null) {
                        UnoController controller = new UnoController(jugadores);
                        UnoView view = new UnoView(controller.getPartida(), controller);
                        view.mostrarVentana();
                        frame.dispose();
                    }
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(Box.createVerticalStrut(20));  // Separación
        panel.add(tituloLabel);
        panel.add(Box.createVerticalStrut(20));  // Separación
        panel.add(iniciarJuegoButton);
        panel.add(Box.createVerticalStrut(20));  // Separación
        panel.add(salirButton);

        frame.add(panel);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.setVisible(true);
    }

    private static int obtenerCantidadJugadores() {
        String cantidadJugadoresStr = JOptionPane.showInputDialog("Ingrese la cantidad de jugadores (2-10):");
        try {
            int cantidadJugadores = Integer.parseInt(cantidadJugadoresStr);
            if (cantidadJugadores >= 2 && cantidadJugadores <= 10) {
                return cantidadJugadores;
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese un número de jugadores válido (entre 2 y 10).", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido para la cantidad de jugadores.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private static String[] obtenerNombresJugadores(int cantidad) {
        String[] jugadores = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del Jugador " + (i + 1) + ":");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un nombre válido para todos los jugadores.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            jugadores[i] = nombre;
        }
        return jugadores;
    }
}
