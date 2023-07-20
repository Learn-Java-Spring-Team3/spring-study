package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest { // 다른 데서 가져다 쓸 게 아니므로 public이 아니어도 됨
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // test가 끝날 때마다 repository를 깔끔하게 지워주는 코드
    @AfterEach // 동작이 끝날 때마다 수행한다는 의미
    public void afterEach(){
        repository.clearStore();
    }

    @Test // save() test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // optional에서 값을 꺼낼 때 get 사용 가능
        System.out.println("result = " + (result == member));
        Assertions.assertEquals(result, member); // result와 member가 같은지 확인(1) (다르면 에러)
        assertThat(member).isEqualTo(result);// result와 member가 같은지 확인(2) (다르면 에러)
    }

    @Test // findByName() test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1); // 다르면 에러
    }

    @Test // findAll() test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2); // member 수와 일치하지 않으면 에러
    }
}
