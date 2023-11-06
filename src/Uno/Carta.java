package Uno;

public class Carta {
    enum Color
    {
        ROJO, VERDE, AMARILLO, AZUL, NEGRO;

        private static final Color[] colores = Color.values();
        public static Color getColor(int i){
            return Color.colores[i]; //VER ESTO
        }
    }

    enum Numero{
        CERO,UNO,DOS,TRES,CUATRO,CINCO,SEIS,SIETE,OCHO,NUEVE,MASDOS,SALTARSE,CAMBIOSENTIDO,CAMBIOCOLOR,MASCUATRO;

        private static final Numero[] numeros = Numero.values();
        public static Numero getNumero(int i){
            return Numero.numeros[i];
        }
        }
    private final Color color;
    private final Numero numero;

    public Carta (final Color color, final Numero numero){
        this.numero = numero;
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    public Numero getNumero(){
        return this.numero;
    }

    public String toString(){
        return color + "_" + numero;
    }
}
