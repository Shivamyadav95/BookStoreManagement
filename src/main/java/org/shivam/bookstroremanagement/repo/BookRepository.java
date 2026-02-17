package org.shivam.bookstroremanagement.repo;

import org.shivam.bookstroremanagement.model.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
    Page<Books> findAll(Pageable pageable);

    List<Books> findByTitleContainingIgnoreCase(String title);

    List<Books> findByAuthorContainingIgnoreCase(String author);
}