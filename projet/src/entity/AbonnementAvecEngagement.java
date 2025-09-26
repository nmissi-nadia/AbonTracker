package entity;


import java.time.LocalDate;

public class AbonnementAvecEngagement extends Abonnement {
    private int dureeEngagementMois;

    public AbonnementAvecEngagement(String nomService, double montantMensuel,
                                    LocalDate dateDebut, int dureeEngagementMois) {
        super(nomService, montantMensuel, dateDebut);
        this.dureeEngagementMois = dureeEngagementMois;
        this.setDateFin(this.getDateDebut().plusMonths(dureeEngagementMois));
        updateStatut();
    }

    public int getDureeEngagementMois() { return dureeEngagementMois; }

    @Override
    public String toString() {
        return super.toString() + " (Avec engagement: " + dureeEngagementMois + " mois)";
    }
}
