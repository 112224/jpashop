package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Album item = createAlbum(18000, 100, "사건의 지평선");
        int orderCount = 10;
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        //주문 상태 확인 -> 주문 시 정상이어야 함
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        //주문 품목 확인 -> 한 종류만 주문했으므로 1개
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        //주문 가격 확인 -> orderCount * price
        assertThat(getOrder.getTotalPrice()).isEqualTo(orderCount * 18000);
        //주문 후 재고 감소 확인
        assertThat(item.getStockQuantity()).isEqualTo(100 - orderCount);
    }



    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Album item = createAlbum(18000, 100, "사건의 지평선");

        int orderCount = 101;
        //when
        assertThrows(NotEnoughStockException.class, () ->
                orderService.order(member.getId(), item.getId(), orderCount)
        );


        //then

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Album album = createAlbum(10000, 10, "사건의 지평선");

        int orderCount = 8;
        Long orderId = orderService.order(member.getId(), album.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        assertThat(orderRepository.findOne(orderId).getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(album.getStockQuantity()).isEqualTo(10);
    }


    private Album createAlbum(int price, int stockQuantity, String name) {
        Album item = new Album();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        item.setArtist("윤하");
        em.persist(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "영등포로", "123-456"));
        em.persist(member);
        return member;
    }
}