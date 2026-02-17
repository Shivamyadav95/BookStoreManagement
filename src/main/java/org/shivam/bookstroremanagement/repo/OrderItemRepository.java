package org.shivam.bookstroremanagement.repo;

import org.shivam.bookstroremanagement.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
