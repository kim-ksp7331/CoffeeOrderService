package com.codestates.member;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    public Member createMember(Member member) {
        // TODO should business logic
        Member createdMember = member;
        System.out.println("MemberService.createMember");
        return createdMember;
    }

    public Member updateMember(Member member) {
        // TODO should business logic
        Member updatedMember = member;
        System.out.println("MemberService.updateMember");
        return updatedMember;
    }

    public Member findMember(long memberId) {
        // TODO should business logic
        if(memberId == 2) throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        Member member = new Member(memberId, "hgd@gmail.com", "홍길동", "010-1234-5678");
        System.out.println("MemberService.findMember");
        return member;
    }

    public List<Member> findMembers() {
        // TODO should business logic
        System.out.println("MemberService.findMembers");
        List<Member> members = List.of(
                new Member(1, "hgd@gmail.com", "홍길동", "010-1234-5678"),
                new Member(2, "lml@gmail.com", "이몽룡", "010-1111-2222")
        );
        return members;
    }

    public void deleteMember(long memberId) {
        // TODO should business logic
        System.out.println("MemberService.deleteMember");
    }

}
