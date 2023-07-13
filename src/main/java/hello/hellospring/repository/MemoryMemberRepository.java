package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // Map에 member save
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // key 값(id)을 생성
    @Override
    public Member save(Member member) {
        member.setId((++sequence));
        store.put(member.getId(), member); // store(member가 save되는 MAP)에 member 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // NULL일 가능성이 있으면 Optional로 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
