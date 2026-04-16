package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Association10;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Association10}
 */
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)


public class Association10Dto implements Serializable {
    private final Association10IdDto id;
    private final ProduitDto idProduit;
    private final PanierDto idPanier;

    public Association10Dto(Association10IdDto id, ProduitDto idProduit, PanierDto idPanier) {
        this.id = id;
        this.idProduit = idProduit;
        this.idPanier = idPanier;
    }

    public Association10IdDto getId() {
        return id;
    }

    public ProduitDto getIdProduit() {
        return idProduit;
    }

    public PanierDto getIdPanier() {
        return idPanier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Association10Dto entity = (Association10Dto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idProduit, entity.idProduit) &&
                Objects.equals(this.idPanier, entity.idPanier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idProduit, idPanier);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "idProduit = " + idProduit + ", " +
                "idPanier = " + idPanier + ")";
    }
}