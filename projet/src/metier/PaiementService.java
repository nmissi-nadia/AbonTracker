package metier;

import dao.PaiementDAO;
import entity.Abonnement;
import entity.Paiement;
import metier.AbonnementService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaiementService {
    private PaiementDAO dao = new PaiementDAO();
    private AbonnementService abonnementService = new AbonnementService();

    public void creer(Paiement p) throws SQLException {
        dao.create(p);
    }

    public List<Paiement> lister() throws SQLException {
        return dao.findAll();
    }

    public void supprimer(String id) throws SQLException {
        dao.delete(id);
    }
    public void modifier(Paiement p) throws SQLException {
        dao.update(p);
    }

//    fonction de recherche
    public Paiement findById(String idPaiement) throws SQLException {
        return dao.findById(idPaiement);
    }
    public List<Paiement> findByAbonnement(String idAbonnement) {
        return dao.findByAbonnement(idAbonnement);
    }

    /** Génère automatiquement les échéances de paiement */
    public List<Paiement> genererEcheances(Abonnement abonnement, String typePaiement) {
        List<Paiement> paiements = new ArrayList<>();
        LocalDate start = abonnement.getDateDebut();
        LocalDate fin = abonnement.getDateFin();

        LocalDate current = start.plusMonths(1);
        while (fin == null || !current.isAfter(fin)) {
            Paiement p = new Paiement(abonnement.getId(), current, typePaiement);
            paiements.add(p);
            current = current.plusMonths(1);
        }
        return paiements;
    }
    // Paiements manqués pour un abonnement
    public List<Paiement> getPaiementsManques(String idAbonnement) {
        return dao.findUnpaidByAbonnement(idAbonnement);
    }

    // Somme payée d'un abonnement
    public double sommePayee(String idAbonnement) throws SQLException {
        Abonnement abo = abonnementService.findById(idAbonnement);
        if (abo == null) return 0;

        return dao.findByAbonnement(idAbonnement).stream()
                .filter(p -> p.getStatut() == Paiement.StatutPaiement.PAYE)
                .mapToDouble(p -> abo.getMontantMensuel()) // chaque paiement payé = montant de l'abonnement
                .sum();
    }



    // 5 derniers paiements
    public List<Paiement> cinqDerniersPaiements() {
        return dao.findLastFivePayments();
    }
}
