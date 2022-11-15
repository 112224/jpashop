package jpabook.jpashop.member;

import jpabook.jpashop.domain.Member;

public interface MemberService {
    /**
     * 회원 가입 하는 함수
     * @param member
     * @return memberId
     */
    Long join(Member member);

    Member find(Long id);
}
