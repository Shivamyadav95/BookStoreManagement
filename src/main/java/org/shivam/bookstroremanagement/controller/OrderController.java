package org.shivam.bookstroremanagement.controller;

import org.shivam.bookstroremanagement.DTO.OrderDTO;
import org.shivam.bookstroremanagement.model.Order;
import org.shivam.bookstroremanagement.model.OrderItem;
import org.shivam.bookstroremanagement.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // PLACE ORDER
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{userId}")
    public OrderDTO placeOrder(@PathVariable Long userId,
                               @RequestBody List<OrderItem> items) {
        return orderService.placeOrder(userId, items);
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDTO updateStatus(@PathVariable Long id,
                                 @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Order> getOrders(Pageable pageable) {
        return orderService.getOrders(pageable);
    }
    }
