package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Getter
public class OrderDto {

    private Long orderId;
    private String name;

    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order o) {
        orderId = o.getId();
        name = o.getMember().getName();
        orderDate = o.getOrderDate();
        orderStatus = o.getStatus();
        address = o.getDelivery().getAddress();
        orderItems = o.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());

    }

}
