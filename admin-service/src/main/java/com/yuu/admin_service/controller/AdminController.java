package com.yuu.admin_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.yuu.admin_service.entity.Customer;
import com.yuu.admin_service.entity.Product;
import com.yuu.admin_service.service.CustomerService;
import com.yuu.admin_service.service.ProductService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/dashboard/admin/")  // các đường dẫn liên quan đến admin sẽ sử dụng /dashboard/admin làm tiền tố
public class AdminController {

    private final CustomerService customerService;
    private final ProductService productService;

    public AdminController(CustomerService customerService, ProductService productService) {
        this.customerService = customerService;
        this.productService = productService;
    }

    // Trang quản lý khách hàng
    @GetMapping("/all-customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "layouts/admin/all-customers";
    }

    // Trang quản lý sản phẩm
    @GetMapping("/all-products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "layouts/admin/all-products";
    }

    // Trang Dashboard admin
    @GetMapping("")
    public String adminDashboard() {
        return "layouts/admin/admin-dashboard";
    }

    // Thêm khách hàng
    @GetMapping("/customers/new")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "layouts/admin/add-customer";
    }

    @PostMapping("/customers")
    public String addCustomer(@Valid @ModelAttribute Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "layouts/admin/add-customer";  // Nếu có lỗi validation, trả lại form với thông báo lỗi
        }
        customerService.addCustomer(customer);
        return "redirect:/dashboard/admin/all-customers";
    }

    // Sửa khách hàng
    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "layouts/admin/edit-customer";
    }

    @PostMapping("/customers/update/{id}")
    public String updateCustomer(@PathVariable Integer id, @Valid @ModelAttribute Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "layouts/admin/edit-customer";  // Nếu có lỗi validation, trả lại form với thông báo lỗi
        }
        customer.setId(id);
        customerService.updateCustomer(customer);
        return "redirect:/dashboard/admin/all-customers";
    }

    // Xóa khách hàng
    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return "redirect:/dashboard/admin/all-customers";
    }

    // Thêm sản phẩm
    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "layouts/admin/add-product";
    }

    @PostMapping("/products/save")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "layouts/admin/add-product";  // Nếu có lỗi validation, trả lại form với thông báo lỗi
        }
        productService.addProduct(product);
        return "redirect:/dashboard/admin/all-products";
    }

    // Sửa sản phẩm
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "layouts/admin/edit-product";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Integer id, @Valid @ModelAttribute Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "layouts/admin/edit-product";  // Nếu có lỗi validation, trả lại form với thông báo lỗi
        }

        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/dashboard/admin/all-products";
    }

    // Xóa sản phẩm
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/dashboard/admin/all-products";
    }
}
