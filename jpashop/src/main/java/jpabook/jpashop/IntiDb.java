package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IntiDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setName("Jedong1");
            member.setAddress(new Address("오산시", "100", "10010"));
            em.persist(member);

            Book book = new Book();
            book.setName("JPA1");
            book.setPrice(10000);
            book.setStockQuantity(100);
            em.persist(book);

            Book book2 = new Book();
            book2.setName("JPA2");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            em.persist(orderItem1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            em.persist(orderItem2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            em.persist(delivery);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("Jedong2");
            member.setAddress(new Address("화성시", "183", "100010"));
            em.persist(member);

            Book book = new Book();
            book.setName("Mybatis1");
            book.setPrice(15000);
            book.setStockQuantity(50);
            em.persist(book);

            Book book2 = new Book();
            book2.setName("Mybatis2");
            book2.setPrice(25000);
            book2.setStockQuantity(50);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 15000, 1);
            em.persist(orderItem1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 25000, 1);
            em.persist(orderItem2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            em.persist(delivery);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

    }

}

