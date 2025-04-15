package Game;

import CardTypes.Cards;
import CardDecks.Deck;

import java.util.*;


/** Die ist die Spieler-Klasse. Sie erzeugt Instanzen der Spieler, die am Spiel teilnehmen. */
public class Player {

    /**Legt fest, ob ein Spieler von der Zofe geschützt ist. Wird vor jeder Runde und wenn ein Spieler dran ist auf false gesetzt.*/
    public boolean isProtected = false;

    /**Legt fest, ob ein Spieler in einer Runde noch teilnimmt. Wird vor jeder neuen Runde wieder auf true gesetzt.*/
    public boolean inRound;

    /**Bestimmt den Namen eines Spielers.*/
    private String name; //

    /**Bestimmt das Alter eines Spielers.*/
    private int age; //

    /**Bestimmt die Anzahl der Tokens, die ein Spieler hat. Ist wichtig für die Spiel-Siegbedingung.*/
    public int ownedTokens; //

    /**Bestimmt (in Tagen) wann das letzte Date des Spielers war. Ist wichtig für die Sortierung in der ersten Spielrunde.*/
    private int lastDate; //

    /**Jeder Spieler hat eine Hand. In dieser sind die Karten, die ein Spieler in der Hand hat. Der Spieler, der dran ist, hat immer 2 Karten.*/
    public List<Cards> hand; //

    /**Dies ist der Ablagestapel des Spielers. Spielt ein Spieler eine Karte aus, wird sie in den eigenen Ablagestapel gelegt.*/
    public List<Cards> discardedCards; //


    /**
     * Dies ist der Konstruktor, der einen Spieler mit Namen, letztes Date, Spielerhand und Tokens erzeugt.
     * @param name Name des erzeugten Spielers
     * @param age Alter des erzeugten Spielers
     * @param lastDate Anzahl der Tage, wann das letzte Date des erzeugten Spielers her ist
     * @param ownedTokens Anzahl der Tokens des erzeugten Spielers im Spiel
     */
    public Player(String name, int age, int lastDate, int ownedTokens){
        this.name = name;
        this.age = age;
        this.lastDate = lastDate;
        this.ownedTokens = ownedTokens;
        this.hand = new ArrayList<>();
    }

    /** Getter für den Namen */
    public String getName() {
        return name;
    }

    /** Setter für den Namen */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter für das Alter*/
    public int getAge() {
        return age;
    }

    /** Setter für das Alter*/
    public void setAge(int age) {
        this.age = age;
    }

    /** Getter für die Tokens des Spielers */
    public int getOwnedTokens() {
        return ownedTokens;
    }

    /** Setter für die Tokens des Spielers */
    public void setOwnedTokens(int ownedTokens) {
        this.ownedTokens = ownedTokens;
    }


    /** Getter für die Anzahl an Tagen, wann das letzte Date des Spielers her ist */
    public int getLastDate() {
        return lastDate;
    }
    /** Setter für die Anzahl an Tagen, wann das letzte Date des Spielers her ist */
    public void setLastDate(int lastDate) {
        this.lastDate = lastDate;
    }


    /** Getter für die Karten des Spielers, die er in der Hand hält. */
    public List<Cards> getHand() {
        return hand;
    }

    /** Setter für den Schutzstatus des Spielers, wenn er eine Zofe spielt */
    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }


    /**
     * Diese Methode zieht die oberste Karte vom Deck, in dem sie die Deck-Methode drawCard() aufruft. Diese Karte wird dann mit hand.add(drawnCard)
     *  der Spieler-Hand hinzugefügt.
     * @param deck Kartendeck, von dem die oberste Karte gezogen wird
     */
    public void drawCardFromDeck(Deck deck) throws InterruptedException {
        Cards drawnCard = deck.drawCard();
        hand.add(drawnCard);
    }

    /**
     *  Diese Methode wird aufgerufen, wenn der Spieler den Befehl \playCard aufruft.
     *  Wenn der Spieler der dran ist eine Gräfin mit König oder Prinz in Kombination hält, wird ihm dies entsprechend mitgeteilt aber er hat auch die Wahl zu bluffen.
     *  Sollte der Spieler freiwillig die Prinzessin spielen wollen, so wird gefragt, ob er sich sicher sei, dass er die Prinzessin spielt.
     *  Mit "n" (=nein) geht er zurück zur Kartenauswahl, mit "j" (=Ja) spielt er die Karte und muss aussetzen.
     *  Sonst kann der Spieler mit der Eingabe 1 oder 2, die erste bzw zweite Karte in der Hand spielen und der Effekt dieser Karte wird ausgeführt.
     *  Hierbei wird die Hand des Spielers mit der showHand()-Methode nummeriert angezeigt.
     *  Sollte der Spieler eine ungültige Eingabe machen und z.B. 3 eingeben oder einen String, wird eine entsprechende Fehlerbehandlung durchgeführt und der Spieler muss
     *  seine Eingabe muss wiederholt werden.
     */
    public void playCard() {
        boolean isChoosing = true;
        Cards firstCard = Game.currentPlayer.getHand().get(0);
        Cards secondCard = Game.currentPlayer.getHand().get(1);

        //Es wird vor jeder Behandlung des Befehls \playCard überprüft, ob eine Gräfin zusammen mit einem König oder einem Prinzen in der Hand des
        //Spielers vorkommt.
        if((firstCard.getValue() == 7 && secondCard.getValue()==6) || (firstCard.getValue() == 7 && secondCard.getValue()==5) ||
           (firstCard.getValue() == 6 && secondCard.getValue()==7) || (firstCard.getValue() == 5 && secondCard.getValue()==7))
        {
            System.out.println(ConsoleColors.CYAN+"\n**DU HAST EINE "+firstCard.getName().toUpperCase()+"-KARTE " +
                    "ZUSAMMEN MIT EINER "+secondCard.getName().toUpperCase()+"-KARTE " +
                    "UND MUSST DIE GRÄFIN SPIELEN!**\n(Du kannst aber auch bluffen)"+ConsoleColors.RESET);
        }
        System.out.println("\nWähle eine Karte zum Spielen: (1|2)\n");
        showHand();
        while (isChoosing) {
            try{
                System.out.print(">> ");
                Scanner inputReader = new Scanner(System.in);
                int choice = inputReader.nextInt();
                if (choice == 1) {
                    if(firstCard.getValue() == 8){
                        System.out.println(ConsoleColors.YELLOW+"+++WARNUNG+++\nWenn du die Prinzessin spielst, musst du die Runde aussetzen!\nMöchtest du fortfahren?? (j|n)\n"+ConsoleColors.RESET);
                        boolean isAcking = true;
                        while(isAcking){
                            System.out.print(">> ");
                            Scanner okReader = new Scanner(System.in);
                            String ack = okReader.nextLine().toLowerCase();
                            try {
                                if(Objects.equals(ack, "j")){
                                    firstCard.makeEffect();
                                    Cards playedCardIndexZero = hand.remove(0);
                                    Cards playedCardIndexOne = hand.remove(0);
                                    discardedCards.add(playedCardIndexZero);
                                    discardedCards.add(playedCardIndexOne);
                                    isAcking = false;
                                    isChoosing = false;
                                }
                                else if (Objects.equals(ack, "n")) {
                                    isAcking = false;
                                    System.out.println("\nWähle eine Karte zum Spielen: (1|2)\n");
                                    showHand();
                                }
                                else{
                                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Auswahl!\nBitte wähle j für Ja und n für Nein."+ConsoleColors.RESET);
                                }
                            }
                            catch (InputMismatchException e){
                                System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format!\nBitte wähle j für Ja und n für Nein."+ConsoleColors.RESET);
                            }
                        }
                    }
                    else {
                        Cards playedCard = hand.remove(0);
                        discardedCards.add(playedCard);
                        System.out.println(ConsoleColors.BOLD+"\n***"+Game.currentPlayer.getName()+" hat "+playedCard.getName()+" gespielt.***"+ConsoleColors.RESET);
                        playedCard.makeEffect();
                        isChoosing = false;
                    }
                }
                else if (choice == 2) {
                    if(secondCard.getValue() == 8){
                        System.out.println(ConsoleColors.YELLOW+"+++WARNUNG+++\nWenn du die Prinzessin spielst, musst du die Runde aussetzen!\nMöchtest du fortfahren?? (j|n)\n"+ConsoleColors.RESET);
                        boolean isAcking = true;
                        while(isAcking){
                            System.out.print(">> ");
                            Scanner okReader = new Scanner(System.in);
                            String ack = okReader.nextLine().toLowerCase();
                            try {
                                if(Objects.equals(ack, "j")){
                                    secondCard.makeEffect();
                                    Cards playedCardIndexOne = hand.remove(1);
                                    Cards playedCardIndexZero = hand.remove(0);
                                    discardedCards.add(playedCardIndexOne);
                                    discardedCards.add(playedCardIndexZero);
                                    isAcking = false;
                                    isChoosing = false;
                                }
                                else if (Objects.equals(ack, "n")) {
                                    isAcking = false;
                                    System.out.println("\nWähle eine Karte zum Spielen: (1|2)\n");
                                    showHand();
                                }
                                else{
                                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Auswahl!\nBitte wähle j für Ja und n für Nein."+ConsoleColors.RESET);
                                }
                            }
                            catch (InputMismatchException e){
                                System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format!\nBitte wähle j für Ja und n für Nein."+ConsoleColors.RESET);
                            }
                        }
                    }
                    else {
                        Cards playedCard = hand.remove(1);
                        discardedCards.add(playedCard);
                        System.out.println(Game.currentPlayer.getName()+" hat "+playedCard.getName()+" gespielt. ");
                        playedCard.makeEffect();
                        isChoosing = false;
                    }
                }
                else{
                    System.out.println(ConsoleColors.RED+"\n###FEHLER: Ungültige Eingabe! \nBitte stelle sicher, dass du eine Karte aus der Auswahlliste wählst."+ConsoleColors.RESET);
                }
            }
            catch (InputMismatchException e){
                System.out.println(ConsoleColors.RED+"\n###FEHLER: Ungültiges Format! \nBitte gebe deine Kartenauswahl IN ZIFFERN an, nicht in Buchstaben!"+ConsoleColors.RESET);
            }
        }
    }


    /**
     *  Diese Methode ist zum bloßen Anzeigen der Spielerhand gedacht.
     *  Dabei wird mit einer for-Schleife für jede Karte in der Hand des Spielers nummeriert angezeigt.
     */
    public void showHand(){
        int i=1;
        for(Cards card : hand){
            System.out.println("("+i+") "+card.getName()+", WERT: "+card.getValue());
            i++;
        }
    }


    /**
     *  Diese Methode gibt die Anzahl der Tokens des Spielers an, der die Methode aufruft.
     *  Auch werden die Tokens in Form von Herzen angezeigt, also ein Spieler mit beispielsweise 3 Tokens bekommt die Ausgabe <3 <3 <3.
     *  Dies soll das Programm ein wenig realistischer an das Spiel angelehnt machen, wo man auch nach jedem Rundensieg ein herzförmiges Token bekommt.
     */
    public void showScore(){
        int tokens = Game.currentPlayer.getOwnedTokens();
        System.out.print("Du hast "+tokens+" Tokens: ");
        for(int i=0; i<tokens; i++){
            System.out.print(ConsoleColors.PURPLE+"<3 "+ConsoleColors.RESET);
        }
        System.out.print("\n");
    }


    /**
     *  Zeigt eine Übersicht über alle möglichen Befehle an, mit einer kurzen Beschreibung dieser.
     */
    public static void showHelp(){
        System.out.println(ConsoleColors.YELLOW+"\n-------HILFE-------\n\n- \\start: Dieser Befehl startet ein Spiel, falls noch kein Spiel gestartet wurde.\n- \\playCard: Dieser Befehl ist zum Spielen einer Karte gedacht. \n   Drücke 1 um die Karte in deiner Hand zu spielen und 2 um die letzte gezogene Karte zu spielen. \n- \\showHand: Zeigt die Karten die du gerade hast. \n- \\showScore: Zeigt deinen Punktestand.\n- \\showPlayers: Zeigt die noch aktiven Spieler in der aktuellen Runde.\n"+ConsoleColors.RESET);
    }


    /**
     *  Zeigt alle Spieler IM SPIEL an, indem sie auf die initiatedPlayer Liste zugreift und gibt an, ob wer von diesen Spielern noch
     *  in der aktuellen Runde dabei ist.
     */
    public static void showPlayers(){
        for(Player p : Game.initiatedPlayers){
            System.out.println(p.getName()+" | Noch in Runde: "+p.inRound);
        }
    }


    /**
     *  Zeigt den individuellen Ablagestapel des Spielers an. Der Spieler sieht - angelehnt an das echte Kartenspiel - die Karten, die er schon abgelegt hat.
     *  Die Linkeste Karte im Ablagestapel ist die, die als erstes abgelegt wurde und die rechteste ist die Karte, die als letztes abgelegt wurde.
     */
    public void showDiscardedPile(){
        for(Cards c : discardedCards){
            System.out.print("("+c.getValue()+") "+c.getName()+"  ");
        }
        Collections.reverse(discardedCards);
    }

    public boolean isProtected() {
        return isProtected;
    }
}