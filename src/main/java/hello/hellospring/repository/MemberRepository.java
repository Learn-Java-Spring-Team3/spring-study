package hello.hellospring.repository;

// 회원 객체를 저장하는 repository

import hello.hellospring.domain.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원 저장 -> 저장된 회원 반환
    Optional<Member> findById(Long id); // ID로 member를 찾음
    // Optional: 없으면 NULL인데, NULL을 반환할 때 Optional로 감싸서 반환
    Optional<Member> findByName(String name); // name으로 menber를 찾음
    List<Member> findAll(); // 전체 member 반환

}
