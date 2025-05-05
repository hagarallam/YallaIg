package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    Page<Order> findAllByCreatedBy(Integer userId, Pageable pageable);
}
