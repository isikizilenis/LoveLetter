package Game;

import CardTypes.*;

import static Game.Game.playersInstanceList;

/**
 * Dies ist die CommandHandler-Klasse, die die Befehle des Spielers verarbeitet. Wurde ein Spiel noch nicht gestartet und
 * es wurde ein spielinterner Befehl(\playcard, \showhand, \showscore, \showplayers) ausgeführt,
 * wird ein Hinweis des Spielsystems ausgegeben, dass man diesen Befehl nicht ausführen kann, bevor man ein Spiel gestartet hat. Man kann den Helpbefehl immer ausführen.
 * Wird ein Spiel bereits ausgeführt und es wird erneut \start aufgerufen, wird der Hinweis ausgegeben, dass ein Spiel bereits läuft.
 */
public class CommandHandler {

    /**
     * Verarbeitet den Command, der vom Spieler eingegeben wurde, abhängig davon, ob ein Spiel gestartet wurde und ob der eingegebene Befehl gültig ist.
     * @param command Der vom Spieler eingegebene Befehl.
     */
    public static void handleCommand(String command) throws InterruptedException {
        command = command.toLowerCase();
        if(!Game.isStarted) {
            switch (command) {
                case "\\start":
                    Game.isStarted = true;
                    Game.askPlayerNumber();
                    Game.initiatePlayers();
                    Game.startRound();
                    break;
                case "\\help":
                    Player.showHelp();
                    break;
                case "\\showhand":
                    System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Bitte starte ein Spiel mit \\start, bevor du den Befehl \\showHand ausführst."+ConsoleColors.RESET);
                    break;
                case "\\showscore":
                    System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Bitte starte ein Spiel mit \\start, bevor du den Befehl \\showScore ausführst."+ConsoleColors.RESET);
                    break;
                case "\\playcard":
                    System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Bitte starte ein Spiel mit \\start, bevor du den Befehl \\playCard ausführst."+ConsoleColors.RESET);
                    break;
                case "\\showplayers":
                    System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Bitte starte ein Spiel mit \\start, bevor du den Befehl \\showPlayers ausführst."+ConsoleColors.RESET);
                    break;
                default:
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiger Befehl! \nDer Befehl "+command+" wurde nicht gefunden. Für Hilfe bitte \\help eingeben."+ConsoleColors.RESET);
                    break;
            }
        }
        else {
            Player victim;
            switch (command) {
                case "\\playcard":
                    Game.choosingCommand = false;
                    Game.currentPlayer.playCard();
                    break;
                case "\\showhand":
                    Game.currentPlayer.showHand();
                    break;
                case "\\showscore":
                    Game.currentPlayer.showScore();
                    break;
                case "\\showplayers":
                    Player.showPlayers();
                    break;
                case "\\help":
                    Player.showHelp();
                    break;
                case "\\start":
                    System.out.println(ConsoleColors.YELLOW+"###SYSTEM: Du kannst jetzt kein neues Spiel starten!"+ConsoleColors.RESET);
                    break;

                    //CHEATS ^^
                case "\\cheat::leakhand 1":
                    try {
                        victim = Game.playersInstanceList.get(0);
                        System.out.println(ConsoleColors.YELLOW + "###CHEAT ACTIVATED: " + victim.getName() + " has following Cards:" + ConsoleColors.RESET);
                        for (Cards c : victim.getHand()) {
                            System.out.print(c.getValue() + " " + c.getName() + "  ");
                        }
                        System.out.println("");
                    }
                    catch (Exception e){
                        System.out.println(ConsoleColors.YELLOW+"###CHEAT FAILED: Invalid Player-ID!"+ConsoleColors.RESET);
                    }
                    break;
                case "\\cheat::leakhand 2":
                    try {
                        victim = Game.playersInstanceList.get(1);
                        System.out.println(ConsoleColors.YELLOW + "###CHEAT ACTIVATED: " + victim.getName() + " has following Cards:" + ConsoleColors.RESET);
                        for (Cards c : victim.getHand()) {
                            System.out.print(c.getValue() + " " + c.getName() + "  ");
                        }
                        System.out.println("");
                    }
                    catch (Exception e){
                        System.out.println(ConsoleColors.YELLOW+"###CHEAT FAILED: Invalid Player-ID!"+ConsoleColors.RESET);
                    }
                    break;
                case "\\cheat::leakhand 3":
                    try {
                        victim = Game.playersInstanceList.get(2);
                        System.out.println(ConsoleColors.YELLOW + "###CHEAT ACTIVATED: " + victim.getName() + " has following Cards:" + ConsoleColors.RESET);
                        for (Cards c : victim.getHand()) {
                            System.out.print(c.getValue() + " " + c.getName() + "  ");
                        }
                        System.out.println("");
                    }
                    catch (Exception e){
                        System.out.println(ConsoleColors.YELLOW+"###CHEAT FAILED: Invalid Player-ID!"+ConsoleColors.RESET);
                    }
                    break;
                case "\\cheat::leakhand 4":
                    try {
                        victim = Game.playersInstanceList.get(3);
                        System.out.println(ConsoleColors.YELLOW + "###CHEAT ACTIVATED: " + victim.getName() + " has following Cards:" + ConsoleColors.RESET);
                        for (Cards c : victim.getHand()) {
                            System.out.print(c.getValue() + " " + c.getName() + "  ");
                        }
                        System.out.println("");
                    }
                    catch (Exception e){
                        System.out.println(ConsoleColors.YELLOW+"###CHEAT FAILED: Invalid Player-ID!"+ConsoleColors.RESET);
                    }
                    break;

                default:
                    System.out.println(ConsoleColors.RED+"###FEHLER: Ungültiger Befehl! \nDer Befehl "+command+" wurde nicht gefunden. Für Hilfe gebe den Befehl \\help ein."+ConsoleColors.RESET);
                    break;
            }
        }
    }
}