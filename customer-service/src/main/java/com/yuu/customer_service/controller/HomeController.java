package com.yuu.customer_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // Trang chủ
    }

    @GetMapping("/menu")
    public String menu() {
        return "layouts/menu";  
    }

    @GetMapping("/promo")
    public String promo() {
        return "layouts/promo"; // Trang khuyến mãi
    }

    @GetMapping("/order")
    public String order() {
        return "layouts/order"; 
    }

    @GetMapping("/layout")
    public String layout() {
        return "layout"; 
    }
}
