package projetPFE.example.monProjet.DTO;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import lombok.*;
import projetPFE.example.monProjet.DTOmapper.RoleDtoMapper;
import projetPFE.example.monProjet.model.Demande;
import projetPFE.example.monProjet.model.Produit;
import projetPFE.example.monProjet.model.Role;
import projetPFE.example.monProjet.model.Utilisateur;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Utilisateur}
 */
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class UtilisateurDto implements Serializable {
    private  Integer idutilisateur;
    private  String nomutilisateur;
    private  String prenom;
    private  String email;
    private  String telephone;
    private  String motdepasse;
    private  String adresse;
    private  String ville;
    private  String codepostal;
    private  LocalDate datenaissance;


    private  RoleDto idRole;

    private  EtatutilisateurDto idEtat;



   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateurDto entity = (UtilisateurDto) o;
        return Objects.equals(this.idutilisateur, entity.idutilisateur) &&
                Objects.equals(this.nomutilisateur, entity.nomutilisateur) &&
                Objects.equals(this.prenom, entity.prenom) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.telephone, entity.telephone) &&
                Objects.equals(this.motdepasse, entity.motdepasse) &&
                Objects.equals(this.adresse, entity.adresse) &&
                Objects.equals(this.ville, entity.ville) &&
                Objects.equals(this.codepostal, entity.codepostal) &&
                Objects.equals(this.datenaissance, entity.datenaissance) &&
                Objects.equals(this.idRole, entity.idRole) &&
                Objects.equals(this.idEtat, entity.idEtat) &&
                Objects.equals(this.idEtat.getLabelleetatutilisateur(), entity.idEtat.getLabelleetatutilisateur());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idutilisateur, nomutilisateur, prenom, email, telephone, motdepasse, adresse, ville, codepostal, datenaissance, idRole, idEtat, idEtat.getLabelleetatutilisateur());
    }

   @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idutilisateur = " + idutilisateur + ", " +
                "nomutilisateur = " + nomutilisateur + ", " +
                "prenom = " + prenom + ", " +
                "email = " + email + ", " +
                "telephone = " + telephone + ", " +
                "motdepasse = " + motdepasse + ", " +
                "adresse = " + adresse + ", " +
                "ville = " + ville + ", " +
                "codepostal = " + codepostal + ", " +
                "datenaissance = " + datenaissance + ", " +
                "idRole = " + idRole + ", " +
                "idEtatIdEtat = " + idEtat + ", " +
                "idEtatLabelleetatutilisateur = " + idEtat.getLabelleetatutilisateur() + ")";
    }





}