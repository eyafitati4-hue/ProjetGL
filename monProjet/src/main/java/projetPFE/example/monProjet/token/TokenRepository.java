package projetPFE.example.monProjet.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projetPFE.example.monProjet.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Token, Integer>  {
    @Query("""
       select t from Token t inner join Utilisateur  u on t.utilisateur.idutilisateur= u.idutilisateur
       where u.idutilisateur =:utilisateurid and(t.expired = false  or t.revoked = false)
              """)
        List<Token> findAllValidTokenByUser(Integer utilisateurid)  ;

        Optional<Token> findByToken(String token);
        void deleteAllByUtilisateur(Utilisateur idUtilisateur);
    }



