package com.yuu.customer_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.yuu.customer_service.entity.Product;
import com.yuu.customer_service.service.ProductService;

import org.springframework.ui.Model;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listProduct")
    public String showProductPage(Model model) {
        // Lấy danh sách sản phẩm từ service
        List<Product> products = productService.getAllProducts();

        // Thêm danh sách sản phẩm vào model
        model.addAttribute("products", products);

        return "layouts/product";
    }

        // Hiển thị chi tiết sản phẩm
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            // Xử lý khi không tìm thấy sản phẩm
            return "redirect:/listProduct";
        }
        model.addAttribute("product", product);
        return "layouts/product-detail"; 
    }
}

