package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api( description="API pour es opérations CRUD sur les produits.")
public class ProductController {

    @Autowired
    private ProductDao productDao;


    @ApiOperation(value = "La Liste des Produits ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )

    @RequestMapping(value = "/Produits/liste", method = RequestMethod.GET, produces = "application/json")
    //Récupérer la liste des produits
    public MappingJacksonValue listeProduits() {

        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }


    //Récupérer un produit par son Id
    // @GetMapping(value = "/Produits/{id}")
    @ApiOperation ( value = "Rechercher un produit avec un ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/Produits/{id}")
   // @RequestMapping ( value = "/Produits/{id}" , method = RequestMethod. GET , produces = "application / json" )
    public Product afficherUnProduit(@PathVariable int id) {
        Product produit = productDao.findById(id);
        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        return produit;
    }


    //ajouter un produit
    //@PostMapping(value = "/Produits")
    @ApiOperation(value = "Veillez Ajouter Un Produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/Produits/ajouter")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        if(product.getPrix()>0)
        {
        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();
        //Condition de verification si le produit est de 0

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    } else
        {
            throw new ProduitGratuitException("Le prix de Vente doit etre Superieur à Zero !");
        }
    }

    //Suppression du produit
    //@DeleteMapping (value = "/Produits/{id}")
    @ApiOperation(value = "Veillez Supprimer le Produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value="/Produits/supprimer/{id}")
    public void supprimerProduit(@PathVariable int id) {
        productDao.delete(id);
    }

    //@PutMapping (value = "/Produits")
    @ApiOperation(value = "Veillez Mettre à jour le Produit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/Produits/modifier")
    public void updateProduit(@RequestBody Product product) {
        productDao.save(product);
    }


    //Pour les tests
    //@GetMapping(value = "test/produits/{prix}")
    @ApiOperation(value = "Rechercher les produits dont le prix est egal à 400 ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value="/Produits/test/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productDao.chercherUnProduitCher(400);
    }

    //Pour la marge des produits
    //@GetMapping(value="/AdminProduits")
    @ApiOperation(value = "Calcul de la marge et Affichage des Produits avec leur marge respective ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value="/Produits/AdminProduits")
    public Map<String, String> calculerMargeProduit(){
        Map<String, String> pproduits = new HashMap<String, String>();
        for (Product product: productDao.findAll()){
            pproduits.put(product.toString(), Integer.toString(product.getPrix()-product.getPrixAchat()));
        }
        return pproduits;
    }

    //Pour trier les produits
    //@GetMapping(value="/Tri")
    @ApiOperation(value = "Affichage des Produits par ordre Alphabetique")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value="/Produits/tri")
    public  List<Product> trieProduitsParOrdreAlphabetique(){
        List<Product> mesproduits= productDao.findAllByOrderByNomAsc();
        return mesproduits;
    }


    //Liste des produits
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/Produits/tousproduits", method = RequestMethod.GET)
    public  List<Product> tousProduits(){
        List<Product> mesproduits= productDao.findAll();
        return mesproduits;
    }

}
