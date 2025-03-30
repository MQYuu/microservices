package com.yuu.admin_service.entity;

import com.yuu.admin_service.validation.ValidField;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Kiểm tra tên sản phẩm, phải có ít nhất 3 ký tự, không chứa số
    @ValidField(minLength = 3, maxLength = 100, requireLetters = true, requireNumbers = false, allowNumbers = false)
    @Column(nullable = false)
    private String name;

    // Kiểm tra giá trị giá sản phẩm, phải là số dương
    @Column(nullable = false)
    private Double price;

    // Kiểm tra số lượng, phải là số nguyên dương
    @Column(nullable = false)
    private Integer quantity;

    // Kiểm tra đường dẫn hình ảnh, yêu cầu URL hợp lệ (tối thiểu 10 ký tự)
    @ValidField(minLength = 10, maxLength = 255, requireLetters = false, requireNumbers = false, allowNumbers = false)
    private String imageUrl;

    // Kiểm tra mô tả sản phẩm, phải có ít nhất 10 ký tự và chứa chữ cái
    @ValidField(minLength = 10, maxLength = 500, requireLetters = true, requireNumbers = false, allowNumbers = false)
    private String description;

    // Custom Validator cho price và quantity để đảm bảo chúng là số dương
    public boolean isValidPrice() {
        return price != null && price > 0;
    }

    public boolean isValidQuantity() {
        return quantity != null && quantity >= 0;
    }
}
