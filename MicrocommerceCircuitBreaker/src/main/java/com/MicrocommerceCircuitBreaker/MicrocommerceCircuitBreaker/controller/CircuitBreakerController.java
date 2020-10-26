package com.MicrocommerceCircuitBreaker.MicrocommerceCircuitBreaker.controller;

import com.MicrocommerceCircuitBreaker.MicrocommerceCircuitBreaker.model.CircuitBreakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CircuitBreakerController {
    @Autowired
    CircuitBreakerService circuitBreakerService;

    // Acces à un produit par son ID
    @GetMapping(value = "/Produits/{id}")
    public String afficherUnProduit(@PathVariable int id){
        System.out.println("Afficher les données du lien");
        return  circuitBreakerService.callProductServiceAndGetData(id);
    }

    // Acces à la liste des produits
    @RequestMapping(value = "/Produits/tousproduits", method = RequestMethod.GET)
    public String listeProduits(){
        System.out.println("Afficher les données du lien");
        return  circuitBreakerService.callListProduct();
    }

    //Acces à la liste des produits par ordre alphabetique
    @GetMapping(value="/Produits/tri")
    public String trieProduitsParOrdreAlphabetique(){
        System.out.println("Afficher les données du lien");
        return  circuitBreakerService.callTriProduct();
    }

    //Acces aux produits et à leurs marges respectives
    @GetMapping(value="/Produits/AdminProduits")
    public String calculerMargeProduits(){
        System.out.println("Afficher les données du lien");
        return  circuitBreakerService.callMargeProduct();
    }

    //Acces à l'ajout des produits
    @PostMapping(value = "/Produits/ajouter")
    public String ajouterProduit(){
        System.out.println("Ajout du produit");
        return  circuitBreakerService.callAjouterProduct();
    }

    //Acces à la modification des produits
    @PutMapping(value = "/Produits/modifier")
    public String updateProduit(){
        System.out.println("Modification du produit");
        return  circuitBreakerService.callUpdateProduct();
    }

    //Confirmation de la suppression des produits
    @DeleteMapping(value="/Produits/supprimer/{id}")
    public String supprimerProduit(){
        System.out.println("Suppression du produit");
        return  circuitBreakerService.callSupprimerProduct();
    }

    //Test  des produits
    @GetMapping(value="/Produits/test/{prix}")
    public String testeDeRequetes(){
        System.out.println("Test du produit");
        return  circuitBreakerService.callTestProduct();
    }

}
