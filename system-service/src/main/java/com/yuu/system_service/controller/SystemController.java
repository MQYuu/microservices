package com.example.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.restaurant.entity.Authority;
import com.example.restaurant.entity.User;
import com.example.restaurant.service.UserService;
import com.example.restaurant.service.AuthorityService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/dashboard/system") // Định nghĩa đường dẫn chung cho các request thuộc System
public class SystemController {

    private final UserService userService;
    private final AuthorityService authorityService;

    public SystemController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    // Hiển thị trang Dashboard của System
    @GetMapping("/")
    public String systemDashboard() {
        return "layouts/system/system-dashboard";
    }

    // Lấy danh sách tất cả Users và hiển thị lên trang Users
    @GetMapping("/all-users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "layouts/system/all-users";
    }

    // Hiển thị form thêm User mới
    @GetMapping("/users/new")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "layouts/system/add-user";
    }

    // Xử lý thêm User mới vào hệ thống
    @PostMapping("/users")
    public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "layouts/system/add-user";
        }
        userService.addUser(user);
        return "redirect:/dashboard/system/all-users";
    }

    // Hiển thị form sửa thông tin User dựa theo ID
    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Integer id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/dashboard/system/all-users";
        }
        model.addAttribute("user", user);
        return "layouts/system/edit-user";
    }

    // Xử lý cập nhật thông tin User
    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id, @Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Nếu có lỗi validation, trả về lại trang chỉnh sửa với lỗi
            model.addAttribute("user", user);
            return "layouts/system/edit-user";  // Trả về trang chỉnh sửa User với lỗi
        }
        user.setId(id);
        userService.updateUser(id, user);
        return "redirect:/dashboard/system/all-users";
    }

    // Xóa User khỏi hệ thống
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/dashboard/system/all-users";
    }

    // Hiển thị danh sách quyền của một User theo userId
    @GetMapping("/authorities/{userId}")
    public String userAuthorities(@PathVariable Integer userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("authorities", authorityService.getAuthoritiesByUserId(userId));
        return "layouts/system/authorities";
    }

    // Lấy danh sách tất cả quyền của tất cả Users
    @GetMapping("/all-authorities")
    public String getAllAuthorities(Model model) {
        List<Authority> authorities = authorityService.getAllAuthorities();
        model.addAttribute("authorities", authorities);
        return "layouts/system/all-authorities";
    }

    // Hiển thị form thêm quyền cho User
    @GetMapping("/authorities/new")
    public String showAddAuthorityForm(Model model) {
        model.addAttribute("users", userService.getAllUsers()); // Lấy danh sách Users để chọn
        model.addAttribute("authority", new Authority());
        return "layouts/system/add-authorities";
    }

    // Xử lý thêm quyền mới cho User
    @PostMapping("/authorities")
    public String addAuthority(@RequestParam Integer userId, @RequestParam String role) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/dashboard/system/all-authorities";
        }

        Authority authority = new Authority();
        authority.setUser(user); // Gán User vào quyền
        authority.setRole(role);
        authorityService.addAuthority(authority);

        return "redirect:/dashboard/system/all-authorities";
    }

    // Hiển thị form chỉnh sửa quyền
    @GetMapping("/authorities/edit/{id}")
    public String showEditAuthorityForm(@PathVariable Integer id, Model model) {
        Authority authority = authorityService.getAuthorityById(id);
        if (authority == null) {
            return "redirect:/dashboard/system/all-authorities";
        }
        model.addAttribute("authority", authority);
        return "layouts/system/edit-authority"; // Trả về trang chỉnh sửa quyền
    }

    // Cập nhật quyền
    @PostMapping("/authorities/update")
    public String updateAuthority(@RequestParam Integer id, @RequestParam String role) {
        authorityService.updateAuthority(id, role);
        return "redirect:/dashboard/system/all-authorities";
    }

    // Xóa quyền của một User theo authorityId và userId
    @DeleteMapping("/authorities/delete/{id}")
    public String deleteAuthority(@PathVariable Integer id) {
        authorityService.removeAuthority(id);
        return "redirect:/dashboard/system/all-authorities";
    }

}