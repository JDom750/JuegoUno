package Uno;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Partida {
    /*
     Controlo de quien es el turno
     */
    private int jugadorActual;
    /*
    Llevo la lista de jugadores de la partida
    */
    private String[] idJugadores;
    /*
    defino el mazo
     */
    private Mazo mazo;
    /*
    Almaceno las manos de cada uno de los jugadores
     */
    private ArrayList<ArrayList<Carta>> manos;
    /*
    controlo la pila de cartas que fueron jugadasd
     */
    private ArrayList<Carta> pila;

    private Carta.Color colorValido;
    private Carta.Numero numeroValido;
    boolean gameDirection;

    public Partida(String[] pids){
        this.mazo = new Mazo();
        this.mazo.barajar();
        this.pila = new ArrayList<Carta>();
        this.idJugadores = pids;
        this.jugadorActual = 0;
        /*
        false sera la direccion por defecto. (antihorario)
         */
        gameDirection = false;
        this.manos = new ArrayList<ArrayList<Carta>>();

        for (int i=0; i< pids.length;i++){
            ArrayList<Carta> mano = new ArrayList<Carta>(Arrays.asList(mazo.robarCartas(7)));
            manos.add(mano);
        }
    }

    public void start(Partida partida){
        Carta carta = this.mazo.robarCarta();
        this.colorValido = carta.getColor();
        this.numeroValido = carta.getNumero();

        if(carta.getNumero() == Carta.Numero.CAMBIOCOLOR){
            start(partida);
        }
        if(carta.getNumero()== Carta.Numero.MASCUATRO || carta.getNumero() == Carta.Numero.MASDOS){
            start(partida);
        }
        if (carta.getNumero()==Carta.Numero.SALTARSE){
            JLabel mensaje = new JLabel(idJugadores[jugadorActual] + " Fue Salteado");
            mensaje.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, mensaje);

            if (gameDirection == false){
                jugadorActual = (jugadorActual +1)% idJugadores.length;
            } else if (gameDirection) {
                jugadorActual = (jugadorActual -1)% idJugadores.length;
                if (jugadorActual == -1){
                    jugadorActual = idJugadores.length -1;
                }
            }
        }
        if(carta.getNumero() == Carta.Numero.CAMBIOSENTIDO){
            JLabel mensaje = new JLabel(idJugadores[jugadorActual] + "El sentido cambio");
            mensaje.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, mensaje);
            gameDirection ^=true;
            jugadorActual = idJugadores.length -1;
        }
        pila.add(carta);
    }
}

//---------------------------------------
