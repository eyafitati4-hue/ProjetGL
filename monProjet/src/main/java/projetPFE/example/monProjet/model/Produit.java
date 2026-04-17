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

    // --- MANUALLY IMPLEMENTED BUILDER PATTERN ---
    private Produit(ProduitBuilder builder) {
        this.idProduit = builder.idProduit;
        this.idmarque = builder.idmarque;
        this.utilisateur = builder.utilisateur;
        this.etatproduit = builder.etatproduit;
        this.modele = builder.modele;
        this.kilometrage = builder.kilometrage;
        this.prixproduit = builder.prixproduit;
        this.description = builder.description;
        this.image = builder.image;
        this.quantite = builder.quantite;
        this.nomproduit = builder.nomproduit;
        this.disponibilite = builder.disponibilite;
        this.carrosserie = builder.carrosserie;
        this.garantie = builder.garantie;
        this.nbplaces = builder.nbplaces;
        this.nbportes = builder.nbportes;
        this.apportpropre = builder.apportpropre;
        this.loyer = builder.loyer;
    }

    public static ProduitBuilder builder() {
        return new ProduitBuilder();
    }

    public static class ProduitBuilder {
        private Integer idProduit;
        private Marque idmarque;
        private Utilisateur utilisateur;
        private Etatproduit etatproduit;
        private String modele;
        private Integer kilometrage;
        private Integer prixproduit;
        private String description;
        private String image;
        private Integer quantite;
        private String nomproduit;
        private String disponibilite;
        private String carrosserie;
        private Integer garantie;
        private Integer nbplaces;
        private Integer nbportes;
        private double apportpropre;
        private double loyer;

        public ProduitBuilder idProduit(Integer idProduit) {
            this.idProduit = idProduit;
            return this;
        }

        public ProduitBuilder idmarque(Marque idmarque) {
            this.idmarque = idmarque;
            return this;
        }

        public ProduitBuilder utilisateur(Utilisateur utilisateur) {
            this.utilisateur = utilisateur;
            return this;
        }

        public ProduitBuilder etatproduit(Etatproduit etatproduit) {
            this.etatproduit = etatproduit;
            return this;
        }

        public ProduitBuilder modele(String modele) {
            this.modele = modele;
            return this;
        }

        public ProduitBuilder kilometrage(Integer kilometrage) {
            this.kilometrage = kilometrage;
            return this;
        }

        public ProduitBuilder prixproduit(Integer prixproduit) {
            this.prixproduit = prixproduit;
            return this;
        }

        public ProduitBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProduitBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ProduitBuilder quantite(Integer quantite) {
            this.quantite = quantite;
            return this;
        }

        public ProduitBuilder nomproduit(String nomproduit) {
            this.nomproduit = nomproduit;
            return this;
        }

        public ProduitBuilder disponibilite(String disponibilite) {
            this.disponibilite = disponibilite;
            return this;
        }

        public ProduitBuilder carrosserie(String carrosserie) {
            this.carrosserie = carrosserie;
            return this;
        }

        public ProduitBuilder garantie(Integer garantie) {
            this.garantie = garantie;
            return this;
        }

        public ProduitBuilder nbplaces(Integer nbplaces) {
            this.nbplaces = nbplaces;
            return this;
        }

        public ProduitBuilder nbportes(Integer nbportes) {
            this.nbportes = nbportes;
            return this;
        }

        public ProduitBuilder apportpropre(double apportpropre) {
            this.apportpropre = apportpropre;
            return this;
        }

        public ProduitBuilder loyer(double loyer) {
            this.loyer = loyer;
            return this;
        }

        public Produit build() {
            return new Produit(this);
        }
    }
}