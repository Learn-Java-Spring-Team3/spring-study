package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach // 각 테스트를 사용하기 전에 같은 메모리 레포지토리를 사용하게 함
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 동작이 끝날 때마다 수행한다는 의미
    public void afterEach(){
        memberRepository.clearStore();
    }
    @Test
    void 회원가입() { // test code는 한글로 적어도 됨
        // given
        Member member = new Member();
        member.setName(("hello"));

        // when
        Long saveId = memberService.join(member);


        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){ // 이름이 중복되는 회원이 가입하려고 할 때 예외 처리가 정상적으로 수행되는지 확인
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring"); // member 이름이 중복됨

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));// A -> B: B가 실행되면 A가 터져야 함

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 중복 회원이 발생했을 때 이 메시지가 정상적으로 발생하는지 확인


        // then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}