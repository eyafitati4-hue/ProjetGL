package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Association4Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Association4Id}
 */

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)
public class Association4IdDto implements Serializable {
    private final Integer idDocument;
    private final Integer idDemande;

    public Association4IdDto(Integer idDocument, Integer idDemande) {
        this.idDocument = idDocument;
        this.idDemande = idDemande;
    }

    public Integer getIdDocument() {
        return idDocument;
    }

    public Integer getIdDemande() {
        return idDemande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Association4IdDto entity = (Association4IdDto) o;
        return Objects.equals(this.idDocument, entity.idDocument) &&
                Objects.equals(this.idDemande, entity.idDemande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocument, idDemande);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idDocument = " + idDocument + ", " +
                "idDemande = " + idDemande + ")";
    }
}