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
        return "layouts/menu"; // Trang menu
    }

    @GetMapping("/promo")
    public String promo() {
        return "layouts/promo"; // Trang khuyến mãi
    }

    @GetMapping("/product")
    public String product() {
        return "layouts/product"; // Trang sản phẩm
    }

    @GetMapping("/order")
    public String order() {
        return "layouts/order"; // Trang đặt hàng
    }
}
