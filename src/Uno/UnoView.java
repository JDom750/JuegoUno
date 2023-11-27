package Uno;

import java.util.ArrayList;
import java.util.Scanner;


public class UnoView {
    private Partida partida;
    private UnoController controller;
    private Scanner scanner;

    public UnoView(Partida partida, UnoController controller) {
        this.partida = partida;
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void actualizar() {
        System.out.println("Turno de: " + partida.obtenerJugadorActual());
        System.out.println("Cartas en mano:");
        for (String jugador : partida.obtJugadores()) {
            System.out.println(jugador + ": " + cartasEnMano(jugador));
        }
        System.out.println("\nÚltima carta jugada:");
        System.out.println(partida.levantarCarta());
        System.out.println("\nElige la posición de la carta que deseas jugar (1, 2, 3, ...) o 0 para robar:");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        if (opcion == 0) {
            controller.tomarCarta(partida.obtenerJugadorActual());
        } else {
            Carta cartaSeleccionada = partida.obtManoJugador(partida.obtenerJugadorActual()).get(opcion - 1);
            System.out.println("La carta seleccionada es: " + cartaSeleccionada.getColor().toString() + " y el numero es: " + cartaSeleccionada.getNumero().toString());
            if (cartaSeleccionada.getColor() != Carta.Color.NEGRO) {
                controller.jugarCarta(partida.obtenerJugadorActual(), cartaSeleccionada,null);
            } else {
                // El jugador tiene una carta negra, pregunta por el color
                System.out.println("Elige un color (ROJO, VERDE, AMARILLO, AZUL):");
                String colorElegido = scanner.next();
                //scanner.nextLine();
                Carta.Color colorDeclarado = Carta.Color.valueOf(colorElegido.toUpperCase());
                controller.jugarCarta(partida.obtenerJugadorActual(), cartaSeleccionada, colorDeclarado);
            }
        }
    }


    private String cartasEnMano(String jugador) {
        StringBuilder cartas = new StringBuilder();
        int i = 1;
        for (Carta carta : partida.obtManoJugador(jugador)) {
            cartas.append(i).append(": ").append(carta).append(", ");
            i++;
        }
        return cartas.toString();
    }
}

