package com.team5.first_project.member.repository;

import com.team5.first_project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndIsDeletedFalse(String email);
    Optional<Member> findByIdAndIsDeletedFalse(long id);
    boolean existsByNickNameAndIsDeletedFalse(String nickName);
    boolean existsByEmailAndIsDeletedFalse(String email);
}
