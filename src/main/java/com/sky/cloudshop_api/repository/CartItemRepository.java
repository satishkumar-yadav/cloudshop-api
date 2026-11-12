package com.sky.cloudshop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sky.cloudshop_api.model.CartItem;
import com.sky.cloudshop_api.model.Product;
import com.sky.cloudshop_api.model.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

}
