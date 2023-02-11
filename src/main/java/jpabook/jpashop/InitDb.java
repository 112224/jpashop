package jpabook.jpashop;

import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.member.Address;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = Book.createBook("JPA1", 10000, 100, "yh", "11");
            em.persist(book1);

            Book book2 = Book.createBook("JPA2", 20000, 100, "yh", "22");
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem1 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem, orderItem1);
            em.persist(order);

        }

        public void dbInit2() {
            Member member = createMember("userB", "인천", "2", "2222");
            em.persist(member);

            Book book1 = Book.createBook("Spring1", 30000, 100, "yh", "33");
            em.persist(book1);
            Book book2 = Book.createBook("Spring2", 40000, 100, "yh", "44");
            em.persist(book2);

            OrderItem orderItem = OrderItem.createOrderItem(book1, 30000, 3);
            OrderItem orderItem1 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Order order = Order.createOrder(member, delivery, orderItem, orderItem1);
            em.persist(order);
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }

}
