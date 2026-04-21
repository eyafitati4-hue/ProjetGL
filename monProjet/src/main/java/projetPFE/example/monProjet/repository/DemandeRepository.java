package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projetPFE.example.monProjet.model.Demande;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.idetatdemade.idetatdemande = ?1")
    int countByEtatDemande_Idetatdemande(int etatDemandeId);
    List<Demande> findByIdutilisateur(Utilisateur utilisateur);

}