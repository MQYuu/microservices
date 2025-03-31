package com.yuu.customer_service.controller;

import com.yuu.customer_service.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private RestTemplate restTemplate; // Inject RestTemplate để gọi API
    String apiUrl = "http://localhost:8080/api/products"; // URL của API

    @GetMapping("/listProduct")
    public String getProducts(Model model) {

        try {
            // Gọi API GET
            ResponseEntity<List<Product>> response = restTemplate.getForEntity(apiUrl,
                    (Class<List<Product>>) (Object) List.class);

            List<Product> products = response.getBody(); // Lấy danh sách sản phẩm từ API

            model.addAttribute("products", products); // Gửi danh sách sản phẩm đến giao diện
        } catch (Exception e) {
            // 🔥 Nếu có lỗi khi gọi API, hiển thị thông báo lỗi trên giao diện
            System.err.println("Error occurred while fetching products: ");
            e.printStackTrace();
            model.addAttribute("error", "Không thể tải danh sách sản phẩm.");
        }

        return "layouts/product"; // Trả về trang hiển thị sản phẩm
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable("id") Integer id, Model model) {
        try {
            // Gọi API lấy sản phẩm từ admin service
            ResponseEntity<Product> response = restTemplate.getForEntity(apiUrl + "/" + id, Product.class);
            System.out.println("API URL: " + apiUrl + "/" + id);
            System.out.println("API Response: " + response.getStatusCode());

            Product product = response.getBody();
            System.out.println("Product Data: " + product);

            if (product != null) {
                model.addAttribute("product", product);
            } else {
                model.addAttribute("error", "Sản phẩm không tồn tại.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API lấy sản phẩm:");
            e.printStackTrace();
            model.addAttribute("error", "Không thể tải thông tin sản phẩm.");
        }

        return "layouts/product-detail";
    }

}
