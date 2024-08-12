package jpabook.jpashop.repository.order.simplequery;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<OrderSimpleQueryDto> orderV2() {
        List<Order> all = orderRepository.findAll();
        List<OrderSimpleQueryDto> simpleOrderList = new ArrayList<>();
        for (Order order : all) {
            OrderSimpleQueryDto simpleOrderDto = new OrderSimpleQueryDto();
            simpleOrderDto.setOrderId(order.getId());
            simpleOrderDto.setName(order.getMember().getName());
            simpleOrderDto.setOrderDate(order.getOrderDate());
            simpleOrderDto.setAddress(order.getDelivery().getAddress());
            simpleOrderList.add(simpleOrderDto);
        }

        return simpleOrderList;

    }

    /*
    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();

        List<OrderSimpleQueryDto> resultList =
                orders.stream()
                .map(o -> new OrderSimpleQueryDto(o))
                .collect(Collectors.toList());

        return  resultList;
    }
    */

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }


}
