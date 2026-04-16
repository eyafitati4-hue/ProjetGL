package projetPFE.example.monProjet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Injecter l'état attendu par défaut (ID = 1)
        jdbcTemplate.execute("INSERT INTO etatutilisateur (idetatutilisateur, labelleetatutilisateur) VALUES (1, 'ACTIF') ON CONFLICT (idetatutilisateur) DO NOTHING");

        // Injecter les rôles attendus par l'AuthenticationService (ID = 1, 2, 3)
        jdbcTemplate.execute("INSERT INTO role (idrole, labelrole) VALUES (1, 'ADMIN') ON CONFLICT (idrole) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO role (idrole, labelrole) VALUES (2, 'CONCESSIONNAIRE') ON CONFLICT (idrole) DO NOTHING");
        jdbcTemplate.execute("INSERT INTO role (idrole, labelrole) VALUES (3, 'CLIENT') ON CONFLICT (idrole) DO NOTHING");
        
        System.out.println("Données initiales (Rôles & Etat) ont été chargées avec succès !");
    }
}
