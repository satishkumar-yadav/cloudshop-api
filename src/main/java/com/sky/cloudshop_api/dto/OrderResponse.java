package com.sky.cloudshop_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.sky.cloudshop_api.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
}
