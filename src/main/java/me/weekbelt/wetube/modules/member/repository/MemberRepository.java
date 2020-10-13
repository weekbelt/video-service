package me.weekbelt.wetube.modules.member.repository;

import me.weekbelt.wetube.modules.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
