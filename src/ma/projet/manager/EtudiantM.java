package ma.projet.manager;

import ma.projet.connexion.Connexion;
import ma.projet.model.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtudiantM {
    private Connection cn = Connexion.getCn();

    public boolean create(Etudiant o) {
        String sql = "INSERT INTO Etudiant (nom, prenom, sexe, filiere) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getSexe());
            ps.setString(4, o.getFiliere());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Etudiant o) {
        String sql = "DELETE FROM Etudiant WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, o.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Etudiant o) {
        String sql = "UPDATE Etudiant SET nom = ?, prenom = ?, sexe = ?, filiere = ? WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            ps.setString(3, o.getSexe());
            ps.setString(4, o.getFiliere());
            ps.setInt(5, o.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM Etudiant";
        try (Statement st = cn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Etudiant etudiant = new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("sexe"),
                        rs.getString("filiere")
                );
                etudiants.add(etudiant);
            }
            // Debugging statement to print retrieved students
            for (Etudiant e : etudiants) {
                System.out.println(e.getId() + ", " + e.getNom() + ", " + e.getPrenom() + ", " + e.getSexe() + ", " + e.getFiliere());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

}
