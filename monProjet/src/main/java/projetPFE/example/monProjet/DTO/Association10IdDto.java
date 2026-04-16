package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Association10Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Association10Id}
 */
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)


public class Association10IdDto implements Serializable {
    private final Integer idProduit;
    private final Integer idPanier;

    public Association10IdDto(Integer idProduit, Integer idPanier) {
        this.idProduit = idProduit;
        this.idPanier = idPanier;
    }

    public Integer getIdProduit() {
        return idProduit;
    }

    public Integer getIdPanier() {
        return idPanier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Association10IdDto entity = (Association10IdDto) o;
        return Objects.equals(this.idProduit, entity.idProduit) &&
                Objects.equals(this.idPanier, entity.idPanier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit, idPanier);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idProduit = " + idProduit + ", " +
                "idPanier = " + idPanier + ")";
    }
}