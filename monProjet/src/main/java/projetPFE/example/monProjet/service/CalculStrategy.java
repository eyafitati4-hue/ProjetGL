package projetPFE.example.monProjet.service;

import projetPFE.example.monProjet.model.Produit;


public interface CalculStrategy {
    double calculerApport(double prixProduit);
    double calculerLoyer(Produit produit, int nombreAnnees, double nouvelApportPropre);
}
