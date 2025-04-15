package Game;

/**
 * Die Klasse ConsoleColors definiert ANSI Escape Codes für Textfarben, die in der Konsole verwendet werden können.
 * Diese Codes ermöglichen das Ändern der Textfarben und das Dickmarkieren in der Konsole, um Texte in verschiedenen Farben anzuzeigen.
 */
public class ConsoleColors {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

}
