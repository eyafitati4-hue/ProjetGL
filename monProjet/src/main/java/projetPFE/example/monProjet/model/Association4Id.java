package projetPFE.example.monProjet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Association4Id implements Serializable {
    @Serial
    private static final long serialVersionUID = 2342435619380099375L;
    @Column(name = "iddocument", nullable = false)
    private Integer idDocument;

    @Column(name = "iddemande", nullable = false)
    private Integer idDemande;

    public Integer getIddocument() {
        return idDocument;
    }

    public void setIddocument(Integer iddocument) {
        this.idDocument = iddocument;
    }

    public Integer getIddemande() {
        return idDemande;
    }

    public void setIddemande(Integer iddemande) {
        this.idDemande = iddemande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Association4Id entity = (Association4Id) o;
        return Objects.equals(this.idDocument, entity.idDocument) &&
                Objects.equals(this.idDemande, entity.idDemande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocument, idDemande);
    }

}