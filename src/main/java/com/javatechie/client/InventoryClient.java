package com.javatechie.client;

import com.javatechie.dto.Product;
import com.javatechie.dto.ProductRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class InventoryClient {


    @Autowired
    private HttpGraphQlClient graphQlClient;


    public List<Product> getAvailableProducts() {
        String graphQLQuery = "query GetProducts {\n" +
                "    getProducts {\n" +
                "        name\n" +
                "        category\n" +
                "        price\n" +
                "    }\n" +
                "}\n";

        Mono<List<Product>> products = graphQlClient.document(graphQLQuery)
                .retrieve("getProducts")
                .toEntityList(Product.class);
        return products.block();

    }

    public List<Product> getProductByCategory(String category) {
        String graphQLQuery = String.format("query GetProductsByCategory {\n" +
                "    getProductsByCategory(category: \"%s\") {\n" +
                "        id\n" +
                "        name\n" +
                "        category\n" +
                "        price\n" +
                "        stock\n" +
                "    }\n" +
                "}\n", category);

        return graphQlClient.document(graphQLQuery)
                .retrieve("getProductsByCategory")
                .toEntityList(Product.class).block();
    }


    public Product updateStock(ProductRequestDTO productRequestDTO) {
        String graphQLQuery = String.format("mutation UpdateStock {\n" +
                "    updateStock(id: \"%s\", stock: %d) {\n" +
                "        id\n" +
                "        name\n" +
                "        category\n" +
                "        price\n" +
                "        stock\n" +
                "    }\n" +
                "}\n", productRequestDTO.getId(), productRequestDTO.getStock());

        // Execute the GraphQL query here and return the updated product
        return graphQlClient.document(graphQLQuery)
                .retrieve("updateStock")
                .toEntity(Product.class).block();
    }
}
