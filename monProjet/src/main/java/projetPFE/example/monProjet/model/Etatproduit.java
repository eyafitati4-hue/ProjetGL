package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "etatproduit", schema = "public")
@AllArgsConstructor
public class Etatproduit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idetatproduit", nullable = false)
    private Integer idetatproduit;

    @Column(name = "labelleetatproduit", length = 254)
    private String labelleetatproduit;

    @OneToMany(mappedBy = "etatproduit")
    private Set<Produit> produits = new LinkedHashSet<>();

    // --- MANUALLY IMPLEMENTED BUILDER PATTERN ---
    private Etatproduit(EtatproduitBuilder builder) {
        this.idetatproduit = builder.idetatproduit;
        this.labelleetatproduit = builder.labelleetatproduit;
    }

    public static EtatproduitBuilder builder() {
        return new EtatproduitBuilder();
    }

    public static class EtatproduitBuilder {
        private Integer idetatproduit;
        private String labelleetatproduit;

        public EtatproduitBuilder idetatproduit(Integer idetatproduit) {
            this.idetatproduit = idetatproduit;
            return this;
        }

        public EtatproduitBuilder labelleetatproduit(String labelleetatproduit) {
            this.labelleetatproduit = labelleetatproduit;
            return this;
        }

        public Etatproduit build() {
            return new Etatproduit(this);
        }
    }
}