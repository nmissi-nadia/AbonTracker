package metier;
import entity.Paiement;
import java.util.List;

public class PaiementService {
    private PaiementDAO paiementDAO;

    public PaiementService() {
        this.paiementDAO = new PaiementDAO();
    }

    public void enregistrerPaiement(Paiement paiement) {
        paiementDAO.create(paiement);
    }

    public List<Paiement> getPaiementsByAbonnement(String idAbonnement) {
        return paiementDAO.findByAbonnement(idAbonnement);
    }

    public List<Paiement> getAllPaiements() {
        return paiementDAO.findAll();
    }

    public void modifierPaiement(Paiement paiement) {
        paiementDAO.update(paiement);
    }

    public void supprimerPaiement(String idPaiement) {
        paiementDAO.delete(idPaiement);
    }
}

