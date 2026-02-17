package org.shivam.bookstroremanagement.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private Double price;
    private String description;
    private Integer stockQuantity;
    private String imageUrl;
}