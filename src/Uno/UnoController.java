package Uno;

import javax.swing.JOptionPane;

public class UnoController {
    private Partida partida;
    private UnoView vista;

    public UnoController() {
        mostrarMenuInicio();
    }

    private void mostrarMenuInicio() {
        UnoMenu.mostrarMenuInicio(this);
    }

    public void iniciarJuego(String[] nombresJugadores) {
        this.partida = new Partida(nombresJugadores);
        this.vista = new UnoView(partida, this);
//        this.vista.mostrarVentana();
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
