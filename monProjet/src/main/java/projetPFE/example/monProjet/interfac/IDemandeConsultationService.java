package projetPFE.example.monProjet.interfac;

import projetPFE.example.monProjet.model.Demande;
import java.util.List;

/**
 * SOLID – ISP (Interface Segregation Principle) :
 * Interface dédiée à la consultation des demandes (lecture seule).
 * Peut être utilisée par tous les rôles autorisés à visualiser les demandes.
 */
public interface IDemandeConsultationService {
    Demande getById(int id);
    List<Demande> getAll();
}
