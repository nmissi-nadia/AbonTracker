package entity;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Abonnement {
    protected String id;
    protected String nomService;
    protected double montantMensuel;
    protected LocalDate dateDebut;
    protected LocalDate dateFin; // auto
    protected StatutAbonnement statut;



    public enum StatutAbonnement {
        ACTIVE,
        SUSPENDU,
        RESILIE
    }
    public Abonnement(String nomService, double montantMensuel, LocalDate dateDebut) {
        this.id = UUID.randomUUID().toString();
        this.nomService = nomService;
        this.montantMensuel = montantMensuel;
        this.dateDebut = dateDebut;
        this.dateFin = dateDebut.plusMonths(1);
        this.statut = StatutAbonnement.ACTIVE;
    }

    /** Mise à jour du statut selon dateFin */
    public void updateStatut() {
        if (dateFin != null && dateFin.isBefore(LocalDate.now())) {
            this.statut = StatutAbonnement.RESILIE;
        } else {
            this.statut = StatutAbonnement.ACTIVE;
        }
    }

    public String getId() { return id; }
    public String getNomService() { return nomService; }
    public double getMontantMensuel() { return montantMensuel; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public StatutAbonnement getStatut() { updateStatut(); return statut; }

    public void setNomService(String nomService) { this.nomService = nomService; }
    public void setMontantMensuel(double montantMensuel) { this.montantMensuel = montantMensuel; }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return String.format("Abonnement{id=%s, service=%s, montant=%.2f, debut=%s, fin=%s, statut=%s}",
                id, nomService, montantMensuel, dateDebut, dateFin, getStatut());
    }
}
