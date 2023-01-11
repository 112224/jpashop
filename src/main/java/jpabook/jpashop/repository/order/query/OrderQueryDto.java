package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime dateTime;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long id, String name, LocalDateTime dateTime, OrderStatus orderStatus, Address address) {
        this.orderId = id;
        this.name = name;
        this.dateTime = dateTime;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
