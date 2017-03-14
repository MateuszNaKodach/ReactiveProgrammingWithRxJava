package wjug.cache;

import java.awt.*;

/**
 * Created by Mateusz on 14.03.2017.
 */
public class Card{

//Poczytaj tutaj o enum:
//https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
//Color ma tylko 4 wartosci i nic wiecej, kazda ma przypisane

    public static void main(String[] args) {
        new Card(Wartosc.Osemka,Kolor.Pik);
    }

    public enum Kolor{
        Kier(0),
        Karo(1),
        Trefl(2),
        Pik(3);


        private final int colourId; //getter, setter

        Kolor(int colourId){
            this.colourId = colourId;
        }


    }


    public enum Wartosc{
        AS(1),
    Dwojka(2),
            Trojka(3),
            Czworka(4),
            Piatka(5),
            Szostka(6),
            Siodemka(7),
            Osemka(8),
            Dziewiatka(9),
            Dziesiatka(10),
        Walet(11),
        Dama(12),
        Krol(13);

        private final int value;

        Wartosc(int value){
            this.value = value;
        }

    }


    Wartosc value;
    Kolor color;

    public Card(Wartosc value, Kolor color){
        this.value = value;
        this.color = color;
    }




}
