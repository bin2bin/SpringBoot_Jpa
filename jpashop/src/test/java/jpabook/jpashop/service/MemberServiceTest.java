package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // juit 실행할 때 스프링이랑 같이 엮어서 실행한다는 의미
@SpringBootTest // 스프링부트를 띄운 상태로 할때는 무조건 있어야 함 없으면 실패 함
@Transactional // @Transactional 걸고 Test를 하면 default가 RollBack의 True임
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //@Autowired EntityManager em; // rollback이 되어도 insert 쿼리를 보고 싶을 경우
    @Test
    // @Rollback(false) default : true임
    public void 회원가입() throws Exception{
        //given 
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        // 24행 작성 후 flush : em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName(("kim"));

        Member member2 = new Member();
        member2.setName(("kim"));

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다!!

        //then
        fail("예외가 발생해야 한다."); // fail : 코드가 돌다가 이쪽으로 오면 안되지만 오면 뭔가 잘못된거임
    }


}