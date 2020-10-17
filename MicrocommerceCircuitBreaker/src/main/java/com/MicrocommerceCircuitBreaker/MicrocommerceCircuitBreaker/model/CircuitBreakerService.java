package com.MicrocommerceCircuitBreaker.MicrocommerceCircuitBreaker.model;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;


@Service
public class CircuitBreakerService {
    @Autowired
    RestTemplate restTemplate;
    //*******************************************************************************/
    //****************End point  Afficher un Produit grace à son ID**************/
    //*******************************************************************************/
    @HystrixCommand(fallbackMethod = "callProductServiceAndGetData_Fallback")
    public String callProductServiceAndGetData(int id) {

        System.out.println("Getting School details for " + id);

        String response = restTemplate
                .exchange("http://localhost:9090/Produits/{id}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<String>() {
                        }, id).getBody();

        System.out.println("Response Received as " + response + " -  " + new Date());

        return "NORMAL FLOW !!! - School Name -  " + id + " :::  " +
                " Student Details " + response + " -  " + new Date();
    }

 @SuppressWarnings("unused")
    private String callProductServiceAndGetData_Fallback(int id) {

        System.out.println("Student Service is down!!! fallback route enabled...");

        return "CIRCUIT BREAKER ENABLED!!! No Response From Student Service at this moment. " +
                " Service will be back shortly - " + new Date();
    }

    //*******************************************************************************/
    //****************End point  Afficher la liste des Produits *********************/
    //*******************************************************************************/
    @HystrixCommand(fallbackMethod = "callListProduct_Fallback")
    public String callListProduct() {
        URI uri = URI.create("http://localhost:9090/Produits");
        return restTemplate.getForObject(uri, String.class);

    }

    @SuppressWarnings("unused")
    private String callListProduct_Fallback() {
        System.out.println("Student Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }


    //*******************************************************************************/
    //*************End point  Trier les produits par ordre alphabetique**************/
    //*******************************************************************************/

    @HystrixCommand(fallbackMethod = "callTriProduct_Fallback")
    public String callTriProduct() {
        URI uri = URI.create("http://localhost:9090/Tri");
        return restTemplate.getForObject(uri, String.class);

    }

    @SuppressWarnings("unused")
    private String callTriProduct_Fallback() {
        System.out.println("Student Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }
    //*******************************************************************************/
    //****************End point  Qui renvoie les produits et leurs marges**************/
    //*******************************************************************************/

    @HystrixCommand(fallbackMethod = "callMargeProduct_Fallback")
    public String callMargeProduct() {
        URI uri = URI.create("http://localhost:9090/AdminProduits");
        return restTemplate.getForObject(uri, String.class);

    }

    @SuppressWarnings("unused")
    private String callMargeProduct_Fallback() {
        System.out.println("Student Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }

    //*******************************************************************************/
    //****************End point  pour l'Ajout des produits **************************/
    //*******************************************************************************/

    @HystrixCommand(fallbackMethod = "callAjouterProduct_Fallback")
    public String callAjouterProduct() {
        String message = "Le produit a été ajouté avec succes ";
        return restTemplate.getForObject(message, String.class);

    }

    @SuppressWarnings("unused")
    private String callAjouterProduct_Fallback() {
        System.out.println("Product Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }

    //*******************************************************************************/
    //****************End point  pour la mise à jour des produits **************************/
    //*******************************************************************************/

    @HystrixCommand(fallbackMethod = "callUpdateProduct_Fallback")
    public String callUpdateProduct() {
        URI uri = URI.create("http://localhost:9090/Produits");
        return restTemplate.getForObject(uri, String.class);

    }

    @SuppressWarnings("unused")
    private String callUpdateProduct_Fallback() {
        System.out.println("Product Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }

    //*******************************************************************************/
    //****************End point  pour la suppression des produits **************************/
    //*******************************************************************************/

    @HystrixCommand(fallbackMethod = "callSupprimerProduct_Fallback")
    public String callSupprimerProduct() {
        URI uri = URI.create("http://localhost:9090/Produits");
        return restTemplate.getForObject(uri, String.class);

    }

    @SuppressWarnings("unused")
    private String callSupprimerProduct_Fallback() {
        System.out.println("Product Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }

    //*******************************************************************************/
    //****************End point  pour la recherche des produits **************************/
    //*******************************************************************************/

    @HystrixCommand(fallbackMethod = "callTesProduct_Fallback")
    public String callTestProduct() {
        URI uri = URI.create("http://localhost:9090/Produits");
        return restTemplate.getForObject(uri, String.class);

    }

    @SuppressWarnings("unused")
    private String callTestProduct_Fallback() {
        System.out.println("Product Service is down!!! fallback route enabled...");
        return "Mauvais Lien !";
    }
//******************************************************************************************/
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
