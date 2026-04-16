package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import projetPFE.example.monProjet.model.Association4;
import projetPFE.example.monProjet.model.Document;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Document}
 */
@Setter
@Getter
@Builder
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class DocumentDto implements Serializable {
    private  Integer idDocument;
    private  String typedocument;
    private  String cheminfichier;
    private byte[] fichier; // Ajout de l'attribut fichier

    //private final Set<Association4> documentDemandeList;

    /*public DocumentDto(Integer idDocument, String typedocument, String cheminfichier, Set<Association4> documentDemandeList) {
        this.idDocument = idDocument;
        this.typedocument = typedocument;
        this.cheminfichier = cheminfichier;
        //this.documentDemandeList = documentDemandeList;
    }*/

   /* public Integer getIdDocument() {
        return idDocument;
    }

    public String getTypedocument() {
        return typedocument;
    }

    public String getCheminfichier() {
        return cheminfichier;
    }*/

    //public Set<Association4> getDocumentDemandeList() {
        //return documentDemandeList;
    //}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentDto entity = (DocumentDto) o;
        return Objects.equals(this.idDocument, entity.idDocument) &&
                Objects.equals(this.typedocument, entity.typedocument) &&
                Objects.equals(this.cheminfichier, entity.cheminfichier) &&
                 Objects.equals(this.fichier, entity.fichier);

        //Objects.equals(this.documentDemandeList, entity.documentDemandeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocument, typedocument, cheminfichier, fichier/*documentDemandeList*/);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idDocument = " + idDocument + ", " +
                "typedocument = " + typedocument + ", " +
                "cheminfichier = " + cheminfichier + ", " +
                "fichier = " + fichier + ")"+
                "documentDemandeList = " + /*documentDemandeList + */")";
    }
}