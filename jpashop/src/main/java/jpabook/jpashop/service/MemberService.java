package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // Spring 제공 @Transactional이 쓸 수 있는게 많아서 좋음
@RequiredArgsConstructor // final만 있는 fild를 가지고 생성자를 만들어줌 20 ~ 22행 생략 가능
public class MemberService {

    private final MemberRepository memberRepository; // final 을 넣으면 컴파일 시점에 체킹 가능

//    public MemberService(MemberRepository memberRepository) { // 생성자 주입 방법
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) { // 단건 조회
        return memberRepository.findOne(memberId);
    }
}
