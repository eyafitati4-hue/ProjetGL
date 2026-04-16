package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import projetPFE.example.monProjet.model.Demande;
import projetPFE.example.monProjet.model.Etatdemande;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Etatdemande}
 */
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)


public class EtatdemandeDto implements Serializable {
    private final Integer idetatdemande;
    private final String labelleetatdemande;
    private final Set<Demande> demandes;

    public EtatdemandeDto(Integer idetatdemande, String labelleetatdemande, Set<Demande> demandes) {
        this.idetatdemande = idetatdemande;
        this.labelleetatdemande = labelleetatdemande;
        this.demandes = demandes;
    }

    public Integer getIdetatdemande() {
        return idetatdemande;
    }

    public String getLabelleetatdemande() {
        return labelleetatdemande;
    }

    public Set<Demande> getDemandes() {
        return demandes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtatdemandeDto entity = (EtatdemandeDto) o;
        return Objects.equals(this.idetatdemande, entity.idetatdemande) &&
                Objects.equals(this.labelleetatdemande, entity.labelleetatdemande) &&
                Objects.equals(this.demandes, entity.demandes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idetatdemande, labelleetatdemande, demandes);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idetatdemande = " + idetatdemande + ", " +
                "labelleetatdemande = " + labelleetatdemande + ", " +
                "demandes = " + demandes + ")";
    }
}