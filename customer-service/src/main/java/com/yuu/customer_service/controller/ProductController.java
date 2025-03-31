package com.yuu.customer_service.controller;

import com.yuu.customer_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/listProduct")
    public String getProducts(Model model) {
        String apiUrl = "http://localhost:8080/api/products";

        try {
            // Tạo Header chứa Basic Auth
            HttpHeaders headers = new HttpHeaders();
            String auth = "admin:123"; // Tài khoản và mật khẩu
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes()); // Mã hóa Base64
            headers.set("Authorization", "Basic " + encodedAuth);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Gửi request với header chứa Basic Auth
            ResponseEntity<List<Product>> response = restTemplate.exchange(
                    apiUrl, 
                    HttpMethod.GET, 
                    entity, 
                    new ParameterizedTypeReference<List<Product>>() {}
            );

            List<Product> products = response.getBody();

            // In ra console để kiểm tra
            System.out.println("Danh sách sản phẩm:");
            for (Product product : products) {
                System.out.println(product);
            }

            model.addAttribute("products", products);
        } catch (Exception e) {
            System.err.println("Error occurred while fetching products: ");
            e.printStackTrace();
            model.addAttribute("error", "Không thể tải danh sách sản phẩm.");
        }

        return "layouts/product";
    }
}
