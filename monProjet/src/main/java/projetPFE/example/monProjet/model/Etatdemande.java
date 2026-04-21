package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "etatdemande", schema = "public")
public class Etatdemande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idetatdemande", nullable = false)
    private Integer idetatdemande;

    @Column(name = "labelleetatdemande", length = 254)
    private String labelleetatdemande;

    @OneToMany(mappedBy = "idetatdemade")
    @JsonIgnore
    private Set<Demande> demandes = new LinkedHashSet<>();


}