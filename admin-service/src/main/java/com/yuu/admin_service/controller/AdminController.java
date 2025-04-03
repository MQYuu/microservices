package com.yuu.admin_service.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yuu.admin_service.entity.Customer;
import com.yuu.admin_service.entity.Product;
import com.yuu.admin_service.service.CustomerService;
import com.yuu.admin_service.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/dashboard/admin/") // các đường dẫn liên quan đến admin sẽ sử dụng /dashboard/admin làm tiền tố
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
            return "layouts/admin/add-customer"; // Nếu có lỗi validation, trả lại form với thông báo lỗi
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
    public String updateCustomer(@PathVariable Integer id, @Valid @ModelAttribute Customer customer,
            BindingResult result) {
        if (result.hasErrors()) {
            return "layouts/admin/edit-customer"; // Nếu có lỗi validation, trả lại form với thông báo lỗi
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

    // @PostMapping("/products/save")
    // public String addProduct(@Valid @ModelAttribute Product product,
    // BindingResult result,
    // @RequestParam("imageFile") MultipartFile imageFile,
    // Model model) {
    // if (result.hasErrors()) {
    // return "layouts/admin/add-product"; // Trả về form nếu có lỗi validation
    // }

    // // Kiểm tra file nhận được
    // System.out.println("File received: " + imageFile.getOriginalFilename());
    // System.out.println("File size: " + imageFile.getSize());

    // // Kiểm tra nếu có file được upload
    // if (imageFile != null && !imageFile.isEmpty()) {
    // try {
    // String fileName = System.currentTimeMillis() + "_" +
    // imageFile.getOriginalFilename();
    // String uploadDir = "src/main/resources/static/uploads/";
    // Path uploadPath = Paths.get(uploadDir);

    // // Debug: Kiểm tra file nhận được
    // System.out.println("Received file: " + fileName);

    // // Nếu thư mục chưa tồn tại thì tạo mới
    // if (!Files.exists(uploadPath)) {
    // Files.createDirectories(uploadPath);
    // System.out.println("Created directory: " + uploadPath);
    // }

    // // Lưu file vào thư mục
    // Path filePath = uploadPath.resolve(fileName);
    // Files.copy(imageFile.getInputStream(), filePath,
    // StandardCopyOption.REPLACE_EXISTING);
    // System.out.println("File saved to: " + filePath.toString());

    // // Lưu đường dẫn vào database
    // product.setImageUrl("/uploads/" + fileName);
    // } catch (IOException e) {
    // e.printStackTrace();
    // model.addAttribute("errorMessage", "Lỗi khi tải ảnh lên!");
    // return "layouts/admin/add-product"; // Trả về form nếu lỗi
    // }
    // }

    // productService.addProduct(product);
    // return "redirect:/dashboard/admin/all-products";
    // }

    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute Product product,
            BindingResult result,
            @RequestParam("imageFile") MultipartFile file) {
        // Kiểm tra lỗi trong BindingResult
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong BindingResult. Trả lại form.");
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage())); // In chi tiết lỗi
            return "layouts/admin/add-product"; // Trả lại form nếu có lỗi
        }

        // Kiểm tra nếu file không rỗng
        if (!file.isEmpty()) {
            try {
                System.out.println("Bắt đầu xử lý hình ảnh...");

                // Tạo tên tệp mới để tránh xung đột (sử dụng timestamp và tên gốc)
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                System.out.println("Tên tệp sau khi xử lý: " + fileName);

                // Đường dẫn thư mục lưu hình ảnh
                Path path = Paths.get("src/main/resources/static/uploads", fileName);
                System.out.println("Lưu hình ảnh tại: " + path.toString());

                // Kiểm tra xem thư mục uploads đã tồn tại chưa, nếu chưa thì tạo mới
                File directory = new File("uploads");
                if (!directory.exists()) {
                    directory.mkdir();
                    System.out.println("Thư mục 'uploads' đã được tạo.");
                }

                // Lưu tệp vào hệ thống (ở thư mục uploads)
                Files.write(path, file.getBytes());
                System.out.println("Hình ảnh đã được lưu thành công.");

                // Lưu tên tệp vào thuộc tính imageUrl của sản phẩm (chỉ lưu tên tệp hoặc đường
                // dẫn)
                product.setImageUrl(fileName);
                System.out.println("Lưu đường dẫn hình ảnh vào sản phẩm: " + fileName);

            } catch (IOException e) {
                e.printStackTrace();
                result.rejectValue("imageUrl", "error.product", "Có lỗi khi tải lên hình ảnh.");
                System.out.println("Lỗi khi tải lên hình ảnh: " + e.getMessage());
                return "layouts/admin/add-product"; // Trả lại form nếu có lỗi
            }
        } else {
            System.out.println("Không có tệp hình ảnh được tải lên.");
        }

        // Lưu sản phẩm vào cơ sở dữ liệu
        System.out.println("Lưu sản phẩm vào cơ sở dữ liệu...");
        productService.addProduct(product);
        System.out.println("Sản phẩm đã được lưu thành công.");

        return "redirect:/dashboard/admin/all-products"; // Sau khi lưu, chuyển hướng đến danh sách sản phẩm
    }

    // Sửa sản phẩm
    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "layouts/admin/edit-product";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Integer id, @Valid @ModelAttribute Product product,
            BindingResult result) {
        if (result.hasErrors()) {
            return "layouts/admin/edit-product"; // Nếu có lỗi validation, trả lại form với thông báo lỗi
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
