package CardTypes;

import Game.Game;
import Game.Player;
import Game.ConsoleColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Die Klasse PriestCard repräsentiert die Karte für den Priester in Love Letter.
 * Diese Karte hat den Namen "Priester" und einen Wert von 2.
 * Wenn diese Karte gespielt wird, ermöglicht sie dem Spieler, die Handkarte eines anderen Spielers anzusehen.
 */
public class PriestCard extends Cards {

    String name = super.name;

    /**
     * Konstruktor für die PristCard
     */
    public PriestCard() {
        super("Priester", 2);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Hier wird der Effekt der PriestCard implementiert, der es dem Spieler ermöglicht, die Handkarte eines anderen Spielers anzusehen.
     * Der Spieler wählt den anderen Spieler aus, dessen Handkarte er sehen möchte.
     * Die ausgewählte Handkarte des anderen Spielers wird dann dem aktuellen Spieler angezeigt.
     */
    @Override
    public void makeEffect() {
        System.out.println(ConsoleColors.CYAN+"\n---Effekt des PRIESTERS:--- \n\nSchaue dir die Handkarte eines Spielers an.\n\n" +ConsoleColors.RESET+
                "Bitte wähle jetzt aus, wessen Karte du sehen möchtest:\n");

        // Es wird eine Auswahlliste erstellt mit den Gegenspielern, aus denen man im Spielerauswahl-Modus dann auswählen kann.
        List<Player> ListOfOtherPlayers = new ArrayList<>();
        int playerchooseoptions = 1;
        for (Player other : Game.playersInstanceList) {
            if(other == Game.currentPlayer){
                System.out.println("("+playerchooseoptions+") "+other.getName()+" (du selbst)");
            }
            else if (other.isProtected) {
                System.out.println("("+playerchooseoptions+") "+other.getName()+" (geschützt)");
            }
            else {
                System.out.println("("+playerchooseoptions+") "+other.getName());
            }
            playerchooseoptions++;
            ListOfOtherPlayers.add(other);
        }

        // Der Spielerauswahl-Modus wird betreten
        boolean choosingPlayer = true;
        while (choosingPlayer) {
            boolean everyoneElseProtected = true;
            for(Player p : ListOfOtherPlayers){
                if(p != Game.currentPlayer && !p.isProtected){
                    everyoneElseProtected = false;
                }
            }
            // Hier wird der Fall abgedeckt, dass alle Spieler geschützt bzw. nicht auswählbar sind.
            if(ListOfOtherPlayers.size() == 1 || everyoneElseProtected){
                System.out.println(ConsoleColors.YELLOW+"+++Es gibt keine Spieler zum Auswählen. Deine gewählte Karte wurde einfach abgespielt.+++"+ConsoleColors.RESET);
                choosingPlayer = false;
                Game.playerPointer = (Game.playerPointer+1) % Game.playersInstanceList.size();
            }

            // Dies ist der Normalfall. Sollte die Liste der Mitspieler nicht leer sein, wird der Karteneffekt normal ausgeführt.
            else{
                try{

                    // Ein neuer Scanner wird erstellt, der eine Mitspieler-Auswahl einliest
                    System.out.print(">> ");
                    Scanner playerSelector = new Scanner(System.in);
                    int playerchoicePlayer = playerSelector.nextInt();

                    // Hier wird der Effekt des Priesters durchgeführt und die Karte des gewählten Spielers eingesehen
                    if (playerchoicePlayer >= 1 && playerchoicePlayer <= playerchooseoptions) {
                        Player selectedPlayer = ListOfOtherPlayers.get(playerchoicePlayer - 1);
                        if(selectedPlayer == Game.currentPlayer){
                            System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Du darfst dich nicht selbst wählen!"+ConsoleColors.RESET);
                        }
                        else if (selectedPlayer.isProtected) {
                            System.out.println(ConsoleColors.BLUE+"###SYSTEM: Dieser Spieler ist durch die Zofe geschützt!"+ConsoleColors.RESET);
                        }
                        else{
                            System.out.println("Du hast die Karte von " +selectedPlayer.getName()+ " angeschaut.\n"
                                    +ConsoleColors.PURPLE+selectedPlayer.getName().toUpperCase()+" HAT DIE KARTE " +selectedPlayer.getHand().get(0).getName().toUpperCase()+"."+ConsoleColors.RESET);

                            // Der Spieler-Pointer wird erhöht und der Spielerauswahl-Modus wird verlassen.
                            choosingPlayer = false;
                            Game.playerPointer = (Game.playerPointer + 1) % Game.playersInstanceList.size();
                        }
                    }

                    // Fehlerbehandlung für ungültige Ziffer
                    else {
                        System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du einen Spieler aus der Auswahlliste wählst."+ConsoleColors.RESET);
                    }
                }

                // Fehlerbehandlung, wenn man einen String eingibt.
                catch (Exception e){
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format! \nBitte gebe deine Spielerauswahl IN ZIFFERN an, nicht in Buchstaben!"+ConsoleColors.RESET);
                }
            }
        }
    }
}
