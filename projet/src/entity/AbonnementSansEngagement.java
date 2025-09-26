package entity;

import java.time.LocalDate;

public class AbonnementSansEngagement extends Abonnement {
    public AbonnementSansEngagement(String nomService, double montantMensuel, LocalDate dateDebut) {
        super(nomService, montantMensuel, dateDebut);
        this.dateFin = null; // pas de fin
        updateStatut();
    }

    @Override
    public String toString() {
        return super.toString() + " (Sans engagement)";
    }
}

