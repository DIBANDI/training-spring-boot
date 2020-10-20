package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicrocommerceApplicationTests {
	@Autowired
	private ProductController productControllerTest;

	@MockBean
	private ProductDao productDaoTest;

	@Test
	public void getListProductTest() {
		when(productDaoTest.findAll()).thenReturn(Stream.of(new Product(4, "Ordinateur fixe" , 355, 200), new Product(5, "Aspirateur hybride" , 400, 100)).collect(Collectors.toList()));
	assertEquals(2, productControllerTest.tousProduits().size());
	}


	@Test
	public void getProductByIdTest() {
		int id =3;
		Product product = new Product(3, "Table de Ping Pong" , 750, 400);
		when(productDaoTest.findById(id)).thenReturn(product);
		assertThat(product.getId(), is(productControllerTest.afficherUnProduit(id).getId()));
	}

	@Test
	@Rollback(false)
	public void addProductTest()
	{
		Product product = new Product(5, "Ordinateur fixe" , 450, 420);
		assertNotNull(productControllerTest.ajouterProduit(product));
	}

	@Test
	public void deleteProductTest(){
		int id = 1;
		productControllerTest.supprimerProduit(id);
		verify(productDaoTest, times(1)).delete(1);
	}

	@Test
	public void updateProductTest(){
		String nomproduit = "Degraisseur";
		Product product = new Product(2, nomproduit , 500, 200);
		product.setId(2);
		when(productDaoTest.save(product)).thenReturn(product);
		Product produit = productDaoTest.save(product);
		assertEquals(2, produit.getId());
		assertEquals("Degraisseur", produit.getNom());
	}

}
