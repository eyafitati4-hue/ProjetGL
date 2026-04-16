package projetPFE.example.monProjet.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.transaction.Transactional;
import lombok.*;
import projetPFE.example.monProjet.DTOmapper.UtilisateurDtoMapper;
import projetPFE.example.monProjet.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO for {@link Role}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RoleDto implements Serializable {

    private Integer idRole;
    private  String labelrole;
    private  Set<UtilisateurDto> utilisateurList;


}