package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;
import projetPFE.example.monProjet.DTOmapper.Association10DtoDtoMapper;
import projetPFE.example.monProjet.model.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Produit}
 */
@Setter
@Getter
@NoArgsConstructor(force = true)
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)


public class ProduitDto implements Serializable {
    private final Integer idProduit;
    private final MarqueDto idmarque;
    private final UtilisateurDto idutilisateur;
    private final String modele;
    private final Integer kilometrage;
    private final Integer prixproduit;
    private final String description;
    private final EtatproduitDto etatproduit;
    private final String image;
    private final Integer quantite;
    private final String nomproduit;
    private   final double apportpropre;
    private  final double loyer;
    private final Integer nbplaces;
    private final String carrosserie;
    private final Integer nbportes;
    private final String disponibilite;
    private final Integer garantie;


    public ProduitDto(Integer idProduit,Integer garantie,String carrosserie,String disponibilite ,Integer nbportes,Integer nbplaces,MarqueDto idmarque, UtilisateurDto idutilisateur, Etatproduit idetatproduit, Set<Association10> panierproduitList, String modele, Integer kilometrage, Integer prixproduit, String description, EtatproduitDto etatproduit, String image, Integer quantite, String nomproduit , double apportpropre,double loyer ) {
        this.idProduit = idProduit;
        this.idmarque = idmarque;
        this.idutilisateur = idutilisateur;
        this.modele = modele;
        this.kilometrage = kilometrage;
        this.prixproduit = prixproduit;
        this.description = description;
        this.etatproduit = etatproduit;
        this.image = image;
        this.quantite = quantite;
        this.nomproduit = nomproduit;
        this.apportpropre= apportpropre;
        this.loyer= loyer ;
        this.nbplaces=nbplaces;
        this.carrosserie=carrosserie;
        this.nbportes=nbportes;
        this.disponibilite=disponibilite;
        this.garantie=garantie;
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitDto entity = (ProduitDto) o;
        return Objects.equals(this.idProduit, entity.idProduit) &&
               // Objects.equals(this.idmarque, entity.idmarque) &&
               // Objects.equals(this.idutilisateur, entity.idutilisateur) &&
              //  Objects.equals(this.idetatproduit, entity.idetatproduit) &&
                //Objects.equals(this.panierproduitList, entity.panierproduitList) &&
                Objects.equals(this.modele, entity.modele) &&
                Objects.equals(this.kilometrage, entity.kilometrage) &&
                Objects.equals(this.prixproduit, entity.prixproduit) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.etatproduit, entity.etatproduit) &&
                Objects.equals(this.image, entity.image) &&
                Objects.equals(this.quantite, entity.quantite) &&
                Objects.equals(this.nomproduit, entity.nomproduit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit/*, idmarque, idutilisateur, idetatproduit, panierproduitList*/, modele, kilometrage, prixproduit, description, etatproduit, image, quantite, nomproduit);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idProduit = " + idProduit + ", " +
              //  "idmarque = " + idmarque + ", " +
               // "idutilisateur = " + idutilisateur + ", " +
              //  "idetatproduit = " + idetatproduit + ", " +
               // "panierproduitList = " + panierproduitList + ", " +
                "modele = " + modele + ", " +
                "kilometrage = " + kilometrage + ", " +
                "prixproduit = " + prixproduit + ", " +
                "description = " + description + ", " +
                "etatproduit = " + etatproduit + ", " +
                "image = " + image + ", " +
                "quantite = " + quantite + ", " +
                "nomproduit = " + nomproduit + ")";
    }



}