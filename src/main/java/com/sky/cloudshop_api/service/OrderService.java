package com.sky.cloudshop_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sky.cloudshop_api.dto.OrderItemDTO;
import com.sky.cloudshop_api.dto.OrderResponse;
import com.sky.cloudshop_api.model.CartItem;
import com.sky.cloudshop_api.model.Order;
import com.sky.cloudshop_api.model.OrderItem;
import com.sky.cloudshop_api.model.OrderStatus;
import com.sky.cloudshop_api.model.User;
import com.sky.cloudshop_api.repository.OrderRepository;
import com.sky.cloudshop_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public Optional<OrderResponse> createOrder(String userId) {
        // validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        // validate for users
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();

        // calculate total price
        BigDecimal totalPrice = cartItems.stream() // not using for loop // stream<cartItem>
                .map(CartItem::getPrice) // stream<BigDecimal>
                .reduce(BigDecimal.ZERO, BigDecimal::add); // combining

        // create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null, item.getProduct(), item.getQuantity(), item.getPrice(), order))
                .collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))))
                        .toList(),
                order.getCreatedAt());
    }

}
