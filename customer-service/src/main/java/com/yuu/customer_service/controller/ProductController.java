package com.yuu.customer_service.controller;

import com.yuu.customer_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/listProduct")
    public String getProducts(Model model) {
        String apiUrl = "http://localhost:8080/api/products";

        try {
            // Sử dụng exchange để gửi request và nhận response
            ResponseEntity<List<Product>> response = restTemplate.exchange(
                    apiUrl, 
                    HttpMethod.GET, 
                    null, 
                    new ParameterizedTypeReference<List<Product>>() {}
            );

            List<Product> products = response.getBody();

            // In ra console để kiểm tra
            System.out.println("Danh sách sản phẩm:");
            for (Product product : products) {
                System.out.println(product);  // Hoặc dùng product.toString() nếu cần format riêng
            }

            model.addAttribute("products", products);
        } catch (Exception e) {
            // In chi tiết lỗi ra console
            System.err.println("Error occurred while fetching products: ");
            e.printStackTrace();  // In chi tiết lỗi lên console

            // Gửi thông báo lỗi vào model
            model.addAttribute("error", "Không thể tải danh sách sản phẩm.");
        }

        return "layouts/product";
    }
}
