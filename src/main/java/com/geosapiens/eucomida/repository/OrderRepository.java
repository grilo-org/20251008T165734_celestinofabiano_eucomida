package com.geosapiens.eucomida.repository;

import com.geosapiens.eucomida.entity.Order;
import com.geosapiens.eucomida.entity.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByStatusAndUserId(OrderStatus status, UUID userId);
    
    List<Order> findByUserId(UUID userId);

    Optional<Order> findByIdAndUserId(UUID id, UUID userId);
}
