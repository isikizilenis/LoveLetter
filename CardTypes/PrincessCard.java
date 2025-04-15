package CardTypes;

import Game.Game;
import Game.ConsoleColors;

/**
 * Die Prinzessin-Klasse repräsentiert die Prinzessin-Annette-Karte im Spiel.
 * Diese Karte hat den Wert 8 und hat einen speziellen Effekt: Wenn ein Spieler die Prinzessin ablegt,
 * scheidet er/sie aus der laufenden Runde aus.
 */
public class PrincessCard extends Cards {

    String name = super.name;

    /**
     * Konstruktor für die Prinzessin-Karte. Setzt den Kartenname auf "Prinzessin Annette" und den Wert auf 8.
     */
    public PrincessCard(){
        super("Prinzessin Annette", 8);
    }

    @Override
    public String getName() {
        return name;
    }


    /**
     * Hier wird der Effekt der Prinzessin-Karte implementiert. Wenn ein Spieler diese Karte ablegt, egal wie, scheidet dieser für den Rest der Runde aus.
     * Der Spieler wird aus der Liste der aktiven Spieler entfernt und die Runde wird ohne diesen fortgesetzt.
     */
    @Override
    public void makeEffect() {
        System.out.println(ConsoleColors.CYAN+"\n---Effekt der PRINZESSIN:--- \n\nDu scheidest aus der Runde aus, wenn du diese Karte ablegst.\n"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.RED+Game.currentPlayer.getName().toUpperCase()+" IST AUS DER RUNDE AUSGESCHIEDEN, WEIL ER/SIE DIE PRINZESSIN ABGELEGT HAT!"+ConsoleColors.RESET);
        Game.currentPlayer.inRound = false;
        Game.playersInstanceList.remove(Game.currentPlayer);
    }
}
