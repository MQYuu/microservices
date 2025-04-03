package com.yuu.customer_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("body", "index");

        return "layout"; // Trang chủ
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        // Thêm thuộc tính "body" vào model, giá trị là trang con menu
        model.addAttribute("body", "layouts/menu");
        return "layout";  // Trả về layout chính, Thymeleaf sẽ sử dụng trang layout để hiển thị nội dung
    }

    @GetMapping("/promo")
    public String promo(Model model) {
        model.addAttribute("body", "layouts/promo");
        return "layout"; // Trang khuyến mãi
    }

    @GetMapping("/order")
    public String order(Model model) {
        model.addAttribute("body", "layouts/order");
        return "layout"; 
    }
}
