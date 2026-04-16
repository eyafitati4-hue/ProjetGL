package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "demande", schema = "public", indexes = {
        @Index(name = "association9_fk", columnList = "idutilisateur"),
        @Index(name = "association6_fk", columnList = "idetatdemade"),
        @Index(name = "association5_fk", columnList = "iddevis")
})
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idDemande", insertable=false, updatable=false)
    private int Demande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "idutilisateur", nullable = false)
    private Utilisateur utilisateur;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "idetatdemade", nullable = false)
    private Etatdemande etatdemande;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    @JoinColumn(name = "iddevis", nullable = false)
    private Devi devis;


    @Column(name = "date", length = 254)
    private String date;

    @Column(name = "montant", length = 254)
    private String montant;

    @Column(name = "status", length = 254)
    private String status;

    @Column(name = "produit")
    private String produit;

    @Column(name = "marque")
    private String marque;

    @Column(name = "apportpropre")
    private double apportpropre;

    @Column(name = "loyer")
    private double loyer;



    @Column(name = "prixproduit")
    private Integer prixproduit;





}
