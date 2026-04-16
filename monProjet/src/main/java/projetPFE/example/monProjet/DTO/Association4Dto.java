package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Association4;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Association4}
 */
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)


public class Association4Dto implements Serializable {
    private final Association4IdDto id;
    private final DocumentDto idDocument;
    private final DemandeDto idDemande;

    public Association4Dto(Association4IdDto id, DocumentDto idDocument, DemandeDto idDemande) {
        this.id = id;
        this.idDocument = idDocument;
        this.idDemande = idDemande;
    }

    public Association4IdDto getId() {
        return id;
    }

    public DocumentDto getIdDocument() {
        return idDocument;
    }

    public DemandeDto getIdDemande() {
        return idDemande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Association4Dto entity = (Association4Dto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idDocument, entity.idDocument) &&
                Objects.equals(this.idDemande, entity.idDemande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDocument, idDemande);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idDocument = " + idDocument + ", " +
                "idDemande = " + idDemande + ")";
    }
}