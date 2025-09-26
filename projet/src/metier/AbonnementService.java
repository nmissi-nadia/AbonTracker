package metier;

import dao.AbonnementDAO;
import dao.PaiementDAO;
import entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class AbonnementService {
    private AbonnementDAO abonnementDAO = new AbonnementDAO();
    private PaiementDAO paiementDAO = new PaiementDAO();

    // Création d’un abonnement
    public void creerAbonnement(Abonnement abonnement) throws SQLException {
        // Générer automatiquement la date de fin
        if (abonnement instanceof AbonnementAvecEngagement) {
            AbonnementAvecEngagement abo = (AbonnementAvecEngagement) abonnement;
            if (abo.getDateDebut() != null && abo.getDureeEngagementMois() > 0) {
                abo.setDateFin(abo.getDateDebut().plusMonths(abo.getDureeEngagementMois()));
            }
        } else {
            abonnement.setDateFin(null); // Sans engagement = pas de date fin
        }
        try {
            genererEcheance(abonnement);
        } catch (SQLException e) {
            // log ou rethrow selon ton choix
            throw e;
        }

        // Enregistrement en base
        abonnementDAO.create(abonnement);
    }

    // Modification
    public void modifierAbonnement(Abonnement abonnement) throws SQLException {
        abonnementDAO.update(abonnement);
    }

    //recherche
    public Abonnement findById(String Id)throws SQLException {
        return abonnementDAO.findById(Id);
    }

        // Résiliation
    public void resilierAbonnement(String id) throws SQLException {
        Abonnement abo = abonnementDAO.findById(id);
        if (abo != null) {
            abo.setStatut(Abonnement.StatutAbonnement.RESILIE);
            abo.setDateFin(LocalDate.now());
            abonnementDAO.update(abo);
        }
    }

    // Suppression
    public void supprimerAbonnement(String id) throws SQLException {
        abonnementDAO.delete(id);
    }

    // Génération d’échéance automatique
    public void genererEcheance(Abonnement abo) throws SQLException {
        List<Paiement> paiements = paiementDAO.findByAbonnement(abo.getId());

        LocalDate derniereEcheance = paiements.isEmpty() ?
                abo.getDateDebut() : paiements.get(paiements.size() - 1).getDateEcheance();

        LocalDate prochaineEcheance = derniereEcheance.plusMonths(1);

        if (abo.getDateFin() == null || prochaineEcheance.isBefore(abo.getDateFin())) {
            Paiement paiement = new Paiement(
                    abo.getId(),
                    prochaineEcheance,
                    null
            );
            paiementDAO.create(paiement);
        }
    }

    // Vérification et mise à jour automatique du statut
    public void verifierStatut(Abonnement abo) throws SQLException {
        if (abo.getDateFin() != null && abo.getDateFin().isBefore(LocalDate.now())) {
            abo.setStatut(Abonnement.StatutAbonnement.RESILIE);
            abonnementDAO.update(abo);
        }
    }

    // Liste
    public List<Abonnement> listerAbonnements() throws SQLException {
        return abonnementDAO.findAll();
    }
}
