package org.shivam.bookstroremanagement.service;


import org.springframework.data.domain.Pageable;
import org.shivam.bookstroremanagement.DTO.OrderDTO;
import org.shivam.bookstroremanagement.DTO.OrderItemDTO;
import org.shivam.bookstroremanagement.exception.ResourceNotFoundException;
import org.shivam.bookstroremanagement.model.Books;
import org.shivam.bookstroremanagement.model.Order;
import org.shivam.bookstroremanagement.model.OrderItem;
import org.shivam.bookstroremanagement.model.User;
import org.shivam.bookstroremanagement.repo.BookRepository;
import org.shivam.bookstroremanagement.repo.OrderRepository;
import org.shivam.bookstroremanagement.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        BookRepository bookRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // PLACE ORDER
    public OrderDTO placeOrder(Long userId, List<OrderItem> items) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        double totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItem item : items) {

            Books book = bookRepository.findById(item.getBook().getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Book not found"));

            if (book.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for book: " + book.getTitle());
            }

            book.setStockQuantity(
                    book.getStockQuantity() - item.getQuantity());
            bookRepository.save(book);

            OrderItem orderItem = new OrderItem();
            orderItem.setBook(book);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(book.getPrice());
            orderItem.setOrder(order);

            totalAmount += book.getPrice() * item.getQuantity();
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    // GET ALL ORDERS
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // GET ORDER BY ID
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return convertToDTO(order);
    }

    // UPDATE STATUS (ADMIN)
    public OrderDTO updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        order.setStatus(status);

        Order updated = orderRepository.save(order);

        return convertToDTO(updated);
    }

    // PAGINATION
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    // ENTITY → DTO
    private OrderDTO convertToDTO(Order order) {

        List<OrderItemDTO> itemDTOs = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemDTO(
                        item.getBook().getId(),
                        item.getBook().getTitle(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderDTO(
                order.getId(),
                order.getUser().getName(),
                order.getUser().getEmail(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderDate(),
                itemDTOs
        );
    }
}