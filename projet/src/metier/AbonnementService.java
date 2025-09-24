package metier;
import entity.Abonnement;

import java.util.List;


public class AbonnementService {
    private AbonnementDAO abonnementDAO;

    public AbonnementService() {
        this.abonnementDAO = new AbonnementDAO();
    }

    public void creerAbonnement(Abonnement abonnement) {
        abonnementDAO.create(abonnement);
    }

    public Abonnement getAbonnementById(String id) {
        return abonnementDAO.findById(id);
    }

    public List<Abonnement> getAllAbonnements() {
        return abonnementDAO.findAll();
    }

    public void modifierAbonnement(Abonnement abonnement) {
        abonnementDAO.update(abonnement);
    }

    public void supprimerAbonnement(String id) {
        abonnementDAO.delete(id);
    }
}
