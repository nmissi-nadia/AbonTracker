package utilitaire;


public class ExceptionUtil {

    /**
     * Affiche un message d'erreur formaté avec la cause de l'exception.
     *
     * @param e       L'exception attrapée
     * @param context Contexte (ex: "Erreur opération")
     */
    public static void handle(Exception e, String context) {
        System.err.println("[" + context + "] " + e.getClass().getSimpleName() + " : " + e.getMessage());
        // Décommente ci-dessous si tu veux voir tout le stacktrace en debug
        // e.printStackTrace();
    }

    /**
     * Variante simple si tu n'as que l'exception.
     */
    public static void handle(Exception e) {
        handle(e, "Erreur");
    }
}
