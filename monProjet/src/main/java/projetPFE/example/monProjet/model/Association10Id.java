package projetPFE.example.monProjet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
@Embeddable

public class Association10Id implements Serializable {
    @Serial
    private static final long serialVersionUID = -3739696035416572361L;

    @NonNull
    @Column(name = "idProduit", nullable = false)
    private Integer idProduit;

    @NonNull
    @Column(name = "idPanier", nullable = false)
    private Integer idPanier;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Association10Id entity = (Association10Id) o;
        return Objects.equals(this.idPanier, entity.idPanier) &&
                Objects.equals(this.idProduit, entity.idProduit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPanier, idProduit);
    }

}