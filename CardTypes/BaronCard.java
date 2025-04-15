package CardTypes;

import Game.Game;
import Game.Player;
import Game.ConsoleColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Die BaronCard Klasse repräsentiert die Baron-Karte des Spiels Love Letter.
 * Sie hat den Namen "Baron" und den Wert 3.
 * Die Baron-Karte hat den Effekt, dass der Kartenwert der eigenen anderen Karte in der Hand, mit der eines ausgewählten Spielers verglichen wird.
 * Der Spieler mit dem niedrigeren Kartenwert scheidet aus. Haben beide Spieler den gleichen Kartenwert, scheidet keiner der Spieler aus.
 */
public class BaronCard extends Cards{

    String name = super.name;

    /**
     * Konstruktor der Baron-Karte Klasse.
     */
    public BaronCard(){
        super("Baron", 3);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Setzt den Karteneffekt der Baron-Karte um.
     * Der Spieler mit dem niedrigeren Kartenwert scheidet aus. Haben beide Spieler den gleichen Kartenwert, scheidet keiner der Spieler aus.
     */
    @Override
    public void makeEffect() {
        System.out.println(ConsoleColors.CYAN+"\n---Effekt des BARON:--- \n\nVergleiche deine andere Karte mit der eines Mitspielers. \nDer Spieler mit dem niedrigeren Wert scheidet aus.\n"+ConsoleColors.RESET);

        int playerchooseoptions = 1;
        System.out.println("Bitte wähle jetzt aus, mit wem du deine Karte vergleichen möchtest:");
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
        // Spielerauswahl-Modus wird betreten
        boolean choosingPlayer = true;
        while (choosingPlayer) {
            boolean everyoneElseProtected = true;
            for(Player p : ListOfOtherPlayers){
                if(p != Game.currentPlayer && !p.isProtected){
                    everyoneElseProtected = false;
                }
            }
            if(ListOfOtherPlayers.size() == 1 || everyoneElseProtected){
                System.out.println(ConsoleColors.YELLOW+"+++Es gibt keine Spieler zum Auswählen. Deine gewählte Karte wurde einfach abgespielt.+++"+ConsoleColors.RESET);
                choosingPlayer = false;
                Game.playerPointer = (Game.playerPointer+1) % Game.playersInstanceList.size();
            }
            else{
                try { //Try-Catch-Block zur Fehlerbehandlung von Zeichenketten
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
                            // Spielerauswahl-Modus wird verlassen.
                            choosingPlayer = false;

                            System.out.println(selectedPlayer.getName() + " hat die Karte " +ConsoleColors.PURPLE+ selectedPlayer.getHand().get(0).getName() + " (Wert: " + selectedPlayer.getHand().get(0).getValue()+ ")\n"+ConsoleColors.RESET +
                                    "Du hast die Karte " + ConsoleColors.PURPLE+Game.currentPlayer.getHand().get(0).getName() + " (Wert: " + Game.currentPlayer.getHand().get(0).getValue() + ")\n"+ConsoleColors.RESET);


                            if (selectedPlayer.getHand().get(0).getValue() > Game.currentPlayer.getHand().get(0).getValue()) {
                                System.out.println(ConsoleColors.RED+"DU SCHEIDEST AUS DER RUNDE AUS, WEIL DIE KARTE VON " + selectedPlayer.getName().toUpperCase() + " EINEN HÖHEREN WERT HAT."+ConsoleColors.RESET);
                                Game.currentPlayer.discardedCards.add(Game.currentPlayer.getHand().get(0));
                                Game.currentPlayer.inRound = false;
                                Game.playersInstanceList.remove(Game.currentPlayer);
                            }

                            else if (selectedPlayer.getHand().get(0).getValue() < Game.currentPlayer.getHand().get(0).getValue()) {
                                System.out.println(ConsoleColors.GREEN+selectedPlayer.getName().toUpperCase() + " SCHEIDET AUS DER RUNDE AUS, WEIL DEINE KARTE EINEN HÖHEREN WERT HAT."+ConsoleColors.RESET);
                                selectedPlayer.discardedCards.add(selectedPlayer.getHand().get(0));
                                selectedPlayer.inRound = false;
                                Game.playersInstanceList.remove(selectedPlayer);

                                if (Game.currentPlayer.getLastDate() < selectedPlayer.getLastDate()) {
                                    Game.playerPointer = (Game.playerPointer + 1) % Game.playersInstanceList.size();
                                }
                            }

                            else if (selectedPlayer.getHand().get(0).getValue() == Game.currentPlayer.getHand().get(0).getValue()) {
                                System.out.println(ConsoleColors.YELLOW+"DU UND " + selectedPlayer.getName().toUpperCase() + " HABT BEIDE DIE KARTE "+selectedPlayer.getHand().get(0).getName().toUpperCase()+" UND NIEMAND SCHEIDET AUS."+ConsoleColors.RESET);
                                Game.playerPointer = (Game.playerPointer + 1) % Game.playersInstanceList.size();
                            }
                        }
                    }
                    else {
                        System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du einen Spieler aus der Auswahlliste wählst."+ConsoleColors.RESET);
                    }
                }
                catch (Exception e){
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format! \nBitte gebe deine Auswahl IN ZIFFERN an, nicht in Buchstaben!"+ConsoleColors.RESET);
                }
            }
        }
    }
}
