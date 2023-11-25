package Uno;

import java.util.Scanner;

public class UnoMenu {

    public static void main(String[] args) {
        UnoController controller = new UnoController();
        mostrarMenuInicio(controller);
    }

    public static void mostrarMenuInicio(UnoController controller) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MENÚ UNO ===");
        System.out.println("1. Iniciar Juego Manualmente");
        System.out.println("2. Iniciar Juego Automático");
        System.out.println("3. Salir");

        int opcion = obtenerOpcion(scanner);

        switch (opcion) {
            case 1:
                int cantidadJugadores = obtenerCantidadJugadores(scanner);
                if (cantidadJugadores > 0) {
                    String[] jugadores = obtenerNombresJugadores(scanner, cantidadJugadores);
                    if (jugadores != null) {
                        controller.iniciarJuego(jugadores);
                    }
                }
                break;
            case 2:
                int cantidadJugadoresAuto = obtenerCantidadJugadores(scanner);
                if (cantidadJugadoresAuto > 0) {
                    String[] jugadoresAuto = obtenerNombresJugadores(scanner, cantidadJugadoresAuto);
                    if (jugadoresAuto != null) {
                        controller.iniciarJuegoAutomatico(jugadoresAuto);
                    }
                }
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida.");
        }

        scanner.close();
    }

    private static int obtenerOpcion(Scanner scanner) {
        System.out.print("Seleccione una opción: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Ingrese un número válido.");
            System.out.print("Seleccione una opción: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static int obtenerCantidadJugadores(Scanner scanner) {
        int cantidadJugadores = -1;

        while (cantidadJugadores < 2 || cantidadJugadores > 10) {
            System.out.print("Ingrese la cantidad de jugadores (2-10): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Ingrese un número válido.");
                System.out.print("Ingrese la cantidad de jugadores (2-10): ");
                scanner.next();
            }
            cantidadJugadores = scanner.nextInt();

            if (cantidadJugadores < 2 || cantidadJugadores > 10) {
                System.out.println("Ingrese un número de jugadores válido (entre 2 y 10).");
            }
        }

        return cantidadJugadores;
    }

    private static String[] obtenerNombresJugadores(Scanner scanner, int cantidad) {
        String[] jugadores = new String[cantidad];

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Ingrese el nombre del Jugador " + (i + 1) + ": ");
            String nombre = scanner.next().trim();

            if (nombre.isEmpty()) {
                System.out.println("Ingrese un nombre válido para todos los jugadores.");
                return null;
            }

            jugadores[i] = nombre;
        }

        return jugadores;
    }
}
