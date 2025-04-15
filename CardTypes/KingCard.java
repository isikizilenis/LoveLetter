package CardTypes;

import Game.Game;
import Game.Player;
import Game.ConsoleColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Die Klasse KingCard repräsentiert die König-Karte in Love Letter.
 * Diese Karte hat den Namen "König" und einen Wert von 6.
 * Wenn diese Karte gespielt wird, ermöglicht sie dem currentPlayer, seine Handkarte mit der eines anderen Spielers zu tauschen.
 */
public class KingCard extends Cards {

    String name = super.name;

    /**
     * Konstruktor der König-Karte
     */
    public KingCard(){
        super("König", 6);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Führt den Effekt der KingCard aus, der es dem Spieler ermöglicht, seine Handkarte mit der eines anderen Spielers zu tauschen.
     * Der aktuelle Spieler wählt den anderen Spieler aus, mit dem er die Karten tauschen möchte.
     * Der Tausch wird dann durchgeführt, und dem aktuellen Spieler angezeigt.
     */
    @Override
    public void makeEffect() {
        int playerchooseoptions = 1;
        System.out.println(ConsoleColors.CYAN+"\n---Effekt des KÖNIG:--- \n\nTausche deine Handkarte mit der eines Mitspielers\n"+ConsoleColors.RESET);
        System.out.println("Bitte wähle jetzt aus, mit wem du die Karte tauschen möchtest:\n");
        List<Player> ListOfOtherPlayers = new ArrayList<>();
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
        boolean choosingPlayer = true;
        while (choosingPlayer) {
            boolean everyoneElseProtected = true;
            for(Player p : ListOfOtherPlayers){
                if (p != Game.currentPlayer && !p.isProtected) {
                    everyoneElseProtected = false;
                }
            }
            if(ListOfOtherPlayers.size() == 1 || everyoneElseProtected){
                System.out.println(ConsoleColors.YELLOW+"\n+++Es gibt keine Spieler zum Auswählen. Deine gewählte Karte wurde einfach abgespielt.+++"+ConsoleColors.RESET);
                choosingPlayer = false;
                Game.playerPointer = (Game.playerPointer+1) % Game.playersInstanceList.size();
            }
            else{
                try{
                    System.out.print(">> ");
                    Scanner playerSelector = new Scanner(System.in);
                    int playerchoicePlayer = playerSelector.nextInt();
                    if (playerchoicePlayer >= 1 && playerchoicePlayer <= playerchooseoptions) {
                        Player selectedPlayer = ListOfOtherPlayers.get(playerchoicePlayer - 1);
                        if(selectedPlayer == Game.currentPlayer){
                            System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Du darfst dich nicht selbst wählen!"+ConsoleColors.RESET);
                        }
                        else if (selectedPlayer.isProtected) {
                            System.out.println(ConsoleColors.BLUE+"###SYSTEM: Dieser Spieler ist durch die Zofe geschützt!"+ConsoleColors.RESET);
                        }
                        else{
                            //Hier wird der Karten Effekt des Königs realisiert. Es wird die eigene Karte gegen die eines anderen gewählten Spielers getauscht
                            Cards currentPlayerCard = Game.currentPlayer.getHand().get(0);
                            Cards selectedPlayerCard = selectedPlayer.getHand().get(0);
                            Game.currentPlayer.getHand().set(0, selectedPlayerCard);
                            selectedPlayer.getHand().set(0, currentPlayerCard);
                            System.out.println(ConsoleColors.PURPLE+"DU HAST SPIELER "+selectedPlayer.getName().toUpperCase()+" AUSGEWÄHLT UND DEINE "
                                    +currentPlayerCard.getName().toUpperCase()+"-KARTE \nGEGEN EINE "
                                    +selectedPlayerCard.getName().toUpperCase()+"-KARTE GETAUSCHT."+ConsoleColors.RESET);

                            choosingPlayer = false;
                            Game.playerPointer = (Game.playerPointer + 1) % Game.playersInstanceList.size();
                        }
                    }
                    else {
                        System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du einen Spieler aus der Auswahlliste wählst."+ConsoleColors.RESET);
                    }
                }
                catch (Exception e){
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format! \nBitte gebe deine Spielerauswahl IN ZIFFERN an, nicht in Buchstaben!"+ConsoleColors.RESET);
                }
            }
        }
    }
}
