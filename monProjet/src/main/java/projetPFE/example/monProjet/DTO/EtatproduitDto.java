package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import projetPFE.example.monProjet.model.Etatproduit;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Etatproduit}
 */

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)

public class EtatproduitDto implements Serializable {
    private final Integer idetatproduit;
    private final String labelleetatproduit;
    private final Set<ProduitDto> produits;

    public EtatproduitDto(Integer idetatproduit, String labelleetatproduit, Set<ProduitDto> produits) {
        this.idetatproduit = idetatproduit;
        this.labelleetatproduit = labelleetatproduit;
        this.produits = produits;
    }

    public Integer getIdetatproduit() {
        return idetatproduit;
    }

    public String getLabelleetatproduit() {
        return labelleetatproduit;
    }

    public Set<ProduitDto> getProduits() {
        return produits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtatproduitDto entity = (EtatproduitDto) o;
        return Objects.equals(this.idetatproduit, entity.idetatproduit) &&
                Objects.equals(this.labelleetatproduit, entity.labelleetatproduit) &&
                Objects.equals(this.produits, entity.produits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idetatproduit, labelleetatproduit, produits);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idetatproduit = " + idetatproduit + ", " +
                "labelleetatproduit = " + labelleetatproduit + ", " +
                "produits = " + produits + ")";
    }
}