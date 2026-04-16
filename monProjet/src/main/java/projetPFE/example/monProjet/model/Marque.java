package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projetPFE.example.monProjet.model.Produit;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "marque", schema = "public")
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmarque", nullable = false)
    private Integer idMarque;

    @Column(name = "nommarque")
    private String nommarque;

    @Column(name = "logomarque")
    private String logomarque;

    @Column(name = "etatmarque")
    private Integer etatmarque;


    @OneToMany(mappedBy="idmarque") // Corrected mappedBy attribute
     Set<Produit> produits= new HashSet<>();

}