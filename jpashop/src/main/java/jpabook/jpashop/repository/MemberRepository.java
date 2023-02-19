package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    // @PersistenceContext : 해당 어노테이션은 Spring이 EntityManager를 만들어서 주입을 시켜줌
    //                     : 13 행의 @RequiredArgsConstructor 를 사용하면서 @PersistenceContext 생략 가능
    private final EntityManager em;

    public void  save(Member member) {
        em.persist(member); // 영속석컨테스트에 member를 넣음
    }

    public Member findOne(Long id) { // Member 를 찾아서 단건을 반환 해주는 메서드
        return em.find(Member.class, id); // em.find( Type, PK )
    }

    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class) // JPQL은 객체를 대상으로 쿼리를 날림
                .getResultList();
    }

    public List<Member> findByName(String name) { // 회원조회 이름에 의해서 조회 하는 메서드
        return  em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
