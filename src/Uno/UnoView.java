package Uno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoView {
    private Partida partida;
    private UnoController controller;
    private JFrame ventana;
    private JTextArea infoTextArea;
    private JPanel mesaPanel;

    public UnoView(Partida partida, UnoController controller) {
        this.partida = partida;
        this.controller = controller;
    }

    public void mostrarVentana() {
        mostrarMenuInicio();
    }

    public void actualizar() {
        mostrarJuego();
    }

    private void mostrarMenuInicio() {
        JFrame frame = new JFrame("UNO - Menú de Inicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JLabel tituloLabel = new JLabel("UNO");
        tituloLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton iniciarJuegoButton = new JButton("Iniciar Juego");
        iniciarJuegoButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        iniciarJuegoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                controller.iniciarJuego(controller.getPartida().obtJugadores());
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

        panelPrincipal.add(Box.createVerticalGlue());
        panelPrincipal.add(tituloLabel);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(iniciarJuegoButton);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(salirButton);
        panelPrincipal.add(Box.createVerticalGlue());

        frame.getContentPane().add(panelPrincipal);
        frame.setVisible(true);
    }

    private void mostrarJuego() {
        ventana = new JFrame("UNO - " + controller.getPartida().obtenerJugadorActual());
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 600);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        mesaPanel = new JPanel();
        panelPrincipal.add(mesaPanel, BorderLayout.SOUTH);

        actualizarInfo();

        ventana.setContentPane(panelPrincipal);
        ventana.setVisible(true);
    }

    private void actualizarInfo() {
        infoTextArea.setText("Turno de: " + controller.getPartida().obtenerJugadorActual() + "\n");
        infoTextArea.append("Cartas en mano:\n");

        for (String jugador : controller.getPartida().obtJugadores()) {
            infoTextArea.append(jugador + ": " + cartasEnMano(jugador) + " cartas\n");
        }

        infoTextArea.append("\nÚltima carta jugada:\n" + partida.levantarCarta());
    }

    private String cartasEnMano(String jugador) {
        StringBuilder cartas = new StringBuilder();
        for (Carta carta : partida.obtManoJugador(jugador)) {
            cartas.append(carta).append(", ");
        }
        return cartas.toString();
    }

    private void mostrarMesa() {
        mesaPanel.removeAll();

        Carta ultimaCarta = partida.levantarCarta();
        JLabel etiquetaUltimaCarta = new JLabel(new ImageIcon(ultimaCarta.toString() + ".png"));
        mesaPanel.add(etiquetaUltimaCarta);

        ventana.revalidate();
        ventana.repaint();
    }
}
