package CardTypes;

import Game.Game;
import Game.Player;
import Game.ConsoleColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Die GuardCard-Klasse repräsentiert die Wächterin-Karte in LoveLetter.
 * Diese Karte hat den Wert 1 und hat einen speziellen Effekt: Wenn ein Spieler die Wächterin spielt und die Handkarte eines anderen Spielers richtig errät,
 * scheidet dieser andere Spieler aus der Runde aus. Wenn die erratene Karte jedoch falsch ist, hat die Wächterin keinen weiteren Effekt und das Spiel geht normal weiter.
 */
public class GuardCard extends Cards{

    String name = super.name;

    /**
     * Konstruktor für die Wächterin-Karte. Setzt den Kartennamen auf "Wächterin" und den Wert auf 1.
     */
    public GuardCard(){
        super("Wächterin", 1);
    }

    @Override
    public String getName() {
        return name;
    }


    /**
     * Effekt der Wächterin-Karte. Der Spieler, der die Wächterin spielt, wählt einen anderen Spieler aus und versucht dessen Handkarte zu erraten.
     * Wenn die Auswahl und die Erratene Karte übereinstimmen, scheidet der andere Spieler aus der Runde aus. Andernfalls geht das Spiel normal weiter.
     */
    @Override
    public void makeEffect() {

        // Für die Kartenauswahl wird ein neues Auswahldeck erstellt
        List<Cards> singleInstanceDeck = new ArrayList<>();
        singleInstanceDeck.add(new GuardCard());
        singleInstanceDeck.add(new PriestCard());
        singleInstanceDeck.add(new BaronCard());
        singleInstanceDeck.add(new HandmaidCard());
        singleInstanceDeck.add(new PrinceCard());
        singleInstanceDeck.add(new KingCard());
        singleInstanceDeck.add(new CountessCard());
        singleInstanceDeck.add(new PrincessCard());

        System.out.println(ConsoleColors.CYAN+"\n---Effekt der WÄCHTERIN:--- \n\nErrätst du die Handkarte eines Mitspielers, scheidet dieser aus. \nDu darfst aber nicht auf Wächterin tippen!\n"+ConsoleColors.RESET);

        int playerchooseoptions = 1;

        System.out.println("Bitte wähle jetzt aus, wessen Karte du erraten möchtest:");
        List<Player> ListOfOtherPlayers = new ArrayList<>();
        for(Player other : Game.playersInstanceList){
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
        System.out.println("------------------------");

        //Spielerasuwahlmodus wird betreten
        boolean choosingPlayer = true;
        while(choosingPlayer){
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
                try{
                    System.out.print(">> ");
                    Scanner playerSelector = new Scanner(System.in);
                    int playerchoicePlayer = playerSelector.nextInt();

                    // Wählt der Spieler einen Spieler, der nicht in der Liste vertreten ist, muss er nochmal wählen.
                    if (playerchoicePlayer < 1 || playerchoicePlayer > playerchooseoptions) {
                        System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du einen Spieler aus der Auswahlliste wählst."+ConsoleColors.RESET);
                    }
                    // sonst
                    else {
                        //selectedPlayer ist der vom currentPlayer ausgewählte Spieler, welcher in der Subliste ListOfOtherPlayers vertreten ist.
                        Player selectedPlayer = ListOfOtherPlayers.get(playerchoicePlayer-1);
                        if(selectedPlayer == Game.currentPlayer){
                            System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Du darfst dich nicht selbst wählen!"+ConsoleColors.RESET);
                        }
                        else if (selectedPlayer.isProtected) {
                            System.out.println(ConsoleColors.BLUE+"###SYSTEM: Dieser Spieler ist durch die Zofe geschützt!"+ConsoleColors.RESET);
                        }
                        else {
                            System.out.println("Du hast "+selectedPlayer.getName()+" ausgewählt.");
                            System.out.println("------------------------");

                            // Wenn die Eingabe passt, wird der Spielerauswahlmodus verlassen.
                            choosingPlayer = false;

                            System.out.println("Versuche jetzt die Karte von "+selectedPlayer.getName()+" zu erraten:\n");

                            // Numeriert und identifiziert die Karten in der Kartenauswahlliste (singleInstanceDeck) von oben, initial auf 1, da Auswahl mit 1 beginnen soll
                            int cardchooseoption = 1;
                            for(Cards options : singleInstanceDeck){
                                System.out.println("("+cardchooseoption+") "+options.getName()+" Wert: "+options.getValue());
                                cardchooseoption++;
                            }
                            System.out.println("------------------------");

                            // Kartenauswahl Modus wird betreten
                            boolean choosingCard = true;
                            while(choosingCard){
                                try{
                                    // Der Spieler trifft eine Auswahl in der Liste, die ihm angezeigt wird
                                    System.out.print(">> ");
                                    Scanner cardSelector = new Scanner(System.in);
                                    int playerChoosenCard = cardSelector.nextInt();

                                    // Wenn die Auswahl ungültig ist, wird der Spieler aufgefordert eine neue Wahl zu treffen.
                                    if (playerChoosenCard < 1 || playerChoosenCard > cardchooseoption) {
                                        System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du eine Karte aus der Auswahlliste wählst."+ConsoleColors.RESET);
                                    }

                                    // Ist die Auswahl gültig, wird die Karte des ausgewählten Spielers mit der ausgewählten Option ausgeglichen
                                    else {

                                        // Hat der Spieler die 1 (Wächterin) ausgewählt, muss er nochmal wählen
                                        if (playerChoosenCard == singleInstanceDeck.get(0).getValue()){
                                            System.out.println(ConsoleColors.YELLOW+"Du darfst nicht auf die Wächterin tippen."+ConsoleColors.RESET+" Wähle eine andere Option!");
                                        }

                                        // Hat der Spieler richtig geraten, scheidet der ausgewählte Spieler aus und der neue Player Pointer wird neu konfiguriert und auf die neu entstandene Liste angepasst
                                        else if (singleInstanceDeck.get(playerChoosenCard - 1).getValue() == selectedPlayer.getHand().get(0).getValue()) {
                                            System.out.println(ConsoleColors.GREEN+"DU HAST DIE KARTE VON "+selectedPlayer.getName().toUpperCase()+" RICHTIG GERATEN!\n"
                                                    +selectedPlayer.getName().toUpperCase()+" HATTE EINE "+selectedPlayer.hand.get(0).getName().toUpperCase()+"-KARTE UND SCHEIDET FÜR DIESE RUNDE AUS."+ConsoleColors.RESET);
                                            selectedPlayer.discardedCards.add(selectedPlayer.getHand().get(0));
                                            selectedPlayer.inRound = false;
                                            Game.playersInstanceList.remove(selectedPlayer);

                                            // Wenn der von currentPlayer ausgewählte Spieler in der Liste vor dessen Eliminierung NACH currentPlayer drankommt, geht der SpielerPointer eins weiter.
                                            // Wenn der von currentPlayer ausgewählte Spieler in der Liste vor dessen Eliminierung VOR currentPlayer drankommt, bewegt sich der SpielerPointer nicht, da sich die Liste verschiebt.
                                            if(Game.currentPlayer.getLastDate()<selectedPlayer.getLastDate()){
                                                Game.playerPointer = (Game.playerPointer+1) % Game.playersInstanceList.size();
                                            }

                                            // Hat der Spieler richtig geraten, wird der Kartenauswahlmodus verlassen
                                            choosingCard = false;
                                        }

                                        //Hat der Spieler die Karte des ausgewählten Spielers falsch geraten, geht das Spiel ganz normal weiter
                                        else if (singleInstanceDeck.get(playerChoosenCard - 1).getValue() != selectedPlayer.hand.get(0).getValue()){
                                            System.out.println(ConsoleColors.RED+"Leider FALSCH! "+ConsoleColors.RESET+selectedPlayer.getName()+" hat keine "+singleInstanceDeck.get(playerChoosenCard - 1).getName()+"-Karte.");
                                            Game.playerPointer = (Game.playerPointer+1) % Game.playersInstanceList.size();

                                            // Hat der Spieler falsch geraten, wird der Kartenauswahl Modus wieder verlassen.
                                            choosingCard = false;
                                        }
                                    }
                                }
                                catch (Exception e){
                                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format! \nBitte gebe deine Kartenauswahl IN ZIFFERN an, nicht in Buchstaben!"+ConsoleColors.RESET);
                                }
                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format! \nBitte gebe deine Spielerauswahl IN ZIFFERN an, nicht in Buchstaben!"+ConsoleColors.RESET);
                }
            }
        }
    }
}
