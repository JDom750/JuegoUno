package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;

public class Mazo {
    private Carta[] cartas;
    private int cartasEnMazo;

    public Mazo() {
        cartas = new Carta[108];
        reiniciar();
    }

    public void reiniciar() {
        Carta.Color[] colores = Carta.Color.values();
        cartasEnMazo = 0;

        for (int i = 0; i < colores.length - 1; i++) {
            Carta.Color color = colores[i];

            // Agregar 1 carta con numero cero a cada color
            cartas[cartasEnMazo++] = new Carta(color, Carta.Numero.getNumero(0));

            // Agregar 2 cartas del 1 al 9
            for (int j = 1; j < 10; j++) {
                cartas[cartasEnMazo++] = new Carta(color, Carta.Numero.getNumero(j));
                cartas[cartasEnMazo++] = new Carta(color, Carta.Numero.getNumero(j));
            }

            // Agregar MASDOS, SALTAR y INVERTIR
            Carta.Numero[] valores = new Carta.Numero[]{Carta.Numero.MASDOS, Carta.Numero.SALTARSE, Carta.Numero.CAMBIOSENTIDO};
            for (Carta.Numero valor : valores) {
                cartas[cartasEnMazo++] = new Carta(color, valor);
                cartas[cartasEnMazo++] = new Carta(color, valor);
            }
        }

        // Agregar Cartas especiales: CAMBIOCOLOR y MASCUATRO
        Carta.Numero[] valoresEspeciales = new Carta.Numero[]{Carta.Numero.CAMBIOCOLOR, Carta.Numero.MASCUATRO};
        for (Carta.Numero valor : valoresEspeciales) {
            for (int i = 0; i < 4; i++) {
                cartas[cartasEnMazo++] = new Carta(Carta.Color.NEGRO, valor);
            }
        }
    }
// --------------------------------------------------------------------

    /**
     *
     * Recibe un array list de Cartas y reemplaza el mazo con ese. Cuando se juega al uno tenes un mazo desde el que sacas cartas pero cuando
     * el mazo de agota tengo una pila de cartas con la que voy a reemplazar el mazo
     */
    public void reemplazarMazoCon(ArrayList<Carta> cartas) {
        this.cartas = cartas.toArray(new Carta[cartas.size()]);
        this.cartasEnMazo = this.cartas.length;
    }

    public boolean estaVacio() {
        return cartasEnMazo == 0;
    }

    public void barajar() {
        int n = cartas.length;
        Random random = new Random();

        for (int i = 0; i < cartas.length; i++) {
            // Obtener un índice aleatorio en el array más allá del índice actual
            // ... El argumento es un límite exclusivo
            // Intercambiar el elemento aleatorio con el elemento actual
            int valorAleatorio = i + random.nextInt(n - i);
            Carta cartaAleatoria = cartas[valorAleatorio];
            cartas[valorAleatorio] = cartas[i];
            cartas[i] = cartaAleatoria;
        }
    }

    public Carta robarCarta() throws IllegalArgumentException {
        if (estaVacio()) {
            throw new IllegalArgumentException("No se puede robar una carta, ya que no hay cartas en el mazo.");
        }
        return cartas[--cartasEnMazo];
    }

    //----- PARA GUI
    public ImageIcon robarImagenCarta() throws IllegalArgumentException {
        if (estaVacio()) {
            throw new IllegalArgumentException("No se puede robar una carta, ya que el mazo está vacío.");
        }
        return new ImageIcon(cartas[--cartasEnMazo].toString() + ".png");
    }

    public Carta[] robarCartas(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Debe robar una cantidad positiva de cartas, pero se intentó robar " + n + " cartas.");
        }

        if (n > cartasEnMazo) {
            throw new IllegalArgumentException("No se pueden robar " + n + " cartas, ya que solo hay " + cartasEnMazo + " cartas en el mazo.");
        }

        Carta[] cartasRobadas = new Carta[n];

        for (int i = 0; i < n; i++) {
            cartasRobadas[i] = cartas[--cartasEnMazo];
        }
        return cartasRobadas;
    }
}

