package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded // 내장타입이기에 사용
    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL을 사용 시 READY, COMP 중간에 뭐가 들어간다고 헀을때 숫자가 다 꼬임 그래서 사용 X
    private DeliveryStatus status; //READY : 배송준비, COMP : 배송


}
