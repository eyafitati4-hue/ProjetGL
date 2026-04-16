package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import projetPFE.example.monProjet.model.Etatutilisateur;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Etatutilisateur}
 *
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
public class EtatutilisateurDto implements Serializable {
    private Integer idEtat;
    @JsonIgnore
    private  Set<UtilisateurDto> utilisateurList;
    private  String labelleetatutilisateur;

   /* public EtatutilisateurDto(Integer idEtat, Set<UtilisateurDto> utilisateurList, String labelleetatutilisateur) {
        this.idEtat = idEtat;
        this.utilisateurList = utilisateurList;
        this.labelleetatutilisateur = labelleetatutilisateur;
    }

    public Integer getIdEtat() {
        return idEtat;
    }

    public Set<UtilisateurDto> getUtilisateurList() {
        return utilisateurList;
    }

    public String getLabelleetatutilisateur() {
        return labelleetatutilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtatutilisateurDto entity = (EtatutilisateurDto) o;
        return Objects.equals(this.idEtat, entity.idEtat) &&
                Objects.equals(this.utilisateurList, entity.utilisateurList) &&
                Objects.equals(this.labelleetatutilisateur, entity.labelleetatutilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtat, utilisateurList, labelleetatutilisateur);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idEtat = " + idEtat + ", " +
                "utilisateurList = " + utilisateurList + ", " +
                "labelleetatutilisateur = " + labelleetatutilisateur + ")";
    }
*/
    /*public void setId(Integer id) {
        this.idEtat = id;
    }

    public Integer getId() {
        return idEtat;
    }*/
}