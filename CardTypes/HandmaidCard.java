package CardTypes;

import Game.Game;
import Game.ConsoleColors;

import static Game.Game.currentPlayer;
import static Game.Game.playersInstanceList;

/**
 * Die Klasse HandmaidCard repräsentiert die Karte der Zofe im Love Letter Spiel.
 * Diese Karte hat den Namen "Zofe" und einen Wert von 4.
 * Wenn diese Karte gespielt wird, schützt sie den Spieler bis zu seinem nächsten Zug vor den Auswirkungen anderer Karten.
 */
public class HandmaidCard extends Cards{

    String name = super.name;

    /**
     * Konstruktor für die Zofe-Karte
     */
    public HandmaidCard(){
        super("Zofe", 4);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Führt den Effekt der HandmaidCard aus, der den Spieler bis zu seinem nächsten Zug vor den Auswirkungen anderer Karten schützt.
     * Der Spieler wird als "geschützt" markiert und erhält eine Benachrichtigung über den Schutz.
     */
    @Override
    public void makeEffect() {
        System.out.println(ConsoleColors.CYAN+"\n---Effekt der ZOFE:--- \n\nDu bist bis zu deinem nächsten Zug geschützt.\n"+ConsoleColors.RESET);

        Game.currentPlayer.setProtected(true);
        System.out.println(ConsoleColors.BLUE+currentPlayer.getName().toUpperCase()+" IST BIS ZU SEINEM NÄCHSTEN ZUG GESCHÜTZT!"+ConsoleColors.RESET);
        Game.playerPointer = (Game.playerPointer+1) % playersInstanceList.size();
    }
}