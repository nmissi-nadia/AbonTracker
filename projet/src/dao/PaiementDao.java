package dao;

import entity.Paiement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementDAO {

    public void create(Paiement paiement) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO Paiement (idPaiement, idAbonnement, dateEcheance, datePaiement, typePaiement, statut) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paiement.getIdPaiement());
            stmt.setString(2, paiement.getIdAbonnement());
            stmt.setDate(3, Date.valueOf(paiement.getDateEcheance()));
            stmt.setDate(4, paiement.getDatePaiement() != null ? Date.valueOf(paiement.getDatePaiement()) : null);
            stmt.setString(5, paiement.getTypePaiement());
            stmt.setString(6, paiement.getStatut());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Paiement> findByAbonnement(String idAbonnement) {
        return new ArrayList<>();
    }

    public List<Paiement> findAll() {
        return new ArrayList<>();
    }

    public void update(Paiement paiement) {
        // TODO : implémenter
    }

    public void delete(String idPaiement) {
        // TODO : implémenter
    }
}

