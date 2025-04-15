package CardDecks;

import CardTypes.*;
import Game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Die Klasse Deck repräsentiert ein Kartendeck für die Spielrunden.
 * Das Deck besteht aus einer Liste von Karten und kann verwendet werden, um Karten zu ziehen und zu mischen.
 * Es enthält eine vordefinierte Menge von Karten, die zu Beginn des Spiels zufällig gemischt werden.
 */
public class Deck {

    /**
     * Die Liste von Karten im Kartendeck.
     */
    public List<Cards> carddeck;

    /**
     * Erstellt eine neue Instanz des Kartendecks und initialisiert es mit einer vordefinierten Menge von Karten,
     * die zufällig gemischt werden.
     */
    public Deck(){

        // Das Kartendeck wird initialisiert.
        carddeck = new ArrayList<>();

        // 5 Wächterin Karten werden hinzugefügt.
        for(int i=0; i<5; i++){
            carddeck.add(new GuardCard());
        }

        // 2 Priester-Karten, 2 Baron-Karten, 2 Zofe-Karten und 2 Prinz-Karten werden hinzugefügt.
        for(int i=0; i<2; i++) {
            carddeck.add(new PriestCard());
            carddeck.add(new BaronCard());
            carddeck.add(new HandmaidCard());
            carddeck.add(new PrinceCard());
        }

        // Ein König, eine Gräfin und eine Prinzessin werden dem Kartendeck hinzugefügt.
        carddeck.add(new KingCard());
        carddeck.add(new CountessCard());
        carddeck.add(new PrincessCard());

        //Das Kartendeck wird gemischt.
        Collections.shuffle(carddeck);
    }

    /**
     * Zieht eine Karte aus dem Deck und entfernt sie aus dem Deck.
     * @return Die gezogene Karte oder null, wenn das Deck leer ist.
     */
    public Cards drawCard() throws InterruptedException {
        if (carddeck.isEmpty()) {
            System.out.println("\nDie Runde ist zu Ende, weil der Kartenstapel leer ist.");
            Game.handleNoCardsInDeckWin();
        } else {
            return carddeck.remove(0);
        }
        return null;
    }
}

