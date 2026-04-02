package org.core.synergy.next30.domain.profile.repository;

import org.core.synergy.next30.domain.profile.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
