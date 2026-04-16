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
@Builder
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




}