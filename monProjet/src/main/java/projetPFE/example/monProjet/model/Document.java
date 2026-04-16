package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "document", schema = "public")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddocument", nullable = false)
    private Integer idDocument;

    @Column(name = "typedocument", length = 254)
    private String typedocument;

    @Column(name = "cheminfichier", length = 254)
    private String cheminfichier;

    @Column(name = "fichier", columnDefinition = "bytea")
    private byte[] fichier;






}