package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {
        Member member = new Member();
        member.setName("you");
        member.setAddress(new Address("오산시", "문시로 183-19", "111동101호"));
        em.persist(member);

        Item book = getBook("JPA 알아보기", 10000, 10);


        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품수 체크 1", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량", book.getPrice() * 2, getOrder.totalPrice());
        Assert.assertEquals("주문한 만큼 재고가 줄어야함", 8, book.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        Member member = getMember();
        Book book = getBook("JPA 알아보기", 10000, 1);

        int orderCnt = 2;

        orderService.order(member.getId(), book.getId(), orderCnt);

        Assert.fail("재고 수량 초과 예외가 발생해야한다.");

    }

    @Test
    public void 상품취소() throws Exception {
        Member member = getMember();
        Book book = getBook("JPA 알아보기", 10000, 10);
        int orderCnt = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCnt);

        orderService.cancelOrder(orderId);

        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 상태 cancel", OrderStatus.CANCEL, getOrder.getStatus());
        Assert.assertEquals("재고 수량 올라가야함", 10, book.getStockQuantity());



    }


    private Book getBook(String name, int price, int quantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("you");
        member.setAddress(new Address("오산시", "문시로 183-19", "111동101호"));
        em.persist(member);
        return member;
    }
}