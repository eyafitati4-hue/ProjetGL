package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projetPFE.example.monProjet.model.Association4;
import projetPFE.example.monProjet.model.Association4Id;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.List;

public interface Association4Repository extends JpaRepository<Association4, Association4Id> {

    @Query("SELECT a FROM Association4 a WHERE a.id.idDemande = :idDemande")
    List<Association4> findByIdDemande(Integer idDemande);



}