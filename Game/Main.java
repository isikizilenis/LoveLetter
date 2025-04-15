package Game;

/** Hiermit versichere ich, jede Zeile dieses Programms eigenhändig geschrieben und überprüft zu haben.
 *  Dieses Programm ist eine Version des Spiels Love Letter auf der Benutzerkonsole ohne eine GUI.
 *  Es beinhaltet nur Spiellogik und wird über Benutzereingaben auf der Konsole bedient.
 *  Dieser Code wurde geschrieben von Isik Arda Kizilenis.
 */
public class Main {

    /**
     * Die Main Methode erstellt eine Instanz der Klasse Game und führt die run()-Methode in dieser aus.
     */
    public static void main(String[] args) throws InterruptedException {
        Game g = new Game();
        g.run();
    }
}