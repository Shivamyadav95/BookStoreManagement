package org.shivam.bookstroremanagement.DTO;

import lombok.*;
import org.shivam.bookstroremanagement.DTO.OrderItemDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;
    private String userName;
    private String userEmail;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    private List<OrderItemDTO> items;
}