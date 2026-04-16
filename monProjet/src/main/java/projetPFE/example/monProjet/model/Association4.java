package projetPFE.example.monProjet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Table(name = "association4", schema = "public")
public class Association4 {
    @GeneratedValue(strategy = GenerationType.AUTO)

    @EmbeddedId
    private Association4Id id;

    @Column(name = "fichiera", columnDefinition = "bytea")
    private byte[] fichiera;





}