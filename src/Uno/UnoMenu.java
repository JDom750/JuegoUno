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
        JFrame frame = new JFrame("Menú de Jugadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField jugador1Field = new JTextField(20);
        JTextField jugador2Field = new JTextField(20);
        JTextField jugador3Field = new JTextField(20);

        JButton iniciarJuegoButton = new JButton("Iniciar Juego");
        JButton salirButton = new JButton("Salir");

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jugador1 = jugador1Field.getText();
                String jugador2 = jugador2Field.getText();
                String jugador3 = jugador3Field.getText();

                // Validar que se hayan ingresado nombres de jugadores
                if (!jugador1.isEmpty() && !jugador2.isEmpty() && !jugador3.isEmpty()) {
                    // Aquí puedes crear tu controlador y vista, y comenzar el juego
                    String[] jugadores = {jugador1, jugador2, jugador3};
                    UnoController controller = new UnoController(jugadores);
                    UnoView view = new UnoView(controller.getPartida(), controller);
                    view.mostrarVentana();
                    frame.dispose();  // Cerrar el menú después de iniciar el juego
                } else {
                    JOptionPane.showMessageDialog(frame, "Ingrese nombres para todos los jugadores.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(new JLabel("Jugador 1:"));
        panel.add(jugador1Field);
        panel.add(new JLabel("Jugador 2:"));
        panel.add(jugador2Field);
        panel.add(new JLabel("Jugador 3:"));
        panel.add(jugador3Field);
        panel.add(Box.createVerticalStrut(10));  // Separación
        panel.add(iniciarJuegoButton);
        panel.add(Box.createVerticalStrut(10));  // Separación
        panel.add(salirButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
