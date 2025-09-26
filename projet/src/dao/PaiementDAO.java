package dao;

import entity.*;
import utilitaire.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class PaiementDAO {

    // CREATE
    public void create(Paiement paiement) throws SQLException {
        String sql = "INSERT INTO paiement (id_paiement, id_abonnement, date_echeance, date_paiement, type_paiement, statut) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paiement.getIdPaiement());
            ps.setString(2, paiement.getIdAbonnement());
            ps.setDate(3, Date.valueOf(paiement.getDateEcheance()));
            ps.setDate(4, paiement.getDatePaiement() != null ? Date.valueOf(paiement.getDatePaiement()) : null);
            ps.setString(5, paiement.getTypePaiement());
            ps.setString(6, paiement.getStatut().name());
            ps.executeUpdate();
        }
    }

    // READ
    public Paiement findById(String id) throws SQLException {
        String sql = "SELECT * FROM paiement WHERE id_paiement=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToPaiement(rs);
            }
        }
        return null;
    }

    public List<Paiement> findByAbonnement(String idAbonnement) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiement WHERE id_abonnement=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idAbonnement);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    paiements.add(mapToPaiement(rs));
                }
            }
        } catch (SQLException e) {
            ExceptionUtil.handle(e, "Erreur lors de la récupération des paiements pour l'abonnement " + idAbonnement);
        }
        return paiements;
    }

    public List<Paiement> findAll() throws SQLException {
        String sql = "SELECT * FROM paiement";
        List<Paiement> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapToPaiement(rs));
            }
        }
        return list;
    }

    // UPDATE
    public void update(Paiement paiement) throws SQLException {
        String sql = "UPDATE paiement SET date_echeance=?, date_paiement=?, type_paiement=?, statut=? WHERE id_paiement=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(paiement.getDateEcheance()));
            ps.setDate(2, paiement.getDatePaiement() != null ? Date.valueOf(paiement.getDatePaiement()) : null);
            ps.setString(3, paiement.getTypePaiement());
            ps.setString(4, paiement.getStatut().name());
            ps.setString(5, paiement.getIdPaiement());
            ps.executeUpdate();
        }
    }

    // DELETE
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM paiement WHERE id_paiement=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    // HELPERS
    private Paiement mapToPaiement(ResultSet rs) throws SQLException {
        Paiement paiement = new Paiement(
                rs.getString("id_abonnement"),
                rs.getDate("date_echeance").toLocalDate(),
                rs.getString("type_paiement")
        );

        paiement.setIdPaiement(rs.getString("id_paiement")); // ⚡ écraser l’UUID généré
        if (rs.getDate("date_paiement") != null) {
            paiement.setDatePaiement(rs.getDate("date_paiement").toLocalDate());
        }
        paiement.setStatut(Paiement.StatutPaiement.valueOf(rs.getString("statut")));

        return paiement;
    }

        //    les abonnement impayé
        public List<Paiement> findUnpaidByAbonnement(String idAbonnement) {
            List<Paiement> paiements = new ArrayList<>();
            String sql = "SELECT * FROM paiement WHERE id_abonnement=? AND statut IN ('NON_PAYE','EN_RETARD')";
            try (Connection conn = Database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, idAbonnement);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        paiements.add(mapToPaiement(rs));
                    }
                }
            } catch (SQLException e) {
                ExceptionUtil.handle(e, "Erreur lors de récupération des paiements manqués");
            }
            return paiements;
        }
            //        dernier 5 payement
            public List<Paiement> findLastFivePayments() {
                List<Paiement> paiements = new ArrayList<>();
                String sql = "SELECT * FROM paiement ORDER BY date_paiement DESC LIMIT 5";
                try (Connection conn = Database.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        paiements.add(mapToPaiement(rs));
                    }
                } catch (SQLException e) {
                    ExceptionUtil.handle(e, "Erreur lors de récupération des 5 derniers paiements");
                }
                return paiements;
            }

}
