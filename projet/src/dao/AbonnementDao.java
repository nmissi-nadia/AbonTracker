package dao;
import entity.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementDAO {

    public void create(Abonnement abonnement) {
        DatabaseMetaData Database;
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO Abonnement (id, nomService, montantMensuel, dateDebut, dateFin, statut, typeAbonnement, dureeEngagementMois) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, abonnement.getId());
            stmt.setString(2, abonnement.getNomService());
            stmt.setFloat(3, abonnement.getMontantMensuel());
            stmt.setDate(4, Date.valueOf(abonnement.getDateDebut()));
            stmt.setDate(5, Date.valueOf(abonnement.getDateFin()));
            stmt.setString(6, abonnement.getStatut());
            stmt.setString(7, abonnement instanceof AbonnementAvecEngagement ? "AVEC" : "SANS");
            stmt.setObject(8, abonnement instanceof AbonnementAvecEngagement ? ((AbonnementAvecEngagement) abonnement).getDureeEngagementMois() : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Abonnement findById(String id) {
        // TODO : implémenter
        return null;
    }

    public List<Abonnement> findAll() {
        return new ArrayList<>();
    }

    public void update(Abonnement abonnement) {
        // TODO : implémenter
    }

    public void delete(String id) {
        // TODO : implémenter
    }
}
