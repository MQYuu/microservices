package com.yuu.admin_service.controller;

import com.yuu.admin_service.entity.Product;
import com.yuu.admin_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products") // Đặt prefix cho API
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts(); // Trả về danh sách sản phẩm dạng JSON
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product); //Trả về sản phẩm nếu tìm thấy
        } else {
            return ResponseEntity.notFound().build(); //Trả về 404 nếu không tìm thấy
        }
    }

    
}
