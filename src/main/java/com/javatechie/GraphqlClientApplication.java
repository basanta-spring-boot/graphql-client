package com.javatechie;

import com.javatechie.client.InventoryClient;
import com.javatechie.dto.Product;
import com.javatechie.dto.ProductRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class GraphqlClientApplication {

	@Autowired
	private InventoryClient client;

	@GetMapping("/fetch/products")
	public List<Product> getAllProducts(){
		return client.getAvailableProducts();
	}

	@GetMapping("/fetch/products/{category}")
	public List<Product> getProductByCategory(@PathVariable String category){
		return client.getProductByCategory(category);
	}

	@PutMapping("/update/stock")
	public Product updateStock(@RequestBody ProductRequestDTO productRequestDTO) {
		return client.updateStock(productRequestDTO);

	}

	public static void main(String[] args) {
		SpringApplication.run(GraphqlClientApplication.class, args);
	}

}
