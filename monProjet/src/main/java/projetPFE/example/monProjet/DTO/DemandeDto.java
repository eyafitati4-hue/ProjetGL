package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import projetPFE.example.monProjet.DTOmapper.DeviDtoMapper;
import projetPFE.example.monProjet.DTOmapper.EtatProduitDtoMapper;
import projetPFE.example.monProjet.DTOmapper.EtatdemandeDtoMapper;
import projetPFE.example.monProjet.model.Demande;
import projetPFE.example.monProjet.model.Devi;
import projetPFE.example.monProjet.model.Etatdemande;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Demande}
 */
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder

public class DemandeDto implements Serializable {
    private final int idDemande;
    private  UtilisateurDto idutilisateur;
    private  EtatdemandeDto idetatdemade;
    private  DeviDto iddevis;
    private final String date;
    private final String montant;
    private final String status;
    private final Integer prixproduit;
    private final double loyer ;
    private final double apportpropre;
    private final String marque;
    private final String produit;




    public void setDevis(Devi deviParDefaut) {
        this.iddevis= DeviDtoMapper.toDto(deviParDefaut);
    }

    public void setEtatdemade(Etatdemande etatDemandeParDefaut) {
        this.idetatdemade= EtatdemandeDtoMapper.toDto(etatDemandeParDefaut);
    }

    /*public DemandeDto(int idDemande, UtilisateurDto idutilisateur, EtatdemandeDto idetatdemade, DeviDto iddevis, String date, String montant, String status) {
        this.idDemande = idDemande;
        this.idutilisateur = idutilisateur;
        this.idetatdemade = idetatdemade;
        this.iddevis = iddevis;
        this.date = date;
        this.montant = montant;
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DemandeDto entity = (DemandeDto) o;
        return Objects.equals(this.idDemande, entity.idDemande) &&
                Objects.equals(this.idutilisateur, entity.idutilisateur) &&
                Objects.equals(this.idetatdemade, entity.idetatdemade) &&
                Objects.equals(this.iddevis, entity.iddevis) &&
                Objects.equals(this.date, entity.date) &&
                Objects.equals(this.montant, entity.montant) &&
                Objects.equals(this.status, entity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDemande, idutilisateur, idetatdemade, iddevis, date, montant, status);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idDemande = " + idDemande + ", " +
                "idutilisateur = " + idutilisateur + ", " +
                "idetatdemade = " + idetatdemade + ", " +
                "iddevis = " + iddevis + ", " +
                "date = " + date + ", " +
                "montant = " + montant + ", " +
                "status = " + status + ")";
    }*/
}