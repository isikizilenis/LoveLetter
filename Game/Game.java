package Game;

import CardDecks.Deck;
import CardTypes.Cards;

import java.util.*;


/**
 * Sendet nach dem Aufruf von der run()-Methode den Befehl des Spielers an den Command Handler, von welchem er Anweisungen bekommt.
 * Fragt Spieler-Informationen sowie Anzahl der Spieler ab und erstellt eine Spielerliste.
 * Startet, verwaltet und beendet Spielrunden und bestimmt am Ende den Sieger des Spiels.
 */
public class Game {

    /**Legt die Anzahl der Spieler im Spiel fest*/
    private static int playerNumber;

    /**Diese Variable zählt, in welcher Runde die (verbliebenen) Spieler sich befinden.*/
    private static int roundCounter = 0;

    /**Hier werden alle Spieler abgespeichert die am Spiel teilnehmen.*/
    public static List<Player> initiatedPlayers;

    /**Hier werden Instanzen der Player-Klasse in einer Runde abgespeichert.*/
    public static List<Player> playersInstanceList;

    /**Erzeugt eine Instanz eines gemischen Kartendecks.*/
    public static Deck gameDeck;

    /**Prüft, ob das Spiel gestartet ist*/
    public static boolean isStarted = false;

    /**Prüft, ob das Programm ausgeführt wurde*/
    private static boolean isRunning;

    /**Prüft, ob der Spieler eine Eingabe eingibt.*/
    public static boolean choosingCommand;

    /**Diese Variable prüft in der playerInstanceList, wer dran ist.*/
    public static int playerPointer;

    /**Eine Spieler-Instanz, die den aktuellen Spieler bestimmt.*/
    public static Player currentPlayer;

    /**Eine Spieler-Instanz, die den Gewinner der letzten Runde markiert*/
    public static Player winnerOfLastRound;


    /**
     *  Startet das Programm an sich.
     *  Erzeugt einen Text, indem der Benutzer begrüßt wird und
     *  aufgefortdert wird einen Befehl einzugeben. Hierbei wird ein Befehl nur dann an der CommandHandler
     *  geschickt, wenn er mit dem Zeichen "\" startet.
     */
    public void run() throws InterruptedException {
        isRunning = true;
        System.out.println(ConsoleColors.CYAN+ConsoleColors.BOLD+"\n-----"+ConsoleColors.UNDERLINE+"WILLKOMMEN BEI LOVE LETTER"+ConsoleColors.RESET+ConsoleColors.CYAN+ConsoleColors.BOLD+"-----\n"+ConsoleColors.RESET+ConsoleColors.CYAN+"\nBitte gebe zum Starten \\start ein.\nFür eine Hilfestellung, gebe \\help ein: \n"+ConsoleColors.RESET);
        while(isRunning){
            System.out.print(">> ");
            Scanner inputReader = new Scanner(System.in);
            String input = inputReader.nextLine().toLowerCase();
            if(!(input.startsWith("\\"))) {
                System.out.println(ConsoleColors.RED+"###FEHLER: Das ist kein Befehl! \nBitte stelle sicher, dass deine Eingabe mit '\\' beginnt."+ConsoleColors.RESET);
            }
            else {
                CommandHandler.handleCommand(input);
            }
        }
    }

    /**
     *  Bekommt die Klasse CommandHandler den Befehl das Spiel zu starten, fordert sie als allererstes
     *  die Game-Klasse auf die Spieleranzahl abzufragen und diese Information in der playerNumber Variable zu speichern.
     *  Während das Spiel läuft, kann das nicht mehr geändert werden. Außerdem werden umfangreiche Fehlerbehandlungsmaßnahmen durchgeführt,
     *  die gewährleisten, dass die Spieleranzahl zwischen 2 und 4 ist und dass keine Exception auftritt durch das Angeben einer nicht-Ziffer.
     */
    static void askPlayerNumber(){
        boolean askingPlayerNumber = true;
        while (askingPlayerNumber) {
            try {
                System.out.print("Wie viele Spieler spielen mit? (2|3|4): \n>> ");
                Scanner sc = new Scanner(System.in);
                playerNumber = Integer.parseInt(sc.nextLine());
                if (playerNumber > 4 || playerNumber < 2) {
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültige Spielerzahl! \nEs müssen mindestens 2 und höchstens 4 Spieler mitspielen!"+ConsoleColors.RESET);
                }
                else {
                    askingPlayerNumber = false;
                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiges Format! \nBitte gebe eine numerische Zahl zwischen 2 und 4 an, um die Spieleranzahl festzulegen!"+ConsoleColors.RESET);
            }
        }
    }

    /**
     *  Zunächst wird eine Spielerliste mit allen Spielern angelegt, welche initial die Größe der Spieleranzahl hat.
     *  Die Spieler werden alle nach dem Namen gefragt. Dabei sollen keine Namen in der Initial-Liste gleich sein.
     *  Darüber hinaus werden die Spieler gefragt, wann sie das letzte Mal ein Rendez-Vous hatten, was darüber bestimmt, welcher Spieler anfängt.
     *  Auch hier werden umfangreiche Fehlerbehandlungen durchgeführt.
     */
    public static void initiatePlayers() throws InterruptedException {
        boolean initiatingPlayers = true;
        while (initiatingPlayers) {
            initiatedPlayers = new ArrayList<>(playerNumber);
            for(int i=0; i<playerNumber; i++){
                System.out.println("Spieler "+(i+1)+", bitte gebe deinen Name ein: ");
                boolean askingName = true;
                while (askingName) {
                    System.out.print(">> ");
                    Scanner namereader = new Scanner(System.in);
                    String nameOfAsked;
                    nameOfAsked = namereader.nextLine();
                    if(!(checkIfNameIsAvailableInList(nameOfAsked, initiatedPlayers))){
                        System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Der Name ist vergeben. Bitte wähle einen anderen Namen!"+ConsoleColors.RESET);
                    }
                    else {
                        askingName = false;
                        System.out.println("Hallo "+nameOfAsked+"! Vor wie vielen Tagen hattest du dein letztes Date? ");
                        int lastDateOfAsked;
                        boolean askingDate = true;
                        while (askingDate) {
                            try{
                                System.out.print(">> ");
                                Scanner lastDateScanner = new Scanner(System.in);
                                lastDateOfAsked = lastDateScanner.nextInt();
                                askingDate = false;
                                System.out.println("Wie alt bist du? ");
                                int ageOfAsked;
                                boolean askingAge = true;
                                while(askingAge){
                                    try{
                                        System.out.print(">> ");
                                        Scanner ageScanner = new Scanner(System.in);
                                        ageOfAsked = ageScanner.nextInt();
                                        askingAge = false;

                                        System.out.print("Erstelle Spieler");
                                        Thread.sleep(100);
                                        for(int j=0; j<3; j++){
                                            System.out.print(".");
                                            Thread.sleep(100);
                                        }
                                        System.out.println("");
                                        Player player = new Player(nameOfAsked, ageOfAsked, lastDateOfAsked,0);

                                        System.out.print("Setze Name");
                                        Thread.sleep(100);
                                        for(int j=0; j<3; j++){
                                            System.out.print(".");
                                            Thread.sleep(100);
                                        }
                                        System.out.println("");
                                        player.setName(nameOfAsked);

                                        System.out.print("Setze Alter");
                                        Thread.sleep(100);
                                        for(int j=0; j<3; j++){
                                            System.out.print(".");
                                            Thread.sleep(100);
                                        }
                                        System.out.println("");
                                        player.setAge(ageOfAsked);

                                        System.out.print("Setze letztes Date");
                                        Thread.sleep(100);
                                        for(int j=0; j<3; j++){
                                            System.out.print(".");
                                            Thread.sleep(100);
                                        }
                                        System.out.println("");
                                        player.setLastDate(lastDateOfAsked);

                                        System.out.print("Initialisiere Spieler-Tokens");
                                        Thread.sleep(100);
                                        for(int j=0; j<3; j++){
                                            System.out.print(".");
                                            Thread.sleep(100);
                                        }
                                        System.out.println("");
                                        player.setOwnedTokens(0);

                                        System.out.print("Füge Spieler zur Liste hinzu");
                                        Thread.sleep(100);
                                        for(int j=0; j<3; j++){
                                            System.out.print(".");
                                            Thread.sleep(100);
                                        }
                                        System.out.println("\n");
                                        initiatedPlayers.add(player);

                                        initiatingPlayers = false;
                                    }
                                    catch (InputMismatchException inputMismatchException){
                                        System.out.println(ConsoleColors.RED+"###FEHLER: Bitte geben dein Alter NUMERISCH (IN ZAHLEN) an!"+ConsoleColors.RESET);
                                    }
                                }
                            }
                            catch (InputMismatchException e){
                                System.out.println(ConsoleColors.RED+"###FEHLER: Bitte gebe die Anzahl der Tage (IN ZAHLEN!) an, seitdem du deine*n Liebste*n nicht mehr gesehen hast.\n(Zum Beispiel '23' dafür, dass dein letztes Date 23 Tage her ist.)"+ConsoleColors.RESET);
                            }
                        }
                    }
                }
            }
        }
        System.out.print("Sortiere Spielerliste");
        Thread.sleep(250);
        for(int j=0; j<4; j++){
            System.out.print(".");
            Thread.sleep(250);
        }
        sortList(initiatedPlayers);
        System.out.println("");
        System.out.println("Spielerliste wurde erfolgreich erstellt und sortiert!");
    }


    /**
     * Diese Methode prüft, ob ein eingegebener Name, der in der Variable nameToCheck gespeichert ist, in einer Spielerliste listToCheck vorkommt.
     * @param nameToCheck Name, die auf Vorkommen in der Liste listToCheck überprüft wird.
     * @param listToCheck Namensliste, in der nameToCheck auf Vorkommen überprüft wird.
     * @return boolean dafür, ob der Name verfügbar ist.
     */
    private static boolean checkIfNameIsAvailableInList(String nameToCheck, List<Player> listToCheck) throws InterruptedException {
        System.out.print("Prüfe Namen");
        Thread.sleep(200);
        for(int i=0; i<4; i++){
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println("");
        if (listToCheck.isEmpty()) {
            return true;
        }
        for(Player checkedPlayer : listToCheck) {
            if (nameToCheck.equals(checkedPlayer.getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     *  Diese Methode ist für das Starten einer neuen Runde zuständig.
     *  Dabei wird zu Beginn jeder Runde der Rundenzähler erhöht, die Spielerliste für die Runde zurückgesetzt,
     *  ein neues Kartendeck erstellt und eine Karte an alle Spieler ausgeteilt wird.
     *  Solange es mehr als einen Spieler im Spiel gibt, wird die Runde durchgeführt, indem jeder Spieler nach einem
     *  Befehl gefragt wird, der dann entsprechend im CommandHandler verarbeitet wird.
     */
    public static void startRound() throws InterruptedException {

        // Zunächst finden die initialen Vorbereitungen auf die neue Runde statt
        roundCounter++;                                 // Der Rundenzähler wird um eins erhöht
        playersInstanceList = new ArrayList<>();        // Die Runden-Spielerliste wird resettet
        playersInstanceList.addAll(initiatedPlayers);   // Die ausgeschiedenen Spieler der Vorrunde werden zur neuen Spielrunde wieder hinzugefügt
        sortList(playersInstanceList);                  // Die Spielerliste wird sortiert

        // Der Gewinner der letzten Runde - bzw der jüngere, wenn es ein Unentschieden gab - fängt an. Dafür wird der Index entsprechend angepasst.
        if(roundCounter == 1){
            playerPointer = 0;
        } else{
            playerPointer = playersInstanceList.indexOf(winnerOfLastRound);
        }

        gameDeck = new Deck();                          // Das Spieldeck wird resettet
        for(Player p : playersInstanceList){
            p.discardedCards = new ArrayList<>();       // Für alle Spieler wird der Ablagestapel resettet
            p.inRound = true;                           // Die Flag, dass alle Spieler an der Runde wieder teilnehmen wird auf true gesetzt
            p.isProtected = false;                      // Der Effekt der Zofe wird resettet, damit der Schutz ggf. nicht in die nächste Runde mit reinwirkt
        }

        // Hier werden initial allen Spielern eine Karte gegeben
        System.out.println(ConsoleColors.GREEN+"\nRunde "+roundCounter+" wird gestartet. Karten werden ausgeteilt...\n"+ConsoleColors.RESET);
        Thread.sleep(600);
        for(Player cardDrawingPlayer : playersInstanceList){
            System.out.println("Gebe Karte an "+cardDrawingPlayer.getName()+" [Letztes Date vor "+cardDrawingPlayer.getLastDate()+" Tagen | Alter: "+cardDrawingPlayer.getAge()+"]...");
            cardDrawingPlayer.drawCardFromDeck(gameDeck);
            Thread.sleep(500);
        }

        // Bei einem 2-Spieler-Spiel müssen die drei obersten Karten aufgedeckt werden.
        if(playerNumber == 2){
            List<Cards> openedCards = new ArrayList<>(3);
            System.out.println(ConsoleColors.YELLOW+ConsoleColors.BOLD+"\n2 SPIELER SPIEL! Es wurden folgende Karten aufgedeckt: "+ConsoleColors.RESET);
            Thread.sleep(2000);
            for(int i=0; i<3; i++){
                openedCards.add(gameDeck.drawCard());
            }
            for(Cards c : openedCards){
                System.out.println(c.getValue()+" "+c.getName());
                Thread.sleep(1500);
            }
        }

        while (playersInstanceList.size() > 1){

            // An dieser Stelle wird der Spielerpointer immer so angepasst, sodass er sich im Kreis dreht.
            // Dabei wird eine Exception aufgefangen, wenn das Ende der playersInstanceList (maximale Spieleranzahl) erreicht ist.
            // Der Spielerpointer wird dann wieder auf 0 gesetzt und die Spielerliste wird von vorne ausgelesen.
            try{
                currentPlayer = playersInstanceList.get(playerPointer);
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException){
                playerPointer = 0;
                currentPlayer = playersInstanceList.get(playerPointer);
            }

            // Der Spieler zieht eine Karte vom Stapel, aber nur wenn dieser nicht schon 2 Karten hat.
            // Die if-Bedingung ist notwendig, da sonst mit dem showHand()-Befehl noch eine Karte gezogen wird.
            if(currentPlayer.getHand().size()<2){
                currentPlayer.drawCardFromDeck(gameDeck);
            }
            Thread.sleep(1600);
            System.out.println("\n"+ConsoleColors.BOLD+ConsoleColors.CYAN+currentPlayer.getName().toUpperCase()+" IST DRANN!\n"+ConsoleColors.RESET);
            Thread.sleep(1500);

            //Dies ist der Code, der alle abgelegten Karten von allen Spielern anzeigt.
            System.out.println("------------------------");
            for(Player p : initiatedPlayers){
                if(p == currentPlayer){
                    System.out.print(ConsoleColors.GREEN+"--> "+ConsoleColors.BOLD+p.getName().toUpperCase()+"'S ABLAGESTAPEL: "+ConsoleColors.RESET);
                    p.showDiscardedPile();
                    System.out.println("");
                }
                else if(!p.inRound){
                    System.out.print(ConsoleColors.RED+" X  "+ConsoleColors.BOLD+p.getName().toUpperCase()+"'S ABLAGESTAPEL: "+ConsoleColors.RESET);
                    p.showDiscardedPile();
                    System.out.println("");
                }
                else if (p.isProtected) {
                    System.out.print(ConsoleColors.BLUE+" O  "+ConsoleColors.RESET+ConsoleColors.BOLD+p.getName().toUpperCase()+"'S ABLAGESTAPEL: "+ConsoleColors.RESET);
                    p.showDiscardedPile();
                    System.out.println("");
                }
                else {
                    System.out.print("    "+ConsoleColors.BOLD+p.getName().toUpperCase()+"'S ABLAGESTAPEL: "+ConsoleColors.RESET);
                    p.showDiscardedPile();
                    System.out.println("");
                }
            }
            System.out.println("------------------------");

            //Hier wird der Effekt der Zofe wieder aufgehoben, falls der aktuelle Spieler geschützt war
            if(currentPlayer.isProtected){
                currentPlayer.setProtected(false);
                System.out.println(ConsoleColors.YELLOW+"\n"+currentPlayer.getName().toUpperCase()+" IST NICHT LÄNGER DURCH DIE ZOFE GESCHÜTZT!"+ConsoleColors.RESET);
            }

            //Hier werden alle Befehle angezeigt, die der Spieler aufrufen kann
            //System.out.println(ConsoleColors.UNDERLINE+"BEFEHLSÜBERSICHT:"+ConsoleColors.RESET);
            //System.out.println("\\playcard\n\\showHand\n\\showScore\n\\showPlayers\n\\help\n------------------------");

            // Hier wird der Command, den der Spieler eingibt an den CommandHandler geschickt,
            // um dort verarbeitet zu werden, falls dieser mit '\' beginnt.
            choosingCommand = true;
            while(choosingCommand){
                System.out.print(">> ");
                Scanner s = new Scanner(System.in);
                String input = s.nextLine();
                if(!(input.startsWith("\\"))) {
                    System.out.println(ConsoleColors.RED+"###FEHLER: Das ist kein Befehl! Bitte stelle sicher, dass deine Eingabe mit '\\' beginnt."+ConsoleColors.RESET);
                }
                else {
                    CommandHandler.handleCommand(input);
                }
            }
        }
        // Die Runde wird beendet, wenn nur noch ein Spieler in der playersInstanceList ist, also die while-Schleife durchbrochen wird.
        handleOnePlayerRemainedWin();
    }

    /**
     * Sortiert eine Liste listToSort nach der Anzahl der Tage, in denen die Spieler ihr letztes Date hatten.
     * Falls es mehrere gleiche Zahlen für lastDate gibt, werden die gleichen Elemente in der Liste hinterher auch nach Alter sortiert.
     * Dabei wird auf getLastDate und getAge Methoden der Player-Klasse verwiesen (mit ::) um einen Vergleich durchzuführen.
     * @param listToSort Liste, die sortiert werden soll.
     */
    private static void sortList(List<Player> listToSort) {
        listToSort.sort(Comparator.comparingInt(Player::getLastDate).thenComparing(Player::getAge));
    }

    /**
     *  Behandelt den Fall, in dem nur noch ein Spieler in der Runde verbleibt und diesen als Gewinner erkennt.
     *  Der Gewinner erhält einen Spiel-Token, und das Ergebnis wird ausgegeben, einschließlich der Rangliste der Spieler.
     *  Wenn die Gewinnbedingung für das Spiel noch nicht erfüllt ist, wird eine neue Runde gestartet.
     *  Andernfalls wird das Spiel beendet, und der Gesamtsieger wird erklärt.
     *  Die Methode wird ausschließlich in der handleRound()-Methode verwendet.
     */
    private static void handleOnePlayerRemainedWin() throws InterruptedException {
        winnerOfLastRound = playersInstanceList.get(0);     // Da es nur einen Spieler in der Liste gibt, ist "winner" automatisch das erste Element der Liste
        winnerOfLastRound.ownedTokens++;                           // "winner" erhält einen Token
        System.out.println(ConsoleColors.GREEN+ConsoleColors.BOLD+"\n+++++"+winnerOfLastRound.getName().toUpperCase()+" GEWINNT ALS EINZIG-VERBLIEBENER SPIELER UND ERHÄLT EINEN TOKEN!+++++\n"+ConsoleColors.RESET);
        System.out.print("\n");

        // Hat "winner" die Spielsieg-Bedingung noch nicht erfüllt, wird eine neue Runde gestartet, wobei die Hand aller Spieler resettet wird.
        if((playerNumber==4 && winnerOfLastRound.getOwnedTokens()<3) ||
                (playerNumber==3 && winnerOfLastRound.getOwnedTokens()<4) ||
                (playerNumber==2 && winnerOfLastRound.getOwnedTokens()<5)) {
            for(Player p : initiatedPlayers){
                if(!p.getHand().isEmpty()){
                    p.getHand().remove(0);
                }
            }
            startRound();
        }

        // Hat "winner" die Spielsieg-Bedingung erfüllt, indem er/sie die notwendige Anzahl an Tokens erreicht, wird "winner" zum Sieger erklärt.
        // Zusätzlich dazu wird ein Ranking angezeigt, der alle initialisierten Spieler nach Anzahl der Tokens sortiert.
        else{
            System.out.println(ConsoleColors.GREEN+ConsoleColors.BOLD+ConsoleColors.UNDERLINE+winnerOfLastRound.getName().toUpperCase()+" HAT DAS SPIEL GEWONNEN!"+ConsoleColors.RESET);
            rankRemainingByTokens(initiatedPlayers);
            showGameRanking(initiatedPlayers);
            isRunning = false;
            System.exit(0);
        }
    }


    /**
     *  Behandelt den Fall, in dem keine Karten mehr im Kartendeck sind. Der Gewinner der Runde ist der, dessen Handkarte den höchsten Wert hat.
     *  Der Gewinner bekommt einen Token.
     *  Ist die Gewinnbedingung für das Spiel noch nicht erfüllt ist, wird eine neue Runde gestartet.
     *  Andernfalls wird das Spiel beendet, und der Gesamtsieger wird erklärt.
     *  Die Methode wird ausschließlich in der drawCard()-Methode in der Deck-Klasse verwendet.
     */
    public static void handleNoCardsInDeckWin() throws InterruptedException {
        rankRemainingByCardValue();                     // Die übrigen Spieler werden nach Kartenwert für die aktuelle Runde geranked.
        showRankingByCardValue();
        winnerOfLastRound = playersInstanceList.get(0);     // Der Spieler mit dem höchsten Kartenwert, welcher in der gerankeden Liste an erster Stelle steht ist der "winner".
        winnerOfLastRound.ownedTokens++;                           // "winner" erhält einen Token.
        System.out.println(ConsoleColors.GREEN+ConsoleColors.BOLD+"\n++++"+winnerOfLastRound.getName().toUpperCase()+" GEWINNT DURCH HÖCHSTEN KARTENWERT UND ERHÄLT EINEN TOKEN!++++\n\n"+ConsoleColors.RESET);

        // Hat "winner" die Gewinnbedingung noch nicht erfüllt, wird eine neue Runde gestartet.
        if((playerNumber==4 && winnerOfLastRound.getOwnedTokens()<3) ||
            (playerNumber==3 && winnerOfLastRound.getOwnedTokens()<4) ||
            (playerNumber==2 && winnerOfLastRound.getOwnedTokens()<5)) {
            for(Player p : initiatedPlayers){
                if(!p.getHand().isEmpty()){
                    p.getHand().remove(0);
                }
            }
            startRound();
        }

        // Sonst, also wenn die Gewinnbedingung für den Spielsieg erfüllt ist, wird "winnerOfLastRound", bzw. bei mehreren Siegern der jüngere, zum Gesamtsieger erklärt.
        // Außerdem wird ein Spielranking angezeigt.
        else{
            System.out.println(ConsoleColors.GREEN+ConsoleColors.BOLD+ConsoleColors.UNDERLINE+winnerOfLastRound.getName().toUpperCase()+" HAT DAS SPIEL GEWONNEN!"+ConsoleColors.RESET);
            rankRemainingByTokens(initiatedPlayers);
            showGameRanking(initiatedPlayers);
            isRunning = false;
            System.exit(0);
        }
    }

    /**
     *  Diese Methode rankt die übrigen Spieler in einer gegebenen Liste nach Kartenwert. Sie ist notwendig, damit (mit get(0)) der Gewinner der Runde ermittelt werden kann und
     *  dieser dann einen Token bekommt.
     */
    public static void rankRemainingByCardValue(){
        playersInstanceList.sort(Comparator.comparingInt((Player p) -> p.getHand().get(0).getValue()).reversed().thenComparing(Player::getAge));
    }

    /**
     *  Diese Methode zeigt den String auf der Konsole an, die ein Ranking visuell erzeugt.
     */
    public static void showRankingByCardValue(){
        System.out.println("");
        for(int i=0; i<playersInstanceList.size(); i++){
            System.out.println((i+1)+".: "+playersInstanceList.get(i).getName()+
                    " | Karte: "+playersInstanceList.get(i).getHand().get(0).getName()+" | Wert : "+playersInstanceList.get(i).getHand().get(0).getValue()+" (Alter: "+playersInstanceList.get(i).getAge()+")");
        }
        System.out.println("");
    }

    /**
     * Sortiert eine als Parameter übergebene Spielerliste anhand der Anzahl der Tokens.
     * An erster Stelle(listToRank.get(0)) steht am Ende der Spieler, der die meisten Tokens hat.
     * @param listToRank Liste, die nach Tokens sortiert werden soll.
     */
    public static void rankRemainingByTokens(List<Player> listToRank){
        listToRank.sort(Comparator.comparingInt(Player::getOwnedTokens).reversed().thenComparing(Player::getAge));
    }

    /**
     * Zeigt einen String an, der eine als Parameter übergebene Liste geranked anzeigt.
     * Die als Parameter übergebene Liste sollte eine gerankede Liste sein, damit diese Funktion Sinn ergibt, denn
     * diese Funktion ist nur zum Anzeigen einer Liste gedacht.
     * @param rankedList Ranking-Liste, die am Ende des Spiels angezeigt wird
     */
    public static void showGameRanking(List<Player> rankedList){
        int numerator = 1;
        for(Player p : rankedList){
            System.out.println(ConsoleColors.BOLD+ConsoleColors.UNDERLINE+numerator+". PLATZ :"+ConsoleColors.RESET+" "+p.getName()+" | Tokens : "+p.getOwnedTokens()+" | Alter: "+p.getAge());
            numerator++;
        }
    }
}