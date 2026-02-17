package org.shivam.bookstroremanagement.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double price;
}