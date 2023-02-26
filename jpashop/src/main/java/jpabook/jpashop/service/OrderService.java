package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId); // member Entity 조회
        Item item = itemRepository.findOne(itemId); // item Entity 조회

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); // 생성 메소드를 통해서 생성

        // OrderItem orderItem1 = new OrderItem(); 
        // 이런식의 생성자 사용 방법을 제한하고 싶을때에 OrderItem에 protected 생성자를 만들어두면 OrderItem()이 빨간줄이 뜨게됨 즉, JPA내에서 쓰지 말라는 뜻
        // OrderItem Class 상단에 @NoArgsConstructor(access = AccessLevel.PROTECTED) 사용 시 42행과 의미가 같음

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem); // 생성 메소드를 통해서 생성

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAll(OrderSearch);
//    }

}
