package Uno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnoController {
    private Partida partida;
    private UnoView vista;  // Necesitarás una vista asociada al controlador

    public UnoController(String[] jugadores) {
        this.partida = new Partida(jugadores);
        this.vista = new UnoView(partida, this);  // Crea tu vista pasándole la partida y el controlador
        this.vista.mostrarVentana();
        iniciarJuego();
    }

    private void iniciarJuego() {
        // Inicia el juego aquí, puedes llamar a métodos en la partida según sea necesario
        partida.start(partida);
        // Actualiza la vista según el estado inicial del juego
        actualizarVista();
    }

    private void actualizarVista() {
        // Actualiza la vista con la información actualizada del modelo
        vista.actualizar();
    }

    public void tomarCarta(String jugador) {
        try {
            partida.tomarCarta(jugador);
            actualizarVista();
        } catch (InvalidPlayerTurnException e) {
            mostrarMensajeError("Error de turno", e.getMessage());
        }
    }

    public void jugarCarta(String jugador, Carta carta, Carta.Color colorDeclarado) {
        try {
            partida.tomarCartaJugador(jugador, carta, colorDeclarado);
            actualizarVista();
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

