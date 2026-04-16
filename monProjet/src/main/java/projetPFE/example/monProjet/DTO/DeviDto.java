package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Demande;
import projetPFE.example.monProjet.model.Devi;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Devi}
 */
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)

public class DeviDto implements Serializable {
    private final Integer idDevis;
    private final String datedevis;
    private final Set<Demande> demandes;

    public DeviDto(Integer idDevis, String datedevis, Set<Demande> demandes) {
        this.idDevis = idDevis;
        this.datedevis = datedevis;
        this.demandes = demandes;
    }

    public Integer getIdDevis() {
        return idDevis;
    }

    public String getDatedevis() {
        return datedevis;
    }

    public Set<Demande> getDemandes() {
        return demandes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviDto entity = (DeviDto) o;
        return Objects.equals(this.idDevis, entity.idDevis) &&
                Objects.equals(this.datedevis, entity.datedevis) &&
                Objects.equals(this.demandes, entity.demandes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDevis, datedevis, demandes);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idDevis = " + idDevis + ", " +
                "datedevis = " + datedevis + ", " +
                "demandes = " + demandes + ")";
    }
}