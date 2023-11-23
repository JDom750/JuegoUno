package Uno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoView {
    private Partida partida;
    private UnoController controller;

    private JFrame frame;
    private JButton tomarCartaButton;
    private JButton jugarCartaButton;

    public UnoView(Partida partida, UnoController controller) {
        this.partida = partida;
        this.controller = controller;

        // Configurar la interfaz gráfica
        frame = new JFrame("UNO Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Botón para tomar una carta
        tomarCartaButton = new JButton("Tomar Carta");
        tomarCartaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.tomarCarta(partida.obtenerJugadorActual());
            }
        });

        // Botón para jugar una carta
        jugarCartaButton = new JButton("Jugar Carta");
        jugarCartaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí deberías tener lógica para seleccionar una carta y declarar un color (si es necesario)
                // Por ahora, solo estamos llamando al método con valores predeterminados
                Carta carta = new Carta(Carta.Color.ROJO, Carta.Numero.UNO);
                Carta.Color colorDeclarado = Carta.Color.ROJO;
                controller.jugarCarta(partida.obtenerJugadorActual(), carta, colorDeclarado);
            }
        });

        // Agregar componentes al contenedor principal
        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());
        container.add(tomarCartaButton);
        container.add(jugarCartaButton);
    }

    public void mostrarVentana() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    // Método para actualizar la vista según el estado actual de la partida
    public void actualizar() {
        // Puedes agregar aquí lógica para actualizar la interfaz gráfica según el estado actual de la partida
        // Por ejemplo, actualizar la mano de los jugadores, la carta actual en la pila, etc.
    }
}

    // Otros métodos de la vista según sea necesario
