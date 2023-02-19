package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")

    private  Long id;

    private String name;

    @Embedded // 내장 타입을 포함했다는 뜻
    private Address address;

    @OneToMany(mappedBy = "member") // 연관관계의 주인이 아님을 JPA에 알려줌 = Order Table에 있는 member fild에 의해서 매핑됬다는 뜻 / 읽기 전용
    private List<Order> orders = new ArrayList<>();

}
