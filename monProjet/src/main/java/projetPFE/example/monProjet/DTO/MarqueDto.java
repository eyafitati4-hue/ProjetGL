package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import projetPFE.example.monProjet.model.Marque;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Marque}
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Builder
public class MarqueDto implements Serializable {
    private final Integer idMarque;
    private final String nommarque;
    private final String logomarque;
    private final Integer etatmarque;


    public MarqueDto(Integer idMarque, String nommarque, String logomarque,Integer etatmarque, Set<ProduitDto> produits) {
        this.idMarque = idMarque;
        this.nommarque = nommarque;
        this.logomarque = logomarque;
        this.etatmarque= etatmarque;

    }

   /* public Integer getIdMarque() {
        return idMarque;
    }

    public String getNommarque() {
        return nommarque;
    }

    public String getLogomarque() {
        return logomarque;
    }

    public Set<ProduitDto> getProduits() {
        return produits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarqueDto entity = (MarqueDto) o;
        return Objects.equals(this.idMarque, entity.idMarque) &&
                Objects.equals(this.nommarque, entity.nommarque) &&
                Objects.equals(this.logomarque, entity.logomarque) &&
                Objects.equals(this.produits, entity.produits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMarque, nommarque, logomarque, produits);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idMarque = " + idMarque + ", " +
                "nommarque = " + nommarque + ", " +
                "logomarque = " + logomarque + ", " +
                "produits = " + produits + ")";
    }*/
}