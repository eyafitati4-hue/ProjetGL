package projetPFE.example.monProjet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.model.Produit;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    List<Produit> findByIdmarqueIdMarque(Integer idMarque);
    List<Produit> findByEtatproduit_Idetatproduit(Integer idEtatProduit);

    @Query("SELECT COUNT(p) FROM Produit p WHERE p.etatproduit.idetatproduit = :etatId")
    Integer getCountByEtatProduit(Integer etatId);
}