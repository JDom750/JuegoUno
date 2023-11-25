package Uno;

import java.util.ArrayList;
import java.util.Scanner;

public class UnoController {
    private Partida partida;
    private UnoView vista;

    public void iniciarJuego(String[] nombresJugadores) {
        this.partida = new Partida(nombresJugadores);
        this.vista = new UnoView(partida, this);

        // Agregar la llamada a start aquí
        this.partida.start(partida);

        distribuirCartas();
        jugar();
    }


    public void iniciarJuegoAutomatico(String[] nombresJugadores) {
        this.partida = new Partida(nombresJugadores);
        this.vista = new UnoView(partida, this);
        distribuirCartas();
        tirarCartaAutomatica();
        jugar();
    }

    private void distribuirCartas() {
        for (String jugador : partida.obtJugadores()) {
            System.out.println(jugador + ", tus cartas son:\n" +
                    partida.obtManoJugador(jugador));
            vista.actualizar();
        }
    }

    private void tirarCartaAutomatica() {
        try {
            partida.start(partida);
            mostrarInfo();
        } catch (Exception ex) {
            System.out.println("Error al tirar la carta automática: " + ex.getMessage());
        }
    }

    private void mostrarInfo() {
        System.out.println("Turno de: " + partida.obtenerJugadorActual());
        System.out.println("Cartas en mano:\n");

        for (String jugador : partida.obtJugadores()) {
            System.out.println(jugador + ": " + cartasEnMano(jugador) + " cartas");
        }

        System.out.println("\nÚltima carta jugada:\n" + partida.levantarCarta());
    }

    private String cartasEnMano(String jugador) {
        StringBuilder cartas = new StringBuilder();
        for (Carta carta : partida.obtManoJugador(jugador)) {
            cartas.append(carta).append(", ");
        }
        return cartas.toString();
    }

    private void jugar() {
        Scanner scanner = new Scanner(System.in);

        while (!partida.TerminoElJuego()) {
            String jugadorActual = partida.obtenerJugadorActual();
            ArrayList<Carta> mano = partida.obtManoJugador(jugadorActual);

            System.out.println("Turno de: " + jugadorActual);
            System.out.println("Cartas en mano:\n" + cartasEnMano(jugadorActual, mano));
            System.out.println("Última carta jugada:\n" + partida.levantarCarta());

            System.out.println("Elige la posición de la carta que deseas jugar (1, 2, 3, ...) o 0 para robar:");
            int posicion = scanner.nextInt();

            if (posicion == 0) {
                // El jugador decide robar una carta
                tomarCarta(jugadorActual);
            } else {
                // El jugador decide jugar una carta
                try {
                    Carta cartaSeleccionada = mano.get(posicion - 1);
                    jugarCarta(jugadorActual, cartaSeleccionada, null);
                } catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
                    System.out.println("Posición inválida. Intenta de nuevo.");
                }
            }
        }

        scanner.close();
    }

    private String cartasEnMano(String jugador, ArrayList<Carta> mano) {
        StringBuilder cartas = new StringBuilder();
        for (int i = 0; i < mano.size(); i++) {
            cartas.append(i + 1).append(": ").append(mano.get(i)).append("\n");
        }
        return cartas.toString();
    }

    public void tomarCarta(String jugador) {
        try {
            partida.tomarCarta(jugador);
        } catch (InvalidPlayerTurnException e) {
            mostrarMensajeError("Error de turno", e.getMessage());
        }
    }

    public void jugarCarta(String jugador, Carta carta, Carta.Color colorDeclarado) {
        try {
            partida.tomarCartaJugador(jugador, carta, colorDeclarado);
        } catch (InvalidColorSubmissionException | InvalidValueSubmissionException | InvalidPlayerTurnException e) {
            mostrarMensajeError("Movimiento inválido", e.getMessage());
        }
    }

    private void mostrarMensajeError(String titulo, String mensaje) {
        System.out.println(titulo + ": " + mensaje);
    }

    public Partida getPartida() {
        return partida;
    }
}
