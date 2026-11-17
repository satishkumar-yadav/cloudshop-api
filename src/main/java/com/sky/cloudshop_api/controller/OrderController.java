package com.sky.cloudshop_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.cloudshop_api.dto.OrderResponse;
import com.sky.cloudshop_api.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-User-ID") String userId) {
        // OrderResponse order = orderService.createOrder(userId);
        // return new ResponseEntity<OrderResponse>(order, HttpStatus.CREATED);
        return orderService.createOrder(userId)
                .map(orderReponse -> new ResponseEntity<>(orderReponse, HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
