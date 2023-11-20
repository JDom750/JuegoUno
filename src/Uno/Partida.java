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
            //XOR or equal
            gameDirection ^=true;
            jugadorActual = idJugadores.length -1;
        }
        pila.add(carta);
    }

    public Carta levantarCarta(){
        return new Carta(colorValido, numeroValido);
    }

    public ImageIcon robarImagenCarta(){
        return new ImageIcon(colorValido +"-" + numeroValido +".png");
    }

    public boolean TerminoElJuego(){
        for(String jugador : this.idJugadores){
            if(manoVacia(jugador)){
                return true;
            }
        }
        return false;
    }
    public String obtenerJugadorActual(){
        return this.idJugadores[this.jugadorActual];
    }

    public String jugadorAnterior(int i){
        int index = this.jugadorActual -1;
        if (index==-1){
            index=idJugadores.length -1;
        }
        return this.idJugadores[index];
    }
    public String[] obtJugadores(){
        return idJugadores;
    }
    public  ArrayList<Carta> obtManoJugador(String pid){
        int index= Arrays.asList(idJugadores).indexOf(pid);
        return manos.get(index);
    }

    public int tamManoJugador(String pid){
        return obtManoJugador(pid).size();
    }

    public Carta obtCartaJugador(String pid, int o){
        ArrayList<Carta> manoJ = obtManoJugador(pid);
        return manoJ.get(o);
    }
    public boolean manoVacia(String pid){
        return obtManoJugador(pid).isEmpty();
    }

    public boolean cartaValida(Carta c){
        return c.getColor() == colorValido || c.getNumero() == numeroValido;
    }
    public void esTurno(String pid) throws InvalidPlayerTurnException{
        if(this.idJugadores[this.jugadorActual] != pid){
            throw new InvalidPlayerTurnException("No es el turno de "+ pid, pid);
        }
    }
    public void tomarCarta(String pid) throws InvalidPlayerTurnException {
        esTurno(pid);

        if(mazo.estaVacio()){
            mazo.reemplazarMazoCon(pila);
            mazo.barajar();
        }
        obtManoJugador(pid).add(mazo.robarCarta());
        if(gameDirection == false) {
            jugadorActual = (jugadorActual +1) % idJugadores.length;
        }
        else if(gameDirection == true){
            jugadorActual = (jugadorActual-1)%idJugadores.length;
            if (jugadorActual ==-1){
                jugadorActual = idJugadores.length -1;
            }
        }
    }
    public void setColorDeCarta(Carta.Color c){
        colorValido = c;
    }

    public void tomarCartaJugador(String pid, Carta carta, Carta.Color colorDeclarado)
        throws InvalidColorSubmissionException, InvalidValueSubmissionException, InvalidPlayerTurnException{
            esTurno(pid);

            ArrayList<Carta> manoJu = obtManoJugador(pid);

            if(!cartaValida(carta)){
                if(carta.getColor() == Carta.Color.NEGRO){
                    colorValido = carta.getColor();
                    numeroValido = carta.getNumero();
                }

                if (carta.getColor() != colorValido){
                    JLabel message = new JLabel("movimiento invalido, color esperado: "+ colorValido + " pero se ingreso el color "+carta.getColor());
                    message.setFont(new Font("Arial", Font.BOLD, 48));
                    JOptionPane.showMessageDialog(null, message);
                    throw new InvalidColorSubmissionException("movimiento invalido, color esperado: "+ colorValido + " pero se ingreso el color "+carta.getColor(), carta.getColor(),colorValido);

                }
                else if(carta.getNumero()!= numeroValido){
                    JLabel message2 = new JLabel("movimiento invalido, numero esperado: "+ numeroValido + " pero se ingreso el numero "+carta.getNumero());
                    message2.setFont(new Font("Arial", Font.BOLD, 48));
                    JOptionPane.showMessageDialog(null, message2);
                    throw new InvalidValueSubmissionException("movimiento invalido, numero esperado: "+ numeroValido + " pero se ingreso el numero "+carta.getNumero(), carta.getNumero(), numeroValido);
                }
            }

            manoJu.remove(carta);

            if(manoVacia(this.idJugadores[jugadorActual])){
                JLabel message3 = new JLabel("El jugador actual: "+ this.idJugadores[jugadorActual] +" gano ");
                message3.setFont(new Font("Arial", Font.BOLD, 48));
                JOptionPane.showMessageDialog(null, message3);
                System.exit(0);
            }

            colorValido = carta.getColor();
            numeroValido = carta.getNumero();
            pila.add(carta);

            if(gameDirection == false){
                jugadorActual = (jugadorActual +1)% idJugadores.length;
            }
            else if (gameDirection == true){
                jugadorActual = (jugadorActual -1)% idJugadores.length;
                if(jugadorActual == -1){
                    jugadorActual = idJugadores.length-1;
                }
            }

            if(carta.getColor() == Carta.Color.NEGRO){
                colorValido = colorDeclarado;
            }

            if(carta.getNumero() == Carta.Numero.MASDOS){
                pid= idJugadores[jugadorActual];
                obtManoJugador(pid).add(mazo.robarCarta());
                obtManoJugador(pid).add(mazo.robarCarta());
                JLabel ms = new JLabel(pid +"Tomo 2 cartas");
            }
            if (carta.getNumero() == Carta.Numero.MASCUATRO){
                pid= idJugadores[jugadorActual];
                obtManoJugador(pid).add(mazo.robarCarta());
                obtManoJugador(pid).add(mazo.robarCarta());
                obtManoJugador(pid).add(mazo.robarCarta());
                obtManoJugador(pid).add(mazo.robarCarta());
                JLabel ms = new JLabel(pid +"Tomo 4 cartas");

            }
            if(carta.getNumero() == Carta.Numero.SALTARSE){
                JLabel message = new JLabel(idJugadores[jugadorActual]+" fue salteado");
                message.setFont(new Font("Arial",Font.BOLD,48));
                JOptionPane.showMessageDialog(null, message);
                if(gameDirection == false){
                    jugadorActual = (jugadorActual+1)%idJugadores.length;
                }
                else if(gameDirection == true){
                    jugadorActual = (jugadorActual-1)%idJugadores.length;
                    if(jugadorActual == -1){
                        jugadorActual = idJugadores.length -1;
                    }
                }
            }

            if(carta.getNumero()==Carta.Numero.CAMBIOSENTIDO){
                JLabel message = new JLabel("La Direccion del juego fue invertida por: "+ pid);
                message.setFont(new Font("Arial",Font.BOLD,48));
                JOptionPane.showMessageDialog(null, message);

                gameDirection ^= true;
                if(gameDirection == true){
                    jugadorActual = (jugadorActual -2)%idJugadores.length;
                    if (jugadorActual==-1){
                        jugadorActual= idJugadores.length -1;
                    }
                    if (jugadorActual==-2){
                        jugadorActual= idJugadores.length -2;
                    }
                }
                else if(gameDirection==false){
                    jugadorActual=(jugadorActual+2)% idJugadores.length;
                }
            }
    }
}

//---------------------------------------
class InvalidPlayerTurnException extends Exception {
    String id_jugador;

    public InvalidPlayerTurnException (String m, String pid){
        super(m);
        id_jugador=pid;
    }

    public String getPid() {
        return id_jugador;
    }
}

class InvalidColorSubmissionException extends Exception {
    private Carta.Color esperado;
    private Carta.Color actual;

    public InvalidColorSubmissionException(String m, Carta.Color actual, Carta.Color esperado){
        this.actual = actual;
        this.esperado = esperado;
    }
}

class InvalidValueSubmissionException extends Exception {
    private Carta.Numero esperado;
    private Carta.Numero actual;

    public InvalidValueSubmissionException(String m, Carta.Numero actual, Carta.Numero esperado){
        this.actual = actual;
        this.esperado = esperado;
    }
}