package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAll();
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> all = orderRepository.findAll();
        List<SimpleOrderDto> simpleOrderList = new ArrayList<>();
        for (Order order : all) {
            SimpleOrderDto simpleOrderDto = new SimpleOrderDto();
            simpleOrderDto.setOrderId(order.getId());
            simpleOrderDto.setName(order.getMember().getName());
            simpleOrderDto.setOrderDate(order.getOrderDate());
            simpleOrderDto.setAddress(order.getMember().getAddress());
            simpleOrderList.add(simpleOrderDto);
        }

        return simpleOrderList;

    }

    @Data
    static class SimpleOrderDto {

        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
    }
}
