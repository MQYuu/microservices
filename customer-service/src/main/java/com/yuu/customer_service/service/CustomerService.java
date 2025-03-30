package com.yuu.customer_service.service;

import org.springframework.stereotype.Service;

import com.yuu.customer_service.entity.Customer;
import com.yuu.customer_service.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

// Class chứa các phương thức để xử lý với DB
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    // Thêm khách hàng mới
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Lấy thông tin khách hàng theo ID
    public Customer getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null); // Trả về null nếu không tìm thấy
    }

    // Cập nhật thông tin khách hàng
    public Customer updateCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            return customerRepository.save(customer);
        }
        return null; // Trả về null nếu không tìm thấy khách hàng để cập nhật
    }

    // Xóa khách hàng theo ID
    public boolean deleteCustomer(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false; // Trả về false nếu không tìm thấy khách hàng để xóa
    }
}
