package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude
@NoArgsConstructor

@Table(name = "produit", schema = "public")
@AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idproduit", nullable = false)
    private Integer idProduit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "idmarque", nullable = false)
    private Marque idmarque;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "idutilisateur")
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "idetatproduit", nullable = false)
    private Etatproduit etatproduit;





    @Column(name = "modele", length = 254)
    private String modele;

    @Column(name = "kilometrage")
    private Integer kilometrage;

    @Column(name = "prixproduit")
    private Integer prixproduit;

    @Column(name = "description", length = 254)
    private String description;

    @Column(name = "image", length = 254)
    private String image;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "nomproduit", length = 254)
    private String nomproduit;


    @Column(name = "disponibilite", length = 254)
    private String disponibilite;

    @Column(name = "carrosserie", length = 254)
    private String carrosserie;


    @Column(name = "garantie")
    private Integer garantie;

    @Column(name = "nbplaces")
    private Integer nbplaces;

    @Column(name = "nbportes")
    private Integer nbportes;


    @Column(name = "apportpropre")
    private double apportpropre;

    @Column(name = "loyer")
    private double loyer;


}