package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "association10", schema = "public")
public class Association10 {
    @GeneratedValue(strategy = GenerationType.AUTO)

    @EmbeddedId
    private Association10Id id;

    @Column(name = "quantite")
    private int quantite;

    //doit etre supprime
   /* @MapsId("idProduit")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "idProduit", nullable = true)
    private Produit Produit;*/

    /*@MapsId("idPanier")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "idPanier", nullable = true)
    private Panier Panier;*/




}