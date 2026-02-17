package org.shivam.bookstroremanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String author;

    private String genre;

    @Column(unique = true)
    private String isbn;



    @NotNull
    @Positive
    @Column(nullable = false)
    private Double price;

    @Column(length = 2000)
    private String description;

    @Min(0)
    private Integer stockQuantity;

    private String imageUrl;







}
