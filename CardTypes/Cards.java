package CardTypes;

/**
 * Die abstrakte Klasse Cards repräsentiert eine allgemeine Spielkarte in LoveLetter.
 * Jede konkrete Kartenklasse erbt von dieser abstrakten Klasse und implementiert den Karten spezifischen Effekt.
 * Diese Klasse enthält grundlegende Informationen über eine Karte wie den Namen und den Wert.
 */
public abstract class Cards {

    /**
     * Der Name der Karte.
     */
    public String name;

    /**
     * Der Wert der Karte.
     */
    private final int value;

    /**
     * Erstellt eine neue Instanz der Cards-Klasse mit einem gegebenen Namen und Wert.
     * @param name  Der Name der Karte.
     * @param value Der Wert der Karte.
     */
    public Cards(String name, int value){
        this.name = name;
        this.value = value;
    }

    /**
     * Gibt den Namen der Karte zurück.
     * @return Der Name der Karte.
     */
    public String getName(){
        return name;
    }

    /**
     * Gibt den Wert der Karte zurück.
     * @return Der Wert der Karte.
     */
    public int getValue(){
        return value;
    }

    /**
     * Diese Methode muss von konkreten Klassen implementiert werden, um den spezifischen Effekt der Karte auszuführen.
     */
    public abstract void makeEffect();
}