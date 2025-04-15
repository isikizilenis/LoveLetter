package CardTypes;

import Game.Game;
import Game.ConsoleColors;

/**
 * Die CountessCard repräsentiert die Gräfin Karte in LoveLetter.
 * Sie hat den Namen "Gräfin" und den Wert 7.
 * Die Gräfin hat keinen wirklichen Effekt, muss nach Regeln aber gespielt werden, wenn zusätzlich in der Hand des Spielers ein König oder ein Prinz ist.
 * Es ist nicht verboten zu bluffen.
 */
public class CountessCard extends Cards {

    String name = super.name;

    /**
     * Konstruktor für die Gräfin-Karte
     */
    public CountessCard(){
        super("Gräfin", 7);
    }

    @Override
    public String getName() {
        return name;
    }


    /**
     * Die Gräfin hat keinen Effekt, muss aber gespielt werden, wenn in der Hand zusätzlich ein König oder ein Prinz vorliegt.
     * Diese Prüfung passiert in der playcard-Methode der Player-Klasse. Es ist jedoch nicht verboten zu bluffen.
     */
    @Override
    public void makeEffect() {
        System.out.println(ConsoleColors.CYAN+"\n---Effekt der GRÄFIN:--- \n\nWenn du zusätzlich König oder Prinz auf der Hand hast musst du die Gräfin spielen.\n"+ConsoleColors.RESET);
        Game.playerPointer = (Game.playerPointer+1) % Game.playersInstanceList.size();
    }
}
