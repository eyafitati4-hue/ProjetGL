package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Association10;
import projetPFE.example.monProjet.model.Panier;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Panier}
 */
@Setter
@Getter
@Builder
@JsonIgnoreProperties

public class PanierDto implements Serializable {
    private final Integer idPanier;

    public PanierDto(Integer idPanier) {
        this.idPanier = idPanier;
    }

    /*
        public Integer getIdPanier() {
            return idPanier;
        }

        public Set<Association10> getPanierproduitList() {
            return panierproduitList;
        }
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PanierDto entity = (PanierDto) o;
        return Objects.equals(this.idPanier, entity.idPanier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPanier);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idPanier = " + idPanier + ", " +
                "panierproduitList = " + ")";
    }



}