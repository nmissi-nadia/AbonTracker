package dao;

import entity.*;
import utilitaire.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AbonnementDAO {

    // CREATE
    public void create(Abonnement abonnement) throws SQLException {
        String sql = "INSERT INTO abonnement " +
                "(id, nom_service, montant_mensuel, date_debut, date_fin, status, type_abonnement, duree_engagement_mois) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();){
             PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, abonnement.getId());
             ps.setString(2, abonnement.getNomService());
             ps.setDouble(3, abonnement.getMontantMensuel());
             ps.setDate(4, java.sql.Date.valueOf(abonnement.getDateDebut()));

// Gestion de la dateFin
            if (abonnement.getDateFin() != null) {
            ps.setDate(5, java.sql.Date.valueOf(abonnement.getDateFin()));
        } else {
            ps.setNull(5, java.sql.Types.DATE);
        }

// Type abonnement
        if (abonnement instanceof AbonnementAvecEngagement) {
            ps.setString(7, "AvecEngagement");
        } else {
            ps.setString(7, "SansEngagement");
        }

// Statut
        ps.setString(6, abonnement.getStatut().name());
            if (abonnement instanceof AbonnementAvecEngagement) {
                ps.setInt(8, ((AbonnementAvecEngagement) abonnement).getDureeEngagementMois());
            } else {
                ps.setInt(8, 0  );
            }
        ps.executeUpdate();
        } catch (Exception e) {
            ExceptionUtil.handle(e, "Création abonnement");
        }
    }

    // READ
    public Abonnement findById(String id) throws SQLException {
        String sql = "SELECT * FROM abonnement WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToAbonnement(rs);
            }
        }
        return null;
    }

    public List<Abonnement> findAll() throws SQLException {
        String sql = "SELECT * FROM abonnement";
        List<Abonnement> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapToAbonnement(rs));
            }
        }
        return list;
    }

    // UPDATE
    public void update(Abonnement abonnement) throws SQLException {
        String sql = "UPDATE abonnement SET nom_service=?, montant_mensuel=?, date_debut=?, date_din=?, status=?, duree_engagement_mois=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, abonnement.getNomService());
            ps.setDouble(2, abonnement.getMontantMensuel());
            ps.setDate(3, Date.valueOf(abonnement.getDateDebut()));
            ps.setDate(4, abonnement.getDateFin() != null ? Date.valueOf(abonnement.getDateFin()) : null);
            ps.setString(5, abonnement.getStatut().name());
            ps.setInt(6, abonnement instanceof AbonnementAvecEngagement ?
                    ((AbonnementAvecEngagement) abonnement).getDureeEngagementMois() : 0);
            ps.setString(7, abonnement.getId());

            ps.executeUpdate();
        }
    }

    // DELETE
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM abonnement WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    // HELPERS
    private Abonnement mapToAbonnement(ResultSet rs) throws SQLException {
        String type = rs.getString("type_abonnement");
        Abonnement abonnement;

        if ("AvecEngagement".equals(type)) {
            abonnement = new AbonnementAvecEngagement(
                    rs.getString("nom_service"),
                    rs.getDouble("montant_mensuel"),
                    rs.getDate("date_debut").toLocalDate(),
                    rs.getInt("duree_engagement_mois")
            );
        } else {
            abonnement = new AbonnementSansEngagement(
                    rs.getString("nom_service"),
                    rs.getDouble("montant_mensuel"),
                    rs.getDate("date_debut").toLocalDate()
            );
        }

        Date dateFinSql = rs.getDate("date_fin");
        if (dateFinSql != null) {
            abonnement.setDateFin(dateFinSql.toLocalDate());
        }

        String statutStr = rs.getString("status");
        if (statutStr != null) {
            abonnement.setStatut(Abonnement.StatutAbonnement.valueOf(statutStr));
        }

        return abonnement;
    }

}
