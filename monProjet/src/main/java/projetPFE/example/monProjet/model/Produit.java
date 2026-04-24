package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import projetPFE.example.monProjet.DTO.ProduitDto;
import projetPFE.example.monProjet.DTOmapper.EtatProduitDtoMapper;
import projetPFE.example.monProjet.DTOmapper.MarqueDtoMapper;
import projetPFE.example.monProjet.DTOmapper.UtilisateurDtoMapper;
import projetPFE.example.monProjet.builder.IProduitBuilder;

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
    /* OCL Invariant: context Produit inv: self.kilometrage >= 0 */
    private Integer kilometrage;

    @Column(name = "prixproduit")
    /* OCL Invariant: context Produit inv: self.prixproduit > 0 */
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
        // -- Application Manuelle des Contraintes OCL --
        validerInvariantsOCL();
        // GRASP - Expert: L'entité décide de son état de disponibilité
        calculerDisponibilite();
    }

    /**
     * @GRASP Expert / OCL Invariant
     * Protection du système contre les erreurs de saisie pour les calculs financiers.
     */
    public void validerInvariantsOCL() {
        // 1. Contraintes de base
        if (this.prixproduit != null && this.prixproduit <= 0) {
            throw new IllegalArgumentException("OCL Violation: le prix doit être strictement supérieur à zéro");
        }
        if (this.kilometrage != null && this.kilometrage < 0) {
            throw new IllegalArgumentException("OCL Violation: le kilométrage ne peut pas être négatif");
        }

        // 2. Contrainte OCL Contextuelle (État vs Kilométrage)
        if (this.etatproduit != null && this.etatproduit.getIdetatproduit() != null) {
            int idEtat = this.etatproduit.getIdetatproduit();
            
            // État NEUF (1) -> Doit avoir 0 km
            if (idEtat == 1 && this.kilometrage != null && this.kilometrage > 0) {
                throw new IllegalArgumentException("OCL Violation: Un véhicule NEUF doit avoir un kilométrage de 0.");
            }
            
            // État OCCASION/VENDU (> 1) -> Doit avoir > 0 km
            if (idEtat > 1 && this.kilometrage != null && this.kilometrage == 0) {
                throw new IllegalArgumentException("OCL Violation: Un véhicule d'OCCASION doit avoir un kilométrage > 0.");
            }
        }
    }

    /**
     * @GRASP Expert
     * Cette logique est placée ici car l'entité Produit possède l'information "quantite".
     */
    public void calculerDisponibilite() {
        if (this.quantite != null && this.quantite > 0) {
            this.disponibilite = "En Stock";
        } else {
            this.disponibilite = "Rupture de Stock";
        }
    }

    public static ProduitBuilder builder() {
        return new ProduitBuilder();
    }

    public static class ProduitBuilder implements IProduitBuilder {
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

        // --- Implémentation de IProduitBuilder (GoF) ---

        @Override
        public IProduitBuilder buildIdentite(ProduitDto dto) {
            this.idProduit = dto.getIdProduit();
            this.nomproduit = dto.getNomproduit();
            this.modele = dto.getModele();
            this.idmarque = MarqueDtoMapper.toEntity(dto.getIdmarque());
            this.utilisateur = UtilisateurDtoMapper.toEntity(dto.getIdutilisateur());
            return this;
        }

        @Override
        public IProduitBuilder buildCaracteristiques(ProduitDto dto) {
            this.description = dto.getDescription();
            this.image = dto.getImage();
            this.kilometrage = dto.getKilometrage();
            this.etatproduit = EtatProduitDtoMapper.toEntity(dto.getEtatproduit());
            this.carrosserie = dto.getCarrosserie();
            this.nbplaces = dto.getNbplaces();
            this.nbportes = dto.getNbportes();
            this.garantie = dto.getGarantie();
            return this;
        }

        @Override
        public IProduitBuilder buildFinancesEtStock(ProduitDto dto) {
            this.prixproduit = dto.getPrixproduit();
            this.quantite = dto.getQuantite();
            this.disponibilite = dto.getDisponibilite();
            this.apportpropre = dto.getApportpropre();
            this.loyer = dto.getLoyer();
            return this;
        }

        @Override
        public Produit getResult() {
            return this.build();
        }
    }
}
