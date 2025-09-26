package entity;

import java.time.LocalDate;
import java.util.UUID;

public class Paiement {
    private String idPaiement;
    private String idAbonnement;
    private LocalDate dateEcheance;
    private LocalDate datePaiement;
    private String typePaiement;
    private StatutPaiement statut;



    public enum StatutPaiement {
        PAYE,
        NON_PAYE,
        EN_RETARD
    }
    public Paiement(String idAbonnement, LocalDate dateEcheance, String typePaiement) {
        this.idPaiement = UUID.randomUUID().toString();
        this.idAbonnement = idAbonnement;
        this.dateEcheance = dateEcheance;
        this.typePaiement = typePaiement;
        updateStatut();
    }
    public Paiement(String idPaiement, String idAbonnement,
                    LocalDate dateEcheance, LocalDate datePaiement,
                    String typePaiement, StatutPaiement statut) {
        this.idPaiement = idPaiement;  // pas de génération ici
        this.idAbonnement = idAbonnement;
        this.dateEcheance = dateEcheance;
        this.datePaiement = datePaiement;
        this.typePaiement = typePaiement;
        this.statut = statut;
    }
    public void setIdPaiement(String idPaiement) {
        this.idPaiement = idPaiement;
    }
    /** mise à jour automatique du statut */
    public void updateStatut() {
        if (datePaiement != null) {
            this.statut = StatutPaiement.PAYE;
        } else if (dateEcheance.isBefore(LocalDate.now())) {
            this.statut = StatutPaiement.EN_RETARD;
        } else {
            this.statut = StatutPaiement.NON_PAYE;
        }
    }

    public String getIdPaiement() { return idPaiement; }
    public String getIdAbonnement() { return idAbonnement; }
    public LocalDate getDateEcheance() { return dateEcheance; }
    public LocalDate getDatePaiement() { return datePaiement; }
    public String getTypePaiement() { return typePaiement; }
    public StatutPaiement getStatut() { updateStatut(); return statut; }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
        updateStatut();
    }
    public void setStatut(StatutPaiement statutPaiement) {
        this.statut = statutPaiement;
    }
    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    @Override
    public String toString() {
        return String.format("Paiement{id=%s, echeance=%s, paiement=%s, statut=%s}",
                idPaiement, dateEcheance, datePaiement, getStatut());
    }
}
