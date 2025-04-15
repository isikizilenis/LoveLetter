package CardTypes;

import Game.Game;
import Game.Player;
import Game.ConsoleColors;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * Die PrinceCard-Klasse repräsentiert die Prinzenkarte im Love Letter-Spiel.
 * Diese Karte hat den Wert 5 und hat einen speziellen Effekt: Der Spieler, der die Prinzenkarte spielt, wählt einen anderen Spieler aus, der seine Handkarte ablegen und eine neue Karte ziehen muss.
 * Wenn die abgelegte Karte des ausgewählten Spielers eine Prinzessin (Wert 8) ist, scheidet dieser Spieler für den Rest der Runde aus. Andernfalls zieht der ausgewählte Spieler eine neue Karte vom Deck.
 */
public class PrinceCard extends Cards {

    String name = super.name;


    /**
     * Konstruktor für die Prinzenkarte. Setzt den Kartennamen auf "Prinz" und den Wert auf 5.
     */
    public PrinceCard() {
        super("Prinz", 5);
    }

    @Override
    public String getName() {
        return name;
    }


    /**
     * Effekt der Prinz-Karte: Der Spieler, der die Prinz-Karte spielt, wählt einen anderen Spieler aus, der seine Handkarte ablegen und eine neue Karte ziehen muss.
     * Wenn die abgelegte Karte des ausgewählten Spielers eine Prinzessin (Wert 8) ist, scheidet dieser Spieler aus. Andernfalls zieht der ausgewählte Spieler eine neue Karte vom Deck.
     */
    @Override
    public void makeEffect() {
        System.out.println(ConsoleColors.CYAN + "\n---Effekt des PRINZEN:--- \n\n" +
                "Wähle einen Spieler, der seine Handkarte ablegt und eine neue Karte zieht.\n\n" + ConsoleColors.RESET +
                "Bitte wähle jetzt aus, wer eine neue Karte vom Deck ziehen soll. Du kannst dich auch selber wählen:\n");

        // Hier wird ein Spielerauswahlmenü erstellt.
        int playerchooseoptions = 1;
        List<Player> ListOfOtherPlayers = new ArrayList<>();
        for (Player other : Game.playersInstanceList) {
            if (other == Game.currentPlayer) {
                System.out.println("(" + playerchooseoptions + ") " + other.getName() + " (du selbst)");
            } else if (other.isProtected) {
                System.out.println("(" + playerchooseoptions + ") " + other.getName() + " (geschützt)");
            } else {
                System.out.println("(" + playerchooseoptions + ") " + other.getName());
            }
            playerchooseoptions++;
            ListOfOtherPlayers.add(other);
        }

        // Hier wird der Spielerauswahl-Modus betreten und erst nach erfolgreicher Auswahl aus der oben erstellten Liste wieder verlassen,
        boolean choosingPlayer = true;
        while (choosingPlayer) {
            try {
                System.out.print(">> ");
                Scanner playerSelector = new Scanner(System.in);
                int playerchoicePlayer = playerSelector.nextInt();
                if (playerchoicePlayer >= 1 && playerchoicePlayer <= playerchooseoptions) {
                    Player selectedPlayer = ListOfOtherPlayers.get(playerchoicePlayer - 1);
                    // Hier wird der Karteneffekt des Prinzen realisiert. Der vom currentPlayer ausgewählte Spieler
                    // muss seine aktuelle Karte ablegen und eine neue Karte ziehen.selectedPlayer.discardedCards.add(selectedPlayer.getHand().get(0));
                    if (selectedPlayer.isProtected) {
                        System.out.println(ConsoleColors.BLUE + "###SYSTEM: Dieser Spieler ist durch die Zofe geschützt!" + ConsoleColors.RESET);
                    }
                    else {
                        //War die abgelegte Karte die Prinzessin (value=8), scheidet der ausgewählte Spieler für den Rest der Runde aus.
                        if (selectedPlayer.getHand().get(0).getValue() == 8) {
                            System.out.println(ConsoleColors.RED + selectedPlayer.getName().toUpperCase() + " IST AUS DER RUNDE AUSGESCHIEDEN, WEIL ER/SIE DIE PRINZESSIN ABGELEGT HAT!" + ConsoleColors.RESET);
                            selectedPlayer.inRound = false;
                            Game.playersInstanceList.remove(selectedPlayer);

                            //Hier wird wieder der Player Pointer angepasst.
                            //Die Spielerliste verschiebt sich, wenn der ausgeschiedene Spieler vor currentPlayer dran war und muss in diesem Fall nicht eins nach oben gerechnet werden.
                            if (Game.currentPlayer.getLastDate() < selectedPlayer.getLastDate()) {
                                Game.playerPointer = (Game.playerPointer + 1) % Game.playersInstanceList.size();
                            }
                        }

                        //War die abgelegte Karte nicht die Prinzessin, so wird erst eine Karte vom Deck gezogen und dann die alte Karte abgelegt.
                        //Dabei ist die Reihenfolge wichtig, damit der ausgewählte Spieler in keinem Fall eine leere Hand hat und es u.U. nicht zu einem IndexOutOfBoundException kommt.
                        else {
                            selectedPlayer.drawCardFromDeck(Game.gameDeck);
                            selectedPlayer.getHand().remove(0);
                            System.out.println(ConsoleColors.YELLOW + selectedPlayer.getName().toUpperCase() + " HAT SEINE KARTE ABGELEGT UND EINE NEUE KARTE GEZOGEN\n" + ConsoleColors.RESET);
                            Game.playerPointer = (Game.playerPointer + 1) % Game.playersInstanceList.size();
                        }

                        //Hier wird der Spielerauswahl-Modus nach erfolgreicher Abfrage und Handling verlassen.
                        choosingPlayer = false;
                    }
                }

                //Fehlerbehandlung für eine ungültige Zahleneingabe
                else {
                    System.out.println(ConsoleColors.RED + "###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du einen Spieler aus der Auswahlliste wählst." + ConsoleColors.RESET);
                }
            }

            //Fehlerbehandlung für einen eingegebenen String, statt eines Integers.
            catch (InputMismatchException e) {
                System.out.println(ConsoleColors.RED + "###FEHLER: Ungültiges Format! \nBitte gebe deine Auswahl IN ZIFFERN an, nicht in Buchstaben!" + ConsoleColors.RESET);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

